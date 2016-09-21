package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import static kz.alser.stepanov.semen.sellerassistant.Helpers.Helpers.ClearSpecialSymbols;

public class Product extends SugarRecord
{

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productDescription")
    @Expose
    private String productDescription;
    @SerializedName("productImagePath")
    @Expose
    private String productImagePath;
    @SerializedName("productSortOrder")
    @Expose
    private String productSortOrder;
    @SerializedName("productModel")
    @Expose
    private String productModel;
    @SerializedName("productSKU")
    @Expose
    private String productSKU;
    @SerializedName("productEAN")
    @Expose
    private String productEAN;
    @SerializedName("productQuantity")
    @Expose
    private String productQuantity;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("productWeight")
    @Expose
    private String productWeight;
    @SerializedName("productLength")
    @Expose
    private String productLength;
    @SerializedName("productWidth")
    @Expose
    private String productWidth;
    @SerializedName("productHeight")
    @Expose
    private String productHeight;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;

    public Product()
    {

    }

    /**
     *
     * @return
     * The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     * The product_id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {

        return ClearSpecialSymbols(productName);
    }

    /**
     *
     * @param productName
     * The productName
     */
    public void setProductName(String productName)
    {
        this.productName = ClearSpecialSymbols(productName);
    }

    /**
     *
     * @return
     * The productDescription
     */
    public String getProductDescription() {

        return ClearSpecialSymbols(productDescription);
    }

    /**
     *
     * @param productDescription
     * The productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = ClearSpecialSymbols(productDescription);
    }

    /**
     *
     * @return
     * The productImagePath
     */
    public String getProductImagePath() {
        return productImagePath;
    }

    /**
     *
     * @param productImagePath
     * The productImagePath
     */
    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    /**
     *
     * @return
     * The productSortOrder
     */
    public String getProductSortOrder() {
        return productSortOrder;
    }

    /**
     *
     * @param productSortOrder
     * The productSortOrder
     */
    public void setProductSortOrder(String productSortOrder) {
        this.productSortOrder = productSortOrder;
    }

    /**
     *
     * @return
     * The productModel
     */
    public String getProductModel() {

        return ClearSpecialSymbols(productModel);
    }

    /**
     *
     * @param productModel
     * The productModel
     */
    public void setProductModel(String productModel) {

        this.productModel = ClearSpecialSymbols(productModel);
    }

    /**
     *
     * @return
     * The productSKU
     */
    public String getProductSKU() {
        return productSKU;
    }

    /**
     *
     * @param productSKU
     * The productSKU
     */
    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    /**
     *
     * @return
     * The productEAN
     */
    public String getProductEAN() {
        return productEAN;
    }

    /**
     *
     * @param productEAN
     * The productEAN
     */
    public void setProductEAN(String productEAN) {
        this.productEAN = productEAN;
    }

    /**
     *
     * @return
     * The productQuantity
     */
    public String getProductQuantity() {
        return productQuantity;
    }

    /**
     *
     * @param productQuantity
     * The productQuantity
     */
    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     *
     * @return
     * The productPrice
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     *
     * @param productPrice
     * The productPrice
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     *
     * @return
     * The productWeight
     */
    public String getProductWeight() {
        return productWeight;
    }

    /**
     *
     * @param productWeight
     * The productWeight
     */
    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
    }

    /**
     *
     * @return
     * The productLength
     */
    public String getProductLength() {
        return productLength;
    }

    /**
     *
     * @param productLength
     * The productLength
     */
    public void setProductLength(String productLength) {
        this.productLength = productLength;
    }

    /**
     *
     * @return
     * The productWidth
     */
    public String getProductWidth() {
        return productWidth;
    }

    /**
     *
     * @param productWidth
     * The productWidth
     */
    public void setProductWidth(String productWidth) {
        this.productWidth = productWidth;
    }

    /**
     *
     * @return
     * The productHeight
     */
    public String getProductHeight() {
        return productHeight;
    }

    /**
     *
     * @param productHeight
     * The productHeight
     */
    public void setProductHeight(String productHeight) {
        this.productHeight = productHeight;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


}