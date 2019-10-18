package cn.farm.Solr.Pojo;

import org.apache.solr.client.solrj.beans.Field;

public class SolrImage {

    @Field
    private String id;
    @Field
    private String imagename;
    @Field
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "imagename:"+imagename+" description:"+description+" id:"+id;
    }
}
