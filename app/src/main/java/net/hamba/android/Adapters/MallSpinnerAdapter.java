package net.hamba.android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import net.hamba.android.Models.MallModel;
import net.hamba.android.R;

import java.util.ArrayList;

public class MallSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MallModel> list;
    private LayoutInflater inflater;
    private int  layout_id;

    public MallSpinnerAdapter(Context context, ArrayList<MallModel> list, LayoutInflater inflater, int layout_id) {
        this.context = context;
        this.list = list;
        this.inflater = inflater;
        this.layout_id = layout_id;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MallModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView           = inflater.inflate(R.layout.spinner_item, null);
        TextView text       = (TextView) convertView.findViewById(R.id.text);
        CheckBox checkBox   = (CheckBox) convertView.findViewById(R.id.checkbox);
        text.setText(list.get(position).getName());
        checkBox.setChecked(list.get(position).isSelected);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    list.get(position).setSelected(true);
//                }else{
//                    list.get(position).setSelected(false);
//                }
//
//            }
//        });
        return convertView;
    }


}
