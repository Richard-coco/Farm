package cn.farm.Util.Determiner;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.InputStream;

public class ImageDeterminer implements Determiner {

    MultipartFile file;

    @Override
    public boolean determine() {
        try{
            // 通过ImageReader来解码这个file并返回一个BufferedImage对象
            // 如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
            // 或者在解析过程中报错，也返回false
            InputStream in = file.getInputStream();
            java.awt.Image image = ImageIO.read(in);
            in.close();
            return image!=null;
        }catch (Exception e){
            return false;
        }
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
