Wir verwenden MySQL zur Persistierung der Daten.
/sql/init.sql muss einmalig ausgeführt werden um volle Funktionsfähigkeit zu ermöglichen.

Default-User:
  Username: admin
  Password: secret

  Username: author
  Password: password

  Username: user
  Password: password

  Username: guest
  Password: password

Abrufen aller Blog-Einträge:
GET /posts
Anlegen eines Blog-Eintrags:
POST /createPost
Löschen eines Blog-Eintrags:
DELETE /post/postId
Verändern eines Blog-Eintrags
PUT /post/postId
Abrufen aller Kommentare zu einem Blog-Eintrag
GET /post/postId/comments
Anlegen eines neuen Kommentars zu einem Blog-Eintrag:
POST /post/postId/createComment
Löschen eines Kommentares
DELETE /post/postId/comment/commentId
Verändern eines Kommentars
PUT /post/postId/comment/commentId
