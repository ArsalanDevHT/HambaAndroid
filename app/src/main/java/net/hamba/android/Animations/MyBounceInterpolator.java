package net.hamba.android.Animations;

/**
 * Created by khawar on 2/13/2019.
 */

public class MyBounceInterpolator implements android.view.animation.Interpolator {
private double mAmplitude = 1;
private double mFrequency = 10;

        public MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
        }

public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
        Math.cos(mFrequency * time) + 1);
        }
        }