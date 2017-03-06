package com.got.krith.gameofthrones.repository;

import com.got.krith.gameofthrones.application.App;
import com.got.krith.gameofthrones.model.Battle;
import com.got.krith.gameofthrones.network.RestApiService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by krith on 04/03/17.
 */

public class BattleRemoteRepository implements BattleRepositoryContract.BattleRemoteRepository {

    @Inject
    Retrofit retrofit;

    public BattleRemoteRepository() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<List<Battle>> getAllBattles() {
        return retrofit.create(RestApiService.class).getBattles();
    }
}