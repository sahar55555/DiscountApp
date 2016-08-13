package elkady.discountapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// for date parsing
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    public final static String STORENAME_EXTRA="elkady.discountapp.STORENAME";

    private ServerUtil server;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.http_test).setOnClickListener(this);

        View ks_button=findViewById(R.id.ks_button);
        ks_button.setOnClickListener(this);

        View albertsons_button=findViewById(R.id.albertsons_button);
        albertsons_button.setOnClickListener(this);

        View sw_button=findViewById(R.id.sw_button);
        sw_button.setOnClickListener(this);

        View sprouts_button=findViewById(R.id.sprouts_button);
        sprouts_button.setOnClickListener(this);

        String s1 = "Mon, 27 Jun 2016 00:04:41"; // as supplied by www.prfol.org/phi/timestamp.php
        String s2 = "Mon, 27 Jun 2016 00:06:34";
        compareTimestamps(s1, s2);

        server = new ServerUtil(this);

    }

    private void showTimeStamp() {
        final Context context = this; // final so it can be used in the callback
        ServerUtil.ProcessGET callback = new ServerUtil.ProcessGET() {
            @Override
            public void process(String data) {
                long checksum = server.getCrc32CheckSum();
                String message = String.format("Data is %d bytes, checksum=%x", data.length(), checksum);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
            Log.e("compareTimestamps", ex.toString());
        }


        return 0;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent=null;

        switch(v.getId())
        {
            case R.id.ks_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.KING_SOOPERS);
                startActivity(intent);
                break;
            case R.id.albertsons_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.ALBERTSONS);
                startActivity(intent);
                break;
            case R.id.sw_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.SAFE_WAY);
                startActivity(intent);
                break;
            case R.id.sprouts_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA, Product.Stores.SPROUTS);
                startActivity(intent);
                break;
            case R.id.http_test:
                showTimeStamp();
                break;
        }

    }
}
