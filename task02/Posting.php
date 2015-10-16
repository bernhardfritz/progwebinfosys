<?php
  class Posting {
    private $id;
    private $author;
    private $title;
    private $text;
    private $keywords;
    private $created;
    private $updated;

    public function __construct($id, $author, $title, $text, $keywords, $created, $updated) {
      $this->id = $id;
      $this->author = $author;
      $this->title = $title;
      $this->text = $text;
      $this->keywords = $keywords;
      $this->created = $created;
      $this->updated = $updated;
    }

    public function getId() {
      return $this->id;
    }

    public function getAuthor() {
      return $this->author;
    }

    public function getTitle() {
      return $this->title;
    }

    public function setTitle($title) {
      $this->title = $title;
    }

    public function getText() {
      return $this->text;
    }

    public function setText($text) {
      $this->text = $text;
    }

    public function getKeywords() {
      return $this->keywords;
    }

    public function setKeywords($keywords) {
      $this->keywords = $keywords;
    }

    public function getCreated() {
      return $this->created;
    }

    public function getUpdated() {
      return $this->updated;
    }

    public function setUpdated($updated) {
      $this->updated = $updated;
    }
  }
?>
