<%@ page import="java.util.*, model.*, control.*" %>
<%
	long categoryId = Long.valueOf(request.getParameter("categoryId"));
	Category category = DBManager.getInstance().getCategoryById(categoryId);
%>
<div class="modal fade" id="editCategoryModal" tabindex="-1" role="dialog" aria-labelledby="editCategoryModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="commentModalLabel">Edit category</h4>
      </div>
      <form action="/WebShop/api/category/<%= category!=null?category.getId():-1 %>" id="editCategoryForm" method="put" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="name" class="control-label">Name:</label>
	            <input type="text" class="form-control" name="name" id="name" value="<%= category!=null?category.getName():"" %>" autofocus required />
	          </div>
	          <div class="form-group">
	            <label for="description" class="control-label">Description:</label>
	            <textarea class="form-control" name="description" id="description"><%= category!=null?category.getDescription():"" %></textarea>
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