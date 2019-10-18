package cn.farm.Util.Determiner;

import org.springframework.web.multipart.MultipartFile;

public interface Determiner {

    boolean determine();

    void setFile(MultipartFile file);
}
