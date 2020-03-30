package com.ihor.solarsystem.screens.planet;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ihor.solarsystem.R;
import com.ihor.solarsystem.models.Planet;

public class PlanetFragment extends Fragment {

    private static final String EXTRA_PLANET = "planet";

    public static PlanetFragment newInstance(Planet planet) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PLANET, planet);
        PlanetFragment fragment = new PlanetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_planet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Planet planet = getArguments().getParcelable(EXTRA_PLANET);

        TextView planetTitle = view.findViewById(R.id.tv_planet_title);
        TextView planetDescription = view.findViewById(R.id.tv_planet_description);
        ImageView planetPicture = view.findViewById(R.id.iv_planet_picture);

        planetTitle.setText(planet.getNameText());
        planetDescription.setText(planet.getDescriptionText());
        Glide.with(planetPicture)
                .load(planet.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground))
                .into(planetPicture);
    }
}
