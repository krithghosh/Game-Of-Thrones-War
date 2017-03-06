package com.got.krith.gameofthrones.king_details;

import com.got.krith.gameofthrones.model.King;

/**
 * Created by krith on 06/03/17.
 */

public interface KingDetailsContract {
    interface View {
        void setUpPresenter(KingDetailsContract.Presenter presenter);

        void showLoader(boolean showLoader);

        void showNetworkError();

        void setupComponents();

        void showNoDataAvailable();

        void showKingDetails(King king);
    }

    interface Presenter {
        void subscribe(int id);

        void unSubscribe();

        void getKingDetails(int id);
    }
}
