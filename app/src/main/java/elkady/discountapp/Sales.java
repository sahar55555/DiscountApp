package elkady.discountapp;

import android.util.Log;

/**
 * Created by elkady on 4/14/16.
 */
public class Sales
{
    private String ProductName;
    public enum Stores {
        KWIK_E_MART(0), KING_SOOPERS(1), SPROUTS(2), SAFE_WAY(3), ALBERTSONS(4);
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

    private Stores Shop;
    private Category Dept;
    private float Price;
    private String detail;

    public Sales(String prName,float prc, Stores store, Category cat)
    {
        this(prName, prc, store, cat, "");
    }

    public Sales(String prName,float prc, Stores store, Category cat, String det)
    {
        setProductName(prName);
        setShop(store);
        setPrice(prc);
        setDept(cat);
        setDetail(det);
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
            case ALBERTSONS:
                return "Alb";
            case SAFE_WAY:
                return "SW";
            case SPROUTS:
                return "SP";
        }
        return "??";
    }

    public static Stores storeValueFromString(String sval)
    {
        if (sval.compareToIgnoreCase("KS") == 0)
            return Stores.KING_SOOPERS;
        if (sval.compareToIgnoreCase("Ab") == 0)
            return Stores.ALBERTSONS;
        if (sval.compareToIgnoreCase("SW") == 0)
            return Stores.SAFE_WAY;
        if (sval.compareToIgnoreCase("SP") == 0)
            return Stores.SPROUTS;
        return Stores.KWIK_E_MART;
    }

    public static Sales parseFromString(String s) {
        Log.d("parseFromString", s);
        String[] fields = s.split("\t");
        String storeName = fields[0];
        Stores store = storeValueFromString(storeName);

        String productName = fields[1];
        String productDetail = fields[2];
        float productPrice = Float.valueOf(fields[3]);
        String productDept = fields[4];

        return new Sales(productName, productPrice, store, DepthFromString(productDept), productDetail);
    }


}
