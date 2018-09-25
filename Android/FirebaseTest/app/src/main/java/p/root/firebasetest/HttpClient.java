package p.root.firebasetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Tim on 3/29/18.
 */

/**
 * To connect to the server, just call HttpClient.getHttpClient( getSupportFragmentManager(), SERVER_ADDRESS).
 * Send requests by .GET (no body message) or .POST (with a message)
 */
public class HttpClient extends Fragment {

    // Used to find existing HttpClient
    private static final String TAG = "HTTPCLIENT";
    // The Fragment class uses key-pairs and setters for arguments instead of a constructor
    private static final String URL_KEY = "URLKEY";

    /** Finds an existing or creates a connection with the server. This class is asynchronous
     *  - send a request and at some later time get the data. This should always be called from the same class.
     *  This factory ensures that no duplicate HttpClients are created - in the case that the activity
     *  calling restarts or has some other unwanted behavior.
     *
     * @param fManager Call getSupportFragmentManager()
     * @param URL Specifies the server (Jetty HTTP server address)
     * @return
     */
    public static HttpClient getHttpClient(FragmentManager fManager, String URL)
    {
        HttpClient doesOneExist = (HttpClient) (fManager.findFragmentByTag(HttpClient.TAG));
        if(doesOneExist == null)
        {
            doesOneExist = new HttpClient();
            Bundle args = new Bundle();
            args.putString(URL_KEY, URL);
            doesOneExist.setArguments(args);
            fManager.beginTransaction().add(doesOneExist, TAG).commit();
        }

        return doesOneExist;
    }


    private class HttpsConnection extends AsyncTask<String, Integer, String> {

        /** This method formats the message and sends with the appropriate HTTP protocol.
         *
         * @param arguments Encapulates the connection, its type, and its message.
         *                  arguments[0] is the URL of the server
         *                  arguments[1] is the HTTP connection type (defaults to GET)
         *                  arguments[2] is the message to be passed
         *
         * @return The result of the server response or the error message.
         */
        @Override
        protected String doInBackground(String... arguments) {

            // The task should still run, the arguments exists and URL is not null
            if(!isCancelled() && arguments.length > 1 && arguments[0] != null) {
                try {
                    URL url = new URL(arguments[0]);
                    send(url, arguments[1], arguments[2]);
                } catch (Exception e) {
                    return e.getMessage();
                }
            }
            return null;
        }

        protected String send(URL url, String RequestMethod, String body) throws IOException
        {
            InputStream stream = null;
            HttpsURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                connection.setReadTimeout(3000);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connection.setConnectTimeout(3000);
                // For this use case, set HTTP method to GET.
                connection.setRequestMethod(RequestMethod);
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                connection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                connection.connect();

                // If is using POST, set up body message here
                if(RequestMethod.equals("POST"))
                {
                    OutputStream streamWrite = new BufferedOutputStream(connection.getOutputStream());
                    writeStream(streamWrite, body);
                }

                // Method to inform the UI of any changes. Triggers the onProgressUpdate(Progress...) in the UI thread
                publishProgress();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                // Retrieve the response body as an InputStream.
                stream = connection.getInputStream();

                // Should inform the UI thread here that the connection was successful
                publishProgress();

                if (stream != null) {
                    // Converts Stream to String with max length of 500.
                    result = readStream(stream, 500);

                    // Inform the UI thread that data was found
                    publishProgress();
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        private void writeStream(OutputStream stream, String message) throws IOException
        {
            OutputStreamWriter write = new OutputStreamWriter(stream, "UFT-8");

            write.append(message);
            write.flush();
            write.close();

        }

        private String readStream(InputStream stream, int maxLength) throws IOException {
            String result = null;
            // Read InputStream using the UTF-8 charset.
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            // Create temporary buffer to hold Stream data with specified max length.
            char[] buffer = new char[maxLength];
            // Populate temporary buffer with Stream data.
            int numChars = 0;
            int readSize = 0;
            while (numChars < maxLength && readSize != -1) {
                numChars += readSize;
                int pct = (100 * numChars) / maxLength;

                // Inform the UI that data is being streamed in
                publishProgress();
                readSize = reader.read(buffer, numChars, buffer.length - numChars);
            }
            if (numChars != -1) {
                // The stream was not empty.
                // Create String that is actual length of response body if actual length was less than
                // max length.
                numChars = Math.min(numChars, maxLength);
                result = new String(buffer, 0, numChars);
            }
            return result;
        }
    }

}