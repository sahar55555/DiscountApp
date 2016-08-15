package elkady.discountapp;

import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * This class represents an item of the Full List View of the Discount App Database.
 * This means that, instead of ENUMs for Categories or Stores, it will house fulltext names for
 * those fields in order to serve as data inside a list item (which needs the strings).
 */


public class Product implements Serializable
{
    private String ProductName;
    public enum Stores {
        KWIK_E_MART(0), KING_SOOPERS(1), SPROUTS(2), SAFEWAY_ALBERTSONS(3), WHOLE_FOODS(4);
        private final int index;
        Stores(int i) {
            this.index = i;
        }
    }
    public enum Category {
        NOTHING,
        BEVERAGE,
        BAKERY,
        CLEANING,
        DAIRY,
        MEAT_SEAFOOD,
        PRODUCE
    }


    public static Category DepthFromString(String sVal) {
        if (sVal.compareToIgnoreCase("beverage") == 0) {
            return Category.BEVERAGE;
        }
        if (sVal.compareToIgnoreCase("meat") == 0) {
            return Category.MEAT_SEAFOOD;
        }
        if (sVal.compareToIgnoreCase("seafood") == 0) {
            return Category.MEAT_SEAFOOD;
        }
        if (sVal.compareToIgnoreCase("meatandseafood") == 0) {
            return Category.MEAT_SEAFOOD;
        }
        if (sVal.compareToIgnoreCase("bakery") == 0) {
            return Category.BAKERY;
        }
        if (sVal.compareToIgnoreCase("DAIRY") == 0) {
            return Category.DAIRY;
        }
        if (sVal.compareToIgnoreCase("fruit") == 0) {
            return Category.PRODUCE;
        }
        if (sVal.compareToIgnoreCase("vegetables") == 0) {
            return Category.PRODUCE;
        }
        if (sVal.compareToIgnoreCase("produce") == 0) {
            return Category.PRODUCE;
        }
        if (sVal.compareToIgnoreCase("home") == 0) {
            return Category.CLEANING;
        }
        if (sVal.compareToIgnoreCase("laundry") == 0) {
            return Category.CLEANING;
        }
        return Category.NOTHING;
    }
/*
    Class properties and fields
 */
    private Stores Shop;
    private Category Dept;
    private float Price;

    /**
     * Deal - the string-based description of price or "deal" offered by the store.
     **/
    private String deal;
    public void setDeal(String val) {
        deal = val;
    }
    public String getDeal() {
        return deal;
    }
    /*
    * Store - Houses the String-based value of the "Store" for use in list item display.
    *   May be be bound to a database query result or other object.
     */
    private String store;
    public void setStore(String val) {
        store = val;
    }
    public String getStore() {
        return store;
    }
    private String category;
    public void setCategory(String val) {
        category = val;
    }
    public String getCategory() {
        return category;
    }

    private String detail;

    /*****
     *
     Constructors using the enums will be removed.
     *******/

    public Product(String prName, float prc, Stores store, Category cat)
    {
        this(prName, prc, store, cat, "");
    }
    public Product(String prName, float prc, Stores store, Category cat, String det)
    {
        setProductName(prName);
        setShop(store);
        setPrice(prc);
        setDept(cat);
        setDetail(det);
    }

    /*****
     * A Class with Strings available for list items
     * @param name - The product name
     * @param category - The product category or department
     * @param store - The store offering the deal
     * @param deal - The deal offered by the store.
     */
    public Product(String name, String category, String store, String deal) {
        setProductName(name);
        setCategory(category);
        setDeal(deal);
        setStore(store);
    }

    public boolean hasDetailField() {
        return detail.length() > 0;
    }
    public void setDetail(String d) {
        this.detail = d;
    }
    public String getDetail() {
        return this.detail;
    }
    public void setProductName(String proName)
    {
        this.ProductName=proName;
    }
    public String getProductName()
    {
        return this.ProductName;
    }

    public void setShop(Stores shp)
    {
        this.Shop = shp;
    }
    public Stores getShop()
    {
        return this.Shop;
    }

    public void setPrice(float prc)
    {
        this.Price = prc;
    }
    public float getPrice()
    {
        return this.Price;
    }

    public void setDept(Category cat)
    {
        this.Dept = cat;
    }
    public Category getDept()
    {
        return this.Dept;
    }

    @Override
    public String toString()
    {
        if (hasDetailField()) {
            return AbbrevStringFromStores(getShop()) + "," + getProductName() + "[" + getDetail() + "]; " +
                    String.format("$%.2f.", getPrice()) +
                    " " + getDept();
        }
        else
        {
            return AbbrevStringFromStores(getShop()) + "," + getProductName() + "; " + String.format("$%.2f.", getPrice()) +
                " " + getDept();
        }

    }

    public static String AbbrevStringFromStores(Stores store) {
        switch(store) {
            case KING_SOOPERS:
                return "KS";
            case SAFEWAY_ALBERTSONS:
                return "SWAlb";
            case SPROUTS:
                return "SP";
        }
        return "??";
    }

    public static Stores storeValueFromString(String sval)
    {
        if (sval.compareToIgnoreCase("KS") == 0)
            return Stores.KING_SOOPERS;
        if (sval.compareToIgnoreCase("SWAlb") == 0)
            return Stores.SAFEWAY_ALBERTSONS;
        if (sval.compareToIgnoreCase("SP") == 0)
            return Stores.SPROUTS;
        return Stores.KWIK_E_MART;
    }

    public static Product parseFromString(String s) {
        Log.d("parseFromString", s);
        String[] fields = s.split("\t");
        String storeName = fields[0];
        Stores store = storeValueFromString(storeName);

        String productName = fields[1];
        String productDetail = fields[2];
        float productPrice = Float.valueOf(fields[3]);
        String productDept = fields[4];

        return new Product(productName, productPrice, store, DepthFromString(productDept), productDetail);
    }


}
