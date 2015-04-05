package im.ene.lab.nnbook.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import im.ene.lab.nnbook.R;

public class BookShelfView extends LinearLayout {

    public TextView mBookShelfTitle;
    public RecyclerView mBookShelfContent;

    private OnBookClickListener mListener;

    public BookShelfView(Context context) {
        super(context);
        inflate(context, R.layout.book_shelf, this);
        init();
    }

    private void init() {
        mBookShelfTitle = (TextView) findViewById(R.id.shelf_title);
        mBookShelfContent = (RecyclerView) findViewById(R.id.book_list);
    }

    public interface OnBookClickListener {

    }
}
