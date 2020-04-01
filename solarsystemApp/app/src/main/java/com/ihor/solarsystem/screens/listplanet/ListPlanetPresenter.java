package com.ihor.solarsystem.screens.listplanet;


interface ListPlanetPresenter {

    void loadData();
    void loadMoreData();
    void setView(ListPlanetView view);
    void destroy();
}
