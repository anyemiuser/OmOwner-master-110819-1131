package org.sairaa.omowner.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.sairaa.omowner.Model.TotalAvailableRoom;
import org.sairaa.omowner.R;

class AvailableRoomHolder extends RecyclerView.ViewHolder {

    private TextView roomType, percentage;
    private ProgressBar progressBar;

    public AvailableRoomHolder(@NonNull View itemView) {
        super(itemView);

        roomType = itemView.findViewById(R.id.room_type_ava);
        percentage = itemView.findViewById(R.id.percentage_ava);
        progressBar = itemView.findViewById(R.id.progress_bar_ava);
    }

    public void set(TotalAvailableRoom room) {
        roomType.setText(room.getRoom_type());
        progressBar.setMax(Integer.parseInt(room.getNo_of_rooms()));
        progressBar.setProgress(50);
    }
}
