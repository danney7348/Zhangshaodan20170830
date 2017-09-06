package com.bwie.text.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.bwie.text.R;
import com.bwie.text.bean.Catogray;

import java.util.List;
public class MyLixianAdapter extends RecyclerView.Adapter<MyLixianAdapter.MyViewHolder> {
    private List<Catogray> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MyLixianAdapter(Context context, List<Catogray> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.catogry_item, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.nametv.setText(list.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(position, holder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nametv;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, View view);
    }

}
