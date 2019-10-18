package cn.farm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Analysis {
    private Integer infoid;

    private String analysis;

    private Integer expert;

    // 使得返回的json数据以yyyy-MM-dd HH:mm:ss形式返回
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public Analysis(Integer infoid, String analysis, Integer expert, Date time) {
        this.infoid = infoid;
        this.analysis = analysis;
        this.expert = expert;
        this.time = time;
    }
}