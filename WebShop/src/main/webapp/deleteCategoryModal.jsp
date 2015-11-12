<%@ page import="java.util.*, model.*, control.*" %>
<%
	long categoryId = Long.valueOf(request.getParameter("categoryId"));
	Category category = DBManager.getInstance().getCategoryById(categoryId);
%>
<div class="modal fade" id="deleteCategoryModal" tabindex="-1" role="dialog" aria-labelledby="deleteCategoryModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="deleteCategoryModalLabel">Delete category</h4>
      </div>
      <form action="/WebShop/api/category/<%= category!=null?category.getId():-1 %>" id="deleteCategoryForm" method="delete" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <p>Are you sure you want to delete category <%= category!=null?category.getName():"" %>?</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
	        <button type="submit" class="btn btn-primary">Yes</button>
	      </div>
      </form>
    </div>
  </div>
</div>