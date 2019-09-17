package com.voyager.boorna.activity.TripDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.voyager.boorna.R;
import com.voyager.boorna.activity.TripDetail.adapter.TripOtherDetailsAdapter;
import com.voyager.boorna.activity.TripDetail.adapter.TripPickDropLocAdapter;
import com.voyager.boorna.activity.landing.model.CardList;
import com.voyager.boorna.activity.landing.model.TripOtherDetails;
import com.voyager.boorna.activity.landing.model.TripPickDropLoc;
import com.voyager.boorna.appconfig.Constances;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class TripDetailActivity extends AppCompatActivity implements OnMapReadyCallback,
                GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener{
    private static final String TAG = TripDetailActivity.class.getSimpleName();
    private GoogleMap mMap;
    MapView mvTripDetail;
    Toolbar tbTripDetail;

    AppCompatImageView ivTripDist;
    AppCompatTextView tvTripDist;
    AppCompatTextView tvTripCollection;
    AppCompatTextView tvTripDrops;
    AppCompatTextView tvTripCat;
    AppCompatTextView tvTripWeight;
    AppCompatTextView tvTripProductWidth;
    AppCompatTextView tvTripProductLength;
    AppCompatTextView tvTripProductHeight;
    AppCompatTextView tvTripPallet;
    AppCompatTextView tvTripPalletHeight;
    AppCompatTextView tvAdminInst;
    AppCompatTextView tvCustomerInst;
    AppCompatButton btnTripAccept;
    AppCompatButton btnTripReject;

    LinearLayout llTripPallet;
    LinearLayout llTripWHL;

    RecyclerView rvTripTravelDetails;
    RecyclerView rvTripOtherDetails;

    TripPickDropLocAdapter tripPickDropLocAdapter;
    ArrayList<TripPickDropLoc> tripPickDropLocs = new ArrayList<>();
    TripOtherDetailsAdapter tripOtherDetailsAdapter;
    ArrayList<TripOtherDetails> tripOtherDetails = new ArrayList<>();

    private GoogleDirection gd;
    private Double distance;
    private Document mDoc;
    LatLng pickPosition;
    LatLng otherPosition1;
    LatLng otherPosition2;
    LatLng dropPosition;

    CardList cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Intent intent = getIntent();
        tbTripDetail = findViewById(R.id.tbTripDetail);
        tbTripDetail.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(tbTripDetail);
        pickPosition = new LatLng(44.4268, 26.1025);
        otherPosition1 = new LatLng(45.7489, 21.2087);
        otherPosition2 = new LatLng(46.1512, 14.9955);
        dropPosition = new LatLng(45.4642, 9.1900);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mvTripDetail = findViewById(R.id.mvTripDetail);
        llTripPallet = findViewById(R.id.llTripPallet);
        llTripWHL = findViewById(R.id.llTripWHL);
        ivTripDist = findViewById(R.id.ivTripDist);
        tvTripDist = findViewById(R.id.tvTripDist);
        tvTripCollection = findViewById(R.id.tvTripCollection);
        tvTripDrops = findViewById(R.id.tvTripDrops);
        tvTripCat = findViewById(R.id.tvTripCat);
        tvTripWeight = findViewById(R.id.tvTripWeight);
        tvTripProductWidth = findViewById(R.id.tvTripProductWidth);
        tvTripProductLength = findViewById(R.id.tvTripProductLength);
        tvTripProductHeight = findViewById(R.id.tvTripProductHeight);
        tvTripPallet = findViewById(R.id.tvTripPallet);
        tvTripPalletHeight = findViewById(R.id.tvTripPalletHeight);
        tvAdminInst = findViewById(R.id.tvAdminInst);
        tvCustomerInst = findViewById(R.id.tvCustomerInst);
        btnTripAccept = findViewById(R.id.btnTripAccept);
        btnTripReject = findViewById(R.id.btnTripReject);
        rvTripTravelDetails = findViewById(R.id.rvTripTravelDetails);
        rvTripOtherDetails = findViewById(R.id.rvTripOtherDetails);

        mvTripDetail.onCreate(savedInstanceState);
        mvTripDetail.getMapAsync(this);

        cardList = intent.getParcelableExtra("CardList");
        if (cardList != null) {
            if(cardList.getTvProductPallets()!=null) {
                llTripWHL.setVisibility(View.GONE);
            }else {
                llTripPallet.setVisibility(View.GONE);
            }
            tvTripDist.setText(cardList.getTvTripTotDistance());
            tvTripCollection.setText(cardList.getTvLoadCnt());
            tvTripDrops.setText(cardList.getTvUnLoadCnt());
            tvTripCat.setText(cardList.getTvProductName());
            tvTripPallet.setText(cardList.getTvProductPallets());
            tvTripPalletHeight.setText("210cm");
            tvTripWeight.setText(cardList.getTvProductWeight());
            tvTripProductWidth.setText(cardList.getTvProductWidth());
            tvTripProductLength.setText(cardList.getTvProductLength());
            tvTripProductHeight.setText(cardList.getTvProductHeight());
            tvAdminInst.setText(cardList.getAdminInstruction());
            tvCustomerInst.setText(cardList.getCustomerInstruction());
            picDropLocList(cardList.getTripPickDropLocs());
            tripOtherList(cardList.getTripOtherDetails());
        } else {
        }


        tbTripDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });
    }

    private void picDropLocList(ArrayList<TripPickDropLoc> tripPickDropLocs ){
        System.out.println(TAG+" -- picDropLocList: ");
        // add a divider after each item for more clarity
        //rvHorizontalView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL));
        tripPickDropLocAdapter = new TripPickDropLocAdapter(tripPickDropLocs);
        rvTripTravelDetails.setHasFixedSize(true);
        rvTripOtherDetails.setLayoutFrozen(true);
        rvTripTravelDetails.setLayoutManager(new LinearLayoutManager(this));
        rvTripTravelDetails.setAdapter(tripPickDropLocAdapter);
        Gson gson = new Gson();
        String jsonString = gson.toJson(tripPickDropLocs);

        System.out.println(TAG+" -- picDropLocList: jsonString : "+jsonString);
    }

    private void tripOtherList(ArrayList<TripOtherDetails> tripOtherDetails ){
        System.out.println(TAG+" -- tripOtherList: ");
        // add a divider after each item for more clarity
        //rvHorizontalView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.HORIZONTAL));
        tripOtherDetailsAdapter = new TripOtherDetailsAdapter(tripOtherDetails);
        rvTripOtherDetails.setHasFixedSize(true);
        rvTripOtherDetails.setLayoutFrozen(true);
        rvTripOtherDetails.setLayoutManager(new LinearLayoutManager(this));
        rvTripOtherDetails.setAdapter(tripOtherDetailsAdapter);
        Gson gson = new Gson();
        String jsonString = gson.toJson(tripOtherDetails);

        System.out.println(TAG+" -- tripOtherList: jsonString : "+jsonString);
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

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        if (mMap != null) {

            Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                    .clickable(true)
                    .add(pickPosition,
                            otherPosition1,
                            otherPosition2,
                            dropPosition));
            mMap.addMarker(new MarkerOptions().position(pickPosition).title("BUCHAREST"));
            mMap.addMarker(new MarkerOptions().position(otherPosition1).title("TIMISOARA"));
            mMap.addMarker(new MarkerOptions().position(otherPosition2).title("SLOVENIA"));
            mMap.addMarker(new MarkerOptions().position(dropPosition).title("MILAN"));

            // Position the map's camera near Alice Springs in the center of Australia,
            // and set the zoom factor so most of Australia shows on the screen.
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(46.1512, 14.9955), 4));

            // Set listeners for click events.
            googleMap.setOnPolylineClickListener(this);
            googleMap.setOnPolygonClickListener(this);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mvTripDetail.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mvTripDetail.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mvTripDetail.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvTripDetail.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvTripDetail.onLowMemory();
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }
}
