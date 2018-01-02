package dietgerpieters.werkstuk.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.GeolocationApi;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.MyTaskParam;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;
import dietgerpieters.werkstuk.Threading.InitMapTask;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Dietger (Pantani) on 31/12/2017.
 */

public class MapsTabFragment extends Fragment implements OnMapReadyCallback {

    private MyTaskParam myTaskParam;
    private GoogleMap mMap;
    private OnFragmentInteractionListener mListener;
    private Button button;
    private static Wedstrijd w;
    private static AppDatabase mDb;
    private LocationManager mLocationManager;
    private static LatLng latLngCurrent;
    private static Geocoder gc;
    private DistanceMatrixApiRequest distanceMatrixApiRequest;


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
        this.gc = new Geocoder(getContext(), Locale.getDefault());

        mDb = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();

        Bundle extras = getActivity().getIntent().getExtras();
        w = (Wedstrijd) extras.getSerializable("wedstrijd");



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
            mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
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


        InitMapTask task = new InitMapTask();

        try {
            if (WedstrijdController.isInternetAvailable())
                myTaskParam = task.execute(new MyTaskParam(w,mMap)).get();

            if (myTaskParam != null){

                this.mMap = myTaskParam.getGoogleMap();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


/*      if (WedstrijdController.isInternetAvailable()) {


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

                String vAdres = w.getVertrekAdres();


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
        } else {
            Toast.makeText(getContext(), "Er is geen internet verbinding", Toast.LENGTH_LONG);

        }*/
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            latLngCurrent = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLngCurrent).title("Current position"));






            DateTime now = new DateTime();
            try {






                double lat = location.getLatitude();
                double lon = location.getLongitude();
                int maxResults = 1;


                List<Address> addresses = gc.getFromLocation(lat, lon, maxResults);


                // Handle case where no address was found.
                if (addresses == null || addresses.size()  == 0) {

                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressFragments = new ArrayList<String>();

                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread.
                    for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));
                    }

                    String[] origin = {TextUtils.join(System.getProperty("line.separator"),
                            addressFragments)};
                    String[] vertrekAdres = {w.getVertrekAdres()};

                    DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING).origin(origin[0]).destination(w.getVertrekAdres()).departureTime(now).await();

                    addPolyline(result,mMap);


                    distanceMatrixApiRequest = DistanceMatrixApi.newRequest(getGeoContext());
                    DistanceMatrix distanceMatrix = distanceMatrixApiRequest.origins(origin).
                            destinations(vertrekAdres).
                            mode(TravelMode.DRIVING).
                            avoid(DirectionsApi.RouteRestriction.TOLLS).
                            language("nl-BE").await();


                    DistanceMatrixRow row = distanceMatrix.rows[0];
                    DistanceMatrixElement[] element = row.elements;

                    System.out.println(distanceMatrix);
                    System.out.println(row.toString());
                    System.out.println(element);
                    System.out.println(element[0].toString());



                    BufferedReader reader = null;






                            GeolocationApi.Response response = new GeolocationApi.Response();


                    response.getResult();




                    // Add a marker in Sydney and move the camera
                    Marker marker = mMap.addMarker(new MarkerOptions().
                            position(myTaskParam.getLatLng()).title(w.getTitel()).snippet("Reisduur: " + element[0].duration.humanReadable + ", " + "Afstand: " + element[0].distance));
                    marker.showInfoWindow();

                }




            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    private GeoApiContext getGeoContext() {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCJOitdqPwgXCgIPw0__SRt64lfKFHp_xw")
                .build();

        return context;
    }
    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
        polyline.remove();

        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }
}

