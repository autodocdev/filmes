package diones.filmes.com.filmes.views.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;

public class AnimUtils {

    public static final int REVEAL_DURATION = 500;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showRevealEffect(final View v, int centerX, int centerY,
                                        @Nullable Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int finalRadius = Math.max(v.getWidth(), v.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, finalRadius);

        anim.setDuration(REVEAL_DURATION);

        if (lis != null)
            anim.addListener(lis);

        anim.start();
    }

}
