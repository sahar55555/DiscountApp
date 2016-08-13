package elkady.discountapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by david on 4/11/16.
 */
public class DBHelper extends SQLiteOpenHelper  {
    private static final String DBNAME = "sales.db";
    private static final int SCHEMA_VERSION = 1;
    private Context _context;

    public DBHelper(Context context) {
        super(context, DBNAME, null, SCHEMA_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        I designed this schema in SQLiteBrowser
         */
        String cat_table_schema = "CREATE TABLE `categories` (\n" +
                "`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "`name` TEXT NOT NULL\n" +
                ")";
        String store_table_schema = "CREATE TABLE `stores` (\n" +
                "`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "`storename` TEXT NOT NULL\n" +
                ");";
        String product_table_schema = "CREATE TABLE \"products\" (\n" +
                "`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "`name` TEXT NOT NULL,\n" +
                "`deal` TEXT NOT NULL,\n" +
                "`detail`TEXT,\n" +
                "`category` INTEGER NOT NULL,\n" +
                "`store_id` INTEGER NOT NULL,\n" +
                "`saledate` INTEGER NOT NULL,\n" +
                "FOREIGN KEY(`category`) REFERENCES `categories`(`_id`),\n" +
                "FOREIGN KEY(`store_id`) REFERENCES `stores`(`_id`),\n" +
                "FOREIGN KEY(`saledate`) REFERENCES saledate(_id)\n" +
                ")";
        String saledate_table_schema = "CREATE TABLE `saledate` (\n" +
                "`_id`INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "`date`TEXT NOT NULL,\n" +
                "`is_current`    INTEGER NOT NULL DEFAULT 0,\n" +
                "`is_verified`    INTEGER NOT NULL DEFAULT 0\n" +
                ")";

        // here goes
        // category tables
        db.execSQL(cat_table_schema);
        db.execSQL(store_table_schema);
        db.execSQL(saledate_table_schema);
        // data tables
        db.execSQL(product_table_schema);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
