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
                <div class="list-group">
                	<%
						long categoryId = -1L;
						List<Category> categories = DBManager.getInstance().getCategories();
						for(Category category : categories) {
							if(categoryId == -1L) {
								categoryId = category.getId();
							}
							out.println("<a href='index.jsp?categoryId=" + category.getId() + "' class='list-group-item'>" + category.getName() + "</a>");
						}
					%>
					<a href="#" class="list-group-item">Create new category...</a>
                </div>
            </div>

            <div class="col-md-9">
                <div class="row">
					<%
						if(request.getParameter("categoryId") != null) {
							categoryId = Long.valueOf(request.getParameter("categoryId"));
						}
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
                            out.println("<p class='pull-right'>0 reviews</p>");
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
                        <a class="btn btn-primary" href="#">Create new article...</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>

</html>
