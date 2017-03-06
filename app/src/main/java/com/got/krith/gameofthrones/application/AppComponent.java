package com.got.krith.gameofthrones.application;

import com.got.krith.gameofthrones.all_kings.AllKingsActivity;
import com.got.krith.gameofthrones.king_details.KingDetails;
import com.got.krith.gameofthrones.network.NetModule;
import com.got.krith.gameofthrones.repository.BattleLocalRepository;
import com.got.krith.gameofthrones.repository.BattleRemoteRepository;
import com.got.krith.gameofthrones.repository.BattleRepository;
import com.got.krith.gameofthrones.repository.BattleRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by krith on 05/03/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class, BattleRepositoryModule.class})
public interface AppComponent {

    void inject(BattleRepository battleRepository);

    void inject(BattleLocalRepository battleLocalRepository);

    void inject(BattleRemoteRepository battleRemoteRepository);

    void inject(AllKingsActivity allKingsActivity);

    void inject(KingDetails kingDetails);
}
