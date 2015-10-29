<?php
  include 'Posting.php';
  include 'User.php';
  include 'Comment.php';

  class DbManager {
    private $servername;
    private $username;
    private $password;
    private $dbname;

    private $conn;

    public function __construct() {
      $this->servername = "localhost";
      $this->username = "myBlogUser";
      $this->password = "blog";
      $this->dbname = "MyBlog";

      $this->connect();
    }


// ++++++++++++++++++++ Comment functions ++++++++++++++++++++

    public function createComment($idPosting, $idUser, $text) {
      $stmt = $this->conn->prepare("INSERT INTO Comment (idPosting, idUser, text, created) VALUES (?, ?, ?, ?);");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('iiss', $idPosting, $idUser, strip_tags($text), $created);

      $created = date('Y-m-d H:i:s', time());
      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }

    public function deleteComment($id) {
      $stmt = $this->conn->prepare("DELETE FROM Comment WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }

    public function getComments($idPosting) {
      $stmt = $this->conn->prepare("SELECT * FROM Comment WHERE idPosting = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('i', $idPosting);

      $stmt->execute();
      $stmt->store_result();

      $array = array();
      if ($stmt->num_rows > 0) {
        $stmt->bind_result($id, $idPosting, $idUser, $text, $created);
        while($stmt->fetch()) {
          $posting = $this->getPosting($idPosting);
          $user = $this->getUser($idUser);
          $comment = new Comment($id, $posting, $user, $text, $created);
          array_push($array, $comment);
        }
      }

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
      return $array;
    }


// ++++++++++++++++++++ DB functions ++++++++++++++++++++

    private function connect() {
      $this->conn = new mysqli($this->servername, $this->username, $this->password, $this->dbname);

      if ($this->conn->connect_error) {
          die("Connection failed: " . $this->conn->connect_error);
      }
    }

    public function disconnect() {
      $this->conn->close();
    }


// ++++++++++++++++++++ Posting functions ++++++++++++++++++++

    public function createPosting($idUser, $title, $text, $keywords) {
      $stmt = $this->conn->prepare("INSERT INTO Posting (idUser, title, text, keywords, created, updated) VALUES (?, ?, ?, ?, ?, ?);");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('ssssss', $idUser, strip_tags($title), strip_tags($text), strip_tags($keywords), $created, $updated);

      $created = date('Y-m-d H:i:s', time());
      $updated = date('Y-m-d H:i:s', time());
      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }

    public function deletePosting($id) {
      $stmt = $this->conn->prepare("DELETE FROM Posting WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }

    public function getAllPostings() {
      $array = array();

      $sql = "SELECT * FROM Posting";
      $result = $this->conn->query($sql);

      if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
          $posting = new Posting($row["id"], $this->getUser($row["idUser"]), $row["title"], $row["text"], $row["keywords"], $row["created"], $row["updated"]);
          array_push($array, $posting);
        }
      }

      return $array;
    }

    public function getPosting($id) {
      $stmt = $this->conn->prepare("SELECT * FROM Posting where id = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();
      $stmt->store_result();

      $posting = NULL;
      if ($stmt->num_rows == 1) {
        $stmt->bind_result($id, $idUser, $title, $text, $keywords, $created, $updated);
        $stmt->fetch();
        $user = $this->getUser($idUser);
        $posting = new Posting($id, $user, $title, $text, $keywords, $created, $updated);
      }

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
      return $posting;
    }

    public function savePosting($id, $title, $text, $keywords) {
      $stmt = $this->conn->prepare("UPDATE Posting SET title = ?, text = ?, keywords = ?, updated = ? WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('ssssi', strip_tags($title), strip_tags($text), strip_tags($keywords), $updated, $id);

      $updated = date('Y-m-d H:i:s', time());
      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }


// ++++++++++++++++++++ User functions ++++++++++++++++++++

    public function getUser($id) {
      $stmt = $this->conn->prepare("SELECT * FROM User WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();
      $stmt->store_result();

      $user = NULL;
      if ($stmt->num_rows == 1) {
        $stmt->bind_result($id, $username, $password, $readPost, $writePost, $deletePost, $readComment, $writeComment, $deleteComment);
        $stmt->fetch();
        $user = new User($id, $username, $password, $readPost, $writePost, $deletePost, $readComment, $writeComment, $deleteComment);
      }

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
      return $user;
    }

    public function login($username, $password) {
      $stmt = $this->conn->prepare("SELECT * FROM User WHERE username = ? AND password = ?;");
      if ($stmt == false) {
        echo "Error: " . $stmt->error;
      }

      $stmt->bind_param('ss', $username, $password);

      $stmt->execute();
      $stmt->store_result();

      $user = NULL;
      if ($stmt->num_rows == 1) {
        $stmt->bind_result($id, $username, $password, $readPost, $writePost, $deletePost, $readComment, $writeComment, $deleteComment);
        $stmt->fetch();
        $user = new User($id, $username, $password, $readPost, $writePost, $deletePost, $readComment, $writeComment, $deleteComment);
      }

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
      return $user;
    }
  }
?>
