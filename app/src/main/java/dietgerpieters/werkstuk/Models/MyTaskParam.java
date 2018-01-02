package dietgerpieters.werkstuk.Models;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by User on 2/01/2018.
 */

public class MyTaskParam  {

    private Wedstrijd w;
    private GoogleMap googleMap;
    private LatLng latLng;

    public MyTaskParam(Wedstrijd w, GoogleMap googleMap) {
        this.w = w;
        this.googleMap = googleMap;

    }

    public Wedstrijd getW() {

        return w;
    }

    public void setW(Wedstrijd w) {
        this.w = w;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
