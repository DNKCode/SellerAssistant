package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import static kz.alser.stepanov.semen.sellerassistant.Helpers.Helpers.ClearSpecialSymbols;

public class Category extends SugarRecord
{
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;
    @SerializedName("categoryImagePath")
    @Expose
    private String categoryImagePath;
    @SerializedName("categoryParentId")
    @Expose
    private String categoryParentId;
    @SerializedName("categorySortOrder")
    @Expose
    private String categorySortOrder;

    public Category()
    {

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

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return ClearSpecialSymbols(categoryName);
    }

    /**
     *
     * @param categoryName
     * The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = ClearSpecialSymbols(categoryName);
    }

    /**
     *
     * @return
     * The categoryDescription
     */
    public String getCategoryDescription() {
        return ClearSpecialSymbols(categoryDescription);
    }

    /**
     *
     * @param categoryDescription
     * The categoryDescription
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = ClearSpecialSymbols(categoryDescription);
    }

    /**
     *
     * @return
     * The categoryImagePath
     */
    public String getCategoryImagePath() {
        return categoryImagePath;
    }

    /**
     *
     * @param categoryImagePath
     * The categoryImagePath
     */
    public void setCategoryImagePath(String categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
    }

    /**
     *
     * @return
     * The categoryParentId
     */
    public String getCategoryParentId() {
        return categoryParentId;
    }

    /**
     *
     * @param categoryParentId
     * The categoryParentId
     */
    public void setCategoryParentId(String categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    /**
     *
     * @return
     * The categorySortOrder
     */
    public String getCategorySortOrder() {
        return categorySortOrder;
    }

    /**
     *
     * @param categorySortOrder
     * The categorySortOrder
     */
    public void setCategorySortOrder(String categorySortOrder) {
        this.categorySortOrder = categorySortOrder;
    }
}