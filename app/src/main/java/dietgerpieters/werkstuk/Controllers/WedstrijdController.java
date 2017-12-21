package dietgerpieters.werkstuk.Controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.Threading.JsonTask;

/**
 * Created by User on 6/12/2017.
 */

public class WedstrijdController {
    private static JsonTask jsonTask;

    public static List<Wedstrijd> getWedstrijdenProfs(String url){
        jsonTask = new JsonTask();
        Wedstrijd wedstrijd;
        List<Wedstrijd> wedstrijden = new ArrayList<>();
        Date datum = Calendar.getInstance().getTime();
        try {
            JSONObject jsonObject = new JSONObject(jsonTask.execute(url).get());
            JSONArray jsonArray =  jsonObject.getJSONArray("Profs");



            for (int i=0; i < jsonArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum);
                    wedstrijden.add(wedstrijd);

                } catch (JSONException e) {
                    // Oops
                }
            }
            return wedstrijden;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
