<%@ page import="model.*, control.*" %>
<div class="modal fade" id="createItemModal" tabindex="-1" role="dialog" aria-labelledby="createItemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="createItemModalLabel">Create item</h4>
      </div>
      <form action="/WebShop/api/item" method="post" id="createItemForm" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="title" class="control-label">Title:</label>
	            <input type="text" class="form-control" name="title" id="itemTitle" autofocus required />
	          </div>
	          <div class="form-group">
	          	<label for="price" class="control-label">Price:</label>
	            <input id="itemPrice" type='text' class="form-control" name='price' pattern="^\d+(\.\d{1,2})?$" required />
	            <span class="help-block with-errors">e.g. 1.99</span>
	          </div>
	          <div class="form-group">
	            <label for="description" class="control-label">Description:</label>
	            <textarea class="form-control" name="description" id="itemDescription"></textarea>
	          </div>
	          <div class="form-group">
	          	<label for="categoryId" class="control-lagel">Category</label>
	          	<select id="itemCategory" name="categoryId">
					<option value="0">Choose category...</option>
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
	        <button id="saveItem" type="submit" class="btn btn-primary">Save</button>
	      </div>
      </form>
    </div>
  </div>
</div>