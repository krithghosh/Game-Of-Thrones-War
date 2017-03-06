package com.got.krith.gameofthrones.king_details;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.got.krith.gameofthrones.R;
import com.got.krith.gameofthrones.adapter.KingsListAdapter;
import com.got.krith.gameofthrones.all_kings.AllKingsActivity;
import com.got.krith.gameofthrones.all_kings.AllKingsContract;
import com.got.krith.gameofthrones.all_kings.AllKingsPresenter;
import com.got.krith.gameofthrones.application.App;
import com.got.krith.gameofthrones.model.King;
import com.got.krith.gameofthrones.repository.BattleRepository;
import com.got.krith.gameofthrones.utils.SnackbarUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KingDetails extends Activity implements KingDetailsContract.View {

    @BindView(R.id.rl_main_layout)
    RelativeLayout mainLayout;

    @BindView(R.id.tv_king_name)
    TextView name;

    @BindView(R.id.tv_initial)
    TextView initial;

    @BindView(R.id.tv_rating)
    TextView rating;

    @BindView(R.id.tv_battles_won)
    TextView battlesWon;

    @BindView(R.id.tv_battles_lost)
    TextView battlesLost;

    @BindView(R.id.tv_strength)
    TextView strength;

    @BindView(R.id.tv_strength_battle_type)
    TextView battleTypeStrength;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private KingDetailsContract.Presenter mPresenter;

    private King king = null;

    private int id;

    private final String ID = "id";

    @Inject
    BattleRepository mBattleRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_king_details);
        ButterKnife.bind(this);
        setupComponents();
        id = getIntent().getIntExtra(ID, 0);
        mPresenter.subscribe(id);
    }

    @Override
    public void setUpPresenter(KingDetailsContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showLoader(boolean showLoader) {
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.data_not_available_from_server));
    }

    @Override
    public void setupComponents() {
        App.getComponent().inject(this);
        new KingDetailsPresenter(this, mBattleRepository);
    }

    @Override
    public void showNoDataAvailable() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.data_not_available));
    }

    @Override
    public void showKingDetails(King king) {
        name.setText(king.getName());
        initial.setText(king.getName().substring(0, 1).toUpperCase());
        rating.setText(String.format(this
                .getResources().getString(R.string.highest_rating), king.getRating()));
        battlesWon.setText(Integer.toString(king.getBattlesWon()));
        battlesLost.setText(Integer.toString(king.getBattlesLost()));
        String _strength = king.getStrength().substring(0, 1).toUpperCase()
                .concat(king.getStrength().substring(1));
        strength.setText(_strength);
        String _battleTypeStrength = king.getStrengthBattleType().substring(0, 1).toUpperCase()
                .concat(king.getStrengthBattleType().substring(1));
        battleTypeStrength.setText(_battleTypeStrength);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AllKingsActivity.class));
    }
}
