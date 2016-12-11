package udacity.nanodegree.android.popularmovies.backend;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;

/**
 * Created by yehia on 12/12/16.
 */

public class ApiRequests {

    public static Observable<String> fetchData(String baseUrl, String apiKey){
        return Observable.fromCallable(() -> {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String posterJsonStr = null;


            try {

                final String APP_ID = "api_key";

                Uri uri = Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(APP_ID, apiKey).build();

                URL url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                posterJsonStr = buffer.toString();
            }

            catch (IOException e){
                return null;
            }

            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                return posterJsonStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
