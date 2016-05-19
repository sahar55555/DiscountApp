package elkady.discountapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


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

    }

    @Override
    public void onClick(View v)
    {

        switch(v.getId())
        {
            case R.id.ks_button:
                Toast.makeText(this, " I clicked KingSoopers button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.albertsons_button:
                Toast.makeText(this," I clicked Albertsons button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sw_button:
                Toast.makeText(this," I clicked SafeWay button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sprouts_button:
                Toast.makeText(this," I clicked Sprouts button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.http_test:
                startActivity(new Intent(this, HttpExampleActivity.class));
                break;
        }

    }
}
