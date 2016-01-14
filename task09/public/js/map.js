var map = new ol.Map({
  target: 'map',
  layers: [
    new ol.layer.Tile({
      source: new ol.source.MapQuest({layer: 'osm'})
    })
  ],
  view: new ol.View({
    center: ol.proj.fromLonLat([11.383333, 47.266667]),
    zoom: 13
  })
});
