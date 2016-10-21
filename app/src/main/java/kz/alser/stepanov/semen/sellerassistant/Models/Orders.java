package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Orders extends SugarRecord
{
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("orderInvoicePrefix")
    @Expose
    private String orderInvoicePrefix;
    @SerializedName("orderStoreId")
    @Expose
    private String orderStoreId;
    @SerializedName("orderCustomerId")
    @Expose
    private String orderCustomerId;
    @SerializedName("orderFirstName")
    @Expose
    private String orderFirstName;
    @SerializedName("orderLastName")
    @Expose
    private String orderLastName;
    @SerializedName("orderEmail")
    @Expose
    private String orderEmail;
    @SerializedName("orderPhone")
    @Expose
    private String orderPhone;
    @SerializedName("orderPaymentMethod")
    @Expose
    private String orderPaymentMethod;
    @SerializedName("orderTotal")
    @Expose
    private String orderTotal;
    @SerializedName("orderStatusId")
    @Expose
    private String orderStatusId;
    @SerializedName("orderLanguageId")
    @Expose
    private String orderLanguageId;
    @SerializedName("orderCurrencyId")
    @Expose
    private String orderCurrencyId;
    @SerializedName("orderFromServer")
    @Expose
    private Integer orderFromServer;

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
     * The orderInvoicePrefix
     */
    public String getOrderInvoicePrefix() {
        return orderInvoicePrefix;
    }

    /**
     *
     * @param orderInvoicePrefix
     * The orderInvoicePrefix
     */
    public void setOrderInvoicePrefix(String orderInvoicePrefix) {
        this.orderInvoicePrefix = orderInvoicePrefix;
    }

    /**
     *
     * @return
     * The orderStoreId
     */
    public String getOrderStoreId() {
        return orderStoreId;
    }

    /**
     *
     * @param orderStoreId
     * The orderStoreId
     */
    public void setOrderStoreId(String orderStoreId) {
        this.orderStoreId = orderStoreId;
    }

    /**
     *
     * @return
     * The orderCustomerId
     */
    public String getOrderCustomerId() {
        return orderCustomerId;
    }

    /**
     *
     * @param orderCustomerId
     * The orderCustomerId
     */
    public void setOrderCustomerId(String orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    /**
     *
     * @return
     * The orderFirstName
     */
    public String getOrderFirstName() {
        return orderFirstName;
    }

    /**
     *
     * @param orderFirstName
     * The orderFirstName
     */
    public void setOrderFirstName(String orderFirstName) {
        this.orderFirstName = orderFirstName;
    }

    /**
     *
     * @return
     * The orderLastName
     */
    public String getOrderLastName() {
        return orderLastName;
    }

    /**
     *
     * @param orderLastName
     * The orderLastName
     */
    public void setOrderLastName(String orderLastName) {
        this.orderLastName = orderLastName;
    }

    /**
     *
     * @return
     * The orderEmail
     */
    public String getOrderEmail() {
        return orderEmail;
    }

    /**
     *
     * @param orderEmail
     * The orderEmail
     */
    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }

    /**
     *
     * @return
     * The orderPhone
     */
    public String getOrderPhone() {
        return orderPhone;
    }

    /**
     *
     * @param orderPhone
     * The orderPhone
     */
    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    /**
     *
     * @return
     * The orderPaymentMethod
     */
    public String getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    /**
     *
     * @param orderPaymentMethod
     * The orderPaymentMethod
     */
    public void setOrderPaymentMethod(String orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    /**
     *
     * @return
     * The orderTotal
     */
    public String getOrderTotal() {
        return orderTotal;
    }

    /**
     *
     * @param orderTotal
     * The orderTotal
     */
    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
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
     * The orderLanguageId
     */
    public String getOrderLanguageId() {
        return orderLanguageId;
    }

    /**
     *
     * @param orderLanguageId
     * The orderLanguageId
     */
    public void setOrderLanguageId(String orderLanguageId) {
        this.orderLanguageId = orderLanguageId;
    }

    /**
     *
     * @return
     * The orderCurrencyId
     */
    public String getOrderCurrencyId() {
        return orderCurrencyId;
    }

    /**
     *
     * @param orderCurrencyId
     * The orderCurrencyId
     */
    public void setOrderCurrencyId(String orderCurrencyId) {
        this.orderCurrencyId = orderCurrencyId;
    }

    public Integer getOrderFromServer ()
    {
        return orderFromServer;
    }

    public void setOrderFromServer (Integer orderFromServer)
    {
        this.orderFromServer = orderFromServer;
    }
}