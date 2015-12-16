package woody.widget.time;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.woody.lib.R;

import woody.utils.BitmapUtils;
import woody.utils.FormateUtils;

/**
 * Created by DELL on 2015/12/8.
 */
public class TimeTaskView extends SurfaceView implements SurfaceHolder.Callback {


    public final static int POSITIVE_ORDER = 0x01;
    public final static String FORMATE_TYPE_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMATE_TYPE_2 = "yyyy-MM-dd";
    private int mInerVal;
    //    public final static int REVERSER_ORDER = 0x02;
    private int mOrder = 0x0;
    private int mMinValue;
    private int mMaxValue = 10000;
    private int mTextColor;
    private float mTextSize;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    int time = mMaxValue;
    private boolean isWorkRun;
    private Bitmap mBackgroudBitmap;
    private Rect mR;
    private int mWidth;
    private int mHeight;
    private TimeWrapper mTimeWrapper;
    private Context mContext;
    private TimeWrapper.TimeType mType;

    public TimeTaskView(Context context) {
        super(context);
        mContext = context;
    }

    public boolean isPostive() {
        return (mOrder | POSITIVE_ORDER) == POSITIVE_ORDER;
    }

    public void setTimeRun(boolean isWorkRun) {
        this.isWorkRun = isWorkRun;
    }

    public TimeTaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimeTaskView);
        mTextColor = array.getColor(R.styleable.TimeTaskView_TextColor, Color.BLACK);
        mTextSize = array.getDimension(R.styleable.TimeTaskView_TextSize, 12);
        mOrder = array.getInt(R.styleable.TimeTaskView_Order, 0x0);
        mTextSize = array.getDimension(R.styleable.TimeTaskView_TextSize, 12);
        mMaxValue = array.getInteger(R.styleable.TimeTaskView_MaxValue, 0);
        mMinValue = array.getInteger(R.styleable.TimeTaskView_MinValue, 0);
        mInerVal = array.getInteger(R.styleable.TimeTaskView_InterVal, 0);
        isWorkRun = array.getBoolean(R.styleable.TimeTaskView_Runable, true);
        setZOrderOnTop(true);
        array.recycle();
        initPaint();
        initView();
    }

    private void initView() {
//        mR = new Rect();
//        getLocalVisibleRect(mR);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        if (getBackground() != null) {
            mBackgroudBitmap = BitmapUtils.drawable2Bitmap(getBackground());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        }

        if (isPostive()) {
            time = mMinValue;
        } else {
            time = mMaxValue;
        }
    }

    public void setOnTimeWrapper(TimeWrapper wrapper) {
        mTimeWrapper = wrapper;
    }

    private boolean isAdapterNull(TimeAdapter adapter) {
        return adapter != null;
    }


    class TimeThread extends Thread {
        @Override
        public void run() {
            if (!isWorkRun && mHolder == null && getVisibility() != View.VISIBLE) {
                return;
            }

            try {
                if (isPostive()) {
                    while (time <= mMaxValue) {
                        draw(time);
                        time++;
                        Thread.sleep(mInerVal);
                    }
                } else {
                    while (time >= mMinValue) {
                        draw(time);
                        time--;
                        Thread.sleep(mInerVal);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = 0;
        mHeight = 0;

        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            if (mBackgroudBitmap != null) {
                mHeight = Math.max(mBackgroudBitmap.getHeight(), getDesiredHeight(heightSize));
            } else {
                mHeight = heightSize;
            }
        } else {
            int desired = getDesiredHeight(heightSize);

            mHeight = desired;
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desired, heightSize);
            }
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            if (mBackgroudBitmap != null) {
                mWidth = Math.max(mBackgroudBitmap.getWidth(), getDesiredWidth(widthSize));
            } else {
                mWidth = widthSize;
            }

//            mWidth = getDesiredWidth(widthSize);
        } else {
            int desired = getDesiredWidth(widthSize);

            mWidth = desired;
            if (heightMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(desired, heightSize);
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    private int getDesiredHeight(int heightSize) {
        return Math.max(heightSize, getSuggestedMinimumHeight());
    }

    private int getDesiredWidth(int widthSize) {
        return Math.max(widthSize, getSuggestedMinimumWidth());
    }


    /**
     * 自定义绘图方法
     * 2014-12-19 下午2:22:45
     */
    public void draw(long time) {
        Log.e("time", time + "");
        if (mHolder == null) {
            return;
        }
        synchronized (mHolder) {
            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                if (mBackgroudBitmap != null) {
                    canvas.drawBitmap(mBackgroudBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaint);
                }
//                setBackgroundColor(Color.TRANSPARENT);
//                canvas.drawColor(Color.BLACK);

                String text = formateTime(time);
                canvas.drawText(text, mWidth / 2 - mTextSize / 2, mHeight / 2 + mTextSize / 2, mPaint);
                mHolder.setSizeFromLayout();
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @NonNull
    private String formateTime(long time) {
        if (mTimeWrapper != null) {
            mType = mTimeWrapper.getTimeType();
            return fomateTimeByType(mType, time);
        } else {
            mType = TimeWrapper.TimeType.HH_MM_SS_PERDAY;
            return fomateTimeByType(mType, time);
        }
    }

    private String fomateTimeByType(TimeWrapper.TimeType type, long time) {
        String temp = "";
        switch (type) {

            case HH_MM_SS_PERDAY:
                temp = FormateUtils.getInstance(mContext).formatNumberOneDay(time);
                break;
            case YYYY_MM_DD_PERYEAR:
                temp = FormateUtils.getInstance(mContext).formateTime(time, FormateUtils.TIME_FORMATE_YY_MM_DD);
                break;
            case YYYY_MM_DD_HH_MM_SS:
                temp = FormateUtils.getInstance(mContext).formateTime(time, FormateUtils.TIME_FORMATE_YY_MM_DD_HH_MM_SS);
                break;
            case TIME_STRING:
                temp = FormateUtils.getInstance(mContext).formateHoldTime(time);
                break;

            default:
                temp = FormateUtils.getInstance(mContext).formatNumberOneDay(time);

        }
        return temp;
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new TimeThread().start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isWorkRun = false;
        getHolder().removeCallback(this);
    }
}
