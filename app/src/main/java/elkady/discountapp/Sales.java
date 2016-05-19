package elkady.discountapp;

import java.util.Date;

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
    public enum Categories{ Fruits, Veg,Breakfast, Bakery,Dairy};
    private Stores Shop;
    private Categories Category;
    private float Price;
    private Date AppDate;

    public Sales(String prName,float prc, Date appdate, Stores store,Categories cat)
    {
        ProductName=prName;
        Shop=store;
        Price=prc;
        AppDate=appdate;
     Category=cat;
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

    public void setAppDate(Date dt)
    {
        this.AppDate=dt;
    }
    public Date getAppDate()
    {
        return this.AppDate;
    }
    public void setCategory( Categories catt)
    {
        this.Category=catt;
    }
    public Categories getCategory()
    {
        return this.Category;
    }

    @Override
    public String toString()
    {
        return " This week's Store is: \n ProductName: "+getProductName()+"\nShop: "+getShop()
                +"\nDate: "+ getAppDate();
    }

}
