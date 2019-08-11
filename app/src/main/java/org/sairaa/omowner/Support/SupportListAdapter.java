package org.sairaa.omowner.Support;

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

public class SupportListAdapter extends RecyclerView.Adapter<SupportListAdapter.ViewHolder>{

    private List<SupportListItem> list;
    private Context context;

    public SupportListAdapter(List<SupportListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.support_list_view,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        SupportListItem listItem = list.get(i);

        viewHolder.helpdesk_number1.setText(listItem.getHelpdesk_number1());
        viewHolder.helpdesk_number2.setText(listItem.getHelpdesk_number2());
        viewHolder.helpdesk_email.setText(listItem.getHelpdesk_number3());
        viewHolder.reconciliation.setText(listItem.getReconciliation());
        viewHolder.TechSupport.setText(listItem.getTechSupport());
      /*  Picasso.with(context)
                .load(listItem.imageUrl)
                .into(viewHolder.image);*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView helpdesk_number1,helpdesk_number2,helpdesk_email,reconciliation,TechSupport;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            helpdesk_number1 = itemView.findViewById(R.id.helpdesk_number1);
            helpdesk_number2 = itemView.findViewById(R.id.helpdesk_number2);
            helpdesk_email = itemView.findViewById(R.id.helpdesk_email);
            reconciliation = itemView.findViewById(R.id.reconciliation);
            TechSupport = itemView.findViewById(R.id.TechSupport);
          /*  image = itemView.findViewById(R.id.image);*/

        }
    }
}
