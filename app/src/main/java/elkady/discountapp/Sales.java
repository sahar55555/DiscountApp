package elkady.discountapp;

import android.os.Debug;
import android.util.Log;

/**
 * Created by elkady on 4/14/16.
 */
public class Sales
{
    private String ProductName;
    public enum Stores {
        KING_SOOPERS(1), SPROUTS(2), SAFE_WAY(3), ALBERTSONS(4);
        private final int index;
        Stores(int i) {
            this.index = i;
        }
    };
    public enum Category {
        Nothing,
        Beverage,
        Bakery,
        Cleaning,
        Dairy,
        MeatSeafood,
        Produce
    };

    public static Category DeptFromString(String sVal) {
        if (sVal.compareToIgnoreCase("beverage") == 0) {
            return Category.Beverage;
        }
        if (sVal.compareToIgnoreCase("meat") == 0) {
            return Category.MeatSeafood;
        }
        if (sVal.compareToIgnoreCase("seafood") == 0) {
            return Category.MeatSeafood;
        }
        if (sVal.compareToIgnoreCase("meatandseafood") == 0) {
            return Category.MeatSeafood;
        }
        if (sVal.compareToIgnoreCase("bakery") == 0) {
            return Category.Bakery;
        }
        if (sVal.compareToIgnoreCase("Dairy") == 0) {
            return Category.Dairy;
        }
        if (sVal.compareToIgnoreCase("fruit") == 0) {
            return Category.Produce;
        }
        if (sVal.compareToIgnoreCase("vegetables") == 0) {
            return Category.Produce;
        }
        if (sVal.compareToIgnoreCase("produce") == 0) {
            return Category.Produce;
        }
        if (sVal.compareToIgnoreCase("laundry") == 0) {
            return Category.Cleaning;
        }
        return Category.Nothing;
    }

    private Stores Shop;
    private Category Dept;
    private float Price;

    public Sales(String prName,float prc, Stores store, Category cat)
    {
        this.setProductName(prName);
        this.setShop(store);
        this.setPrice(prc);
        this.setDept(cat);
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
        this.Shop=shp;
    }
    public Stores getShop()
    {
        return this.Shop;
    }

    public void setPrice(float prc)
    {
        this.Price=prc;
    }
    public float getPrice()
    {
        return this.Price;
    }

    public void setDept(Category cat)
    {
        this.Dept =cat;
    }
    public Category getDept()
    {
        return this.Dept;
    }

    @Override
    public String toString()
    {
        return getShop() + ","+ getProductName() + "; " + String.format("$%.2f per unit.", getPrice());
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
        return Stores.KING_SOOPERS;
    }

    public static Sales parseFromString(String s) {
        Log.d("parseFromString", s);
        String[] fields = s.split("\t");
        String storeName = fields[0];
        Stores store = storeValueFromString(storeName);

        String productName = fields[1];
        String productDetail = fields[2];
        float productPrice = Float.valueOf(fields[3]);
        String productDept = fields[3];

        return new Sales(productName, productPrice, store, DeptFromString(productDept));
    }


}
