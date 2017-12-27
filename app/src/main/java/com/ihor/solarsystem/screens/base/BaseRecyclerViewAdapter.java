package com.ihor.solarsystem.screens.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<D, H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<H> {

    private final LayoutInflater inflater;
    private final List<D> data;

    public BaseRecyclerViewAdapter(@NonNull final Context context) {
        this.inflater = LayoutInflater.from(context);
        data = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public BaseRecyclerViewAdapter(@NonNull final Context context, @NonNull List<D> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>(data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressWarnings("WeakerAccess")
    public D getItem(final int position) {
        return data.get(position);
    }

    @SuppressWarnings("unused")
    public boolean add(D object) {
        return data.add(object);
    }

    @SuppressWarnings("unused")
    public boolean remove(D object) {
        return data.remove(object);
    }

    @SuppressWarnings("unused")
    public D remove(int position) {
        return data.remove(position);
    }

    public void clear() {
        data.clear();
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addAll(@NonNull Collection<? extends D> collection) {
        return data.addAll(collection);
    }

    @SuppressWarnings("unused")
    public List<D> getSnapshot() {
        return new ArrayList<>(data);
    }

    protected LayoutInflater getInflater() {
        return inflater;
    }

    @SuppressWarnings("unused")
    public int getItemPosition(D object) {
        return data.indexOf(object);
    }

    @SuppressWarnings("unused")
    public void insert(D object, int position) {
        data.add(position, object);
    }

    @SuppressWarnings("unused")
    public void insertAll(Collection<? extends D> object, int position) {
        data.addAll(position, object);
    }
}