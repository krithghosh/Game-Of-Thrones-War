package com.got.krith.gameofthrones.all_kings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.got.krith.gameofthrones.adapter.KingsListAdapter;
import com.got.krith.gameofthrones.R;
import com.got.krith.gameofthrones.application.App;
import com.got.krith.gameofthrones.king_details.KingDetails;
import com.got.krith.gameofthrones.model.King;
import com.got.krith.gameofthrones.repository.BattleRepository;
import com.got.krith.gameofthrones.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class AllKingsActivity extends Activity implements AllKingsContract.View, View.OnClickListener {

    @BindView(R.id.rl_main_layout)
    RelativeLayout mainLayout;

    @BindView(R.id.iv_filter)
    ImageView filter;

    @BindView(R.id.et_search)
    EditText search;

    @BindView(R.id.rv_all_kings)
    RecyclerView recyclerView;

    @BindView(R.id.iv_back)
    ImageView back;

    @BindView(R.id.iv_next)
    ImageView next;

    @BindView(R.id.rl_footer)
    RelativeLayout footer;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AllKingsContract.Presenter mPresenter;

    private KingsListAdapter mKingsListAdapter;

    private List<King> kings = null;

    private final String ID = "id";

    @Inject
    BattleRepository mBattleRepository;

    static int positionShow = 0;
    static int currentLastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_kings);
        ButterKnife.bind(this);
        setupComponents();
        setupTaskAdapter();
        setupRecyclerView();
        setupUIComponents();
    }

    private void setupUIComponents() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        filter.setOnClickListener(this);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Text Size", "" + s.length());
                if (s.length() != 0) {
                    String str = s.toString().toLowerCase();
                    List<King> tempList = new ArrayList<King>();
                    for (int i = 0; i < kings.size(); i++)
                        if (kings.get(i).getName().toLowerCase().startsWith(str))
                            tempList.add(kings.get(i));
                    showKingsListFromSearch(tempList);
                } else
                    showKingsList(kings, 0, false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mKingsListAdapter);
    }

    private void setupTaskAdapter() {
        mKingsListAdapter = new KingsListAdapter(new ArrayList<King>(), this);
        mKingsListAdapter.SetOnItemClickListener(new KingsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id) {
                startActivity(new Intent(getApplicationContext(), KingDetails.class)
                        .putExtra(ID, id));
            }
        });
    }

    @Override
    public void setupPresenter(AllKingsContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showLoader(boolean showLoader) {
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.GONE);
    }

    private void showKingsListFromSearch(List<King> kings) {
        setVisibility(footer, false);
        mKingsListAdapter.updateContactList(kings);
    }

    @Override
    public void showKingsList(List<King> kings, int positionShow, boolean setListData) {
        if (setListData)
            this.kings = kings;
        if (positionShow < 0)
            positionShow = 0;
        this.positionShow = positionShow;
        List<King> paginatedKingsData = new ArrayList<>();
        if (kings.size() - 1 > positionShow) {
            currentLastPosition = positionShow + 4;
            if (currentLastPosition > kings.size()) {
                currentLastPosition = kings.size();
                next.setVisibility(View.GONE);
            }
            for (int i = positionShow; i < currentLastPosition; i++) {
                paginatedKingsData.add(kings.get(i));
            }
        }
        if (positionShow == 0)
            back.setVisibility(View.GONE);
        mKingsListAdapter.updateContactList(paginatedKingsData);

        setVisibility(filter, true);
        setVisibility(search, true);
        setVisibility(recyclerView, true);
        setVisibility(footer, true);
    }

    @Override
    public void setupComponents() {
        App.getComponent().inject(this);
        new AllKingsPresenter(this, mBattleRepository);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.data_not_available_from_server));
    }

    @Override
    public void showNoDataAvailable() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.data_not_available));
    }

    @Override
    public void setVisibility(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                next.setVisibility(View.VISIBLE);
                showKingsList(kings, positionShow - 4, false);
                break;

            case R.id.iv_next:
                back.setVisibility(View.VISIBLE);
                showKingsList(kings, positionShow + 4, false);
                break;

            case R.id.iv_filter:
                mPresenter.fetchKingsByStrength();
                break;
        }
    }
}
