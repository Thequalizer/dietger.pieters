package dietgerpieters.werkstuk.Fragments;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Dietger (Pantani) on 31/12/2017.
 */

public class MapsTabFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;
    private Button button;
    private static Wedstrijd w;
    private static AppDatabase mDb;
    private LocationManager mLocationManager;
    private static LatLng latLngCurrent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps_tab, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDb = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        Bundle extras = getActivity().getIntent().getExtras();
        w = (Wedstrijd) extras.getSerializable("wedstrijd");


        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Long.valueOf("1"), Float.valueOf("5"), mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Long.valueOf("1"), Float.valueOf("5"), mLocationListener);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCJOitdqPwgXCgIPw0__SRt64lfKFHp_xw")
                .build();


        GeocodingResult[] geocodingApiRequest = null;
        try {
            geocodingApiRequest = GeocodingApi.geocode(context, "Alsemberg").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(gson.toJson(geocodingApiRequest));

        GeocodingResult[] geocodingResult = new GeocodingResult[0];
        try {
            geocodingResult = GeocodingApi.geocode(context, "Alsemberg").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(gson.toJson(geocodingResult[0].geometry.location.lat));
        System.out.println(gson.toJson(geocodingResult[0].geometry.location.lng));

        LatLng sydney = new LatLng(Double.parseDouble(gson.toJson(geocodingResult[0].geometry.location.lat)), Double.parseDouble(gson.toJson(geocodingResult[0].geometry.location.lng)));


        GeocodingResult[] results = new GeocodingResult[0];
        try {
            /*
            * String vAdres = w.getVertrekAdres();
            *
            * */
            results = GeocodingApi.geocode(context, "Alsemberg").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(gson.toJson(results[0].geometry));


        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Vertrek"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            latLngCurrent = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLngCurrent).title("Current position"));

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}

