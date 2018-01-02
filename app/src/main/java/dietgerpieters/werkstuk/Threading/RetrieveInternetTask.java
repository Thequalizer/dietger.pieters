package dietgerpieters.werkstuk.Threading;

import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by User on 2/01/2018.
 */

public class RetrieveInternetTask extends AsyncTask<String, Boolean, Boolean> {


        protected Boolean doInBackground(String... params)
        {
            InetAddress addr = null;
            try
            {
                addr = InetAddress.getByName(params[0]);
            }

            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            if(addr == null){
                return false;
            } else
            {
                return true;
            }
        }


    @Override
    protected void onPostExecute(Boolean aBoolean) {


        super.onPostExecute(aBoolean);


    }
}
