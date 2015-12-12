<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.*, control.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="header.jsp" />
</head>

<body>
    <jsp:include page="navbar.jsp" /> 
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <ul class="list-group">
                	<%
						long categoryId = -1L;
                		if(request.getParameter("categoryId") != null) {
							categoryId = Long.valueOf(request.getParameter("categoryId"));
						}
						List<Category> categories = DBManager.getInstance().getCategories();
						for(Category category : categories) {
							if(categoryId == -1L) {
								categoryId = category.getId();
							}
							String suffix = category.getId() == categoryId ? "active" : "";
							out.println("<a id='category" + category.getName() + "' href='index.jsp?categoryId=" + category.getId() + "' class='list-group-item " + suffix + "'>" + category.getName() + "</a>");
						}
						
						User currentUser = (User)session.getAttribute("user");
                    	if (currentUser != null && currentUser.isCategoryWrite()) {
                    		out.println("<a id='createNewCategory' href='#' class='list-group-item' data-toggle='modal' data-target='#createCategoryModal'>Create new category...</a>");
                    	}
					%>
                </ul>
            </div>
            <div class="col-md-9">
                <div class="row">
					<%
						DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.GERMAN);
	           			DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00", symbols);
						List<Item> items = DBManager.getInstance().getItems(categoryId);
						for(Item item : items) {
							out.println("<div class='col-sm-4 col-lg-4 col-md-4'>");
							out.println("<div class='thumbnail'>");
                            out.println("<img data-src='holder.js/320x150' alt=''>");
                            out.println("<div class='caption'>");
							out.println("<h4 class='pull-right'>" + decimalFormatter.format(item.getPrice()) + "&euro;</h4>");
                            out.println("<h4><a href='item.jsp?itemId=" + item.getId() + "'>" + item.getTitle() + "</a></h4>");
                            if(currentUser != null && currentUser.isUser()) {
                            	out.println("<button id='" + item.getTitle() + "ToShoppingCart' class='btn btn-sm btn-success pull-right' onclick='addItemToShoppingCart("+item.getId()+")'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'></span></button>");
                            }
                            out.println("<p>" + item.getDescription() + "</p>");
                            out.println("</div>");
                            out.println("<div class='ratings'>");
                            out.println("<p class='pull-right'>" + DBManager.getInstance().getItemComments(item.getId()).size() + " reviews</p>");
                            out.println("<p>");
                            float average = 0.0f;
 			               	for(ItemComment comment : DBManager.getInstance().getItemComments(item.getId())) {
 			               		average += comment.getRating();
 			              	}
 			               	average /= DBManager.getInstance().getItemComments(item.getId()).size();
 			               	for(int i = 0; i < Math.round(average); i++) {
 			            		out.println("<span class='glyphicon glyphicon-star'></span>");   
 			               	}
 			               	for(int i = Math.round(average); i < 5; i++) {
 			  					out.println("<span class='glyphicon glyphicon-star-empty'></span>");
 			              	}
                            out.println("</p>");
                            out.println("</div>");
                        	out.println("</div>");
                    		out.println("</div>");
						}
					%>

                    <div class="col-sm-4 col-lg-4 col-md-4">
                    	<div class="btn-group-vertical" role="group">
                    	<%
	                    	if(categoryId > 0 && currentUser != null && currentUser.isItemWrite()) {
	                    		out.println("<button id='createNewItem' type='button' class='btn btn-primary' data-toggle='modal' data-target='#createItemModal'>Create new item</button>");
	                    	}
                    		if(categoryId > 0 && currentUser != null && currentUser.isCategoryWrite()) {
                    			out.println("<button id='editCategory' type='button' class='btn btn-warning' data-toggle='modal' data-target='#editCategoryModal'>Edit category</button>");
                    		}
                    		if(categoryId > 0 && currentUser != null && currentUser.isCategoryDelete()) {
                    			out.println("<button id='deleteCategory' type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteCategoryModal'>Delete category</button>");
                    		}
                    	%>
                    	</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="createCategoryModal.jsp" />
	<jsp:include page="editCategoryModal.jsp">
		<jsp:param name="categoryId" value="<%= categoryId %>" />
	</jsp:include>	
	<jsp:include page="deleteCategoryModal.jsp">
		<jsp:param name="categoryId" value="<%= categoryId %>" />
	</jsp:include>
	<jsp:include page="createItemModal.jsp" />
    <jsp:include page="footer.jsp" />
</body>

</html>
