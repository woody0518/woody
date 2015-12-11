package woody.task;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.TimerTask;

/**
 * Created by DELL on 2015/12/8.
 */
public class TimeTask extends TimerTask {

    public final static int POSITIVE_ORDER = 0x01;
    public final static int REVERSER_ORDER = 0x02;
    private int mOrder = 0x0;
    private OnTimeChangeLiseter mL;
    private long mMax;
    private long mMin;
    private long mTime;
    private boolean mIsPositive;
    private SurfaceHolder mHolder;
    private boolean mIsWorkRun;

    public interface OnTimeChangeLiseter {
        void onTimeChange(long time, Canvas canvas);
    }

    public TimeTask() {
    }

    public TimeTask(SurfaceView view, int order, OnTimeChangeLiseter l) {
        mOrder = order;
        mL = l;
        mHolder = view.getHolder();
        mIsWorkRun = true;
        mIsPositive = (mOrder | POSITIVE_ORDER) == POSITIVE_ORDER;
        if (mIsPositive) {
            mTime = mMin;
        } else {
            mTime = mMax;
        }
    }

    public long getMin() {
        return mMin;
    }

    public void setMin(long min) {
        mMin = min;
    }

    public long getMax() {
        return mMax;
    }

    public void setMax(long max) {
        mMax = max;
    }

    public void setWorkRun(boolean isWorkRun) {
        mIsWorkRun = isWorkRun;
    }

    @Override
    public void run() {

        if (!mIsWorkRun) {
            return;
        }

        synchronized (mHolder) {
            Canvas canvas = mHolder.lockCanvas();

            if (canvas != null) {

                if (mIsPositive) {
                    mTime++;
                    if (mTime >= mMax)
                        return;

                } else {
                    mTime--;
                    if (mTime <= mMax)
                        return;
                }

                if (mL != null) {
                    mL.onTimeChange(mTime, canvas);
                }
//                mL.onTimeChange(mTime);
                mHolder.unlockCanvasAndPost(canvas);
            }

//            if (canvas != null)
        }
    }
}
