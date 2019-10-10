package com.example.alarstudios.activities;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.alarstudios.R;
import com.example.alarstudios.model.Datum;
import com.example.alarstudios.utils.ConstantManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CardActivity extends BaseActivity implements OnMapReadyCallback {

  public static final String TAG = CardActivity.class.getSimpleName();

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
  private Datum mDatum;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ButterKnife.bind(this);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      mDatum = (Datum) bundle.getSerializable(ConstantManager.DATA);
      setData();
    }
    mMapView.onCreate(savedInstanceState);
    mMapView.getMapAsync(this);

  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_card;
  }

  private void setData() {
    mCardName.setText(mDatum.getName());
    mCardId.setText(mDatum.getId());
    mCardCountry.setText(mDatum.getCountry());
    mCardLat.setText(String.valueOf(mDatum.getLat()));
    mCardLon.setText(String.valueOf(mDatum.getLon()));
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
    map.setMyLocationEnabled(true);

    LatLng latLng = new LatLng(mDatum.getLat(), mDatum.getLon());
    map.addMarker(new MarkerOptions().position(latLng).title("Marker"));

    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    map.setMinZoomPreference(15);

  }
}
