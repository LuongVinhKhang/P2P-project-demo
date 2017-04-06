package com.light.p2pdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong Kang on 04-Apr-17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();

    public ChatAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_chat, parent, false);

        return new ChatViewHolder(itemView);
    }

    public void add(String data) {
        list.add(data);
        notifyItemInserted(list.size() - 1);

    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ChatViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.text_item_chat);
        }
    }
}
