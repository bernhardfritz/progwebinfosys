<%@ page import="model.*, control.*" %>
<%
	long itemId = Integer.valueOf(request.getParameter("itemId"));
	Item item = DBManager.getInstance().getItem(itemId);
%>
<div class="modal fade" id="editItemModal" tabindex="-1" role="dialog" aria-labelledby="editItemModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="editItemModalLabel">Edit item</h4>
      </div>
      <form action="/WebShop/api/item/<%= item.getId() %>" method="put" id="editItemForm" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="title" class="control-label">Title:</label>
	            <input type="text" class="form-control" name="title" id="title" value="<%= item.getTitle() %>" autofocus required />
	          </div>
	          <div class="form-group">
	          	<label for="price" class="control-label">Price:</label>
	            <input type='text' class="form-control" name='price' pattern="^\d+(\.\d{1,2})?$" value="<%= item.getPrice() %>" required />
	            <span class="help-block with-errors">e.g. 1.99</span>
	          </div>
	          <div class="form-group">
	            <label for="description" class="control-label">Description:</label>
	            <textarea class="form-control" name="description" id="description"><%= item.getDescription() %></textarea>
	          </div>
	          <div class="form-group">
	          	<label for="categoryId" class="control-lagel">Category</label>
	          	<select name="categoryId">
					<option value="null">Choose category...</option>
						<%
							for (Category c : DBManager.getInstance().getCategories()) {
								String selected = c.equals(item.getCategory())?"selected='selected'":"";
								out.println("<option value='" + c.getId() + "' " + selected + ">" + c.getName() + "</option>");
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