package cn.farm.Util.Translation;

public interface Translation<E,K> {

    void translate(K k, String path);

    Translation set(E e);
}
