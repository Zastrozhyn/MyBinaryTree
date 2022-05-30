package com.epam.study;

public interface BinaryTree<T> {
    //Add object
    void add(T t);

    //Delete object from Tree
    boolean remove(T t);

    //return if obj contains in Tree
    boolean contains(T t);

    //return size of Tree
    int getSize();
}
