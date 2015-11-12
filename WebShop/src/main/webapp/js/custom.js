// Every time a modal is shown, if it has an autofocus element, focus on it.
$('.modal').on('shown.bs.modal', function() {
  $(this).find('[autofocus]').focus();
});

var rating = 0;
var rated = false;

function fillStars(amount) {
	if(rated === true) return;
	if(amount > 0) document.getElementById("rating1").className='glyphicon glyphicon-star';
	if(amount > 1) document.getElementById("rating2").className='glyphicon glyphicon-star';
	if(amount > 2) document.getElementById("rating3").className='glyphicon glyphicon-star';
	if(amount > 3) document.getElementById("rating4").className='glyphicon glyphicon-star';
	if(amount > 4) document.getElementById("rating5").className='glyphicon glyphicon-star';
}

function emptyStars(amount) {
	if(rated === true) return;
	if(amount > 0) document.getElementById("rating1").className='glyphicon glyphicon-star-empty';
	if(amount > 1) document.getElementById("rating2").className='glyphicon glyphicon-star-empty';
	if(amount > 2) document.getElementById("rating3").className='glyphicon glyphicon-star-empty';
	if(amount > 3) document.getElementById("rating4").className='glyphicon glyphicon-star-empty';
	if(amount > 4) document.getElementById("rating5").className='glyphicon glyphicon-star-empty';
}

function setRating(amount) {
	rated = false;
	emptyStars(5);
	fillStars(amount);
	rating = amount;
	rated = true;
	document.getElementById("rating").value = rating;
}

jQuery.each( [ "put", "delete" ], function( i, method ) {
  jQuery[ method ] = function( url, data, callback, type ) {
    if ( jQuery.isFunction( data ) ) {
      type = type || callback;
      callback = data;
      data = undefined;
    }

    return jQuery.ajax({
      url: url,
      type: method,
      dataType: type,
      data: data,
      success: callback
    });
  };
});

$("#editCategoryForm" ).on( "submit", function( event ) {
	event.preventDefault();
	var arr = $(this).serialize().split('&');
	var name = arr[0].split('=')[1];
	var description = arr[1].split('=')[1];
	$.put($(this).attr('action'), {name: name, description: description}, function(result){
		window.location.href = "/WebShop/index.jsp";
	});
});

$("#deleteCategoryForm").on("submit", function(event) {
	event.preventDefault();
	$.delete($(this).attr('action'), {}, function(result) {
		window.location.href = "/WebShop/index.jsp";
	});
});

$("#editItemForm").on("submit", function(event) {
	event.preventDefault();
	var arr = $(this).serialize().split('&');
	var title = arr[0].split('=')[1];
	var price = arr[1].split('=')[1];
	var description = arr[2].split('=')[1];
	var categoryId = arr[3].split('=')[1];
	var itemId = $(this).attr('action').split('/')[4];
	$.put($(this).attr('action'), {title: title, price: price, description: description, categoryId: categoryId}, function(result) {
		window.location.href = "/WebShop/item.jsp?itemId=" + itemId;
	});
});

$("#deleteItemForm").on("submit", function(event) {
	event.preventDefault();
	$.delete($(this).attr('action'), {}, function(result) {
		window.location.href = "/WebShop/index.jsp";
	});
});