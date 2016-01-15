var vectorSource = new ol.source.Vector({});

var iconStyle = new ol.style.Style({
  image: new ol.style.Icon({
    anchor: [0.5, 1.0],
    opacity: 1,
    src: '/assets/images/marker-icon.png'
  })
});

$.get('/personswithaddress', function(results) {
  for (i = 0; i < JSON.parse(results).length; i++) {
    var result = JSON.parse(results)[i];
    var person = result.person;
    var address = result.address;

    addMarker(person, address);
  }

  var vectorLayer = new ol.layer.Vector({
    source: vectorSource
  });

  var map = new ol.Map({
    target: 'map',
    layers: [
      new ol.layer.Tile({
        source: new ol.source.MapQuest({layer: 'osm'})
      }),
      vectorLayer
    ],
    view: new ol.View({
      center: ol.proj.fromLonLat([11.383333, 47.266667]),
      zoom: 14,
      minZoom:3,
      maxZoom: 21
    })
  });

  var element = document.getElementById('popup');

  var popup = new ol.Overlay({
   element: element,
   positioning: 'bottom-center',
   stopEvent: false
  });
  map.addOverlay(popup);

  // display popup on click
  map.on('click', function(evt) {
   var feature = map.forEachFeatureAtPixel(evt.pixel,
       function(feature, layer) {
         return feature;
       });
   if (feature) {
     popup.setPosition(evt.coordinate);

     $(element).popover({
       placement: 'top',
       html: true,
     });

     // fix to update popup content dynamically
     $(element).data('bs.popover').options.content = 'Firstname:&nbsp;' + feature.get('firstname') + '<br/>Lastname:&nbsp;' + feature.get('lastname') + '<br/>Sex:&nbsp;' + feature.get('sex') + '<br />Age:&nbsp;' + feature.get('age');

     $(element).popover('show');
   } else {
     $(element).popover('destroy');
   }
  });

  // change mouse cursor when over marker
  map.on('pointermove', function(e) {
    if (e.dragging) {
      $(element).popover('destroy');
      return;
    }
    var pixel = map.getEventPixel(e.originalEvent);
    var hit = map.hasFeatureAtPixel(pixel);
    var target = map.getTarget();
    var jTarget = typeof target === "string" ? $("#" + target) : $(target);
    if(hit) {
      jTarget.css("cursor", "pointer");
    } else {
      jTarget.css("cursor", "");
    }
  });
});

function addMarker(person, address) {
  $.get("http://nominatim.openstreetmap.org/search", {
  format: 'json',
  country: address.country,
  state: address.state,
  city: address.city,
  street: address.housenumber + ' ' + address.streetname,
  }, function(data) {
    var lat = parseFloat(data[0].lat);
    var lon = parseFloat(data[0].lon);

    var iconFeature = new ol.Feature({
      geometry: new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857')),
      firstname: person.firstname,
      lastname: person.lastname,
      sex: person.sex,
      age: person.age
    });

    iconFeature.setStyle(iconStyle);

    vectorSource.addFeature(iconFeature);
   });
}
