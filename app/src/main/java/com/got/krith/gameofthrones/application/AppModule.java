package com.got.krith.gameofthrones.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.got.krith.gameofthrones.database.DbModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krith on 05/03/17.
 */

@Module(includes = {DbModule.class})
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Resources providesResource(Context context) {
        return context.getResources();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    SharedPreferenceManager providesSharedPreferenceManager(SharedPreferences sharedPreferences) {
        return new SharedPreferenceManager(sharedPreferences);
    }
}
