package com.ihor.solarsystem.network;


import com.ihor.solarsystem.models.PlanetContainer;

import io.reactivex.Flowable;

public class PlanetReceiverImpl implements PlanetReceiver {

    private final ApiPlanet mApiPlanet;

    public PlanetReceiverImpl() {
        mApiPlanet = RestClient.getInstance().create(ApiPlanet.class);
    }

    @Override
    public Flowable<PlanetContainer> getPlanets(long page) {
        return mApiPlanet.getPlanets(page);
    }
}
