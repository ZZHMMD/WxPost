package com.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class TbUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8966005664733381390L;

	private String id;

    private String name;

    private String phoneNum;
    
    private String school;

    private String college;

    private String studentCardNum;

    private String studentCard;

    private Integer creditScore;

    private String location;

    private Boolean enable;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getStudentCardNum() {
        return studentCardNum;
    }

    public void setStudentCardNum(String studentCardNum) {
        this.studentCardNum = studentCardNum;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	@Override
	public String toString() {
		return "TbUser [id=" + id + ", name=" + name + ", phoneNum=" + phoneNum + ", school=" + school + ", college="
				+ college + ", studentCardNum=" + studentCardNum + ", studentCard=" + studentCard + ", creditScore="
				+ creditScore + ", location=" + location + ", enable=" + enable + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + "]";
	}
    
}