package kz.alser.stepanov.semen.sellerassistant.Models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CategoryResponse {

    @SerializedName("rspCode")
    @Expose
    private Integer rspCode;
    @SerializedName("rspMessage")
    @Expose
    private String rspMessage;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = new ArrayList<Category>();

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
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}