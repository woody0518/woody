package woody.utils.animation;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2015/12/6.
 */
public class AnimUtils {
    private static AnimUtils util = new AnimUtils();

    private AnimUtils() {

    }

    public static AnimUtils getInstance() {
        return util;
    }

    public void setPivotX(View target, float pivotx, float pivoty) {
        ViewHelper.setPivotX(target, pivotx);
        ViewHelper.setPivotY(target, pivoty);
    }

    public void translateX(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "translationX", Value).setDuration(duration).start();
    }

    public void translateY(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "translationY", Value).setDuration(duration).start();
    }

    public void scaleX(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "scaleX", 1, 2, 1).setDuration(duration).start();
    }

    public void scaleY(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "scaleY", 1, 2, 1).setDuration(duration).start();
    }

    public void alpha(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "alpha", 1, 0, 1).setDuration(duration).start();
    }

    public void rotationX(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "rotationX", 1, 0, 1).setDuration(duration).start();
    }

    public void rotationY(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "rotationY", 1, 0, 1).setDuration(duration).start();
    }

    public void rotation(Object target, long duration, float... Value) {
        ObjectAnimator.ofFloat(target, "rotation", 1, 0, 1).setDuration(duration).start();
    }







}
