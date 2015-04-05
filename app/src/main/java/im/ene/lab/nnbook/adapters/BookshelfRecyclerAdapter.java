package im.ene.lab.nnbook.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.models.NNBook;
import im.ene.lab.nnbook.utils.ENEUtils;

/**
 * Created by eneim on 1/18/15.
 */
public class BookshelfRecyclerAdapter extends RecyclerView.Adapter {

    private List<NNBook> mBooks;
    private Context mContext;

    public BookshelfRecyclerAdapter(Context context, @NonNull List<NNBook> books) {
        super();
        this.mContext = context;
        this.mBooks = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (this.mContext == null)
            this.mContext = viewGroup.getContext();
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.book_item,
                viewGroup, false);
        final ViewGroup.LayoutParams parentParams = viewGroup.getLayoutParams();

//        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right,
//                                       int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                // TODO Auto-generated method stub
//                int childWidth = right - left;
//                if (parentParams != null)
//                    parentParams.height = (int) (childWidth * 1.8);
//            }
//        });

        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (!(viewHolder instanceof BookHolder))
            throw new IllegalArgumentException("you have to use BookHolder");

        BookHolder vh = (BookHolder) viewHolder;
        NNBook item = this.mBooks.get(i);
        if (item != null) {
            vh.book_title.setText(item.getTitle());
            Ion.with(this.mContext).load(item.getCover()).withBitmap()
                    .resizeHeight((int) ENEUtils.getPixelFromDP(mContext, 172.0f))
                    .placeholder(R.drawable.book_cover_placeholder)
                    .intoImageView(vh.book_cover);
        }

    }

    @Override
    public int getItemCount() {
        return this.mBooks.size();
    }

    static class BookHolder extends RecyclerView.ViewHolder {
        TextView book_title;
        ImageView book_cover;
        View view;

        public BookHolder(View view) {
            super(view);
            this.book_title = (TextView) view
                    .findViewById(R.id.book_title);
            this.book_cover = (ImageView) view
                    .findViewById(R.id.book_cover);
            this.view = view;
        }
    }
}
