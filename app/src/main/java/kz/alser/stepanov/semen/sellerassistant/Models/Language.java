package kz.alser.stepanov.semen.sellerassistant.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Language extends SugarRecord
{
    @SerializedName("languageId")
    @Expose
    private String languageId;
    @SerializedName("languageName")
    @Expose
    private String languageName;
    @SerializedName("languageImagePath")
    @Expose
    private String languageImagePath;
    @SerializedName("languageCode")
    @Expose
    private String languageCode;
    @SerializedName("languageSortOrder")
    @Expose
    private String languageSortOrder;

    /**
     *
     * @return
     * The languageId
     */
    public String getLanguageId() {
        return languageId;
    }

    /**
     *
     * @param languageId
     * The languageId
     */
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    /**
     *
     * @return
     * The languageName
     */
    public String getLanguageName() {
        return languageName;
    }

    /**
     *
     * @param languageName
     * The languageName
     */
    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    /**
     *
     * @return
     * The languageImagePath
     */
    public String getLanguageImagePath() {
        return languageImagePath;
    }

    /**
     *
     * @param languageImagePath
     * The languageImagePath
     */
    public void setLanguageImagePath(String languageImagePath) {
        this.languageImagePath = languageImagePath;
    }

    /**
     *
     * @return
     * The languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     *
     * @param languageCode
     * The languageCode
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     *
     * @return
     * The languageSortOrder
     */
    public String getLanguageSortOrder() {
        return languageSortOrder;
    }

    /**
     *
     * @param languageSortOrder
     * The languageSortOrder
     */
    public void setLanguageSortOrder(String languageSortOrder) {
        this.languageSortOrder = languageSortOrder;
    }

}