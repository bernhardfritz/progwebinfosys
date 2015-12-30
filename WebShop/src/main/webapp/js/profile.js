var accessToken = 'pk.eyJ1IjoiYmVybmhhcmRmcml0eiIsImEiOiJjaWl0OTBjaDMwMGExdzlrc3FqaTBkdXZjIn0.jm5I3E659TeH67zEmnc_BA';
var id = 'bernhardfritz.oiicp9j3';
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
		var map = L.map('map').setView([lat, lon], 16);
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
		    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
		    maxZoom: 18,
		    id: id,
		    accessToken: accessToken
		}).addTo(map);
		var marker = L.marker([lat, lon]).addTo(map);
	});
}

$(document).ready(readTable);