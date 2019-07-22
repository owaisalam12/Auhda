package com.core2plus.auhda.API.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class variationsResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private List<variationMessage> message = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<variationMessage> getMessage() {
        return message;
    }

    public void setMessage(List<variationMessage> message) {
        this.message = message;
    }
}
