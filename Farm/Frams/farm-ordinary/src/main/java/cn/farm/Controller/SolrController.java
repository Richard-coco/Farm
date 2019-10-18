package cn.farm.Controller;


import cn.farm.MessgaeModel.Common;
import cn.farm.MessgaeModel.SolrMessage;
import cn.farm.Solr.MySolr;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolrController {

    @Autowired
    MySolr mySolr;

    @ResponseBody
    @RequestMapping("solr")
    public Common select(String context, String start, String rows){

        System.out.println(context+"---------"+start+"---------"+rows);
        if(context == null){
            return new SolrMessage(400,"请传入参数",0,null);
        }
        SolrDocumentList solrDocumentList;
        try{
            solrDocumentList = mySolr.select(context, start, rows);
        }catch (Exception e){
            return new Common(500,"server error");
        }

        System.out.println("into solr -----");
        return new SolrMessage(200,"ok",solrDocumentList.size(),solrDocumentList);
    }
}
