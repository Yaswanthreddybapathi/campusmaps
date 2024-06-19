package com.example.campusmaps;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private EditText startLocation, endLocation;
    private Button getRouteButton;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        startLocation = findViewById(R.id.startLocation);
        endLocation = findViewById(R.id.endLocation);
        getRouteButton = findViewById(R.id.getRouteButton);

        // Initialize the map view
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        getRouteButton.setOnClickListener(v -> {
            String start = startLocation.getText().toString();
            String end = endLocation.getText().toString();
            if (!start.isEmpty() && !end.isEmpty()) {
                // Example coordinates, replace with actual logic to get coordinates from the input
                LatLng startLatLng = getCoordinatesForLocation(start);
                LatLng endLatLng = getCoordinatesForLocation(end);
                if (startLatLng != null && endLatLng != null) {
                    showRoute(startLatLng, endLatLng);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid locations", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please enter both locations", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap map) {
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 15));
    }

    private LatLng getCoordinatesForLocation(String location) {
        // Example hardcoded locations, replace with actual logic
        switch (location.toLowerCase()) {
            case "a":
                return new LatLng(0, 0);
            case "b":
                return new LatLng(0.01, 0.01);
            case "c":
                return new LatLng(0.02, 0.02);
            case "d":
                return new LatLng(0.01, 0.02);
            default:
                return null;
        }
    }

    private void showRoute(LatLng start, LatLng end) {
        if (googleMap != null) {
            googleMap.clear();
            List<LatLng> routePoints = new ArrayList<>();
            // Example route, replace with actual routing logic
            routePoints.add(start);
            routePoints.add(new LatLng((start.latitude + end.latitude) / 2, (start.longitude + end.longitude) / 2));
            routePoints.add(end);

            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(routePoints)
                    .color(0xFF0000FF)
                    .width(5);
            googleMap.addPolyline(polylineOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
}
