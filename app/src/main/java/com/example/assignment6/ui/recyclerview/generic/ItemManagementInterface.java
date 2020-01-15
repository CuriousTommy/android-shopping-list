package com.example.assignment6.ui.recyclerview.generic;

public interface ItemManagementInterface<A> {
    void remove(long index);
    void update(A item);
    void add(A item);
}
