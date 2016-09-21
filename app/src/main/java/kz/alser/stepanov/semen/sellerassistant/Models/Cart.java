package kz.alser.stepanov.semen.sellerassistant.Models;

/**
 * Created by semen.stepanov on 19.09.2016.
 */
public class Cart
{
    private int CategoryId;

    private int ProductId;

    private String ProductName;

    private String ProductDescription;

    private String ProductImagePath;

    private int Quantity;

    private double BeginPrice;

    private double EndPrice;

    public Cart()
    {

    }

    public Cart (double beginPrice, int categoryId, double endPrice, String productDescription, int productId, String productImagePath, String productName, int quantity)
    {
        BeginPrice = beginPrice;
        CategoryId = categoryId;
        EndPrice = endPrice;
        ProductDescription = productDescription;
        ProductId = productId;
        ProductImagePath = productImagePath;
        ProductName = productName;
        Quantity = quantity;
    }

    public double getBeginPrice ()
    {
        return BeginPrice;
    }

    public void setBeginPrice (double beginPrice)
    {
        BeginPrice = beginPrice;
    }

    public int getCategoryId ()
    {
        return CategoryId;
    }

    public void setCategoryId (int categoryId)
    {
        CategoryId = categoryId;
    }

    public double getEndPrice ()
    {
        return EndPrice;
    }

    public void setEndPrice (double endPrice)
    {
        EndPrice = endPrice;
    }

    public String getProductDescription ()
    {
        return ProductDescription;
    }

    public void setProductDescription (String productDescription)
    {
        ProductDescription = productDescription;
    }

    public int getProductId ()
    {
        return ProductId;
    }

    public void setProductId (int productId)
    {
        ProductId = productId;
    }

    public String getProductImagePath ()
    {
        return ProductImagePath;
    }

    public void setProductImagePath (String productImagePath)
    {
        ProductImagePath = productImagePath;
    }

    public String getProductName ()
    {
        return ProductName;
    }

    public void setProductName (String productName)
    {
        ProductName = productName;
    }

    public int getQuantity ()
    {
        return Quantity;
    }

    public void setQuantity (int quantity)
    {
        Quantity = quantity;
    }
}
