package cn.farm.Solr;


import cn.farm.Solr.Pojo.SolrImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class MySolr {

    String morphology = "morphology:";
    String chineseName = "chineseName:";
    String alias = "alias:";
    String latinName = "latinName:";
    String distribution = "distribution:";
    String category_phylum = "category_phylum:";
    String category_genus = "category_genus:";
    String category_species = "category_species:";
    String category_family = "category_family:";
    String category_kingdom = "category_kingdom:";
    String category_class = "category_class:";
    String category_order = "category_order:";
    @Autowired
    SolrClient solrClient;

    public String makeQ(String context){
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        list.add(morphology);
        list.add(chineseName);
        list.add(alias);
        list.add(latinName);
        list.add(distribution);
        list.add(category_phylum);
        list.add(category_genus);
        list.add(category_species);
        list.add(category_family);
        list.add(category_kingdom);
        list.add(category_class);
        list.add(category_order);
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            sb.append(iterator.next()+context+" ");
        }
        return sb.toString();
    }


    public SolrImage selectImageByID(String id){
        SolrImage solrImage = null;
        try {
            SolrDocument document = solrClient.getById(id);
            System.out.println("filedName"+ document.getFieldNames());
            System.out.println("fieldValueMap"+document.getFieldValueMap());
            System.out.println("childDocuments"+document.getChildDocuments());
            //System.out.println("childDocumentCount"+ document.getChildDocumentCount());
            solrImage = new SolrImage();
            solrImage.setId((String) document.getFieldValue("id"));
            solrImage.setImagename((String) document.getFieldValue("imagename"));
            solrImage.setDescription((String) document.getFieldValue("description"));
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solrImage;
    }



    //根据map查询索引库 返回SolrDocumentList
    public SolrDocumentList selectByMap(Map<String,String> map){
        MapSolrParams mapSolrParams = new MapSolrParams(map);
        SolrDocumentList results = null;
        try {
            QueryResponse response = solrClient.query(mapSolrParams);
            results = response.getResults();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public SolrDocumentList select(String context, String start , String rows){
        Map<String,String> map = new HashMap<>();
        String q = makeQ(context);
        map.put("q",q);
        map.put("start",start);
        map.put("rows",rows);
        return selectByMap(map);
    }
}
