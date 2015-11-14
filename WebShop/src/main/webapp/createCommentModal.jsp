<div class="modal fade" id="createCommentModal" tabindex="-1" role="dialog" aria-labelledby="createCommentModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="createCommentModalLabel">Create review</h4>
      </div>
      <form action="/WebShop/api/item/<%= request.getParameter("itemId") %>/comment" method="post" id="createCommentForm" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="message-text" class="control-label">Comment:</label>
	            <textarea class="form-control" name="text" id="message-text" autofocus required></textarea>
	          </div>
	          <div class="form-group">
	            <label for="rating" class="control-label">Rating:</label>
		        <span id='rating1' onmouseover='fillStars(1)' onmouseleave='emptyStars(1)' onclick='setRating(1)' class='glyphicon glyphicon-star-empty'></span>
		        <span id='rating2' onmouseover='fillStars(2)' onmouseleave='emptyStars(2)' onclick='setRating(2)' class='glyphicon glyphicon-star-empty'></span>
		        <span id='rating3' onmouseover='fillStars(3)' onmouseleave='emptyStars(3)' onclick='setRating(3)' class='glyphicon glyphicon-star-empty'></span>
		        <span id='rating4' onmouseover='fillStars(4)' onmouseleave='emptyStars(4)' onclick='setRating(4)' class='glyphicon glyphicon-star-empty'></span>
		        <span id='rating5' onmouseover='fillStars(5)' onmouseleave='emptyStars(5)' onclick='setRating(5)' class='glyphicon glyphicon-star-empty'></span>
	            <input class="form-control" name="rating" id="rating" type='hidden' value='0'></input>
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