<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<jsp:include page="header.jsp" />
	<%
		long itemId = 1L;
		if(request.getParameter("itemId") != null) {
			itemId = Long.valueOf(request.getParameter("itemId"));
		}
		Item item = DBManager.getInstance().getItem(itemId);
		List<ItemComment> comments = DBManager.getInstance().getItemComments(itemId);
		
		out.println("<meta property='og:url' content='http://localhost:8080/WebShop/item/" + item.getId() + "' />");
		out.println("<meta property='og:type' content='website' />");
		out.println("<meta property='og:title' content='" + item.getTitle() + "' />");
		out.println("<meta property='og:description'   content='" + item.getDescription() + "' />");
		out.println("<meta property='og:image' content='http://placehold.it/320x150' />");
	%>
</head>

<body>
	<jsp:include page="navbar.jsp" />
    <div class="container">
    	<div class="row">
    		<div class="col-md-3">
    			<ul class="list-group">
                	<%
						long categoryId = item.getCategory().getId();
						List<Category> categories = DBManager.getInstance().getCategories();
						for(Category category : categories) {
							String suffix = category.getId() == categoryId ? "active" : "";
							out.println("<a href='index.jsp?categoryId=" + category.getId() + "' class='list-group-item " + suffix + "'>" + category.getName() + "</a>");
						}
						
						User currentUser = (User)session.getAttribute("user");
                    	if (currentUser != null && currentUser.isCategoryWrite()) {
                    		out.println("<a href='#' class='list-group-item' data-toggle='modal' data-target='#createCategoryModal'>Create new category...</a>");
                    	}
					%>
                </ul>
    		</div>
    		<div class="col-md-9">
		      	<div class="thumbnail">
		           <img class="img-responsive" data-src="holder.js/900x300" alt="">
		           <div class="caption-full">
		               <h4 class="pull-right">
		               		<% 
		               			DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
		               			DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00", symbols);
		               			out.println(decimalFormatter.format(item.getPrice()));
		               		%>
		               		&euro;
		               </h4>
		               <h1><a href="#"><%= item.getTitle() %></a></h1>
		               <%
		               		if(currentUser != null && currentUser.isUser()) {
		               			out.println("<button id='" + item.getTitle() + "ToShoppingCart' class='btn btn-sm btn-success pull-right' onclick='addItemToShoppingCart(" + item.getId() + ")'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span></button>");		
		               		}
		               %>
		               <p><%= item.getDescription() %>&nbsp;</p>
		           </div>
		           <div class="ratings">
		               <p class="pull-right"><%= comments.size() %> reviews</p>
		               <p>
		               <% 
			               float average = 0.0f;
			               for(ItemComment comment : comments) {
			               		average += comment.getRating();
			               }
			               if(comments.size() > 0) average /= comments.size();
			               for(int i = 0; i < Math.round(average); i++) {
			            		out.println("<span class='glyphicon glyphicon-star'></span>");   
			               }
			               for(int i = Math.round(average); i < 5; i++) {
			  					out.println("<span class='glyphicon glyphicon-star-empty'></span>");
			               }
			           %>
		               </p>
		           </div>
		       </div>
		       <div class="well">
		           <div class="text-right">
		           	<div class="fb-share-button" data-href="http://localhost:8080/WebShop/item/<%= item.getId() %>" data-layout="button"></div>
		           	<!--<a href="javascript:window.location=%22http://www.facebook.com/sharer.php?u=%22+encodeURIComponent(document.location)+%22&#38;t=%22+encodeURIComponent(document.title)" title="Share on Facebook">
		           		<img src="" alt="Share on Facebook">
		           	</a>-->
			           <%
			           		if(currentUser != null && currentUser.isItemCommentWrite()) {
			           			out.println("<button id='createComment' type='button' class='btn btn-primary' data-toggle='modal' data-target='#createCommentModal'>Leave a review</button>");
			           		}
			           		if(currentUser != null && currentUser.isItemWrite()) {
			           			out.println("<button type='button' class='btn btn-warning' data-toggle='modal' data-target='#editItemModal'>Edit item</button>");
			           		}
			           		if(currentUser != null && currentUser.isItemDelete()) {
			           			out.println("<button type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteItemModal'>Delete item</button>");
			           		}
			           %>
		           </div>
		           <hr>
		           <%
		           	for(ItemComment comment : comments) {
		           		out.println("<div class='row'>");
		           		out.println("<div class='col-md-12'>");
		           		for(int i = 0; i < comment.getRating(); i++) {
		           			out.println("<span class='glyphicon glyphicon-star'></span>");	
		           		}
		           		for(int i = comment.getRating(); i < 5; i++) {
		           			out.println("<span class='glyphicon glyphicon-star-empty'></span>");
		           		}
		           		out.println(comment.getCreateUser().getUsername());
		           		if(currentUser != null && ((currentUser.isItemCommentWrite() && currentUser.equals(comment.getCreateUser()) || currentUser.isItemCommentDelete()))) out.println("<button class='btn btn-sm btn-warning' onclick='editComment(" + comment.getId() + ", \"" + comment.getText() + "\", " + comment.getRating() + ")'><span class='glyphicon glyphicon-edit'></span></button>");
		           		if(currentUser != null && currentUser.isItemCommentDelete()) out.println("<button class='btn btn-sm btn-danger' onclick='deleteComment(" + comment.getId() + ")'><span class='glyphicon glyphicon-trash'></span></button>");
		           		out.println("<span class='pull-right'>" + new SimpleDateFormat("dd.MM.yyyy kk:mm").format(comment.getCreateTimestamp()) + "</span>");
		           		out.println("<p id='comment" + comment.getText() + "'>" + comment.getText() + "</p>");
		           		out.println("</div>");
		           		out.println("</div>");
		           	}
		           %>
		       </div>
	       </div>
       </div>
    </div>
	<jsp:include page="createCommentModal.jsp">
		<jsp:param name="itemId" value="<%= item.getId() %>" />
	</jsp:include>
	<jsp:include page="editItemModal.jsp">
		<jsp:param name="itemId" value="<%= item.getId() %>" />
	</jsp:include>
	<jsp:include page="deleteItemModal.jsp">
		<jsp:param name="itemId" value="<%= item.getId() %>" />
	</jsp:include>
	<jsp:include page="createCategoryModal.jsp" />
	<jsp:include page="footer.jsp" />
</body>