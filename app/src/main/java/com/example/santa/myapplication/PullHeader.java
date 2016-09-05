package com.example.santa.myapplication;

/**
 * Created by santa on 16/6/21.
 */
public interface PullHeader {
    boolean isMoveWithContent();

    boolean isCanRefresh();

    void onPullProgress(float percent, int status);

    void onRefreshComplete();
}
