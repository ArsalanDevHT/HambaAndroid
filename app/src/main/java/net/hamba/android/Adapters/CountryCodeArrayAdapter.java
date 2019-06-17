package net.hamba.android.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import net.hamba.android.Customes.CountryCodePicker;
import net.hamba.android.Models.Country;
import net.hamba.android.Utils.CountryUtils;

import java.util.List;

public class CountryCodeArrayAdapter extends ArrayAdapter<Country> {
    private final CountryCodePicker mCountryCodePicker;

    public CountryCodeArrayAdapter(Context ctx, List<Country> countries, CountryCodePicker picker) {
        super(ctx, 0, countries);
        mCountryCodePicker = picker;
    }

    private static class ViewHolder {
        RelativeLayout rlyMain;
        TextView tvName, tvCode;
        ImageView imvFlag;
        LinearLayout llyFlagHolder;
        View viewDivider;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Country country = getItem(position);

        CountryCodeArrayAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CountryCodeArrayAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(com.rilixtech.R.layout.item_country, parent, false);

            viewHolder.rlyMain = convertView.findViewById(com.rilixtech.R.id.item_country_rly);
            viewHolder.tvName = convertView.findViewById(com.rilixtech.R.id.country_name_tv);
            viewHolder.tvCode = convertView.findViewById(com.rilixtech.R.id.code_tv);
            viewHolder.imvFlag = convertView.findViewById(com.rilixtech.R.id.flag_imv);
            viewHolder.llyFlagHolder = convertView.findViewById(com.rilixtech.R.id.flag_holder_lly);
            viewHolder.viewDivider = convertView.findViewById(com.rilixtech.R.id.preference_divider_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CountryCodeArrayAdapter.ViewHolder) convertView.getTag();
        }
        setData(country, viewHolder);
        return convertView;
    }

    private void setData(Country country, CountryCodeArrayAdapter.ViewHolder viewHolder) {
        // set data
        if (country == null) {
            viewHolder.viewDivider.setVisibility(View.VISIBLE);
            viewHolder.tvName.setVisibility(View.GONE);
            viewHolder.tvCode.setVisibility(View.GONE);
            viewHolder.llyFlagHolder.setVisibility(View.GONE);
        } else {
            viewHolder.viewDivider.setVisibility(View.GONE);
            viewHolder.tvName.setVisibility(View.VISIBLE);
            viewHolder.tvCode.setVisibility(View.VISIBLE);
            viewHolder.llyFlagHolder.setVisibility(View.VISIBLE);
            Context ctx = viewHolder.tvName.getContext();
            String name = country.getName();
            String iso = country.getIso().toUpperCase();
            String countryNameAndCode = ctx.getString(com.rilixtech.R.string.country_name_and_code, name, iso);
            viewHolder.tvName.setText(countryNameAndCode);
            if (mCountryCodePicker.isHidePhoneCode()) {
                viewHolder.tvCode.setVisibility(View.GONE);
            } else {
                viewHolder.tvCode.setText(ctx.getString(com.rilixtech.R.string.phone_code, country.getPhoneCode()));
            }
            Typeface typeface = mCountryCodePicker.getTypeFace();
            if (typeface != null) {
                viewHolder.tvCode.setTypeface(typeface);
                viewHolder.tvName.setTypeface(typeface);
            }
            viewHolder.imvFlag.setImageResource(CountryUtils.getFlagDrawableResId(country));
            int color = mCountryCodePicker.getDialogTextColor();
            if (color != mCountryCodePicker.getDefaultContentColor()) {
                viewHolder.tvCode.setTextColor(color);
                viewHolder.tvName.setTextColor(color);
            }
        }
    }
}
