var accessToken = 'pk.eyJ1IjoiYmVybmhhcmRmcml0eiIsImEiOiJjaWl0OTBjaDMwMGExdzlrc3FqaTBkdXZjIn0.jm5I3E659TeH67zEmnc_BA';
var id = 'bernhardfritz.oiicp9j3';
var map;
var markers = new L.FeatureGroup();

function getLocation() {
	navigator.geolocation.getCurrentPosition(useCurrentPosition);
}

function useCurrentPosition(position) {
	var lat = position.coords.latitude;
	var lon = position.coords.longitude;
	map.setView([lat, lon], 14);
	setMarkers([lat, lon]);
	sessionStorage.currentLat = lat;
	sessionStorage.currentLon = lon;
}

function setMarkers(currentPosition) {
	map.removeLayer(markers);
	markers = new L.FeatureGroup();
	
	var RedIcon = L.Icon.Default.extend({
        options: { iconUrl: 'leaflet/images/marker-icon-red.png' }
     });
	
	var marker = L.marker([currentPosition[0], currentPosition[1]], {icon: new RedIcon()});
	marker.bindPopup('Current position');
	markers.addLayer(marker);
	
	var southWest = map.getBounds().getSouthWest();
	var northEast = map.getBounds().getNorthEast();
	var keyValue = getURLParameter("keyValue");
	if (!keyValue) {
		keyValue = "supermarket";
	}
	$.get("http://overpass-api.de/api/interpreter?data=[out:json];node(" + southWest.lat + "," + southWest.lng + "," + 
				northEast.lat + "," + northEast.lng + ")[shop=" + keyValue + "];out;", 
		function(data) {
			for (i = 0; i < data.elements.length; i++) {
				var element = data.elements[i];
				marker = L.marker([element.lat, element.lon]);
				marker.bindPopup(element.tags.name);
				markers.addLayer(marker);
			}
			map.addLayer(markers);
	});
}

$(document).ready(function() {	
	var defaultLat = 47.26428145;
	var defaultLon = 11.3442061494943
	if (sessionStorage.currentLat && sessionStorage.currentLon) {
		map = L.map('map').setView([sessionStorage.currentLat, sessionStorage.currentLon], 14);
	}
	else {
		map = L.map('map').setView([defaultLat, defaultLon], 14);
	}
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
	    maxZoom: 18,
	    id: id,
	    accessToken: accessToken
	}).addTo(map);
	
	if (sessionStorage.currentLat && sessionStorage.currentLon) {
		setMarkers([sessionStorage.currentLat, sessionStorage.currentLon]);
	}
	else {
		getLocation();
		setMarkers([defaultLat, defaultLon]);
	}
});

function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [,""])[1].replace(/\+/g, '%20')) || null;
}