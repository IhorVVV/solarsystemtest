package com.ihor.solarsystem.network;


import com.ihor.solarsystem.models.PlanetContainer;

import io.reactivex.Flowable;

interface PlanetReceiver {

    Flowable<PlanetContainer> getPlanets(long page);

}
