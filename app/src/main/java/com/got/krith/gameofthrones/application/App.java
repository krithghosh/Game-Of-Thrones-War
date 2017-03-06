package com.got.krith.gameofthrones.application;

import android.app.Application;

import com.got.krith.gameofthrones.network.NetModule;
import com.got.krith.gameofthrones.repository.BattleRepositoryModule;

/**
 * Created by krith on 05/03/17.
 */

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        String BASE_URL = "https://demo6869072.mockable.io";
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .battleRepositoryModule(new BattleRepositoryModule())
                .build();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }
}