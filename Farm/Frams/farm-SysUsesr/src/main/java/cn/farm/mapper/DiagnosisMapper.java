package cn.farm.mapper;

import cn.farm.entity.Diagnosis;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisMapper {
    @Insert("insert into diagnosis(inquiryID,ruleID,modelID,type,degree) values(#{inquiryid},#{ruleid},#{modelid},#{type},#{degree})")
    void insert(Diagnosis diagnosis);

    @Select("select * from diagnosis")
    List<Diagnosis> selectAll();

    @Select("select inquiryID from diagnosis where inquiryID = #{inquiryid}")
    Integer selectById(Integer inquiryid);

    @Delete("delete from diagnosis where inquiryID = #{inquiryid}")
    void delete(Integer inquiryid);

}
