package com.jean.models;

import java.io.Serializable;
import java.util.Date;

public class BizDistrict implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.DISTRICT_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private Integer districtId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.DISTRICT_NAME
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private String districtName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.CITY_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private Integer cityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.STATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.LAST_MODIFIER
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column biz_district.LAST_MODIFY_DATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private Date lastModifyDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.DISTRICT_ID
     *
     * @return the value of biz_district.DISTRICT_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.DISTRICT_ID
     *
     * @param districtId the value for biz_district.DISTRICT_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.DISTRICT_NAME
     *
     * @return the value of biz_district.DISTRICT_NAME
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.DISTRICT_NAME
     *
     * @param districtName the value for biz_district.DISTRICT_NAME
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.CITY_ID
     *
     * @return the value of biz_district.CITY_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.CITY_ID
     *
     * @param cityId the value for biz_district.CITY_ID
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.STATE
     *
     * @return the value of biz_district.STATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.STATE
     *
     * @param state the value for biz_district.STATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.LAST_MODIFIER
     *
     * @return the value of biz_district.LAST_MODIFIER
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.LAST_MODIFIER
     *
     * @param lastModifier the value for biz_district.LAST_MODIFIER
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column biz_district.LAST_MODIFY_DATE
     *
     * @return the value of biz_district.LAST_MODIFY_DATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column biz_district.LAST_MODIFY_DATE
     *
     * @param lastModifyDate the value for biz_district.LAST_MODIFY_DATE
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}