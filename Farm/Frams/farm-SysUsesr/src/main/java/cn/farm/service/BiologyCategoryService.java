package cn.farm.service;


public interface BiologyCategoryService {
    int addBiologyCategory(String kingdom, String phylum, String bio_class, String order,
                           String family, String genus, String species);

    int findBioId(Integer bioid);
}
