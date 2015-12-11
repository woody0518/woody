package woody.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.woody.lib.R;

import woody.utils.ScreenUtils;

public class ViewPagerIndicator extends View {

    private static final int CY = 30;
    private Paint paint;
    private Paint paint2;
    private Paint paint3;
    private float offset;
    private int mIndicatorNum;
    private int mIndicatorBackColor = Color.BLACK;
    private int mIndicatorForeColor = Color.BLACK;
    private float mIndicatorRadius = 12;
    private int mIndicatorLineColor = Color.BLACK;
    private int mGravity;
    private final int mMiddle = 0;
    private final int mLeft = 1;
    private final int mRight = 2;


    @Override
    protected void onDraw(Canvas canvas) {
        int cx = 0;
        switch (mGravity) {
            case mMiddle:
                cx = (int) (ScreenUtils.getScreenWidth(getContext()) / 2 - (mIndicatorNum - 1)
                        * 1.5 * mIndicatorRadius);
                break;

            case mLeft:
                cx = 0;
                break;

            case mRight:
                cx = (int) (ScreenUtils.getScreenWidth(getContext()) - (mIndicatorNum * 3 - 1) * mIndicatorRadius);
                break;

            default:
                break;
        }
        for (int i = 0; i < mIndicatorNum; i++) {
            canvas.drawCircle(cx + i * mIndicatorRadius * 3, CY, mIndicatorRadius, paint);
            canvas.drawCircle(cx + i * mIndicatorRadius * 3, CY, mIndicatorRadius, paint2);
        }

        canvas.drawCircle(cx + offset, CY, mIndicatorRadius, paint3);
    }

    public void setNum(int mIndicatorNum) {
        this.mIndicatorNum = mIndicatorNum;
    }

    public void move(int position, float perc) {
        offset = position * 3 * mIndicatorRadius;
        if (position != mIndicatorNum - 1) {
            offset += (int) (perc * 3 * mIndicatorRadius);
        }
        invalidate();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ViewPagereIndicator);
        mIndicatorNum = array.getInt(R.styleable.ViewPagereIndicator_IndicatorNum, 4);
        mIndicatorForeColor = array
                .getColor(R.styleable.ViewPagereIndicator_IndicatorForeColor, Color.WHITE);
        mIndicatorBackColor = array
                .getColor(R.styleable.ViewPagereIndicator_IndicatorBackColor, Color.RED);
        mIndicatorLineColor = array
                .getColor(R.styleable.ViewPagereIndicator_IndicatorLineColor, Color.BLACK);
        mIndicatorRadius = array.getDimension(
                R.styleable.ViewPagereIndicator_IndicatorRadius, 20);
        mGravity = array.getInt(R.styleable.ViewPagereIndicator_IndicatorGravity, 0);
//		recycle the useless instance, Or memory will be out of size
        array.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mIndicatorBackColor);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(mIndicatorLineColor);
        paint2.setStyle(Style.STROKE);
        paint2.setStrokeWidth(2);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(mIndicatorForeColor);
    }

}
