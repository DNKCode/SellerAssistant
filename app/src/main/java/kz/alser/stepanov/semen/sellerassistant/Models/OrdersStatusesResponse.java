package kz.alser.stepanov.semen.sellerassistant.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersStatusesResponse
{

    @SerializedName("rspCode")
    @Expose
    private Integer rspCode;
    @SerializedName("rspMessage")
    @Expose
    private String rspMessage;
    @SerializedName("ordersStatuses")
    @Expose
    private List<OrdersStatus> ordersStatuses = new ArrayList<OrdersStatus>();

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
     * The ordersStatuses
     */
    public List<OrdersStatus> getOrdersStatuses() {
        return ordersStatuses;
    }

    /**
     *
     * @param ordersStatuses
     * The ordersStatuses
     */
    public void setOrdersStatuses(List<OrdersStatus> ordersStatuses) {
        this.ordersStatuses = ordersStatuses;
    }

}