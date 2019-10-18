package cn.farm.Controller;

import cn.farm.Entity.Information;
import cn.farm.Entity.SwiperImage;
import cn.farm.Message.Common;
import cn.farm.Message.ListMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping(value = "getNavList",produces = "application/json")
    public Common getNavList(){
        List<String> list = new ArrayList<>();
        list.add("草害");
        list.add("病害");
        list.add("虫害");
        list.add("虫害");
        list.add("虫害");
        list.add("虫害");
        return new ListMessage(200,"ok",list);
    }

    @GetMapping(value = "getSwiperList",produces = "application/json")
    public Common getSwiperList(){
        List<SwiperImage> list = new ArrayList<>();
        list.add(new SwiperImage("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570802941129&di=a6f7152a66b39e77fbd84834303d584e&imgtype=0&src=http%3A%2F%2Fpic16.nipic.com%2F20110909%2F6995791_133826633000_2.jpg"));
        list.add(new SwiperImage("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570802960171&di=870ccaecfb5085bbf497a77488c0aa84&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D109353495%2C3394727874%26fm%3D214%26gp%3D0.jpg"));
        list.add(new SwiperImage("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570802941127&di=e2643d78b3b8a26b18f467e9063ae6b8&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F120822%2F214833-120R21P35697.jpg"));
        list.add(new SwiperImage("","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1570802941125&di=5b3f740e3ca1f59f784c8e721cd52171&imgtype=0&src=http%3A%2F%2Fpic21.nipic.com%2F20120525%2F9705247_104140570143_2.jpg"));
        return new ListMessage(200,"ok",list);
    }

    @GetMapping(value = "getInforList" ,produces = "application/json")
    public Common getInforList(){
        List<Information> list = new ArrayList<>();
        list.add(new Information(1,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3310400099,2915280352&fm=26&gp=0.jpg",
                "真菌性病害由真菌侵染所致的病害...","101.8万","15.1万","http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
        list.add(new Information(2,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3310400099,2915280352&fm=26&gp=0.jpg",
                "细菌性病害由细菌侵染所致的病害...","55.9万","5万","http://vjs.zencdn.net/v/oceans.mp4"));
        list.add(new Information(3,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3310400099,2915280352&fm=26&gp=0.jpg",
                "病毒病主要借助于带毒昆虫传染,...","88.8万","14.8万","https://media.w3.org/2010/05/sintel/trailer.mp4"));
        list.add(new Information(4,"","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3310400099,2915280352&fm=26&gp=0.jpg",
                "线虫病植物病原线虫,体积微小,...","77.6万","10.1万","http://mirror.aarnet.edu.au/pub/TED-talks/911Mothers_2010W-480p.mp4"));
        return new ListMessage(200,"ok",list);
    }
}
