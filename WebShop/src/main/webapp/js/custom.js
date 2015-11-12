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

function serializedStringToJSON(qs) {            
    var pairs = qs.split('&');
    
    var result = {};
    pairs.forEach(function(pair) {
        pair = pair.split('=');
        result[pair[0]] = decodeURIComponent(pair[1] || '');
    });

    return JSON.parse(JSON.stringify(result));
}

$("#editCategoryForm" ).on( "submit", function( event ) {
	event.preventDefault();
	var form = serializedStringToJSON($(this).serialize());
	$.put($(this).attr('action'), {name: form.name, description: form.description}, function(result){
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
	var form = serializedStringToJSON($(this).serialize());
	var itemId = $(this).attr('action').split('/')[4];
	$.put($(this).attr('action'), {title: form.title, price: form.price, description: form.description, categoryId: form.categoryId}, function(result) {
		window.location.href = "/WebShop/item.jsp?itemId=" + itemId;
	});
});

$("#deleteItemForm").on("submit", function(event) {
	event.preventDefault();
	$.delete($(this).attr('action'), {}, function(result) {
		window.location.href = "/WebShop/index.jsp";
	});
});

function editComment(commentId, commentText, rating) {
	var text = prompt("Edit comment", commentText);
	$.put('/WebShop/api/comment/'+commentId, {text: text, rating: rating}, function(result) {
		location.reload(true);
	});
}

function deleteComment(commentId) {
	if (confirm('Are you sure you want to delete this comment?')) {
		$.delete('/WebShop/api/comment/'+commentId, {}, function(result) {
			location.reload(true);
		});
	}
}