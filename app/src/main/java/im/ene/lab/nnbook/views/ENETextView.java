package im.ene.lab.nnbook.views;

import im.ene.lab.nnbook.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ENETextView extends TextView {

	Context context;
	String ttfName = null;

	String TAG = getClass().getName();

	public ENETextView(Context context) {
		super(context);
	}

	public ENETextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(attrs);
	}

	public ENETextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		this.context = context;
		init(attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	private void init(AttributeSet attrs) {
		if (attrs == null) {
			return; // quick exit
		}

		TypedArray typed_array = null;

		try {
			typed_array = getContext().obtainStyledAttributes(attrs,
					R.styleable.ENETextView);
			ttfName = typed_array.getString(R.styleable.ENETextView_ttf_name);
		} finally {
			if (typed_array != null) {
				typed_array.recycle(); // ensure this is always called
			}
		}

		if (ttfName == null)
			return;

		Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ ttfName);

		if (font == null)
			return;

		setTypeface(font);
	}

	@Override
	public void setTypeface(Typeface tf) {
		super.setTypeface(tf);
	}
}
