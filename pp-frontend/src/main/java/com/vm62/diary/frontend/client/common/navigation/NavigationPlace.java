package com.vm62.diary.frontend.client.common.navigation;

public class NavigationPlace {
    private NavigationUrl url;

    public NavigationPlace(NavigationUrl url) {
        this.url = url;
    }

    public NavigationUrl getUrl() {
        return url;
    }
}