package com.ihor.solarsystem.network;


import com.ihor.solarsystem.models.PlanetContainer;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface ApiPlanet {

    @GET(NetworkContract.Base.ENDPOINT + "{page}")
    Flowable<PlanetContainer> getPlanets(@Path("page") long page);
}
