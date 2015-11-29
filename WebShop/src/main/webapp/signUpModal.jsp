<div class="modal fade" id="signUpModal" tabindex="-1" role="dialog" aria-labelledby="signUpLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="commentModalLabel">Sign up</h4>
      </div>
      <form action="/WebShop/api/user" method="post" id="signupForm" role="form" data-toggle="validator">
	      <div class="modal-body">
	          <div class="form-group">
	            <label for="username" class="control-label">Username:</label>
	            <input type="text" class="form-control" name="username" id="usernameSignup" autofocus required />
	          </div>
	          <div class="form-group">
	            <label for="password" class="control-label">Password:</label>
	            <input type="password" class="form-control" name="password" id="passwordSignup" data-minlength="6" required />
	            <span class="help-block">Minimum of 6 characters</span>
	          </div>
	          <div class="form-group">
	            <label for="confirmpassword" class="control-label">Retype password:</label>
	            <input type="password" class="form-control" name="confirmpassword" id="confirmpassword" data-match="#passwordSignup" data-match-error="Whoops, these don't match" required />
	            <div class="help-block with-errors"></div>
	          </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        <button type="submit" class="btn btn-primary">Register</button>
	      </div>
      </form>
    </div>
  </div>
</div>