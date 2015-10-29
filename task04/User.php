<?php
  class User {
    private $id;
    private $username;
    private $password;
    private $readPost;
    private $writePost;
    private $deletePost;
    private $readComment;
    private $writeComment;
    private $deleteComment;

    public function __construct($id, $username, $password, $readPost, $writePost, $deletePost, $readComment, $writeComment, $deleteComment) {
      $this->id = $id;
      $this->username = $username;
      $this->password = $password;
      $this->readPost = $readPost;
      $this->writePost = $writePost;
      $this->deletePost = $deletePost;
      $this->readComment = $readComment;
      $this->writeComment = $writeComment;
      $this->deleteComment = $deleteComment;
    }

    public function getId() {
      return $this->id;
    }

    public function getUsername() {
      return $this->username;
    }

    public function getPassword() {
      return $this->password;
    }

    public function setPassword($password) {
      $this->password = $password;
    }

    public function getReadPost() {
      return $this->readPost;
    }

    public function getWritePost() {
      return $this->writePost;
    }

    public function getDeletePost() {
      return $this->deletePost;
    }

    public function getReadComment() {
      return $this->readComment;
    }

    public function getWriteComment() {
      return $this->writeComment;
    }

    public function getDeleteComment() {
      return $this->deleteComment;
    }
  }
?>
