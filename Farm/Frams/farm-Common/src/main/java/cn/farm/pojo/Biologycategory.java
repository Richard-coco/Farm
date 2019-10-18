package cn.farm.pojo;

import javax.persistence.Column;

public class Biologycategory {
    private Integer bioid;

    private String kingdom;

    private String phylum;
@Column(name = "class")
    private String _class;

    private String bioOrder;

    public void set_class(String _class) {
        this._class = _class;
    }

    private String family;

    private String genus;

    private String species;

    public Integer getBioid() {
        return bioid;
    }

    public void setBioid(Integer bioid) {
        this.bioid = bioid;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom == null ? null : kingdom.trim();
    }

    public String getPhylum() {
        return phylum;
    }

    public void setPhylum(String phylum) {
        this.phylum = phylum == null ? null : phylum.trim();
    }

    public String get_class() {
        return _class;
    }

    public void setClass(String _class) {
        this._class = _class == null ? null : _class.trim();
    }

    public String getBioOrder() {
        return bioOrder;
    }

    public void setBioOrder(String bioOrder) {
        this.bioOrder = bioOrder == null ? null : bioOrder.trim();
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family == null ? null : family.trim();
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus == null ? null : genus.trim();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species == null ? null : species.trim();
    }
}