package com.example.santa.myapplication;

/**
 * Created by santa on 16/6/24.
 */
public interface PullHandler {
    public void onRefreshBegin(final PullRefreshLayout layout);

    public void onRefreshFinshed();
}
