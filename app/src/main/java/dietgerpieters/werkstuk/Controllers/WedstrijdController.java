package dietgerpieters.werkstuk.Controllers;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.InetAddress;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.Threading.JsonTask;
import dietgerpieters.werkstuk.Threading.RetrieveInternetTask;

/**
 * Created by User on 6/12/2017.
 */

public class WedstrijdController {
    private static JsonTask jsonTask;

    public static List<Wedstrijd> initWedstrijdDB(String url){
        jsonTask = new JsonTask();
        List<Wedstrijd> wedstrijden = new ArrayList<>();
        Date datum = Calendar.getInstance().getTime();
        Wedstrijd wedstrijd;

        try {
            JSONObject jsonObject = new JSONObject(jsonTask.execute(url).get());


            JSONArray jsonArray1 =  jsonObject.getJSONArray("Profs");
            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");


            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    String vertrekString = oneObject.getString("vertrekGemeente");
                    String aankomstString = oneObject.getString("aankomstGemeente");


                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.PROFS, pk);
                    wedstrijd.setVertrekAdres(vertrekString);
                    wedstrijd.setAankomstAdres(aankomstString);

                    wedstrijden.add(wedstrijd);




                } catch (JSONException e) {
                    // Oops
                }
            }

            jsonArray1 =  jsonObject.getJSONArray("Elite zonder contract");
            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    String vertrekString = oneObject.getString("vertrekGemeente");
                    String aankomstString = oneObject.getString("aankomstGemeente");


                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.ELITEZC, pk);
                    wedstrijd.setAankomstAdres(aankomstString);
                    wedstrijd.setVertrekAdres(vertrekString);

                    wedstrijden.add(wedstrijd);




                } catch (JSONException e) {
                    // Oops
                }
            }
            jsonArray1 =  jsonObject.getJSONArray("Belofte");
            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.BELOFTEN, pk);


                    wedstrijden.add(wedstrijd);




                } catch (JSONException e) {
                    // Oops
                }
            }
            jsonArray1 =  jsonObject.getJSONArray("Junioren");
            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.JUNIOREN, pk);


                    wedstrijden.add(wedstrijd);




                } catch (JSONException e) {
                    // Oops
                }
            }
            jsonArray1 =  jsonObject.getJSONArray("Nieuwelingen");
            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.NIEUWELINGEN, pk);


                    wedstrijden.add(wedstrijd);




                } catch (JSONException e) {
                    // Oops
                }
            }
            jsonArray1 =  jsonObject.getJSONArray("Aspiranten");
            for (int i=0; i < jsonArray1.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray1.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, Wedstrijd.Categorie.ASPIRANTEN, pk);


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
    public static List<Wedstrijd> getAanbevolenWedstrijden(Wedstrijd.Categorie categorie, String url, ArrayList<Wedstrijd> gekozenWedstrijden){
        jsonTask = new JsonTask();
        List<Wedstrijd> wedstrijden = new ArrayList<>();
        Date datum = Calendar.getInstance().getTime();
        Wedstrijd wedstrijd;

        try {

            JSONObject jsonObject = new JSONObject(jsonTask.execute(url).get());
            String catAfterToString;


            switch (categorie){
                case PROFS:
                    catAfterToString = "Profs";
                    break;
                case ELITEZC:
                    catAfterToString = "Elite zonder contract";
                    break;
                case BELOFTEN:
                    catAfterToString = "Belofte";
                    break;
                case JUNIOREN:
                    catAfterToString = "Junioren";
                    break;
                case NIEUWELINGEN:
                    catAfterToString = "Nieuwelingen";
                    break;
                case ASPIRANTEN:
                    catAfterToString = "Aspiranten";
                    break;
                default:
                    catAfterToString = "Profs";

            }

            JSONArray jsonArray =  jsonObject.getJSONArray(catAfterToString);
            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");


            for (int i=0; i < jsonArray.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, categorie, pk);



                    boolean erin = false;
                    for (Wedstrijd w : gekozenWedstrijden){
                        if (w.getId() == wedstrijd.getId()){
                            erin = true;
                        }

                    }
                    if (!erin){
                        wedstrijden.add(wedstrijd);

                    }


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
    public static List<Wedstrijd> getWedstrijdenMetDatum(String url, Date dateVan, Date dateTot, String categorie){
        jsonTask = new JsonTask();
        Wedstrijd wedstrijd;
        List<Wedstrijd> wedstrijden = new ArrayList<>();
        Date datum = Calendar.getInstance().getTime();
        try {
            JSONObject jsonObject = new JSONObject(jsonTask.execute(url).get());
            JSONArray jsonArray =  jsonObject.getJSONArray(categorie);

            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
            Wedstrijd.Categorie categorie1;

            switch (categorie){
                case "Profs":
                    categorie1 = Wedstrijd.Categorie.PROFS;
                    break;
                case "Elite zonder contract":
                    categorie1 = Wedstrijd.Categorie.ELITEZC;
                    break;
                case "Belofte":
                    categorie1 = Wedstrijd.Categorie.BELOFTEN;
                    break;
                case "Junioren":
                    categorie1 = Wedstrijd.Categorie.JUNIOREN;
                    break;
                case "Nieuwelingen":
                    categorie1 = Wedstrijd.Categorie.NIEUWELINGEN;
                    break;
                case "Aspiranten":
                    categorie1 = Wedstrijd.Categorie.ASPIRANTEN;
                    break;
                default: categorie1 = Wedstrijd.Categorie.PROFS;
            }


            for (int i=0; i < jsonArray.length(); i++)
            {

                try {
                    JSONObject oneObject = jsonArray.getJSONObject(i);
                    // Pulling items from the array
                    String oneObjectsItem = oneObject.getString("wedstrijdNaam");
                    String oneObjectsItem2 = oneObject.getString("afstand");
                    String datumString = oneObject.getString("datum");
                    String vertrekString = oneObject.getString("vertrekGemeente");
                    String aankomstString = oneObject.getString("aankomstGemeente");

                    int pk = oneObject.getInt("id");

                    try {
                        datum = dateFormat.parse(datumString);

                    } catch (Exception e){
                        //oopsie
                    }


                    int afstand = Integer.parseInt(oneObjectsItem2);
                    wedstrijd = new Wedstrijd(oneObjectsItem, afstand, 50, datum, categorie1, pk);
                    wedstrijd.setAankomstAdres(aankomstString);
                    wedstrijd.setVertrekAdres(vertrekString);



                    if (wedstrijd.getVertrekDatum().after(dateVan) && wedstrijd.getVertrekDatum().before(dateTot)){
                        wedstrijden.add(wedstrijd);
                    }

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
    public static boolean isInternetAvailable() {
        RetrieveInternetTask task = new RetrieveInternetTask();
        boolean isOnline = false;
        String url = "www.google.com";

        try {
            isOnline = task.execute(url).get().booleanValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isOnline;
    }

}

