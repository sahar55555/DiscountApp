package elkady.discountapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;

import javax.net.ssl.HttpsURLConnection;

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
    private static final String SERVER_URL_BASE = "https://pricklys-wwwss32.ssl.supercp.com/DiscountApp"; // SSL version of prickly soft
    private static final String TIMESTAMP_URL = SERVER_URL_BASE + "/api/lists/products";
    // ... configuration
    private static final int READ_TIMEOUT = 10000; // milliseconds
    private static final int CONNECT_TIMEOUT = 15000; // milliseconds
    private static final String CONNECT_METHOD = "POST"; // don't have a use for POST yet
    private static final boolean DO_INPUT = true;

    // connection vars
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;

    // checksum
    private long crc32CheckSum;

    public long getCrc32CheckSum() { return crc32CheckSum; }


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
                Log.e("doInBackground", e.toString());
                return "Unable to retrieve web page. URL may be invalid. " + e;
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
            int len = 1024 * 1024;

            try {
                URL url = new URL(URLString);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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

            // the empty spots in the char[] cause parsing problems,
            //  transfer data to an exactly sized array
            char[] truncated = TruncateCharBuf(buffer, numRead);
            String str_truncated = new String(truncated);
            byte[] byte_truncated = str_truncated.getBytes(StandardCharsets.UTF_8);
            CRC32 crc32 = new CRC32();
            crc32.update(byte_truncated);
            crc32CheckSum = crc32.getValue();
            return new String(truncated);
        }

        char[] TruncateCharBuf(char[] buf, int newSize) {
            char[] truncated = new char[newSize];
            for (int i = 0; i < newSize; i++) truncated[i] = buf[i];
            return truncated;
        }

        public String ReadIntoString(InputStream stream) throws IOException {
            Reader reader = new InputStreamReader(stream, "UTF-8");
            int numRead, chunk = 1024;
            char[] buffer = new char[chunk];

            StringWriter writer = new StringWriter();

            do {
                numRead = reader.read(buffer, 0, chunk);
                if (numRead > 0) writer.write(buffer, 0, numRead);

            }while (numRead != -1);

            return writer.toString();
        }
    }
}
