
package com.example.google.maps.wmstiles;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {
    protected static final String LOG_TAG = "ExampleApp";

    private GoogleMap map;
    private TileOverlay tileOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NONE);

        TileProvider tileProvider = WMSTileProviderFactory.getTileProvider(providers[0]);
        tileOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TileProvider tileProvider;
        switch (item.getItemId()) {
            case R.id.menu_earth_engine:
                tileOverlay.remove();
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[0]);
                map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
                return true;
            case R.id.menu_nationalatlas_precip:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[1]);
                tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                        .tileProvider(tileProvider));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4230, -98.7372), 0));
                return true;
            case R.id.menu_noaa_warnings:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[2]);
                tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                        .tileProvider(tileProvider));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4230, -98.7372), 0));
                return true;
            case R.id.menu_noaa_radar:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[3]);
                tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                        .tileProvider(tileProvider));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4230, -98.7372), 0));
                return true;
            case R.id.menu_usgs_counties:
                tileOverlay.remove();
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                tileProvider = WMSTileProviderFactory.getTileProvider(providers[4]);
                tileOverlay = map.addTileOverlay(new TileOverlayOptions()
                        .tileProvider(tileProvider));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.4230, -98.7372), 0));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private WMSProvider[] providers = {
            new WMSProvider()
                    .url("https://earthbuilder.google.com/10446176163891957399-13737975182519107424-4/wms/")
                    .layers("10446176163891957399-13761542579191661551-4"),
            new WMSProvider()
                    .url("http://webservices.nationalatlas.gov/wms")
                    .layers("precip,states"),
            new WMSProvider()
                    .url("http://gis.srh.noaa.gov/ArcGIS/services/watchWarn/MapServer/WMSServer")
                    .layers("0"),
            new WMSProvider()
                    .url("http://gis.srh.noaa.gov/ArcGIS/services/Radar_warnings/MapServer/WMSServer")
                    .crs("EPSG:102100")
                    .layers("0"),
            new WMSProvider()
                    .url("http://frameworkwfs.usgs.gov/framework/wms/wms.cgi")
                    .layers("Framework.COUNTY_OR_EQUIVAL")
                    .styles("County%20Styles")
    };
}
