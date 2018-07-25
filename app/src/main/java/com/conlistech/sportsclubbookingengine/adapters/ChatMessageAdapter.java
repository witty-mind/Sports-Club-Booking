package com.conlistech.sportsclubbookingengine.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conlistech.sportsclubbookingengine.R;
import com.conlistech.sportsclubbookingengine.models.BaseMessage;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private ArrayList<BaseMessage> mArrayList;
    Context context;
    DatabaseReference mDatabase;
    private RecentChatListAdapter.ItemClickListener mClickListener;


    public ChatMessageAdapter(Context ctx, ArrayList<BaseMessage> userModels) {
        this.mArrayList = userModels;
        this.context = ctx;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_message, parent, false);

        return new ViewHolder(view);
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {

        if (mArrayList.get(position).getUser().equalsIgnoreCase("sender")) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                viewHolder.mLytSend.setVisibility(View.VISIBLE);
                viewHolder.mLytReceive.setVisibility(View.GONE);
                viewHolder.tvMessageSend.setText(mArrayList.get(position).getmArrMessageList().get(position).getMessage());
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                viewHolder.mLytReceive.setVisibility(View.VISIBLE);
                viewHolder.mLytSend.setVisibility(View.GONE);
                viewHolder.tvMessageReceive.setText(mArrayList.get(position).getmArrMessageList().get(position).getMessage());
                break;
        }
    }

    private int getCategoryPos(String category) {
        return mArrayList.indexOf(category);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvFullname, tvMessageSend, tvMessageReceive, tvUnreadMsgs;
        private ConstraintLayout mLytSend, mLytReceive;
        private View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvMessageSend = (TextView) view.findViewById(R.id.text_message_send);
            tvMessageReceive = (TextView) view.findViewById(R.id.text_message_receive);
            mLytSend = view.findViewById(R.id.lyt_item_send);
            mLytReceive = view.findViewById(R.id.lyt_item_receive);
            //  cbSelection = (CheckBox) view.findViewById(R.id.cbSelectTimeSlot);
            view.setOnClickListener(this);
            view.setTag(view);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(RecentChatListAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

