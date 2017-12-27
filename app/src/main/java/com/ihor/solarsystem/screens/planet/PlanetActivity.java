package com.ihor.solarsystem.screens.planet;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ihor.solarsystem.R;
import com.ihor.solarsystem.models.Planet;

public class PlanetActivity extends AppCompatActivity {

    private static final String EXTRA_PLANET = "planet";

    @SuppressWarnings("unchecked")
    public static void start(Activity activity, Planet planet, Pair<View, String> ... pairs) {
        Intent intent = new Intent(activity, PlanetActivity.class);
        intent.putExtra(EXTRA_PLANET, planet);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        activity.startActivity(intent, options.toBundle());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);

        Planet planet = getIntent().getParcelableExtra(EXTRA_PLANET);
        TextView planetTitle = findViewById(R.id.tv_planet_title);
        TextView planetDescription = findViewById(R.id.tv_planet_description);
        ImageView planetPicture = findViewById(R.id.iv_planet_picture);

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
