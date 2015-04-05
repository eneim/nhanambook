package im.ene.lab.nnbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.models.NNBook;

public class NNBookItemAdapter extends ArrayAdapter<NNBook> {

    private List<NNBook> mBooks;
    private Context mContext;

    public NNBookItemAdapter(Context context, int resource, List<NNBook> objects) {
        super(context, resource, objects);
        mContext = context;
        mBooks = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View view = convertView;
        View view = convertView;
        final BookShelfViewHolder mBookShelfViewHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.book_item,
                    parent, false);
            mBookShelfViewHolder = new BookShelfViewHolder(view);

            view.setTag(mBookShelfViewHolder);
        } else {
            mBookShelfViewHolder = (BookShelfViewHolder) view.getTag();
        }

        NNBook item = mBooks.get(position);
        if (item != null) {
            Log.i("book_url", item.getTitle() + " | " + item.getUrl());

            mBookShelfViewHolder.book_title.setText(item.getTitle());
            Ion.with(mContext).load(item.getCover()).withBitmap()
                    .placeholder(R.drawable.book_cover_placeholder)
                    .intoImageView(mBookShelfViewHolder.book_cover);
        }

        final LayoutParams parentParams = parent.getLayoutParams();

        view.addOnLayoutChangeListener(new OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // TODO Auto-generated method stub
                int childWidth = right - left;
                if (parentParams != null)
                    parentParams.height = (int) (childWidth * 1.8);
            }
        });

        return view;
    }

    static class BookShelfViewHolder extends RecyclerView.ViewHolder {
        TextView book_title;
        ImageView book_cover;

        public BookShelfViewHolder(View view) {
            super(view);
            this.book_title = (TextView) view
                    .findViewById(R.id.book_title);
            this.book_cover = (ImageView) view
                    .findViewById(R.id.book_cover);

        }
    }
}
