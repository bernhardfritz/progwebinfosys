$.get('/personswithaddress', function(results) {
  console.log(JSON.parse(results));

  var iconFeature = new ol.Feature({
    geometry: new ol.geom.Point(ol.proj.transform([11.383333, 47.266667], 'EPSG:4326', 'EPSG:3857')),
    firstname: 'Hans',
    lastname: 'Wurst',
  });

  var iconStyle = new ol.style.Style({
    image: new ol.style.Icon({
      anchor: [0.5, 1.0],
      opacity: 1,
      src: '/assets/images/marker-icon.png'
    })
  });

  iconFeature.setStyle(iconStyle);

  var vectorSource = new ol.source.Vector({
    features: [iconFeature]
  });

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
      zoom: 13
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
       'placement': 'top',
       'html': true,
       'content': feature.get('firstname') + ' ' + feature.get('lastname')
     });
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
