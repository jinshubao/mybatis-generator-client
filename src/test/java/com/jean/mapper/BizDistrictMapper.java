package com.jean.mapper;

import com.jean.models.BizDistrict;

public interface BizDistrictMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    int deleteByPrimaryKey(Integer districtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    int insert(BizDistrict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    int insertSelective(BizDistrict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    BizDistrict selectByPrimaryKey(Integer districtId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    int updateByPrimaryKeySelective(BizDistrict record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table biz_district
     *
     * @mbg.generated Sun Apr 16 17:13:08 CST 2017
     */
    int updateByPrimaryKey(BizDistrict record);
}