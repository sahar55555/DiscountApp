package elkady.discountapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.text.format.DateUtils;
//import android.text.SimpleDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    public final static String STORENAME_EXTRA="elkady.discountapp.STORENAME";
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
            getLogger(Package.class.getName()).log(Level.SEVERE, null, ex);
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
                intent.putExtra(STORENAME_EXTRA,Sales.Stores.KING_SOOPERS);
                startActivity(intent);
                break;
            case R.id.albertsons_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA,Sales.Stores.ALBERTSONS);
                startActivity(intent);
                break;
            case R.id.sw_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA,Sales.Stores.SAFE_WAY);
                startActivity(intent);
                break;
            case R.id.sprouts_button:
                intent=new Intent (this, StoreDetail.class);
                intent.putExtra(STORENAME_EXTRA,Sales.Stores.SPROUTS);
                startActivity(intent);
                break;
            case R.id.http_test:
                startActivity(new Intent(this, HttpExampleActivity.class));
                break;
        }

    }
}
