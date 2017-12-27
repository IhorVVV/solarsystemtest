package com.ihor.solarsystem.screens.listplanet;


import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ihor.solarsystem.R;
import com.ihor.solarsystem.models.Planet;

class PlanetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView mPlanetPicture;
    private final TextView mPlanetTitle;
    private final TextView mPlanetDescription;

    private final RequestOptions placeholderOption;

    private final PlanetsAdapter.OnPlanetClickListener mPlanetClickListener;

    private PlanetHolder(View itemView, PlanetsAdapter.OnPlanetClickListener clickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        mPlanetPicture = itemView.findViewById(R.id.iv_planet_picture);
        mPlanetTitle = itemView.findViewById(R.id.tv_planet_title);
        mPlanetDescription = itemView.findViewById(R.id.tv_planet_description);
        placeholderOption = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground);
        mPlanetClickListener = clickListener;
    }

    static PlanetHolder newInstance(LayoutInflater inflater, ViewGroup parent, PlanetsAdapter.OnPlanetClickListener clickListener) {
        View view = inflater.inflate(R.layout.list_item_planet, parent, false);
        return new PlanetHolder(view, clickListener);
    }

    void bind(Planet planet) {
        itemView.setTag(planet);
        mPlanetTitle.setText(planet.getNameText());
        mPlanetDescription.setText(planet.getDescriptionText());
        Glide.with(mPlanetPicture)
                .load(planet.getImageUrl())
                .apply(placeholderOption)
                .into(mPlanetPicture);
    }


    @Override
    public void onClick(View view) {
        Object tag = view.getTag();
        if (mPlanetClickListener != null && tag instanceof Planet) {
            //noinspection unchecked
            mPlanetClickListener.onPlanetClick((Planet) tag,
                    Pair.create(mPlanetPicture, mPlanetPicture.getContext().getString(R.string.picture)),
                    Pair.create(mPlanetTitle, mPlanetTitle.getContext().getString(R.string.title)),
                    Pair.create(mPlanetDescription, mPlanetTitle.getContext().getString(R.string.description)));

        }
    }
}
