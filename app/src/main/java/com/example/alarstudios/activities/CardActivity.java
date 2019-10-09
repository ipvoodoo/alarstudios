package com.example.alarstudios.activities;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.alarstudios.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class CardActivity extends BaseActivity implements OnMapReadyCallback {

  @BindView(R.id.mapView)
  MapView mMapView;
  @BindView(R.id.card_name)
  TextView mCardName;
  @BindView(R.id.card_id)
  TextView mCardId;
  @BindView(R.id.card_country)
  TextView mCardCountry;
  @BindView(R.id.card_lat)
  TextView mCardLat;
  @BindView(R.id.card_lon)
  TextView mCardLon;

  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(this);
    map.getUiSettings().setMyLocationButtonEnabled(false);
    map.setMyLocationEnabled(true);

    MapsInitializer.initialize(this);


  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_card;
  }

  @Override
  public void onResume() {
    mMapView.onResume();
    super.onResume();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mMapView.onDestroy();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;
    map.getUiSettings().setMyLocationButtonEnabled(false);
    map.setMyLocationEnabled(true);

    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
    map.animateCamera(cameraUpdate);
  }
}
