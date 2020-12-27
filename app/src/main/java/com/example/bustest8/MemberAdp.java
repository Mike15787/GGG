package com.example.bustest8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdp extends RecyclerView.Adapter<MemberAdp.ViewHolder> {
    ArrayList<String> arrayListMember;
    public MemberAdp(ArrayList<String> arrayListMember) {
        this.arrayListMember = arrayListMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_member,parent,false);
        return new MemberAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvname.setText(arrayListMember.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tv_name);
        }
    }
}
