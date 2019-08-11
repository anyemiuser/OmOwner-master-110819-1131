package org.sairaa.omowner.HotelContact;

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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private List<ContactListItem> list;
    private Context context;

    public ContactAdapter(List<ContactListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_view,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ContactListItem listItem = list.get(i);

        viewHolder.manager_number.setText(listItem.getManager_number());
        viewHolder.manager_email.setText(listItem.getManager_email());
        viewHolder.reception_number.setText(listItem.getReception_number());
        viewHolder.reception_email.setText(listItem.getReception_email());
      /*  viewHolder.desc3.setText(listItem.getDescription3());
        Picasso.with(context)
                .load(listItem.imageUrl)
                .into(viewHolder.image);*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      //  public TextView head,desc,desc1,desc2,desc3;
      public TextView manager_number,manager_email,reception_number,reception_email;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            manager_number = itemView.findViewById(R.id.manager_number);
            manager_email = itemView.findViewById(R.id.manager_email);
            reception_number = itemView.findViewById(R.id.reception_number);
            reception_email = itemView.findViewById(R.id.reception_email);
          /*  desc3 = itemView.findViewById(R.id.textviewHeading3);
            image = itemView.findViewById(R.id.image);*/

        }
    }
}
