<%@ page import="java.util.*, model.*, control.*" %>
<%
	long itemId = Long.valueOf(request.getParameter("itemId"));
	Item item = DBManager.getInstance().getItem(itemId);
%>
<div class="modal fade" id="deleteItemModal" tabindex="-1" role="dialog" aria-labelledby="deleteItemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="deleteItemModalLabel">Delete item</h4>
      </div>
      <form action="/WebShop/api/item/<%= item!=null?item.getId():-1 %>" id="deleteCategoryForm" method="delete" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <p>Are you sure you want to delete category <%= item!=null?item.getTitle():"" %>?</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
	        <button type="submit" class="btn btn-primary">Yes</button>
	      </div>
      </form>
    </div>
  </div>
</div>