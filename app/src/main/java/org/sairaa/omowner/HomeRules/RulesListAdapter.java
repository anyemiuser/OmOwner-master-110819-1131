package org.sairaa.omowner.HomeRules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sairaa.omowner.R;

import java.util.List;

public class RulesListAdapter extends RecyclerView.Adapter<RulesListAdapter.ViewHolder>{

    private List<RulesListItem> list;
    private Context context;

    public RulesListAdapter(List<RulesListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rules_list_view,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        RulesListItem listItem = list.get(i);

        viewHolder.rule1.setText(listItem.getRule1());
        viewHolder.rule2.setText(listItem.getRule2());
        viewHolder.rule3.setText(listItem.getRule3());
        viewHolder.rule4.setText(listItem.getRule4());
        viewHolder.rule5.setText(listItem.getRule5());
        viewHolder.rule6.setText(listItem.getRule6());
      /*  Picasso.with(context)
                .load(listItem.imageUrl)
                .into(viewHolder.image);*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView rule1,rule2,rule3,rule4,rule5,rule6;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rule1 = itemView.findViewById(R.id.rule1);
            rule2 = itemView.findViewById(R.id.rule2);
            rule3 = itemView.findViewById(R.id.rule3);
            rule4 = itemView.findViewById(R.id.rule4);
            rule5 = itemView.findViewById(R.id.rule5);
            rule6 = itemView.findViewById(R.id.rule6);
          /*  image = itemView.findViewById(R.id.image);*/

        }
    }
}
