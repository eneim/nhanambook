package im.ene.lab.nnbook.fragments;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.adapters.ENESmartFragmentStatePagerAdapter;
import im.ene.lab.nnbook.libs.pagerslidingtabstrip.PagerSlidingTabStrip;
import im.ene.lab.nnbook.libs.pagerslidingtabstrip.PagerSlidingTabStrip.IconTabProvider;
import im.ene.lab.nnbook.views.StopableViewPager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NNMainFragment extends Fragment {

	private Context mContext;

	private PagerSlidingTabStrip mPagerTabs;
	private StopableViewPager mViewPager;
	// private static List<Fragment> mFragmentList;
	private ENESmartFragmentStatePagerAdapter mPagerAdapter;

	public static NNMainFragment getFragment() {
		NNMainFragment fragment = new NNMainFragment();
		return fragment;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// mFragmentList = new ArrayList<Fragment>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mViewPager = (StopableViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setPagingEnabled(false);

		mPagerTabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);

		return rootView;

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		mPagerTabs.setOnPageChangeListener(mPageChangeListener);

		// mFragmentList.add(new FacebookLoginFragment());
		// mFragmentList.add(ShowRoomFragment.newInstance("Home"));
		// mFragmentList.add(new FacebookLoginFragment());

		// mPagerAdapter.notifyDataSetChanged();

		mPagerAdapter = new CSNMainPageFragmentStatePageAdapter(
				getChildFragmentManager());

		mViewPager.setAdapter(mPagerAdapter);
		mPagerTabs.setViewPager(mViewPager);

		mViewPager.setCurrentItem(1);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				break;
			default:
				break;
			}
		}

	};

	public class CSNMainPageFragmentStatePageAdapter extends
			ENESmartFragmentStatePagerAdapter implements IconTabProvider {

		private FragmentManager mFragmentManager;

		// private List<Fragment> fragmentList;

		public CSNMainPageFragmentStatePageAdapter(FragmentManager fm) {
			super(fm);
			this.mFragmentManager = fm;
			// this.fragmentList = list;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			if (position == 0) {
				fragment = CategoryFragment.newInstance("Categories");
			} else if (position == 1) {
				fragment = ShowRoomFragment.newInstance("Home");
			}

			return fragment;

		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) {
				return "Categories";
			} else if (position == 1) {
				return "Home";
			} else if (position == 2) {
				return "Profile";
			} else
				return super.getPageTitle(position);
		}

		@Override
		public int getPageIconResId(int position) {
			// if (position == 0)
			// return R.drawable.temp_top_icon;
			return 0;
		}

	}
}
