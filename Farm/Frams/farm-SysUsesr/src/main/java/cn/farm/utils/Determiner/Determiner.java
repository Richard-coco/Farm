package cn.farm.utils.Determiner;

import org.springframework.web.multipart.MultipartFile;

public interface Determiner {

    boolean determine();

    void setFile(MultipartFile file);
}
