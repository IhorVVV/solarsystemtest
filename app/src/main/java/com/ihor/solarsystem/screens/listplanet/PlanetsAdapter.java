package com.ihor.solarsystem.screens.listplanet;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import com.ihor.solarsystem.models.Planet;
import com.ihor.solarsystem.screens.base.BaseRecyclerViewAdapter;

class PlanetsAdapter extends BaseRecyclerViewAdapter<Planet, PlanetHolder> {

    private final OnPlanetClickListener mPlanetClickListener;

    PlanetsAdapter(@NonNull Context context, OnPlanetClickListener clickListener) {
        super(context);
        mPlanetClickListener = clickListener;
    }

    @Override
    public PlanetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlanetHolder.newInstance(getInflater(), parent, mPlanetClickListener);
    }

    @Override
    public void onBindViewHolder(PlanetHolder holder, int position) {
        holder.bind(getItem(position));
    }

    interface OnPlanetClickListener {
        @SuppressWarnings("unchecked")
        void onPlanetClick(Planet planet, Pair<View, String>... sharedViews);
    }

}
