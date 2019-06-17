package net.hamba.android.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hamba.android.Adapters.MainSliderAdapter;
import net.hamba.android.R;
import net.hamba.android.Service.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;

public class IndividualHomeFragment extends Fragment {

    private Slider slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Slider.init(new PicassoImageLoadingService(getContext()));

        return inflater.inflate(R.layout.individual_home_fragment, parent, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);

    }
    public void InitUI(View v){
        /*slider = v.findViewById(R.id.banner_slider1);
        slider.setAdapter(new MainSliderAdapter());*/
    }
}
