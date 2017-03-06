package com.got.krith.gameofthrones.repository;


import android.util.Log;

import com.got.krith.gameofthrones.model.Battle;
import com.got.krith.gameofthrones.model.King;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by krith on 04/03/17.
 */

public class BattleRepository implements BattleRepositoryContract.BattleMainRepository {

    private final BattleLocalRepository localRepository;
    private final BattleRemoteRepository remoteRepository;

    private final String ATTACK = "attack";
    private final String DEFENSE = "defense";
    private final String AMBUSH = "ambush";
    private final String PITCHED = "pitched battle";
    private final String RAZING = "razing";
    private final String SIEGE = "siege";
    private final String WIN = "win";
    private final String DRAW = "draw";
    private final String LOSS = "loss";

    private Map<Integer, King> mCachedKings;

    @Inject
    public BattleRepository(BattleLocalRepository localRepository, BattleRemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    @Override
    public Observable<List<King>> getAllBattles() {
        if (mCachedKings != null && mCachedKings.size() != 0)
            return Observable.from(mCachedKings.values()).toList();
        mCachedKings = new LinkedHashMap<>();
        return getAllKingsDataFromLocal();
    }

    private Observable<List<King>> getAllKingsDataFromLocal() {
        return localRepository.getAllKings()
                .flatMap(new Func1<List<King>, Observable<List<King>>>() {
                    @Override
                    public Observable<List<King>> call(List<King> kings) {
                        if (kings.size() == 0)
                            return getAllKingsDataFromRemote();
                        else
                            return Observable.just(kings);
                    }
                });
    }

    private Observable<List<King>> getAllKingsDataFromRemote() {
        return remoteRepository.getAllBattles()
                .doOnNext(new Action1<List<Battle>>() {
                    @Override
                    public void call(List<Battle> battles) {
                        localRepository.deleteAllData();
                    }
                })
                .map(new Func1<List<Battle>, List<King>>() {
                    @Override
                    public List<King> call(List<Battle> battles) {
                        Map<String, King> map = new HashMap<String, King>();
                        for (int i = 0; i < battles.size(); i++) {
                            Battle battle = battles.get(i);
                            int rating1 = 400;
                            int rating2 = 400;
                            if (map.containsKey(battle.getAttackerKing()))
                                rating1 = map.get(battle.getAttackerKing()).getRating();
                            if (map.containsKey(battle.getDefenderKing()))
                                rating2 = map.get(battle.getDefenderKing()).getRating();
                            BattlesResult battlesResult = ratingCalculator(rating1, rating2, battle.getAttackerOutcome());

                            King king1 = null;
                            King king2 = null;

                            if (map.containsKey(battle.getAttackerKing())) {
                                king1 = map.get(battle.getAttackerKing());
                            }
                            king1 = setKingData(king1, battle.getAttackerKing(), battle,
                                    (int) battlesResult.rating1, battle.getAttackerOutcome());
                            if (king1.getName().length() != 0)
                                map.put(king1.getName(), king1);

                            String result = "";
                            switch (battle.getAttackerOutcome()) {
                                case "win":
                                    result = LOSS;
                                    break;

                                case "draw":
                                    result = DRAW;

                                case "loss":
                                    result = WIN;
                                    break;
                            }

                            if (map.containsKey(battle.getDefenderKing())) {
                                king2 = map.get(battle.getDefenderKing());
                            }
                            king2 = setKingData(king2, battle.getDefenderKing(), battle,
                                    (int) battlesResult.rating2, result);
                            if (king2.getName().length() != 0)
                                map.put(king2.getName(), king2);
                        }
                        return new ArrayList<>(map.values());
                    }
                })
                .flatMap(new Func1<List<King>, Observable<List<King>>>() {
                    @Override
                    public Observable<List<King>> call(List<King> kings) {
                        return localRepository.addKingsData(kings);
                    }
                })
                .flatMap(new Func1<List<King>, Observable<List<King>>>() {
                    @Override
                    public Observable<List<King>> call(List<King> kings) {
                        return Observable.from(kings)
                                .doOnNext(new Action1<King>() {
                                    @Override
                                    public void call(King king) {
                                        mCachedKings.put(king.getId(), king);
                                    }
                                }).toList();
                    }
                });
    }

    @Override
    public Observable<List<King>> getAllKings() {
        return null;
    }

    @Override
    public Observable<King> getKing(int kingId) {
        return localRepository.getKing(kingId)
                .flatMap(new Func1<King, Observable<King>>() {
                    @Override
                    public Observable<King> call(King king) {
                        return Observable.just(king);
                    }
                });
    }

    @Override
    public Observable<List<King>> getKingByRating() {
        return null;
    }

    @Override
    public Observable<List<King>> getKingByStrength() {
        return localRepository.getKingByStrength();
    }

    private BattlesResult ratingCalculator(int rating1, int rating2, String result) {
        int K = 32;
        double power1 = rating1 / 400;
        double power2 = rating2 / 400;
        double R1 = Math.pow(10, power1);
        double R2 = Math.pow(10, power2);
        double E1 = R1 / (R1 + R2);
        double E2 = R2 / (R1 + R2);
        double S1 = 0.0, S2 = 0.0;
        switch (result) {
            case "win":
                S1 = 1;
                S2 = 0;
                break;

            case "draw":
                S1 = 0.5;
                S2 = 0.5;
                break;

            case "loss":
                S1 = 0;
                S2 = 1;
                break;
        }
        double _rating1 = rating1 + K * (S1 - E1);
        double _rating2 = rating2 + K * (S2 - E2);
        BattlesResult battlesResult = new BattlesResult();
        battlesResult.rating1 = _rating1;
        battlesResult.rating2 = _rating2;
        return battlesResult;
    }

    private King setKingData(King king, String name, Battle battle, int rating, String result) {
        if (king != null) {
            king.setRating(rating);
            switch (result) {
                case "win":
                    king.setBattlesWon(king.getBattlesWon() + 1);
                    king.battleStrengthMap.put(battle.getBattleType(),
                            king.battleStrengthMap.get(battle.getBattleType()) + 1);
                    if (battle.getAttackerKing().equals(name))
                        king.strengthMap.put(ATTACK, king.strengthMap.get(ATTACK) + 1);
                    else if (battle.getDefenderKing().equals(name))
                        king.strengthMap.put(DEFENSE, king.strengthMap.get(DEFENSE) + 1);
                    break;

                case "loss":
                    king.setBattlesLost(king.getBattlesLost() + 1);
                    break;
            }
            if (king.strengthMap.get(ATTACK) >= king.strengthMap.get(DEFENSE))
                king.setStrength(ATTACK);
            else
                king.setStrength(DEFENSE);
            king.setStrengthBattleType(findMaxBattleStrengthType(king.battleStrengthMap));
        } else {
            king = new King();
            king.setName(name);
            king.setRating(rating);

            king.strengthMap.put(ATTACK, 0);
            king.strengthMap.put(DEFENSE, 0);

            king.battleStrengthMap.put(AMBUSH, 0);
            king.battleStrengthMap.put(PITCHED, 0);
            king.battleStrengthMap.put(RAZING, 0);
            king.battleStrengthMap.put(SIEGE, 0);

            switch (result) {
                case "win":
                    king.setBattlesWon(1);
                    king.battleStrengthMap.put(battle.getBattleType(),
                            king.battleStrengthMap.get(battle.getBattleType()) + 1);
                    if (battle.getAttackerKing().equals(name))
                        king.strengthMap.put(ATTACK, king.strengthMap.get(ATTACK) + 1);
                    else if (battle.getDefenderKing().equals(name))
                        king.strengthMap.put(DEFENSE, king.strengthMap.get(DEFENSE) + 1);
                    break;

                case "loss":
                    king.setBattlesLost(1);
                    break;
            }
            if (king.strengthMap.get(ATTACK) >= king.strengthMap.get(DEFENSE))
                king.setStrength(ATTACK);
            else
                king.setStrength(DEFENSE);
            king.setStrengthBattleType(findMaxBattleStrengthType(king.battleStrengthMap));
        }
        return king;
    }

    private String findMaxBattleStrengthType(Map<String, Integer> battleStrengthType) {
        Set<String> set = battleStrengthType.keySet();
        Iterator<String> iterator = set.iterator();
        String result = "";
        int max = Integer.MIN_VALUE;
        while (iterator.hasNext()) {
            String type = iterator.next();
            if (battleStrengthType.get(type) > max) {
                result = type;
                max = battleStrengthType.get(type);
            }
        }
        return result;
    }

    public class BattlesResult {
        double rating1;
        double rating2;
    }
}
