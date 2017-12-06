package dietgerpieters.werkstuk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by User on 6/12/2017.
 */

public class WedstrijdController {
    private static JsonTask jsonTask;

    public static JSONArray getWedstrijdenProfs(String url){
        jsonTask = new JsonTask();
        try {
            JSONObject jsonObject = new JSONObject(jsonTask.execute(url).get());
            return jsonObject.getJSONArray("Profs");
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
