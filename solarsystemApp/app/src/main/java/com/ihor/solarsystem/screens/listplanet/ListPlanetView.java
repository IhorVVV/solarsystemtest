package com.ihor.solarsystem.screens.listplanet;


import com.ihor.solarsystem.models.Planet;

import java.util.List;

interface ListPlanetView {

    void addPlanets(List<Planet> planetList);

    void updatePlanets(List<Planet> planetList);

    void disablePagination();

    void onError();
}
