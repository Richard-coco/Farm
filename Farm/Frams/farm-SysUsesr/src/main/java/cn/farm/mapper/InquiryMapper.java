package cn.farm.mapper;

import cn.farm.entity.Inquiry;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryMapper {
    @Select("select * from inquiry where userID = #{userid} and cropID = #{cropid} and symptom = #{symptom} ")
    Integer select(Inquiry inquiry);

    @Insert("insert into inquiry(userID,cropID,symptom,image,video,spectral,time) values(#{userid},#{cropid},#{symptom},#{image},#{video},#{spectral},#{time})")
    void add(Inquiry inquiry);

    @Select("select * from inquiry where inquiryID = #{inquiryid}")
    Integer selectById(Integer inquiryid);

    @Delete(" delete from inquiry where inquiryID = #{inquiryid}")
    void delete(Integer inquiryid);

    @Select("select * from inquiry")
    List<Inquiry> selectAll();

    @Update("update inquiry set userID = #{userid},cropID = #{cropid},symptom = #{symptom},image = #{image},video = #{video},spectral = #{spectral} where inquiryID = #{inquiryid}")
    Integer modify(Inquiry inquiry);
}
