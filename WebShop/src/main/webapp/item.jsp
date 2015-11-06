<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<jsp:include page="header.jsp" />
</head>

<body>
	<jsp:include page="navbar.jsp" />
	<%
		long itemId = 1L;
		if(request.getParameter("itemId") != null) {
			itemId = Long.valueOf(request.getParameter("itemId"));
		}
		Item item = DBManager.getInstance().getItem(itemId);
		List<ItemComment> comments = DBManager.getInstance().getItemComments(itemId);
	%>
    <div class="container">
      	<div class="thumbnail">
           <img class="img-responsive" src="http://placehold.it/800x300" alt="">
           <div class="caption-full">
               <h4 class="pull-right"><%= item.getPrice() %>&euro;</h4>
               <h1><a href="#"><%= item.getTitle() %></a></h1>
               <p><%= item.getDescription() %></p>
           </div>
           <div class="ratings">
               <p class="pull-right"><%= comments.size() %> reviews</p>
               <p>
                   <span class="glyphicon glyphicon-star"></span>
                   <span class="glyphicon glyphicon-star"></span>
                   <span class="glyphicon glyphicon-star"></span>
                   <span class="glyphicon glyphicon-star"></span>
                   <span class="glyphicon glyphicon-star-empty"></span>
                   4.0 stars
               </p>
           </div>
       </div>
       <div class="well">
           <div class="text-right">
               <button type="button" class="btn btn-success" data-toggle="modal" data-target="#commentModal">Leave a review</button>
           </div>
           <hr>
           <%
           	for(ItemComment comment : comments) {
           		out.println("<div class='row'>");
           		out.println("<div class='col-md-12'>");
           		out.println("<span class='glyphicon glyphicon-star'></span>");
           		out.println("<span class='glyphicon glyphicon-star'></span>");
           		out.println("<span class='glyphicon glyphicon-star'></span>");
           		out.println("<span class='glyphicon glyphicon-star'></span>");
           		out.println("<span class='glyphicon glyphicon-star-empty'></span>");
           		out.println(comment.getCreateUser().getUsername());
           		out.println("<span class='pull-right'>" + comment.getCreateTimestamp() + "</span>");
           		out.println("<p>" + comment.getText() + "</p>");
           		out.println("</div>");
           		out.println("</div>");
           	}
           %>
       </div>
    </div>
	<div class="modal fade" id="commentModal" tabindex="-1" role="dialog" aria-labelledby="commentModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="commentModalLabel">New message</h4>
	      </div>
	      <form action="/WebShop/api/item/<%= item.getId() %>/comment" method="post">
		      <div class="modal-body">
		          <div class="form-group">
		            <label for="message-text" class="control-label">Comment:</label>
		            <textarea class="form-control" name="text" id="message-text"></textarea>
		          </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary">Send message</button>
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
	<jsp:include page="footer.jsp" />
</body>