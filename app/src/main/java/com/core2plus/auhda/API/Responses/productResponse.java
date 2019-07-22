package com.core2plus.auhda.API.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class productResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private List<productMessage> message = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<productMessage> getMessage() {
        return message;
    }

    public void setMessage(List<productMessage> message) {
        this.message = message;
    }
}
