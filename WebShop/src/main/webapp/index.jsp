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
					<a href="#" class="list-group-item" data-toggle="modal" data-target="#categoryModal">Create new category</a>
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
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#itemModal">Create new item</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="commentModalLabel">Create category</h4>
	      </div>
	      <form action="/WebShop/api/category" method="post">
		      <div class="modal-body">
		          <div class="form-group">
		            <label for="name" class="control-label">Name:</label>
		            <input type="text" class="form-control" name="name" id="name" />
		          </div>
		          <div class="form-group">
		            <label for="description" class="control-label">Description:</label>
		            <textarea class="form-control" name="description" id="description"></textarea>
		          </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary">Save</button>
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
	<div class="modal fade" id="itemModal" tabindex="-1" role="dialog" aria-labelledby="itemModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="commentModalLabel">Create item</h4>
	      </div>
	      <form action="/WebShop/api/item" method="post">
		      <div class="modal-body">
		          <div class="form-group">
		            <label for="title" class="control-label">Title:</label>
		            <input type="text" class="form-control" name="title" id="title" />
		          </div>
		          <div class="form-group">
		          	<label for="price" class="control-label">Price:</label>
		            <input type='text' class="form-control" name='price' />
		          </div>
		          <div class="form-group">
		            <label for="description" class="control-label">Description:</label>
		            <textarea class="form-control" name="description" id="description"></textarea>
		          </div>
		          <div class="form-group">
		          	<label for="categoryId" class="control-lagel">Category</label>
		          	<select name="categoryId">
						<option value="null">Choose category...</option>
							<%
								for (Category c : DBManager.getInstance().getCategories()) {
									out.println("<option value='" + c.getId() + "'>" + c.getName() + "</option>");
								}
							%>
					</select>
		          </div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="submit" class="btn btn-primary">Save</button>
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
    <jsp:include page="footer.jsp" />
</body>

</html>
