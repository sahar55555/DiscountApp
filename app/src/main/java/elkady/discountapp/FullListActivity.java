package elkady.discountapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by david on 8/14/16.
 */
public class FullListActivity extends Activity {
    private static String TAG = "FullListActivity";
    private ArrayList<Product> productArrayList;
    private ListView listView;
    private ProductListAdapter productListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_list);
        productArrayList = (ArrayList<Product>) getIntent().getSerializableExtra("ARRAYLIST");
        Log.d(TAG, "productArrayList.size(): " + productArrayList.size());
        listView = (ListView)findViewById(R.id.product_list);
        productListAdapter = new ProductListAdapter(FullListActivity.this);
        listView.setAdapter(productListAdapter);
    }
    private class ProductListAdapter extends ArrayAdapter<Product> {

        public ProductListAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, productArrayList);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Holder holder = null;
            // if no row exists, create one using inflator
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false);
                holder = new Holder(row);
                row.setTag(holder);
            }
            else {
                holder = (Holder)row.getTag();
            }
            holder.populateFrom(productArrayList.get(position));
            return row;
        }
    }
    static class Holder {
        private TextView name = null;
        private TextView deal = null;
        private TextView category = null;
        private TextView store = null;
        Holder(View row) {
            name = (TextView)row.findViewById(R.id.item_name);
            deal = (TextView)row.findViewById(R.id.item_deal);
            store = (TextView)row.findViewById(R.id.item_store);
            category= (TextView)row.findViewById(R.id.item_category);
        }
        void populateFrom(Product p) {
            name.setText(p.getProductName());
            deal.setText(p.getDeal());
            store.setText(p.getStore());
            category.setText(p.getCategory());

        }

    }
}
