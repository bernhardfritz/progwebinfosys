<div class="modal fade" id="createCategoryModal" tabindex="-1" role="dialog" aria-labelledby="createCategoryModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="createCategoryModalLabel">Create category</h4>
      </div>
      <form action="/WebShop/api/category" method="post" role="form" id="createCategoryForm" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="name" class="control-label">Name:</label>
	            <input id="categoryName" type="text" class="form-control" name="name" id="name" autofocus required />
	          </div>
	          <div class="form-group">
	            <label for="description" class="control-label">Description:</label>
	            <textarea id="categoryDescription" class="form-control" name="description" id="description"></textarea>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button id="saveCategory" type="submit" class="btn btn-primary">Save</button>
	      </div>
      </form>
    </div>
  </div>
</div>