package woody.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 
 * @author Administrator
 *
 */
public class ScrollViewExtend extends ScrollView{
//	private MainActivity mActivity;

//	public void setActivity(MainActivity mActivity) {
//		this.mActivity = mActivity;
//	}
	
	public interface OnScrollChangedListener{
		void onScrollChange(int offset);
	}

	private OnScrollChangedListener mListener;
	
	public void setOnScrollChangedListener(OnScrollChangedListener listener){
		this.mListener = listener;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mListener != null){
			mListener.onScrollChange(t);
		}
//		mActivity.scroll(t);
	}

	public ScrollViewExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


}
