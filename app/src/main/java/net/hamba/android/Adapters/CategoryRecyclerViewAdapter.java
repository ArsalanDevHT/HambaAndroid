package net.hamba.android.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hamba.android.Instagram.CategoryModel;
import net.hamba.android.R;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter  extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.MyViewHolder> {
   public ArrayList<CategoryModel> data;
    Context context;
    int layout;
    private String selected_cat="";

    public String getSelected_cat() {
        return selected_cat;
    }

    public CategoryRecyclerViewAdapter(ArrayList<CategoryModel> data, Context context, int layout) {
        this.data = data;
        this.context = context;
        this.layout = layout;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView close;
        private TextView CateName;
        private TextView CateName_;
        private View rootView;
        private LinearLayout selected;
        private LinearLayout unselected;

        public MyViewHolder(View convertView) {
            super(convertView);
            rootView    = convertView.findViewById(R.id.rootView);
            close       = (ImageView) convertView.findViewById(R.id.close);
            CateName    = (TextView) convertView.findViewById(R.id.cate);
            CateName_   = (TextView) convertView.findViewById(R.id.cate_);
            selected    = convertView.findViewById(R.id._selected);
            unselected  = convertView.findViewById(R.id._unselected);
        }
    }
    @Override
    public void onBindViewHolder(final CategoryRecyclerViewAdapter.MyViewHolder holder, final int position) {
        CategoryModel model = data.get(position);
        holder.CateName.setText(model.getCate_name());
        holder.CateName_.setText(model.getCate_name());
        if(model.isSelected()){
            holder.selected.setVisibility(View.VISIBLE);
            holder.unselected.setVisibility(View.GONE);
            selected_cat+=model.getCate_name()+",";

        }else{
            selected_cat =  selected_cat.replace(model.getCate_name()+",","");
            holder.unselected.setVisibility(View.VISIBLE);
            holder.selected.setVisibility(View.GONE);

        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!model.isSelected()){
                    model.setSelected(true);
                holder.unselected.setVisibility(View.GONE);
                holder.selected.setVisibility(View.VISIBLE);
                selected_cat+=model.getCate_name()+",";
                    Log.e( "onClick: ","==> "+selected_cat );

                }else{
                  selected_cat =  selected_cat.replace(model.getCate_name()+",","");
                    Log.e( "onClick: ","==> "+selected_cat );

                    model.setSelected(false);
                    holder.unselected.setVisibility(View.VISIBLE);
                    holder.selected.setVisibility(View.GONE);
                }
            }
        });



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new CategoryRecyclerViewAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }




}

