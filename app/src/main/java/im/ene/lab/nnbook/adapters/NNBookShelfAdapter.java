package im.ene.lab.nnbook.adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.models.NNBookShelf;
import im.ene.lab.nnbook.views.DividerItemDecoration;

public class NNBookShelfAdapter extends ArrayAdapter<NNBookShelf> {

    private Context mContext;
    private List<NNBookShelf> mData;

    private LinearLayoutManager mLayoutManager;

    public NNBookShelfAdapter(Context context, int resource,
                              List<NNBookShelf> object) {
        super(context, resource, object);

        mContext = context;
        mData = object;

        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final BookShelfViewHolder mViewHolder;

        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.book_shelf,
                    parent, false);

            mViewHolder = new BookShelfViewHolder();
            mViewHolder.title = (TextView) view.findViewById(R.id.shelf_title);
            mViewHolder.books = (RecyclerView) view.findViewById(R.id.book_list);

            mViewHolder.books.setItemAnimator(new DefaultItemAnimator());
            mViewHolder.books.setLayoutManager(mLayoutManager);
            mViewHolder.books.addItemDecoration(new DividerItemDecoration(
                    mContext, DividerItemDecoration.HORIZONTAL_LIST));

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (BookShelfViewHolder) view.getTag();
        }

        NNBookShelf item = mData.get(position);

        if (mViewHolder != null && item != null) {
            BookshelfRecyclerAdapter bookAdapter = new BookshelfRecyclerAdapter(mContext,
                    item.getBooks());

            mViewHolder.title.setText(item.getTitle() + "");
            mViewHolder.books.setAdapter(bookAdapter);
        }

        return view;
    }

    private final class BookShelfViewHolder {
        public TextView title;
        public RecyclerView books;
    }

}
