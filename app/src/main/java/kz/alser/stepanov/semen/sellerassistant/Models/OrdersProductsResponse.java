package kz.alser.stepanov.semen.sellerassistant.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersProductsResponse
{

    @SerializedName("rspCode")
    @Expose
    private Integer rspCode;
    @SerializedName("rspMessage")
    @Expose
    private String rspMessage;
    @SerializedName("ordersProducts")
    @Expose
    private List<OrdersProduct> ordersProducts = new ArrayList<OrdersProduct>();

    /**
     *
     * @return
     * The rspCode
     */
    public Integer getRspCode() {
        return rspCode;
    }

    /**
     *
     * @param rspCode
     * The rspCode
     */
    public void setRspCode(Integer rspCode) {
        this.rspCode = rspCode;
    }

    /**
     *
     * @return
     * The rspMessage
     */
    public String getRspMessage() {
        return rspMessage;
    }

    /**
     *
     * @param rspMessage
     * The rspMessage
     */
    public void setRspMessage(String rspMessage) {
        this.rspMessage = rspMessage;
    }

    /**
     *
     * @return
     * The ordersProducts
     */
    public List<OrdersProduct> getOrdersProducts() {
        return ordersProducts;
    }

    /**
     *
     * @param ordersProducts
     * The ordersProducts
     */
    public void setOrdersProducts(List<OrdersProduct> ordersProducts) {
        this.ordersProducts = ordersProducts;
    }

}