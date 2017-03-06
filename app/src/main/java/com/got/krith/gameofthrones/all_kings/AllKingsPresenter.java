package com.got.krith.gameofthrones.all_kings;

import com.got.krith.gameofthrones.model.King;
import com.got.krith.gameofthrones.repository.BattleRepository;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by krith on 05/03/17.
 */

public class AllKingsPresenter implements AllKingsContract.Presenter {

    private final AllKingsContract.View mView;
    private final BattleRepository mBattleRespository;
    private static Subscription mSubscription;
    private final boolean SHOW_LOADER = Boolean.TRUE;

    public AllKingsPresenter(AllKingsContract.View mView, BattleRepository mBattleRespository) {

        this.mView = mView;
        this.mBattleRespository = mBattleRespository;
        mView.setupPresenter(this);
    }

    @Override
    public void subscribe() {
        fetchBattlesData();
    }

    @Override
    public void unSubscribe() {
        if (!(mSubscription == null || mSubscription.isUnsubscribed()))
            mSubscription.unsubscribe();
    }

    @Override
    public void fetchKingsByStrength() {
        mSubscription = mBattleRespository.getKingByStrength()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<King>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.showNetworkError();
                    }

                    @Override
                    public void onNext(List<King> kings) {
                        if (kings == null || kings.size() < 1) {
                            mView.showNoDataAvailable();
                            return;
                        }
                        mView.showLoader(!SHOW_LOADER);
                        mView.showKingsList(kings, 0, true);
                        unsubscribe();
                        return;
                    }
                });
    }

    @Override
    public void fetchBattlesData() {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mBattleRespository.getAllBattles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<King>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.showNetworkError();
                    }

                    @Override
                    public void onNext(List<King> kings) {
                        if (kings == null || kings.size() < 1) {
                            mView.showNoDataAvailable();
                            return;
                        }
                        mView.showLoader(!SHOW_LOADER);
                        mView.showKingsList(kings, 0, true);
                        unsubscribe();
                        return;
                    }
                });
    }
}
