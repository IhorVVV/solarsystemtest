package com.ihor.solarsystem.screens.listplanet;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.ihor.solarsystem.R;
import com.ihor.solarsystem.models.Planet;
import com.ihor.solarsystem.screens.base.BaseRecyclerViewAdapter;
import com.ihor.solarsystem.screens.planet.PlanetActivity;
import com.ihor.solarsystem.utils.EndlessScrollListener;

import java.util.List;

public class ListPlanetFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        EndlessScrollListener.OnLoadMoreListener,
        ListPlanetView,
        PlanetsAdapter.OnPlanetClickListener {

    private static final int SWITCHER_LIST = 0;
    private static final int SWITCHER_PLACEHOLDER = 1;

    private static final int VISIBLE_USERS_THRESHOLD = 10;

    private ViewSwitcher mSwitcher;
    private SwipeRefreshLayout mRefreshLayout;

    private EndlessScrollListener mEndlessScrollListener;

    private BaseRecyclerViewAdapter<Planet, PlanetHolder> mAdapter;

    private ListPlanetPresenter mPresenter;

    public static ListPlanetFragment newInstance() {
        Bundle args = new Bundle();
        ListPlanetFragment fragment = new ListPlanetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new ListPlanetPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable
                                     Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_planet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwitcher = view.findViewById(R.id.view_switcher);
        mSwitcher.setDisplayedChild(SWITCHER_LIST);

        mRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        initList(view);
        mPresenter.loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setView(this);
    }

    @Override
    public void onPause() {
        mPresenter.setView(null);
        super.onPause();
    }

    @Override
    public void onDetach() {
        mPresenter.destroy();
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        mEndlessScrollListener.onLoadMoreListener(this);
        mPresenter.loadData();
    }

    @Override
    public void loadMore() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.loadMoreData();
    }

    @Override
    public void addPlanets(List<Planet> planetList) {
        mRefreshLayout.setRefreshing(false);
        int size = mAdapter.getItemCount();
        mAdapter.addAll(planetList);
        mAdapter.notifyItemRangeInserted(size, planetList.size());
    }

    @Override
    public void updatePlanets(List<Planet> planetList) {
        mSwitcher.setDisplayedChild(planetList.isEmpty() ? SWITCHER_PLACEHOLDER : SWITCHER_LIST);
        mRefreshLayout.setRefreshing(false);
        mEndlessScrollListener.reset();
        mAdapter.clear();
        mAdapter.addAll(planetList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void disablePagination() {
        mRefreshLayout.setRefreshing(false);
        mEndlessScrollListener.onLoadMoreListener(null);
    }

    @Override
    public void onError() {
        mSwitcher.setDisplayedChild(SWITCHER_PLACEHOLDER);
    }

    @SafeVarargs
    @Override
    public final void onPlanetClick(Planet planet, Pair<View, String>... sharedViews) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            PlanetActivity.start(activity, planet, sharedViews);
        }
    }

    private void initList(View view) {
        RecyclerView mListRv = view.findViewById(R.id.rv_list);
        mAdapter = new PlanetsAdapter(mListRv.getContext(), this);
        mListRv.setAdapter(mAdapter);
        mListRv.setLayoutManager(new LinearLayoutManager(mListRv.getContext(), LinearLayoutManager
                .VERTICAL, false));
        mEndlessScrollListener = EndlessScrollListener.create(mListRv, VISIBLE_USERS_THRESHOLD);
        mEndlessScrollListener.onLoadMoreListener(this);
    }
}
