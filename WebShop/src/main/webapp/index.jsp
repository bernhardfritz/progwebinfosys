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
							out.println("<a href='index.jsp?categoryId=" + category.getId() + "' class='list-group-item " + suffix + "'>" + category.getName() + "</a>");
						}
						
						User currentUser = (User)session.getAttribute("user");
                    	if (currentUser != null && currentUser.isCategoryWrite()) {
                    		out.println("<a href='#' class='list-group-item' data-toggle='modal' data-target='#categoryModal'>Create new category...</a>");
                    	}
					%>
                </ul>
            </div>
            <div class="col-md-9">
                <div class="row">
					<%
						List<Item> items = DBManager.getInstance().getItems(categoryId);
						for(Item item : items) {
							out.println("<div class='col-sm-4 col-lg-4 col-md-4'>");
							out.println("<div class='thumbnail'>");
                            out.println("<img src='http://placehold.it/320x150' alt=''>");
                            out.println("<div class='caption'>");
							out.println("<h4 class='pull-right'>" + item.getPrice() + "&euro;</h4>");
                            out.println("<h4><a href='item.jsp?itemId=" + item.getId() + "'>" + item.getTitle() + "</a></h4>");
                            out.println("<p>" + item.getDescription() + "</p>");
                            out.println("</div>");
                            out.println("<div class='ratings'>");
                            out.println("<p class='pull-right'>" + DBManager.getInstance().getItemComments(item.getId()).size() + " reviews</p>");
                            out.println("<p>");
                            out.println("<span class='glyphicon glyphicon-star'></span>");
							out.println("<span class='glyphicon glyphicon-star'></span>");
							out.println("<span class='glyphicon glyphicon-star'></span>");
							out.println("<span class='glyphicon glyphicon-star'></span>");
							out.println("<span class='glyphicon glyphicon-star-empty'></span>");
                            out.println("</p>");
                            out.println("</div>");
                        	out.println("</div>");
                    		out.println("</div>");
						}
					%>

                    <div class="col-sm-4 col-lg-4 col-md-4">
                    	<%
	                    	if (currentUser != null && currentUser.isItemWrite()) {
	                    		out.println("<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#itemModal'>Create new item</button>");
	                    	}
                    	%>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<jsp:include page="categoryModal.jsp" />
	<jsp:include page="itemModal.jsp" />
    <jsp:include page="footer.jsp" />
</body>

</html>
