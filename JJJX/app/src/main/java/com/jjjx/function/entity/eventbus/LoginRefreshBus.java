package com.jjjx.function.entity.eventbus;

/**
 *
 * @author xz
 * @date 2017/11/2 0002
 * 用来刷新已登陆的界面
 */

public class LoginRefreshBus {
    private boolean isRefresh;

    public LoginRefreshBus(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
