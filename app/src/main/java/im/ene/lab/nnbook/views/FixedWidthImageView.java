package im.ene.lab.nnbook.views;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.utils.ENEUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FixedWidthImageView extends ImageView {

	private Context mContext;

	private float mWidthRate, mHeightRatio;
	private int mWidth, mHeight;

	public FixedWidthImageView(Context context) {
		super(context);
	}

	public FixedWidthImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.FixedWidthImageView);
		this.mWidthRate = a.getFloat(R.styleable.FixedWidthImageView_widh_rate,
				0.6f);

		this.mHeightRatio = a.getFloat(
				R.styleable.FixedWidthImageView_height_ratio, 0.0f);
		
		Point point = ENEUtils.getFullDisplaySize(context);

		this.mWidth = (int) (point.x * mWidthRate);

		if (this.mWidth == 0) {
			this.mWidth = attrs.getAttributeIntValue(
					"http://schemas.android.com/apk/res/android",
					"layout_width", 0);
		}

		a.recycle();

		// setScaleType(ScaleType.FIT_END);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Drawable drawable = getDrawable();

		if (drawable != null) {

			int drw_w = drawable.getIntrinsicWidth();
			int drw_h = drawable.getIntrinsicHeight();

			float ratio = (float) drw_h / drw_w;

//			if (this.mHeightRatio != 0.0)
//				ratio = this.mHeightRatio;
			
			this.mHeight = (int) (this.mWidth * ratio);

			int fullWidth = this.mWidth + getPaddingLeft() + getPaddingRight();
			int fullHeight = this.mHeight + getPaddingTop()
					+ getPaddingBottom();

			setMeasuredDimension(fullWidth, fullHeight);

		} else
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// @Override
	// public void requestLayout() {
	// forceLayout();
	// }

}
