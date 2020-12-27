package com.example.bustest8;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Groupadp extends RecyclerView.Adapter<Groupadp.ViewHolder> {
    private Activity activity;
    ArrayList<String> arrayListGroup;
    //Create constructor
    Groupadp(Activity activity,ArrayList<String> arrayListGroup){
        this.activity = activity;
        this.arrayListGroup = arrayListGroup;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_group,parent,false);
        return new Groupadp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set group name on Textview
        GlobalVariable globalVariable = (GlobalVariable) activity.getApplicationContext();
        String p[][] = globalVariable.getTF();

        holder.tvname.setText(arrayListGroup.get(position));
        ArrayList<String> arrayListMember = new ArrayList<>();

        for (int h=0;h<p.length;h++){
            if (p[h].length!=0){
                String w = p[h][0];
                arrayListMember.add(w);//+h 自動組成字串
            }
        }
        MemberAdp adapterMember = new MemberAdp(arrayListMember);
        LinearLayoutManager layoutManagerMember = new LinearLayoutManager(activity);
        holder.rvmember.setLayoutManager(layoutManagerMember);
        holder.rvmember.setAdapter(adapterMember);

    }

    @Override
    public int getItemCount() {
        return arrayListGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        RecyclerView rvmember;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tv_name);
            rvmember = itemView.findViewById(R.id.rv_member);
        }
    }
}
