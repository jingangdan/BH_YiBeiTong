package com.bh.yibeitong.refresh.recyclerview;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 12406 on 2016/4/16.
 */
public class RecyclerViewUtils {

    static LoadingData data;


    public static void OnScrollListener(final Activity activity, RecyclerView recyclerView) {

        data = (LoadingData) activity;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();

                    int totalItemCount = manager.getItemCount();

                    if (lastVisibleItem == (totalItemCount - 1) && !isSlidingToLast) {

                        data.onLoadMore();


                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dx > 0) {

                    isSlidingToLast = true;

                } else {

                    isSlidingToLast = false;

                }

            }
        });
    }

    public interface LoadingData {

        void onLoadMore();

    }
}

