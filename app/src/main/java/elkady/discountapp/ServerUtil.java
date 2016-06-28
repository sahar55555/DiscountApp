package elkady.discountapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by david on 6/27/16.
 */
public class ServerUtil {

    // provide a generic callback
    public interface ProcessGET {
        void process(String data);
    }

    // settings
    // ... server location
    private static final String SERVER_URL_BASE = "http://www.prfol.org/phi";
    private static final String TIMESTAMP_URL = SERVER_URL_BASE + "/timestamp.php";
    // ... configuration
    private static final int READ_TIMEOUT = 10000; // milliseconds
    private static final int CONNECT_TIMEOUT = 15000; // milliseconds
    private static final String CONNECT_METHOD = "GET"; // don't have a use for POST yet
    private static final boolean DO_INPUT = true;

    // connection vars
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;


    public ServerUtil(Activity parentActivity) {
        connMgr = (ConnectivityManager)parentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
    }

    public void getTimeStamp(ProcessGET callback) {

        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask(callback).execute(TIMESTAMP_URL);
        }
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        private ProcessGET callbackFxn;

        DownloadWebpageTask(ProcessGET callback) {
            super();
            callbackFxn = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(url);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            if (callbackFxn != null) {
                callbackFxn.process(result);
            }

        }
        private String downloadUrl(String URLString) throws IOException {
            InputStream is = null;
            int len = 4028;

            try {
                URL url = new URL(URLString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECT_TIMEOUT);
                conn.setRequestMethod(CONNECT_METHOD);
                conn.setDoInput(DO_INPUT);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = StreamToString(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        // Reads an InputStream and converts it to a String.
        public String StreamToString(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            int numRead = reader.read(buffer);
            char[] truncated = new char[numRead];
            // the empty spots in the char[] cause parsing problems,
            //  transfer data to an exactly sized array
            for (int i = 0; i < numRead; i++) truncated[i] = buffer[i];
            return new String(truncated);
        }
    }
}
