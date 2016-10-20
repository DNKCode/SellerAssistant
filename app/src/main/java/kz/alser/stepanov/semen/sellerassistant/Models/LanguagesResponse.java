package kz.alser.stepanov.semen.sellerassistant.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagesResponse {

    @SerializedName("rspCode")
    @Expose
    private Integer rspCode;
    @SerializedName("rspMessage")
    @Expose
    private String rspMessage;
    @SerializedName("languages")
    @Expose
    private List<Language> languages = new ArrayList<Language>();

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
     * The languages
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     *
     * @param languages
     * The languages
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

}