package elkady.discountapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// these are the Views defined in the xml file
import static elkady.discountapp.R.id.Bakery;
import static elkady.discountapp.R.id.Bath;
import static elkady.discountapp.R.id.Dairy;
import static elkady.discountapp.R.id.Food_N_Beverages;
import static elkady.discountapp.R.id.Produce;
import static elkady.discountapp.R.id.storeName;


/**
 * Created by elkady on 5/15/16.
 */
public class StoreDetail extends Activity implements View.OnClickListener
{


    TextView stname=null;
    Button dairy_button=null;
    Button meat_button=null;
    Button produce_button=null;
    Button bakery_button=null;
    Button bath_button=null;
    Button foodNbeverages_button=null;
    Product.Stores store_id; // this could be equivalent to KING_SOOPERS

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_activity);

        stname=(TextView)findViewById(storeName);
        dairy_button=(Button)findViewById(Dairy);
        meat_button=(Button)findViewById(R.id.Meat);
        produce_button=(Button)findViewById(Produce);
        bakery_button=(Button)findViewById(Bakery);
        bath_button=(Button)findViewById(Bath);
        foodNbeverages_button=(Button)findViewById(Food_N_Beverages);

        //set our store_id variable to the param passed in through Intent.putExtra
        store_id = (Product.Stores)getIntent().getSerializableExtra(MainActivity.STORENAME_EXTRA);
        switch(store_id)
        {
            case KING_SOOPERS:
                stname.setText("King Sooper's");
                break;
            case SAFEWAY_ALBERTSONS:
                stname.setText("Albertson's");
                break;
            case WHOLE_FOODS:
                stname.setText("Whole Foods");
                break;
            case SPROUTS:
                stname.setText("Sprouts");
                break;
            default:
                stname.setText("Undefined store name");
                break;
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            default:
               // Toast.makeText(this, store_id, Toast.LENGTH_LONG).show();
                break;
        }
    }

}
