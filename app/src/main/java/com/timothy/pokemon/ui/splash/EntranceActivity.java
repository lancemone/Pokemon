package com.timothy.pokemon.ui.splash;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.splashscreen.SplashScreen;

import com.timothy.common.arouter.ARouterManager;

/**
 * @Project: Pokemon
 * @ClassPath: com.timothy.pokemon.ui.splash.EntranceActivity
 * @Author: MoTao
 * @Date: 2023-03-09
 * <p>
 * <p/>
 */

public class EntranceActivity extends Activity {

    private static final String TAG = "Splash";

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
//        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
//            Log.d(TAG, "onSplashScreenExit ");
//            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
//                    splashScreenView,
//                    View.TRANSLATION_Y,
//                    0f,
//                    -splashScreenView.getHeight());
//            slideUp.setInterpolator(new AnticipateInterpolator());
//            slideUp.setDuration(200);
//
//            slideUp.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                    splashScreenView.remove();
//                }
//            });
//            slideUp.start();
//        });

        try {
            Thread.sleep(2000);
            toNextActivity();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void toNextActivity() {
        ARouterManager.toMainActivity(this, getIntent(), TAG);
    }
}
