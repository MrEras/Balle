package com.we.alejandroalcaraz.ballet;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirstMapFragment mFirstMapFragment;
    int flag = 0;
    public double send_lat;
    public double send_long;
    Marker markerName = null;
    MarkerOptions marker;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences sp = getSharedPreferences("preference", ServiceActivity.MODE_PRIVATE);
        final int logged_in = sp.getInt("logged_in", -1);
        System.out.println(logged_in);

        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mFirstMapFragment)
                .commit();
        mFirstMapFragment.getMapAsync(this);

        Button btn_service = (Button) findViewById(R.id.request_service);

        btn_service.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference root_child = database_reference.child("pedidos").push();
                root_child.child("lat").setValue(send_lat);
                root_child.child("long").setValue(send_long);*/
                Toast.makeText(getApplicationContext(), "Se ha enviado la ubicacion: "+logged_in, Toast.LENGTH_LONG).show();
                if (logged_in == 1){
                    Intent intent = new Intent(ServiceActivity.this, PaymentOption.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(ServiceActivity.this, SignUp.class);
                    startActivity(intent);
                    finish();

                }
            }
        });



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Toca en el lugar donde quieres que recojamos tu carro");
            // alert.setMessage("Message");

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Your action here
                }
            });
            alert.show();

            googleMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                LatLng myPosition = new LatLng(latitude, longitude);
                markerName = googleMap.addMarker(new MarkerOptions().position(myPosition).title("Punto de reunión"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            } else {
                Toast.makeText(getApplicationContext(), "Porfavor activar la localización en ajustes", Toast.LENGTH_LONG).show();
            }
        }
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("Punto de reunión");

                if (markerName != null) {
                    markerName.setPosition(point);
                }
                else{
                    markerName = googleMap.addMarker(new MarkerOptions().position(point).title("Punto de reunión"));
                }
                send_lat = point.latitude;
                send_long = point.longitude;
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
}
