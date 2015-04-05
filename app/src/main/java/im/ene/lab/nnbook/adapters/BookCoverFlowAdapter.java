package im.ene.lab.nnbook.adapters;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.libs.fancycoverflow.FancyCoverFlowAdapter;
import im.ene.lab.nnbook.models.NNBook;
import im.ene.lab.nnbook.models.NNBookShelf;
import im.ene.lab.nnbook.views.FixedWidthImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.ion.Ion;

public class BookCoverFlowAdapter extends FancyCoverFlowAdapter {

	private NNBookShelf mShelf;

	public BookCoverFlowAdapter() {
		super();
		mShelf = new NNBookShelf();
	}

	public BookCoverFlowAdapter(NNBookShelf shelf) {
		this.mShelf = shelf;
	}

	public void setData(NNBookShelf shelf) {
		this.mShelf = shelf;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (this.mShelf != null && this.mShelf.books != null)
			return this.mShelf.books.size();

		return 0;
	}

	@Override
	public NNBook getItem(int position) {
		if (this.mShelf != null && this.mShelf.books != null)
			return this.mShelf.books.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getCoverFlowItem(int position, View reuseableView,
			ViewGroup parent) {

		FixedWidthImageView imageView = null;

		if (reuseableView != null) {
			imageView = (FixedWidthImageView) reuseableView;
		} else {
			imageView = (FixedWidthImageView) LayoutInflater.from(
					parent.getContext()).inflate(R.layout.book_cover_image,
					parent, false);
		}

		Ion.with(parent.getContext()).load(this.getItem(position).cover)
				.withBitmap().placeholder(R.drawable.book_cover_placeholder)
				.intoImageView(imageView);

		return imageView;
	}

}
