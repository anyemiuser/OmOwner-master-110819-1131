package org.sairaa.omowner.RoomUtility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;

import java.util.ArrayList;
import java.util.List;

public class UtilityAdapter extends RecyclerView.Adapter<UtilityMainHolder> {
    private List<UtilitiyDetails> utilitiyDetails;
    private Context context;
    public UtilityAdapter(List<UtilitiyDetails> utilitiyDetails, Context context) {
        this.utilitiyDetails = utilitiyDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public UtilityMainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_main, viewGroup, false);
        return new UtilityMainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilityMainHolder holder, int position) {
        UtilitiyDetails utilList = utilitiyDetails.get(position);
        holder.set(utilList);
    }

    @Override
    public int getItemCount() {
        return utilitiyDetails.size();
    }

    public void updateList(List<UtilitiyDetails> utilitiyDetails) {
        this.utilitiyDetails = utilitiyDetails;
        notifyDataSetChanged();
    }
}
