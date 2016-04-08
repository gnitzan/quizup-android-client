package com.rom.quizup.client.ui.splash;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rom.quizup.client.R;

/**
 * The purpose of this class is to support a logo splash animation
 */
public class SplashAnimation extends LinearLayout {
    private ImageView movie;
    private AnimationDrawable animation = null;

    int currentIndex = 0;

    /**
     * Constructor
     *
     * @param context The context
     */
    public SplashAnimation(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor
     *
     * @param context The context
     * @param attrs The attributes
     */
    public SplashAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Constructor
     *
     * @param context The context
     * @param attrs The attributes
     * @param defStyle
     */
    public SplashAnimation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * Start the animation
     */
    public void start() {
        if (!animation.isRunning()) {
            animation.start();
        }
    }

    /**
     * Stop the animation
     */
    public void stop() {
        if (animation.isRunning()) {
            animation.stop();
        }
    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.fragment_splashscreen_animation, this);

        this.setWillNotDraw(false);

        movie = (ImageView) findViewById(R.id.splash_movie);

        animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.splash_0), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_1), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_2), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_3), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_4), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_5), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_6), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_7), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_8), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_0), 120);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_0), 120);
        animation.setOneShot(false);

        movie.setBackground(animation);
    }
}