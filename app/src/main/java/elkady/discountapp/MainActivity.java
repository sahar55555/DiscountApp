package elkady.discountapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// for date parsing
import org.json.JSONException;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final static String TAG = "MainActivity";
    public final static String STORENAME_EXTRA="elkady.discountapp.STORENAME";
    private ArrayList<Product> ProductArrayList = null;

    private ServerUtil server;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.http_test).setOnClickListener(this);

        View kingsoopers_button=findViewById(R.id.kingsoopers_button);
        kingsoopers_button.setOnClickListener(this);

        View albertsons_button=findViewById(R.id.swalb_button);
        albertsons_button.setOnClickListener(this);

        View wholefoods_button=findViewById(R.id.wholefoods_button);
        wholefoods_button.setOnClickListener(this);

        View sprouts_button=findViewById(R.id.sprouts_button);
        sprouts_button.setOnClickListener(this);

        /** Currently unused and throwing exception
         *String s1 = "Mon, 27 Jun 2016 00:04:41"; // as supplied by www.prfol.org/phi/timestamp.php
        String s2 = "Mon, 27 Jun 2016 00:06:34";
        compareTimestamps(s1, s2);**/

        server = new ServerUtil(this);

    }

    private void updateFromServer() {
        final Context context = this; // final so it can be used in the callback
        ServerUtil.ProcessGET callback = new ServerUtil.ProcessGET() {
            @Override
            public void process(String data) {
                long checksum = server.getCrc32CheckSum();
                String message = String.format("Data is %d bytes, checksum=%x", data.length(), checksum);
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                Log.d(TAG, message);

                JSONParser dp = null;
                // parse data into JSON object
                try {
                    dp = new JSONParser(data);
                    //Toast.makeText(context, "Parsed " + dp.size() + " rows.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Parsed " + dp.size() + " rows.");
                    ProductArrayList = dp.GetArrayList();
                    Log.d(TAG, "ProductArrayList.size() = " + ProductArrayList.size());
                    // Load the ArrayList screen
                    if (ProductArrayList != null) {
                        Intent intent = new Intent(MainActivity.this, FullListActivity.class);
                        intent.putExtra("ARRAYLIST", ProductArrayList);
                        startActivity(intent);
                    }

                }
                catch(JSONException e) {
                    Log.e(TAG, "error parsing JSON data: " + e.toString());

                }
            }
        };

        server.getTimeStamp(callback);
    }

    public int compareTimestamps(String oldTimestamp,  String newTimeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date frstdate = sdf.parse(oldTimestamp);
            Date newdate = sdf.parse(newTimeStamp);
            if (frstdate.compareTo(newdate) < 0)
            {
                return -1;
            } else if(frstdate.compareTo(newdate)>0)
                return 1;
            else if(frstdate.compareTo(newdate)==0)
                return 0;

        } catch (ParseException ex) {
            Log.e(TAG, "compareTimestamps:" + ex.toString());
        }

        return 0;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent=null;

        switch(v.getId())
        {
            case R.id.kingsoopers_button:
                intent=new Intent (this, StoreDetailActivity.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.KING_SOOPERS);
                startActivity(intent);
                break;
            case R.id.wholefoods_button:
                intent=new Intent (this, StoreDetailActivity.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.WHOLE_FOODS);
                startActivity(intent);
                break;
            case R.id.swalb_button:
                intent=new Intent (this, StoreDetailActivity.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.SAFEWAY_ALBERTSONS);
                startActivity(intent);
                break;
            case R.id.sprouts_button:
                intent=new Intent (this, StoreDetailActivity.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.SPROUTS);
                startActivity(intent);
                break;
            case R.id.http_test:
                updateFromServer();
                break;

        }

    }
}
