package cn.farm.mapper;

import cn.farm.entity.Spectral;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SpectralMapper {

    @Select("select * from spectral where name = #{name} and format = #{format} and description = #{description} and author = #{author} and location = #{location} and device = #{device}")
    Integer select(Spectral spectral);

    @Insert("insert into spectral(name,format,description,author,time,location,device,spectralFile) values(#{name},#{format},#{description},#{author},#{time},#{location},#{device},#{spectralfile})")
    void add(Spectral spectral);

    @Select("select * from spectral where spectralID = #{spectralid}")
    Integer selectById(Integer spectralid);

    @Delete("delete from spectral where spectralID = #{spectralid}")
    void delete(Integer spectralid);

    @Update("update spectral set name = #{name},format = #{format},description = #{description},author = #{author},location = #{location},device = #{device},spectralFile = #{spectralfile} where spectralID = #{spectralid}")
    Integer modify(Spectral spectral);

    @Select("select * from spectral")
    List<Spectral> selectAll();
}
