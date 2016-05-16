package elkady.discountapp;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by elkady on 5/13/16.
 */
public class StoreListFragment extends ListFragment implements AdapterView.OnItemClickListener
{
    Cursor st_curs=null;


    public final static String ID_EXTRA="STORE_NAME";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.store_fragment,container,false);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent screen=new Intent(getActivity(),StoreDetail.class);
        //screen.putExtra(ID_EXTRA),String.valueOf(id));
        startActivity(screen);
    }
}
