package com.ihor.solarsystem.screens.listplanet;


import android.util.Log;

import com.ihor.solarsystem.BuildConfig;
import com.ihor.solarsystem.models.Planet;
import com.ihor.solarsystem.network.NetworkContract;
import com.ihor.solarsystem.network.PlanetReceiverImpl;
import com.ihor.solarsystem.utils.Rxx;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

class ListPlanetPresenterImpl implements ListPlanetPresenter {

    private final PlanetReceiverImpl mPlanetReceiver;

    private ListPlanetView mView;

    private long mLastPage = 0;
    private final List<Planet> mPlanetList;
    private final CompositeDisposable mCompositeDisposable;

    ListPlanetPresenterImpl() {
        mPlanetReceiver = new PlanetReceiverImpl();
        mPlanetList = new ArrayList<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadData() {
        mLastPage = 0;
        mCompositeDisposable.add(mPlanetReceiver.getPlanets(mLastPage)
                .compose(Rxx.ioAndMain())
                .subscribe(planetContainer -> {
                    mLastPage = planetContainer.getPage();
                    mPlanetList.clear();
                    mPlanetList.addAll(planetContainer.getItems());
                    if (mView != null) {
                        mView.updatePlanets(planetContainer.getItems());
                    }
                }, throwable -> {
                    if (mView != null) {
                        mView.onError();
                    }
                }));
    }

    @Override
    public void loadMoreData() {
        mCompositeDisposable.add(mPlanetReceiver.getPlanets(mLastPage)
                .compose(Rxx.ioAndMain())
                .subscribe(planetContainer -> {
                    mLastPage = planetContainer.getPage();
                    mPlanetList.addAll(planetContainer.getItems());
                    if (mView != null) {
                        mView.addPlanets(planetContainer.getItems());
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException exception = (HttpException) throwable;
                        if (exception.code() == NetworkContract.HttpStatus.PAGE_NOT_FOUND
                                && mView != null) {
                            mView.disablePagination();
                        }
                    } else {
                        printError(throwable);
                    }
                }));
    }

    @Override
    public void setView(ListPlanetView view) {
        mView = view;
        if (mView != null && !mPlanetList.isEmpty()) {
            mView.updatePlanets(mPlanetList);
        }
    }

    @Override
    public void destroy() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    private void printError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(ListPlanetPresenterImpl.class.getSimpleName(), "Error", throwable);
        }
    }
}
