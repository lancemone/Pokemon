package com.timothy.pokemon.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.splashscreen.SplashScreen;

import com.timothy.common.arouter.ARouterManager;
import com.timothy.common.utils.HandlerTaskUtils;
import com.timothy.pokemon.databinding.ActivitySplashBinding;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.pokemon.ui.splash.EntranceActivity
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 * <p/>
 */

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends Activity {

    private static final String TAG = "Splash";
    private ActivitySplashBinding binding;
    private boolean isReady = false;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long startTime = System.currentTimeMillis();
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        splashScreen.setOnExitAnimationListener(splashScreenViewProvider -> {
            Log.d(TAG, "onSplashScreenExit all time: " + (System.currentTimeMillis() - startTime));
            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    splashScreenViewProvider.getView(),
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenViewProvider.getView().getHeight());
            slideUp.setInterpolator(new AnticipateInterpolator());
            slideUp.setDuration(500);

            slideUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    splashScreenViewProvider.remove();
                    toNextActivity();
                }
            });
            slideUp.start();
        });
        splashScreen.setKeepOnScreenCondition(() -> {
            Log.d(TAG, "splashScreen KeepOnScreenCondition: " + !isReady);
            return !isReady;
        });
        setContentView(binding.getRoot());
        checkData();
    }

    private void toNextActivity() {
        ARouterManager.toMainActivity(this, getIntent(), TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
        binding = null;
        Log.d(TAG, "onDestroy");
    }

    private void checkData() {
        isReady = false;
        HandlerTaskUtils.getInstance().postDelayedOnDefaultThread(() -> isReady = true, 5000);
    }
}
