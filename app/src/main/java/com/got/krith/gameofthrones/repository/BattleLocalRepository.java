package com.got.krith.gameofthrones.repository;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.got.krith.gameofthrones.application.App;
import com.got.krith.gameofthrones.database.KingTable;
import com.got.krith.gameofthrones.model.King;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by krith on 04/03/17.
 */

public class BattleLocalRepository implements BattleRepositoryContract.BattleLocalRepository {

    @Inject
    BriteDatabase briteDatabase;

    public BattleLocalRepository() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<List<King>> addKingsData(List<King> kings) {
        BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (King king : kings) {
                briteDatabase.insert(KingTable.TABLE, new KingTable.Builder()
                        .setName(king.getName())
                        .setRating(king.getRating())
                        .setStrength(king.getStrength())
                        .setStrengthBattleType(king.getStrengthBattleType())
                        .setBattlesWon(king.getBattlesWon())
                        .setBattlesLost(king.getBattlesLost())
                        .build(), SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        return getAllKings();
    }

    @Override
    public Observable<List<King>> getAllKings() {
        return briteDatabase.createQuery(KingTable.TABLE, KingTable.QUERY_ALL_KINGS)
                .mapToList(KingTable.MAPPER);
    }

    @Override
    public Observable<King> getKing(int kingId) {
        Observable<King> obs = null;
        try {
            obs = briteDatabase.createQuery(KingTable.TABLE, KingTable.QUERY_A_KING,
                    String.valueOf(kingId))
                    .mapToOne(KingTable.MAPPER);
        } catch (Exception e) {
            Log.d("Krith Value", e.getMessage());
        }
        return obs;
    }

    @Override
    public Observable<List<King>> getKingByRating() {
        return briteDatabase.createQuery(KingTable.TABLE, KingTable.QUERY_KINGS_BY_RATING)
                .mapToList(KingTable.MAPPER);
    }

    @Override
    public Observable<List<King>> getKingByStrength() {
        return briteDatabase.createQuery(KingTable.TABLE, KingTable.QUERY_KINGS_BY_STRENGTH)
                .mapToList(KingTable.MAPPER);
    }

    @Override
    public void deleteAllData() {
        briteDatabase.delete(KingTable.TABLE, null, null);
    }
}
