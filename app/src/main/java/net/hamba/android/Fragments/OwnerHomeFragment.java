package net.hamba.android.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.hamba.android.Activities.OwnerHomeActivity;
import net.hamba.android.Adapters.MainSliderAdapter;
import net.hamba.android.R;
import net.hamba.android.Service.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;

public class OwnerHomeFragment extends Fragment implements View.OnClickListener {

    private Slider slider;
    private LinearLayout create_store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        Slider.init(new PicassoImageLoadingService(getContext()));

        return inflater.inflate(R.layout.owner_home_fragment, parent, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitUI(view);

    }
    public void InitUI(View v){
        slider          = v.findViewById(R.id.banner_slider1);
        create_store    = v.findViewById(R.id.create_store);
        slider.setAdapter(new MainSliderAdapter());
        create_store.setOnClickListener(this);
    }
    private void gotoCreateStore(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
        ft.replace(R.id.fragment, new CreateStoreMainFragment());
        ft.commit();
        OwnerHomeActivity.toolbar.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.create_store:
                gotoCreateStore();
                break;
        }
    }
}
