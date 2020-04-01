package com.ihor.solarsystem.utils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal;
    private boolean loading = true;
    private final int visibleThreshold;
    private final RecyclerView recyclerView;
    private final LinearLayoutManager layoutManager;
    private OnLoadMoreListener onLoadMoreListener;

    private EndlessScrollListener(@NonNull RecyclerView recyclerView, int visibleThreshold) {
        this.recyclerView = recyclerView;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.visibleThreshold = visibleThreshold;
        recyclerView.addOnScrollListener(this);
    }

    public static EndlessScrollListener create(@NonNull RecyclerView recyclerView, int visibleThreshold) {
        return new EndlessScrollListener(recyclerView, visibleThreshold);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = this.recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            if (onLoadMoreListener != null) {
                onLoadMoreListener.loadMore();
            }
            loading = true;
        }
    }

    public void reset() {
        loading = false;
        previousTotal = 0;
    }

    public void onLoadMoreListener(@Nullable OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @FunctionalInterface
    public interface OnLoadMoreListener {
        void loadMore();
    }
}
