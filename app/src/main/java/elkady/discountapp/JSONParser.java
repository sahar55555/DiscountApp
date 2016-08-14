package elkady.discountapp;
import android.util.Log;

import org.json.*;

import java.util.ArrayList;

/**
 * Created by david on 8/13/16.
 */
public class JSONParser {
    private static final String TAG = "JSONParser";
    private String jsonText;
    private JSONTokener jsonTokener;
    private JSONObject root;
    JSONParser(String input) throws JSONException {
        jsonText = input;
        jsonTokener = new JSONTokener(jsonText);
        try {
            root = (JSONObject)jsonTokener.nextValue();
            // retrieve errors, numrows
            parseMeta();
            // retrieve data rows
            parseData();
        }
        catch (JSONException ex) {
            Log.e(TAG, "root = jsonTokener(): " + ex.toString());
            root = null;
            throw ex;
        }
    }
    private JSONArray errors;
    private int numRows;
    void parseMeta() throws JSONException {
        // retrieve any error messages in the JSON object
        try {
            errors = root.getJSONArray("errors");
        }
        catch (JSONException ex) {
            Log.e(TAG, "getJSONArray(\"errors\"):" + ex.toString());
            throw ex;
        }
        // retrieve the numrows value
        try {
            numRows = root.getInt("numrows");
        }
        catch (JSONException ex) {
            Log.e(TAG, "getInt(\"numrows\"):" + ex.toString());
            throw ex;
        }
    }

    private ArrayList<Product> ProductsArrayList;
    public ArrayList<Product> GetArrayList() { return ProductsArrayList;
    }
    public int size() {
        if (ProductsArrayList == null) {
            return 0;
        }
        return ProductsArrayList.size();
    }

    void parseData() throws JSONException {
        JSONArray rows;
        ProductsArrayList = new ArrayList<>();
        try {
            rows = root.getJSONArray("rows");
        }
        catch (JSONException ex) {
            Log.e(TAG, "getJSONArray" + ex.toString());
            throw ex;
        }

        for (int i = 0; i < rows.length(); i++) {
            try {
                JSONObject obj = (JSONObject) rows.get(i);
                String deal = obj.getString("deal");
                String store = obj.getString("store");
                String category = obj.getString("category");
                String name = obj.getString("name");
                // create a new Product object
                Product newProduct = new Product(name, category, store, deal);
                ProductsArrayList.add(newProduct);
            }
            catch (JSONException ex) {
                Log.e(TAG, "Error Parsing " + i + "th object: " + ex.toString());
                // should we continue trying to parse?
                throw ex;
            }


        }

    }
}
