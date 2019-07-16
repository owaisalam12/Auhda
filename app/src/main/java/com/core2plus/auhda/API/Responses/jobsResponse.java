package com.core2plus.auhda.API.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class jobsResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("Jobtype")
    @Expose
    private String jobtype;
    @SerializedName("salarypackage")
    @Expose
    private String salarypackage;
    @SerializedName("careerlevel")
    @Expose
    private String careerlevel;
    @SerializedName("experiencetotal")
    @Expose
    private String experiencetotal;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("cs_post_comp_address")
    @Expose
    private String csPostCompAddress;
    @SerializedName("content")
    @Expose
    private Content content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getSalarypackage() {
        return salarypackage;
    }

    public void setSalarypackage(String salarypackage) {
        this.salarypackage = salarypackage;
    }

    public String getCareerlevel() {
        return careerlevel;
    }

    public void setCareerlevel(String careerlevel) {
        this.careerlevel = careerlevel;
    }

    public String getExperiencetotal() {
        return experiencetotal;
    }

    public void setExperiencetotal(String experiencetotal) {
        this.experiencetotal = experiencetotal;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCsPostCompAddress() {
        return csPostCompAddress;
    }

    public void setCsPostCompAddress(String csPostCompAddress) {
        this.csPostCompAddress = csPostCompAddress;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
