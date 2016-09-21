package kz.alser.stepanov.semen.sellerassistant.Models;

/**
 * Created by semen.stepanov on 15.09.2016.
 */
public class Items4Selection
{
    public enum Item_Type { CATEGORY, PRODUCT, BACK_MENU }

    private int categoryId;

    private int ItemId;

    private String ItemName;

    private String ItemDescription;

    private String ItemImagePath;

    private int categoryParentId;

    private String ItemSortOrder;

    private int ItemQuantity;

    private double ItemPrice;

    private Item_Type ItemType;

    public Items4Selection()
    {

    }

    public Items4Selection (int categoryId, int categoryParentId, String itemDescription, int itemId, String itemImagePath, String itemName, double itemPrice, int itemQuantity, String itemSortOrder, Item_Type itemType)
    {
        this.categoryId = categoryId;
        this.categoryParentId = categoryParentId;
        ItemDescription = itemDescription;
        ItemId = itemId;
        ItemImagePath = itemImagePath;
        ItemName = itemName;
        ItemPrice = itemPrice;
        ItemQuantity = itemQuantity;
        ItemSortOrder = itemSortOrder;
        ItemType = itemType;
    }

    public int getCategoryId ()
    {
        return categoryId;
    }

    public void setCategoryId (int categoryId)
    {
        this.categoryId = categoryId;
    }

    public int getCategoryParentId ()
    {
        return categoryParentId;
    }

    public void setCategoryParentId (int categoryParentId)
    {
        this.categoryParentId = categoryParentId;
    }

    public String getItemDescription ()
    {
        return ItemDescription;
    }

    public void setItemDescription (String itemDescription)
    {
        ItemDescription = itemDescription;
    }

    public int getItemId ()
    {
        return ItemId;
    }

    public void setItemId (int itemId)
    {
        ItemId = itemId;
    }

    public String getItemImagePath ()
    {
        return ItemImagePath;
    }

    public void setItemImagePath (String itemImagePath)
    {
        ItemImagePath = itemImagePath;
    }

    public String getItemName ()
    {
        return ItemName;
    }

    public void setItemName (String itemName)
    {
        ItemName = itemName;
    }

    public double getItemPrice ()
    {
        return ItemPrice;
    }

    public void setItemPrice (double itemPrice)
    {
        ItemPrice = itemPrice;
    }

    public String getItemSortOrder ()
    {
        return ItemSortOrder;
    }

    public void setItemSortOrder (String itemSortOrder)
    {
        ItemSortOrder = itemSortOrder;
    }

    public int getItemQuantity ()
    {
        return ItemQuantity;
    }

    public void setItemQuantity (int itemQuantity)
    {
        ItemQuantity = itemQuantity;
    }

    public Item_Type getItemType ()
    {
        return ItemType;
    }

    public void setItemType (Item_Type itemType)
    {
        ItemType = itemType;
    }
}
