var accessToken = 'pk.eyJ1IjoiYmVybmhhcmRmcml0eiIsImEiOiJjaWl0OTBjaDMwMGExdzlrc3FqaTBkdXZjIn0.jm5I3E659TeH67zEmnc_BA';
var id = 'bernhardfritz.oiicp9j3';
var map;
var marker;
function readTable() {
	var country = $('#country').html();
	var state = $('#state').html();
	var county = $('#county').html();
	var postcode = $('#postcode').html();
	var city = $('#city').html();
	var streetname = $('#streetname').html();
	var housenumber = $('#housenumber').html();
	console.log(country, state, county, postcode, city, streetname, housenumber);
	$.get("http://nominatim.openstreetmap.org/search", {
		format: 'json',
		country: country,
		state: state,
		//county: county,
		//postcode: postcode,
		city: city,
		street: housenumber + ' ' + streetname,
		}, function(data) {
		console.log(data);
		var lat = parseFloat(data[0].lat);
		var lon = parseFloat(data[0].lon);
		console.log(lat, lon);
		map.setView([lat, lon], 16);
		if(marker) marker.setLatLng([lat, lon]);
		else marker = L.marker([lat, lon]).addTo(map);
	});
}

$(document).ready(function() {
	map = L.map('map');
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
	    maxZoom: 18,
	    id: id,
	    accessToken: accessToken
	}).addTo(map);
	readTable();
});

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function editAndConfirm(userId, name, entity) {
	if(entity) {
		var obj = {};
		obj[name] = entity;
		//console.log("/WebShop/api/user/" + userId + "/" + name, obj);
		$.put("/WebShop/api/user/" + userId + "/" + name, obj, function(result) {
			//console.log(result);
			$('#' + name).html(entity);
			$('#edit' + capitalizeFirstLetter(name) + 'Button').html('<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>');
			readTable();
		});
	} else {
		var tmp = $('#' + name).html();
		if(tmp === 'unknown') tmp = ''; 
		$('#' + name).html('<input type="text" class="form-control" id="edit' + capitalizeFirstLetter(name) + 'Field" value="' + tmp + '" placeholder="' + capitalizeFirstLetter(name) + '" />');
		$('#edit' + capitalizeFirstLetter(name) + 'Button').html('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>');
		$('#edit' + capitalizeFirstLetter(name) + 'Button').removeAttr('onClick');
		$('#edit' + capitalizeFirstLetter(name) + 'Button').click(function() {
			editAndConfirm(userId, name, $('#edit' + capitalizeFirstLetter(name) + 'Field').val());
		});
		$('#' + name).find('input').select();
	}
}

function editAndConfirm2(userId, name1, name2, entity1, entity2) {
	if(entity1 && entity2) {
		var obj1 = {};
		var obj2 = {};
		obj1[name1] = entity1;
		obj2[name2] = entity2;
		$.put("/WebShop/api/user/" + userId + "/" + name1, obj1, function(result) {
			$.put("/WebShop/api/user/" + userId + "/" + name2, obj2, function(result) {
				$('#' + name1).html(entity1);
				$('#' + name2).html(entity2);
				$('#edit' + capitalizeFirstLetter(name1) + 'And' + capitalizeFirstLetter(name2) + 'Button').html('<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>');
				readTable();
			});
		});
		$('#' + name1).html(entity1);
		$('#' + name2).html(entity2);
		$('#edit' + capitalizeFirstLetter(name1) + 'And' + capitalizeFirstLetter(name2) + 'Button').html('<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>');
	} else {
		var tmp1 = $('#' + name1).html();
		var tmp2 = $('#' + name2).html();
		if(tmp1 === 'unknown') tmp1 = '';
		if(tmp2 === 'unknown') tmp2 = '';
		$('#' + name1).html('<input type="text" class="form-control" id="edit' + capitalizeFirstLetter(name1) + 'Field" value="' + tmp1 + '" placeholder="' + capitalizeFirstLetter(name1) + '" />');
		$('#' + name2).html('<input type="text" class="form-control" id="edit' + capitalizeFirstLetter(name2) + 'Field" value="' + tmp2 + '" placeholder="' + capitalizeFirstLetter(name2) + '" />');
		$('#edit' + capitalizeFirstLetter(name1) + 'And' + capitalizeFirstLetter(name2) + 'Button').html('<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>');
		$('#edit' + capitalizeFirstLetter(name1) + 'And' + capitalizeFirstLetter(name2) + 'Button').removeAttr('onClick');
		$('#edit' + capitalizeFirstLetter(name1) + 'And' + capitalizeFirstLetter(name2) + 'Button').click(function() {
			editAndConfirm2(userId, name1, name2, $('#edit' + capitalizeFirstLetter(name1) + 'Field').val(), $('#edit' + capitalizeFirstLetter(name2) + 'Field').val());
		});
		$('#' + name1).find('input').select();
	}
}