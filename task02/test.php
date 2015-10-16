<?php
  include 'DbManager.php';

  $dbManager = new DbManager();
  #$dbManager->createPosting('Autor', 'Titel', 'Text', 'Keywords');
  #$p = $dbManager->getPosting(4);
  #echo $p->getAuthor();
  #echo $dbManager->getAllPostings()[0]->getTitle();
  #$p->setTitle('Titel_Neu');
  #$p->setText('Text_Neu');
  #$p->setKeywords('Keywords_Neu');
  #$dbManager->savePosting($p);
  #$dbManager->deletePosting(6);
  $dbManager->disconnect();
?>
