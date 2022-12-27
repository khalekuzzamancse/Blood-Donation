package com.example.blooddonation;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.blooddonation.databinding.ActivityMapsMarkerBinding;

public class MapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsMarkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsMarkerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarker(23.633841,89.066856);
        addMarker(24.403490,90.772301);
        addMarker(24.934725,90.751511);
        addMarker(23.754253,90.393425);
        addMarker(23.720881,90.483269);
        addMarker(23.777628,90.405449);
        addMarker(23.783726,90.344246);
        addMarker(24.899536,91.845856);
        addMarker(25.080624,91.421356);





        LatLng bd = new LatLng(23.6850, 90.3563);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bd));
//        LatLng currentLatLng = new LatLng(location.getLatitude(),
//                location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(bd, 5.5f);
        googleMap.moveCamera(update);
    }

    private void addMarker(Double la, Double lon) {   // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(la,lon))
                .title("A+")
                .icon(BitmapDescriptorFactory.fromBitmap(
                        getBitmapFromVectorDrawable(
                                this, R.drawable.icon_blood_group))));

    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}