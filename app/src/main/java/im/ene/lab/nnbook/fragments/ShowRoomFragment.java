package im.ene.lab.nnbook.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.adapters.BookCoverFlowAdapter;
import im.ene.lab.nnbook.adapters.BookshelfRecyclerAdapter;
import im.ene.lab.nnbook.libs.fancycoverflow.FancyCoverFlow;
import im.ene.lab.nnbook.loaders.NNBookShelvesLoader;
import im.ene.lab.nnbook.loaders.NNBookShelvesLoader.OnBooksLoadedCallback;
import im.ene.lab.nnbook.loaders.NNTopSliderLoader;
import im.ene.lab.nnbook.loaders.NNTopSliderLoader.OnContentLoadedCallback;
import im.ene.lab.nnbook.models.NNBook;
import im.ene.lab.nnbook.models.NNBookShelf;
import im.ene.lab.nnbook.utils.ENEUtils;
import im.ene.lab.nnbook.views.BookShelfView;
import im.ene.lab.nnbook.views.DividerItemDecoration;
import im.ene.lab.nnbook.views.StopableViewPager;

public class ShowRoomFragment extends Fragment implements
        OnItemSelectedListener, OnItemClickListener {
    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static ShowRoomFragment newInstance(String name) {
        ShowRoomFragment fragment = new ShowRoomFragment();
        Bundle args = new Bundle();
        args.putString(BasedFragment.ARC_TAB_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public ShowRoomFragment() {

    }

    private StopableViewPager mAdsSlider;
    private AdsSliderPagerAdapter mPagerAdapter;

    private FancyCoverFlow mCoverFlow;
    private BookCoverFlowAdapter mTopAdapter;

    private TextView mBookInfo;

    private LinearLayout mShowRoom;
    
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_homepage,
                container, false);

        mAdsSlider = (StopableViewPager) rootView
                .findViewById(R.id.slider_view_pager);
        mPagerAdapter = new AdsSliderPagerAdapter();
        mAdsSlider.setAdapter(mPagerAdapter);
        mAdsSlider.setVisibility(View.INVISIBLE);

        mShowRoom = (LinearLayout) rootView.findViewById(R.id.show_room);
        mShowRoom.setVisibility(View.GONE);

        mCoverFlow = (FancyCoverFlow) rootView.findViewById(R.id.cover_flow);
        mBookInfo = (TextView) rootView.findViewById(R.id.item_info);

        Point point = ENEUtils.getFullDisplaySize(getActivity());
        int maxHeight = point.y - ENEUtils.getStatusBarHeight(getActivity())
                - ENEUtils.getNaviBarHeight(getActivity());

        maxHeight = (int) (maxHeight - ENEUtils.getPixelFromDP(getActivity(),
                96 // book title height
                        + 48 // page indicatior height
                        + 96 // ads slider height
        ));

        ENEUtils.setViewDimens(getActivity(), (ViewGroup) rootView, mCoverFlow,
                LayoutParams.MATCH_PARENT, maxHeight);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPagingHandler = new PagingHandler(this);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTopAdapter = new BookCoverFlowAdapter();

        if (mCoverFlow != null) {
            mCoverFlow.setAdapter(mTopAdapter);
            mCoverFlow.setUnselectedAlpha(0.65f);
            mCoverFlow.setUnselectedSaturation(1.0f);
            mCoverFlow.setUnselectedScale(0.65f);
            // this.fancyCoverFlow.setSpacing(0);
            mCoverFlow.setMaxRotation(0);
            mCoverFlow.setScaleDownGravity(0.8f);
            mCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
            mCoverFlow.setOnItemSelectedListener(this);
            mCoverFlow.setOnItemClickListener(this);
        }

        new NNTopSliderLoader(new OnContentLoadedCallback() {

            @Override
            public void onLoaded(List<String> results) {
                if (results == null || results.size() < 1)
                    return;

                mPagerAdapter.setData(results);
                mAdsSlider.setVisibility(View.VISIBLE);
                // changeViewPagerToPage();
            }
        }).load();

        new NNBookShelvesLoader(new OnBooksLoadedCallback() {

            @Override
            public void shelvesLoaded(List<NNBookShelf> bookshelves) {

                if (bookshelves == null || bookshelves.size() < 1)
                    return;

                mTopAdapter.setData(bookshelves.get(0));

                for (int i = 0; i < bookshelves.size(); i++) {
                    final NNBookShelf shelf = bookshelves.get(i);

                    // View view = LayoutInflater.from(mContext).inflate(
                    // R.layout.book_shelf, null);
                    BookShelfView view = new BookShelfView(mContext);
                    BookShelfViewHolder mViewHolder = new BookShelfViewHolder();

                    view.mBookShelfContent.setItemAnimator(new DefaultItemAnimator());

                    LinearLayoutManager mLayoutManager;
                    mLayoutManager = new LinearLayoutManager(mContext);
                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                    view.mBookShelfContent.setLayoutManager(mLayoutManager);
                    view.mBookShelfContent.addItemDecoration(new DividerItemDecoration(
                            mContext, DividerItemDecoration.HORIZONTAL_LIST));
                    view.mBookShelfContent.setHasFixedSize(true);

                    if (shelf != null) {
                        BookshelfRecyclerAdapter bookAdapter = new BookshelfRecyclerAdapter(
                                mContext, shelf.getBooks());

                        view.mBookShelfTitle.setText(shelf.getTitle() + "");
                        view.mBookShelfContent.setAdapter(bookAdapter);

                        // view.setBackgroundResource(color_id[i]);
                    }

                    mShowRoom.addView(view);
                    mShowRoom.setVisibility(View.VISIBLE);
                }
            }
        }).execute();

    }

    private final class BookShelfViewHolder {
        public TextView title;
        public RecyclerView books;
    }

    private class AdsSliderPagerAdapter extends PagerAdapter {

        private List<String> mUrls;

        public AdsSliderPagerAdapter() {
            mUrls = new ArrayList<String>();
        }

        public void setData(List<String> urls) {
            if (mUrls == null)
                mUrls = new ArrayList<String>();

            mUrls.clear();
            mUrls.addAll(urls);
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = new ImageView(mContext);
            img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            Ion.with(mContext).load(mUrls.get(position)).intoImageView(img);
            ((ViewPager) container).addView(img);
            return img;
        }

        @Override
        public int getCount() {
            if (mUrls != null)
                return mUrls.size();
            return 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (ImageView) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

    }

    int counter = 0;

    public void changeViewPagerToPage() {
        counter = counter % mPagerAdapter.getCount();
        mAdsSlider.setCurrentItem(counter);
        counter++;
        final Message message = mPagingHandler.obtainMessage(1);
        mPagingHandler.removeMessages(1);
        mPagingHandler.sendMessageDelayed(message, 2000);
    }

    private PagingHandler mPagingHandler;

    private static final class PagingHandler extends Handler {

        private final WeakReference<ShowRoomFragment> mParent;

        public PagingHandler(ShowRoomFragment parent) {
            this.mParent = new WeakReference<ShowRoomFragment>(parent);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mParent.get().changeViewPagerToPage();
                    break;

                default:
                    break;
            }
        }
    }

    private Handler mHandler = new Handler();

    private void updateInfo(final int position) {
        final NNBook item = mTopAdapter.getItem(position);
        if (item == null)
            return;

        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mBookInfo.setText(item.getTitle() + "");

            }
        }, 250);
    }

    @Override
    public void onItemSelected(android.widget.AdapterView<?> parent, View view,
                               int position, long id) {
        updateInfo(position);
    }

    @Override
    public void onNothingSelected(android.widget.AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(android.widget.AdapterView<?> parent, View view,
                            int position, long id) {
        NNBook item = mTopAdapter.getItem(position);
        if (item != null) {
            Toast.makeText(mContext, item.getTitle() + " | " + item.getUrl(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}