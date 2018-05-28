package com.demo.pojo;

import java.util.Date;

public class TbEvaluation {
    private Integer id;

    private Integer orderId;

    private Integer serviceEfficiency;

    private Integer serviceAttitude;

    private String desc;

    private String enable;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceEfficiency() {
        return serviceEfficiency;
    }

    public void setServiceEfficiency(Integer serviceEfficiency) {
        this.serviceEfficiency = serviceEfficiency;
    }

    public Integer getServiceAttitude() {
        return serviceAttitude;
    }

    public void setServiceAttitude(Integer serviceAttitude) {
        this.serviceAttitude = serviceAttitude;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}