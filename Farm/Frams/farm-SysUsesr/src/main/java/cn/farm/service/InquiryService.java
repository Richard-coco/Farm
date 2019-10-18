package cn.farm.service;

import cn.farm.entity.Inquiry;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

public interface InquiryService {
    void add(Integer userid, int plantid, String symptom, int images, int videoid, Integer spectral, Date date);

    void delete(Integer inquiryid);

    PageInfo<Inquiry> selectAll(int page,int pagesize);

    Integer modify(Inquiry inquiry);
}
