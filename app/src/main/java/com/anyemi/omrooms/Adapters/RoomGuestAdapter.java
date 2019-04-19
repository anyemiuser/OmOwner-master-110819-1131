package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.R;

import java.util.ArrayList;
import java.util.List;

public class RoomGuestAdapter extends RecyclerView.Adapter<RoomGuestAdapter.RoomsGuestHolder> {

    private List<RoomsGuest> roomsGuests;
    private Context context;

    public RoomGuestAdapter(List<RoomsGuest> roomsGuests, Context context) {
        this.roomsGuests = roomsGuests;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomsGuestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_guest, viewGroup, false);
        return new RoomsGuestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsGuestHolder holder, final int position) {
        RoomsGuest roomsGuest = roomsGuests.get(position);

        holder.roomText.setText(context.getString(R.string.room_no_text).concat(String.valueOf(position+1)));
        holder.guestText.setText(String.valueOf(roomsGuest.getGuests()).concat(" Adults ")
                .concat(String.valueOf(roomsGuest.getChildren())).concat(" Children"));
        Log.e("childeren:",""+roomsGuest.getChildren());
        if(roomsGuest.getChildren()>0){
            holder.childrenCheck.setChecked(true);
            holder.childrenL.setVisibility(View.VISIBLE);
            holder.spinner.setSelection(roomsGuest.getChildren()-1);

        }
        if(position == 0){
            holder.deleteRoom.setVisibility(View.GONE);
        }else
            holder.deleteRoom.setVisibility(View.VISIBLE);
        holder.addDeleteLayout.setVisibility(View.VISIBLE);
        holder.addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.addDeleteLayout.setVisibility(View.GONE);

                RoomsGuest roomsGuest1 = new RoomsGuest(1,2,0);
                roomsGuests.add(roomsGuest1);
                notifyDataSetChanged();
            }
        });

        holder.deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomsGuests.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        switch (roomsGuest.getGuests()){
            case 1:
                holder.one.setChecked(true);
                break;
            case 2:
                holder.two.setChecked(true);
                break;
            case 3:
                holder.three.setChecked(true);
                break;
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.oneRadioButton:
//                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        roomsGuest.setGuests(1);

                        break;
                    case R.id.twoRadioButton:
//                        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                        roomsGuest.setGuests(2);
                        break;
                    case R.id.threeRadioButton:
//                        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
                        roomsGuest.setGuests(3);
                        break;
                }

                notifyDataSetChanged();
//                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
            }
        });

        holder.childrenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.childrenL.setVisibility(View.VISIBLE);
                    holder.spinner.setSelection(0);
                }else {
                    holder.childrenL.setVisibility(View.GONE);
                    roomsGuest.setChildren(0);
                    notifyDataSetChanged();

                }
//                notifyDataSetChanged();
            }
        });

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                roomsGuest.setChildren(Integer.parseInt(parent.getSelectedItem().toString()));
                notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return roomsGuests.size();
    }


    public class RoomsGuestHolder extends RecyclerView.ViewHolder{

        private TextView roomText;
        private TextView guestText;
        private RadioGroup radioGroup;
        private RadioButton one,two,three;
        private Button addRoom, deleteRoom;
        private CheckBox childrenCheck;
        private LinearLayout addDeleteLayout, childrenL;
        private Spinner spinner;
        public RoomsGuestHolder(@NonNull View itemView) {
            super(itemView);
            roomText = itemView.findViewById(R.id.room_count);
            guestText = itemView.findViewById(R.id.guest_count);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            one = itemView.findViewById(R.id.oneRadioButton);
            two = itemView.findViewById(R.id.twoRadioButton);
            three = itemView.findViewById(R.id.threeRadioButton);
            addRoom = itemView.findViewById(R.id.add_room);
            deleteRoom = itemView.findViewById(R.id.delete_room);
            childrenCheck = itemView.findViewById(R.id.check_children);
            addDeleteLayout = itemView.findViewById(R.id.add_delete_layout);
            childrenL = itemView.findViewById(R.id.children_layout);
            spinner = itemView.findViewById(R.id.spinner_child);
        }
    }
}
