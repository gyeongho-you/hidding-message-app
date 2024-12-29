package com.example.chatapp2023;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Chat> mDataset;

    User user;
    private long lastClickTime = 0;

    public ArrayList<Integer> see_position = new ArrayList<Integer>();
    String stMyEmail = "";

    int position;
    private RecyclerView mRecyclerView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView time_textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tvChat);
            time_textView = v.findViewById(R.id.time_tvChat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastClickTime < 1000) { // 1초 동안 중복 클릭 무시
                        return true;
                    }
                    lastClickTime = currentTime;

                    int position = getAdapterPosition();
                    String Chat_Email = mDataset.get(position).getEmail();
                    String Check_Hide = mDataset.get(position).getCheck_hide();
                    String Chat_id = mDataset.get(position).getChat_id();

                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, ChatSettingActivity.class);

                    intent.putExtra("position", position); // 상태 변경을 원하는 채팅의 위치를 전송
                    intent.putExtra("Chat_Email", Chat_Email);
                    intent.putExtra("My_Email", stMyEmail);
                    intent.putExtra("Check_Hide", Check_Hide);
                    intent.putExtra("Chat_id", Chat_id);
                    ((Activity) context).startActivityForResult(intent, 2);

                    return true;
                }
            });
        }
    }



    @Override   // chatbubble 상대방이 보낸 것과 내가 보낸 것 구분
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        if (mDataset.get(position) != null && "no".equals(mDataset.get(position).check_hide)) {
            if (mDataset.get(position).email.equals(stMyEmail)) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if(see_position.contains(mDataset.get(position))) {
                if (mDataset.get(position).email.equals(stMyEmail))
                    return 1;
                else
                    return 2;
            }else{
                if (mDataset.get(position).email.equals(stMyEmail))
                    return 3;
                else
                    return 4;
            }
        }
    }

    public MyAdapter(ArrayList<Chat> myDataset, String stEmail, User user) {
        mDataset = myDataset;
        this.stMyEmail = stEmail;
        this.user = user;
    }

    @Override // dataset 구성
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        if (viewType == 2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_text_view, parent, false);
        } else if (viewType == 3) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_space_text, parent, false);
        } else if (viewType == 4) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_space_text, parent, false);
        }

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mDataset.get(position) != null && "no".equals(mDataset.get(position).check_hide)) {
            holder.textView.setText(mDataset.get(position).getText());
            holder.time_textView.setText((mDataset.get(position).getChat_time()));
        } else{
            holder.textView.setText("숨겨진 메세지입니다.");
            holder.time_textView.setText((mDataset.get(position).getChat_time()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
