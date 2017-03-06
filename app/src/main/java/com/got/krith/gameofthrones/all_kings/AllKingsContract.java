package com.got.krith.gameofthrones.all_kings;

import android.view.View;

import com.got.krith.gameofthrones.model.King;

import java.util.List;

/**
 * Created by krith on 05/03/17.
 */

public class AllKingsContract {
    interface View {
        void setupPresenter(Presenter presenter);

        void showLoader(boolean showLoader);

        void showKingsList(List<King> kings, int positionShow, boolean setListData);

        void setupComponents();

        void showNetworkError();

        void showNoDataAvailable();

        void setVisibility(android.view.View view, boolean visibility);
    }

    interface Presenter {
        void subscribe();

        void unSubscribe();

        void fetchBattlesData();

        void fetchKingsByStrength();
    }
}
