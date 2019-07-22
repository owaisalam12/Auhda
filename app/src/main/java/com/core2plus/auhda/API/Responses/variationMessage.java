package com.core2plus.auhda.API.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class variationMessage {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
