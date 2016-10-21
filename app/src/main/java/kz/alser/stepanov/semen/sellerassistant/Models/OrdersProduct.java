package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class OrdersProduct extends SugarRecord
{

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderProductId")
    @Expose
    private String orderProductId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productQuantity")
    @Expose
    private String productQuantity;
    @SerializedName("productTotal")
    @Expose
    private String productTotal;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The orderProductId
     */
    public String getOrderProductId() {
        return orderProductId;
    }

    /**
     *
     * @param orderProductId
     * The orderProductId
     */
    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
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
     * The productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
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
     * The productTotal
     */
    public String getProductTotal() {
        return productTotal;
    }

    /**
     *
     * @param productTotal
     * The productTotal
     */
    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
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

}