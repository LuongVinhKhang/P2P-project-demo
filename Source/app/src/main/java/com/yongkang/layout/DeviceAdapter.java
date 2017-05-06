package com.yongkang.layout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong Kang on 07-May-17.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    List<DeviceModel> list = new ArrayList<>();
    Context context;

    public DeviceAdapter(Context context, List<DeviceModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_rcv_device, parent, false);

        return new DeviceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder holder, int position) {
        if (list.get(position).getConnected()) {
            holder.image.setImageResource(R.drawable.connect);
        } else {
            holder.image.setImageResource(R.drawable.disconnect);
        }

        holder.text.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView text;

        public DeviceViewHolder(final View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_rcv_device_image);
            text = (TextView) itemView.findViewById(R.id.item_rcv_device_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (list.get(getAdapterPosition()).getConnected()) {
                        Intent intent = new Intent(context, FileMainActivity.class);
                        context.startActivity(intent);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setTitle("Connect")
                                .setMessage("Connect to " + list.get(getAdapterPosition()).getName())
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Boolean isOK = true;
                                        // thêm code kết nối ở đây
                                        // isOK : kết nối thành công

                                        if (isOK) {
                                            list.get(getAdapterPosition()).setConnected(true);
                                            notifyItemChanged(getAdapterPosition());
                                        } else {
                                            Toast.makeText(context, "Cannot connect!!!", Toast.LENGTH_LONG).show();
                                        }


                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create();
                        alertDialog.show();
                    }
                }
            });
        }
    }

}
