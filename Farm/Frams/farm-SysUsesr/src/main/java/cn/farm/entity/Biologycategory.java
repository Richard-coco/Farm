package cn.farm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "biologycategory")
public class Biologycategory {
    @Id
    private Integer bioid;

    private String kingdom;

    private String phylum;

    @Column(name = "class")
    private String bio_class;

    @Column(name = "bio_order")
    private String order;

    private String family;

    private String genus;

    private String species;

    public Biologycategory(String kingdom, String phylum, String bio_class, String order, String family, String genus, String species) {
        this.kingdom = kingdom;
        this.phylum = phylum;
        this.bio_class = bio_class;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
    }

    public Biologycategory() {

    }
}