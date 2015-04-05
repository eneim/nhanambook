package im.ene.lab.nnbook.dialogs;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.models.NNBookInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class NNBookInfoDialogFragment extends NNBasedDialogFragment {

	public static final String TAG = "im.ene.lab.nnbook.book_info_dialog_fragment";

	private Context mContext;
	private final NNBookInfo mBookInfo;

	private ImageView mCover;
	private TextView mTextInfo, mTextIntro;

	public NNBookInfoDialogFragment(NNBookInfo bookInfo) {
		this.mBookInfo = bookInfo;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_book_info, null);
		mCover = (ImageView) view.findViewById(R.id.book_cover);
		mTextInfo = (TextView) view.findViewById(R.id.book_info);
		mTextIntro = (TextView) view.findViewById(R.id.book_intro);

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setView(view);

		AlertDialog dialog = builder.create();
		return dialog;
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);

		Ion.with(mContext).load(mBookInfo.getCover()).withBitmap()
				.placeholder(R.drawable.book_cover_placeholder)
				.intoImageView(mCover);
		mTextInfo.setText(mBookInfo.getInfo().trim());
		mTextIntro.setText(mBookInfo.getIntro().trim());
		mTextIntro.setMovementMethod(new ScrollingMovementMethod());
	}
}
