<?php
  class Comment {
    private $id;
    private $posting;
    private $user;
    private $text;
    private $created;

    public function __construct($id, $posting, $user, $text, $created) {
      $this->id = $id;
      $this->posting = $posting;
      $this->user = $user;
      $this->text = $text;
      $this->created = $created;
    }

    public function getId() {
      return $this->id;
    }

    public function getPosting() {
      return $this->posting;
    }

    public function getUser() {
      return $this->user;
    }

    public function getText() {
      return $this->text;
    }

    public function getCreated() {
      return $this->created;
    }
  }
?>
