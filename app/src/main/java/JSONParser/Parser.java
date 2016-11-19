package JSONParser;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Simon on 8/14/2016.
 */
public class Parser {
    public static String RepsonceParser(String responce){
        JSONArray ar = null;
        final String TAG_STATUS="ar";
        String res="";
        try {
            JSONObject object = new JSONObject(res);
            ar = object.getJSONArray(TAG_STATUS);
            for (int i =0; i < ar.length(); i++){
                JSONObject obj = ar.getJSONObject(i);
                res = obj.getString("status");
            }
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
