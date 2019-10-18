package cn.farm.expert.utils;

import cn.farm.expert.domain.Information;
import org.apache.ibatis.jdbc.SQL;

public class InformationProvider {

    public String updateInformation(final Information information){

        return new SQL(){{
            UPDATE("information");

            //条件写法.
            if(information.getTitle()!= null){
                SET("title=#{title}");
            }
            if(information.getContent() != null) {
                SET("content = #{content}");
            }
            if(information.getAuthor() != 0) {
                SET("author = #{author}");
            }
            if(information.getTime() != null) {
                SET("time = #{time}");
            }
            if(information.getLocation() != null) {
                SET("location = #{location}");
            }
            if(information.getImages()!= 0) {
                SET("images = #{images}");
            }
            if (information.getVideos() != 0) {
                SET("videos = #{videos}");
            }
            if (information.getInfoId() != 0) {
                SET("infoId = #{infoId}");
            }

            WHERE("infoId=#{infoId} and author = #{author}");
        }}.toString();
    }
    }
