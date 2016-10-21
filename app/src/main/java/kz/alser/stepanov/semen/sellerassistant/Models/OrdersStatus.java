package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class OrdersStatus extends SugarRecord
{

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderStatusId")
    @Expose
    private String orderStatusId;
    @SerializedName("orderHistoryId")
    @Expose
    private String orderHistoryId;
    @SerializedName("orderHistoryDate")
    @Expose
    private String orderHistoryDate;
    @SerializedName("orderStatusName")
    @Expose
    private String orderStatusName;
    @SerializedName("orderStatusLanguageId")
    @Expose
    private String orderStatusLanguageId;

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
     * The orderStatusId
     */
    public String getOrderStatusId() {
        return orderStatusId;
    }

    /**
     *
     * @param orderStatusId
     * The orderStatusId
     */
    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    /**
     *
     * @return
     * The orderHistoryId
     */
    public String getOrderHistoryId() {
        return orderHistoryId;
    }

    /**
     *
     * @param orderHistoryId
     * The orderHistoryId
     */
    public void setOrderHistoryId(String orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    /**
     *
     * @return
     * The orderHistoryDate
     */
    public String getOrderHistoryDate() {
        return orderHistoryDate;
    }

    /**
     *
     * @param orderHistoryDate
     * The orderHistoryDate
     */
    public void setOrderHistoryDate(String orderHistoryDate) {
        this.orderHistoryDate = orderHistoryDate;
    }

    /**
     *
     * @return
     * The orderStatusName
     */
    public String getOrderStatusName() {
        return orderStatusName;
    }

    /**
     *
     * @param orderStatusName
     * The orderStatusName
     */
    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    /**
     *
     * @return
     * The orderStatusLanguageId
     */
    public String getOrderStatusLanguageId() {
        return orderStatusLanguageId;
    }

    /**
     *
     * @param orderStatusLanguageId
     * The orderStatusLanguageId
     */
    public void setOrderStatusLanguageId(String orderStatusLanguageId) {
        this.orderStatusLanguageId = orderStatusLanguageId;
    }

}