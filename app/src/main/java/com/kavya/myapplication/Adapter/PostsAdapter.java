package com.kavya.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kavya.myapplication.ItemClickListener.PostsItemClicklistener;
import com.kavya.myapplication.Model.PostsModel;
import com.kavya.myapplication.R;
import com.kavya.myapplication.Utils.AppUtils;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyViewHolder> {
    private List<PostsModel> postsModelList;
    private PostsItemClicklistener postsItemClicklistener;
    private int selected_count=0;
    public PostsAdapter(List<PostsModel> postsModelList, PostsItemClicklistener postsItemClicklistener) {
        this.postsModelList = postsModelList;
        this.postsItemClicklistener= postsItemClicklistener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(postsModelList.get(position).getTitle());
        holder.created.setText(AppUtils.getFormattedDate(postsModelList.get(position).getCreated_at()));
        holder.switch_selected.setChecked(postsModelList.get(position).isSwitchChecked());
        holder.switch_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selected_count++;
                    postsModelList.get(position).setSwitchChecked(true);
                }else{
                    selected_count--;
                    postsModelList.get(position).setSwitchChecked(false);
                }
                postsItemClicklistener.onItemClick(position,selected_count);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.switch_selected.performClick();
            }
        });

    }
@Override
public int getItemViewType(int position){
        return position;
}
    @Override
    public int getItemCount() {
        return postsModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,created;
        Switch switch_selected;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_name);
            created=itemView.findViewById(R.id.created_at);
            switch_selected=itemView.findViewById(R.id.switch_select);

        }
    }
}
