package elkady.discountapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_detail_activity);


        stname=(TextView)findViewById(R.id.storeName);
        dairy_button=(Button)findViewById(R.id.Dairy);
        meat_button=(Button)findViewById(R.id.Meat);
        produce_button=(Button)findViewById(R.id.Produce);
        bakery_button=(Button)findViewById(R.id.Bakery);
        bath_button=(Button)findViewById(R.id.Bath);
        foodNbeverages_button=(Button)findViewById(R.id.Food_N_beverages);
    }

    @Override
    public void onClick(View v) {

    }
}
