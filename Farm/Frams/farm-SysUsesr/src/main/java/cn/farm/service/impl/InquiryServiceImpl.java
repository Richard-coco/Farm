package cn.farm.service.impl;

import cn.farm.entity.Inquiry;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.exception.DataException;
import cn.farm.mapper.InquiryMapper;
import cn.farm.mapper.SpectralMapper;
import cn.farm.service.InquiryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService {
    @Autowired
    InquiryMapper inquiryMapper;
    @Autowired
    SpectralMapper spectralMapper;

    @Transactional
    @Override
    public void add(Integer userid, int plantid, String symptom, int images, int videoid, Integer spectral, Date date) {
        Inquiry inquiry = new Inquiry(userid, plantid, symptom, images, videoid, spectral, date);
        try {

            // 1 添加之前1先查询数据库中是否存在该数据
            Integer inq = inquiryMapper.select(inquiry);
            if (inq != null) {
                // 2. 如果存在，则不插入
                throw new DataException("400", "存在该数据,请勿重新添加");
            } else {
                // 3. 如果不存在， 则插入
                Integer id = spectralMapper.selectById(spectral);
                inquiry.setSpectral(id);
                inquiryMapper.add(inquiry);
            }
        } catch (DataException da) {
            throw new DataException(da.getCode(), da.getMessage());
        }

    }

    @Override
    public void delete(Integer inquiryid) {
        // 1. 先查询数据库中是否存在该数据
        Integer id = inquiryMapper.selectById(inquiryid);
        if (id != null) {
            // 2. 如果存在，则根据id删除数据
            inquiryMapper.delete(inquiryid);
        } else {
            // 3. 如果不存在，则报错
            throw new DataException("400", "数据库中不存在该数据，删除异常");
        }

    }


    @Override
    public PageInfo<Inquiry> selectAll(int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Inquiry> inquiries = inquiryMapper.selectAll();
        PageInfo<Inquiry> pageInfoInquiryList = new PageInfo<Inquiry>(inquiries);
        return pageInfoInquiryList;
    }

    @Override
    public Integer modify(Inquiry inquiry) {
        // 1. 在更新之前根据id查询是否有要查询的数据
        Integer id = inquiryMapper.selectById(inquiry.getInquiryid());

        if (id != null) {
            // 2. 如果有则，更新数据
            return inquiryMapper.modify(inquiry);
        } else {
            // 3, 如果没有则报错
            throw new DataException("400", "数据不存在，不能更新");
        }
    }
}

