package com.got.krith.gameofthrones.king_details;

import com.got.krith.gameofthrones.model.King;
import com.got.krith.gameofthrones.repository.BattleRepository;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by krith on 06/03/17.
 */

public class KingDetailsPresenter implements KingDetailsContract.Presenter {

    private final KingDetailsContract.View mView;
    private final BattleRepository mBattleRespository;
    private static Subscription mSubscription;
    private final boolean SHOW_LOADER = Boolean.TRUE;

    public KingDetailsPresenter(KingDetailsContract.View mView, BattleRepository mBattleRespository) {
        this.mView = mView;
        this.mBattleRespository = mBattleRespository;
        mView.setUpPresenter(this);
    }

    @Override
    public void subscribe(int id) {
        getKingDetails(id);
    }

    @Override
    public void unSubscribe() {
        if (!(mSubscription == null || mSubscription.isUnsubscribed()))
            mSubscription.unsubscribe();
    }

    @Override
    public void getKingDetails(int id) {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mBattleRespository.getKing(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<King>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.showNetworkError();
                    }

                    @Override
                    public void onNext(King king) {
                        if (king == null) {
                            mView.showNoDataAvailable();
                            return;
                        }
                        mView.showLoader(!SHOW_LOADER);
                        mView.showKingDetails(king);
                        unSubscribe();
                        return;
                    }
                });
    }
}
