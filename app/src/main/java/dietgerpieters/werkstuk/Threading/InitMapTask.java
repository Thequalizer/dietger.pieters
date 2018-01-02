package dietgerpieters.werkstuk.Threading;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

import dietgerpieters.werkstuk.Controllers.WedstrijdController;
import dietgerpieters.werkstuk.Fragments.MapsTabFragment;
import dietgerpieters.werkstuk.Models.MyTaskParam;
import dietgerpieters.werkstuk.Models.Wedstrijd;

/**
 * Created by User on 2/01/2018.
 */

public class InitMapTask extends AsyncTask<MyTaskParam, Void, GoogleMap> {

    private LatLng latLng;
    private boolean aBoolean;
    private Wedstrijd w;
    private DistanceMatrixApiRequest distanceMatrixApiRequest;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


        aBoolean = WedstrijdController.isInternetAvailable();

    }

    protected GoogleMap doInBackground(MyTaskParam... googleMaps) {

        GoogleMap googleMap = googleMaps[0].getGoogleMap();
        String adres = googleMaps[0].getW().getVertrekAdres();
        w = googleMaps[0].getW();

        if (aBoolean) {


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCJOitdqPwgXCgIPw0__SRt64lfKFHp_xw")
                    .build();



            GeocodingResult[] geocodingResult = new GeocodingResult[0];





            try {
                geocodingResult = GeocodingApi.geocode(context, adres).await();


            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            LatLng sydney = new LatLng(Double.parseDouble(gson.toJson(geocodingResult[0].geometry.location.lat)), Double.parseDouble(gson.toJson(geocodingResult[0].geometry.location.lng)));





            latLng = sydney;

            return googleMap;
        }
        return null;
    }

    @Override
    protected void onPostExecute(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(9));


        /*// Add a marker in Sydney and move the camera
        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Vertrek").snippet(w.getVertrekAdres()));
        marker.showInfoWindow();*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        super.onPostExecute(googleMap);



    }
}
