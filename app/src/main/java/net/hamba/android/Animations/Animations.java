package net.hamba.android.Animations;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import net.hamba.android.R;


public class Animations extends Animation{

    View mView;
    int mFromHeight;
    int mToHeight;
    public void doBounceAnimation(View targetView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationX", 0, 100, 0);
        animator.setStartDelay(500);
        animator.setDuration(1500);
        animator.start();
    }
    public void SlideIn(Context context, View view){
     Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_left);
     view.setVisibility(View.VISIBLE);
     view.setAnimation(anim);
    }
    public void SlideOut(Context context, View view){
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.anim_slide_out_right);
        view.setAnimation(anim);
        view.setVisibility(View.GONE);


    }
    public void SlideInFromTop(Context context, View view){
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        view.setAnimation(anim);
        view.setAlpha(1);
        view.setVisibility(View.VISIBLE);

    }
    public void SlideOutUp(Context context, View view){
        view.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_out_up);
        view.setAnimation(anim);
        view.setAlpha(0);


    }
    private Animation animate(boolean up,Context context) {
        Animation anim = AnimationUtils.loadAnimation( context, up ? R.anim.rotate_up_anim : R.anim.rotate_down_anim);
        anim.setInterpolator(new LinearInterpolator()); // for smooth animation
        return anim;
    }
    public void CollapsMenuAnimation(boolean isMenuDown,Context context,View body, View icon){
        if(!isMenuDown){
            SlideInFromTop(context,body);
            icon.startAnimation(animate(isMenuDown,context));
        }else{
            SlideOutUp(context,body);
            icon.startAnimation(animate(isMenuDown,context));

        }

    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int newHeight;

        if (mView.getHeight() != mToHeight) {
            newHeight = (int) (mFromHeight + ((mToHeight - mFromHeight) * interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }


}
