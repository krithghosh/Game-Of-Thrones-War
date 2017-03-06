package com.got.krith.gameofthrones.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.got.krith.gameofthrones.R;
import com.got.krith.gameofthrones.all_kings.AllKingsActivity;
import com.got.krith.gameofthrones.model.King;

import java.util.List;

/**
 * Created by krith on 05/03/17.
 */

public class KingsListAdapter extends RecyclerView.Adapter<KingsListAdapter.KingsListViewHolder> {
    private List<King> kings;
    private OnItemClickListener mItemClickListener;
    private final AllKingsActivity allKingsActivity;

    public KingsListAdapter(List<King> kings, AllKingsActivity allKingsActivity) {
        updateContactList(kings);
        this.allKingsActivity = allKingsActivity;
    }

    @Override
    public KingsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kings_row, parent, false);
        return new KingsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KingsListViewHolder holder, int position) {
        King king = this.kings.get(position);
        holder.tvIinitial.setText(king.getName().substring(0, 1).toUpperCase());
        holder.tvKingName.setText(king.getName().toUpperCase());
        holder.tvRating.setText(String.format(allKingsActivity
                .getResources().getString(R.string.highest_rating), king.getRating()));
        String strength = king.getStrength().substring(0, 1).toUpperCase()
                .concat(king.getStrength().substring(1));
        if (king.getStrength() != null)
            holder.tvBattleStrength.setText(String.format(allKingsActivity.getResources()
                    .getString(R.string.battle_strength), strength));
    }

    @Override
    public int getItemCount() {
        if (kings == null)
            return 0;
        return kings.size();
    }

    public void updateContactList(List<King> kings) {
        this.kings = kings;
        notifyDataSetChanged();
    }

    public class KingsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvIinitial;
        public final TextView tvKingName;
        public final TextView tvRating;
        public final TextView tvBattleStrength;

        public KingsListViewHolder(View view) {
            super(view);
            tvIinitial = (TextView) view.findViewById(R.id.tv_initial);
            tvKingName = (TextView) view.findViewById(R.id.tv_king_name);
            tvRating = (TextView) view.findViewById(R.id.tv_rating);
            tvBattleStrength = (TextView) view.findViewById(R.id.tv_battle_strength);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition(), kings.get(getAdapterPosition()).getId());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
