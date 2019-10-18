package cn.farm.MessgaeModel;

import org.apache.solr.common.SolrDocumentList;

public class SolrMessage extends Common {

    int num;

    SolrDocumentList solrDocuments;

    public SolrMessage(int code, String message, int num, SolrDocumentList solrDocuments) {
        super(code, message);
        this.num = num;
        this.solrDocuments = solrDocuments;
    }

    public SolrMessage(SolrDocumentList solrDocuments) {
        this.solrDocuments = solrDocuments;
    }

    public SolrDocumentList getSolrDocuments() {
        return solrDocuments;
    }

    public void setSolrDocuments(SolrDocumentList solrDocuments) {
        this.solrDocuments = solrDocuments;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
