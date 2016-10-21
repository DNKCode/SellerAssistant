package kz.alser.stepanov.semen.sellerassistant.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersResponse
{

    @SerializedName("rspCode")
    @Expose
    private Integer rspCode;
    @SerializedName("rspMessage")
    @Expose
    private String rspMessage;
    @SerializedName("orders")
    @Expose
    private List<Orders> orders = new ArrayList<Orders>();

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
     * The orders
     */
    public List<Orders> getOrders() {
        return orders;
    }

    /**
     *
     * @param orders
     * The orders
     */
    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

}