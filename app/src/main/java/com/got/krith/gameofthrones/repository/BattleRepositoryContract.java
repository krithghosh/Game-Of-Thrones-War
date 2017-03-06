package com.got.krith.gameofthrones.repository;

import com.got.krith.gameofthrones.model.Battle;
import com.got.krith.gameofthrones.model.King;

import java.util.List;

import rx.Observable;

/**
 * Created by krith on 04/03/17.
 */

public interface BattleRepositoryContract {
    interface BattleMainRepository {
        Observable<List<King>> getAllBattles();

        Observable<List<King>> getAllKings();

        Observable<King> getKing(int kingId);

        Observable<List<King>> getKingByRating();

        Observable<List<King>> getKingByStrength();
    }

    interface BattleLocalRepository {
        Observable<List<King>> addKingsData(List<King> kings);

        Observable<List<King>> getAllKings();

        Observable<King> getKing(int kingId);

        Observable<List<King>> getKingByRating();

        Observable<List<King>> getKingByStrength();

        void deleteAllData();
    }

    interface BattleRemoteRepository {
        Observable<List<Battle>> getAllBattles();
    }
}
