<?php
  include 'Posting.php';

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

    private function connect() {
      $this->conn = new mysqli($this->servername, $this->username, $this->password, $this->dbname);

      if ($this->conn->connect_error) {
          die("Connection failed: " . $this->conn->connect_error);
      }
    }

    public function disconnect() {
      $this->conn->close();
    }

    public function createPosting($author, $title, $text, $keywords) {
      $stmt = $this->conn->prepare("INSERT INTO Posting (author, title, text, keywords, created, updated) VALUES (?, ?, ?, ?, ?, ?);");
      if ($stmt == false) {
        echo "Error: " . $sql->error;
      }

      $stmt->bind_param('ssssss', $author, $title, $text, $keywords, $created, $updated);

      $created = date('Y-m-d H:i:s', time());
      $updated = date('Y-m-d H:i:s', time());
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
          $posting = new Posting($row["id"], $row["author"], $row["title"], $row["text"], $row["keywords"], $row["created"], $row["updated"]);
          array_push($array, $posting);
        }
      }

      return $array;
    }

    public function getPosting($id) {
      $stmt = $this->conn->prepare("SELECT * FROM Posting where id = ?");
      if ($stmt == false) {
        echo "Error: " . $sql->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();
      $stmt->store_result();

      $posting = NULL;
      if ($stmt->num_rows == 1) {
        $stmt->bind_result($id, $author, $title, $text, $keywords, $created, $updated);
        $stmt->fetch();
        $posting = new Posting($id, $author, $title, $text, $keywords, $created, $updated);
      }

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
      return $posting;
    }

    public function savePosting($id, $title, $text, $keywords) {
      $stmt = $this->conn->prepare("UPDATE Posting SET title = ?, text = ?, keywords = ? WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $sql->error;
      }

      $stmt->bind_param('sssi', $title, $text, $keywords, $id);

      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }

    public function deletePosting($id) {
      $stmt = $this->conn->prepare("DELETE FROM Posting WHERE id = ?;");
      if ($stmt == false) {
        echo "Error: " . $sql->error;
      }

      $stmt->bind_param('i', $id);

      $stmt->execute();

      if ($stmt->errno) {
        echo "Error: " . $stmt->error;
      }

      $stmt->close();
    }
  }
?>
