package cn.farm.utils.Translation;


import cn.farm.entity.Image;

public interface Translation<E,T> {

    void translate(T t, String path);

    Translation set(E e);
}
