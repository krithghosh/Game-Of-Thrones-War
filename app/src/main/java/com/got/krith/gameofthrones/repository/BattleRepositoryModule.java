package com.got.krith.gameofthrones.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by krith on 04/03/17.
 */

@Module
public class BattleRepositoryModule {
    @Singleton
    @Provides
    BattleLocalRepository providesLocalRepository() {
        return new BattleLocalRepository();
    }

    @Singleton
    @Provides
    BattleRemoteRepository providesRemoteRepository() {
        return new BattleRemoteRepository();
    }
}
