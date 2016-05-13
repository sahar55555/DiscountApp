package elkady.discountapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        String store_id = "not initialized";
        switch(v.getId()) {
            case R.id.albertsons_button:
                store_id = "albertsons";
                break;
        }
        Toast.makeText(this, store_id, Toast.LENGTH_LONG).show();
        //Intent i = new Intent(fragmentname.class);
        // somewhere in here we pass the store_id argument
        // startActivity(i);
    }
}
