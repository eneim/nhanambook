package im.ene.lab.nnbook.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import im.ene.lab.nnbook.R;
import im.ene.lab.nnbook.models.NNCategory;

public class CategoryFragment extends ListFragment {

    public static CategoryFragment newInstance(String name) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(BasedFragment.ARC_TAB_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    final Gson gson = new Gson();

    private List<NNCategory> mCategories = new ArrayList<NNCategory>(),
            mCatHolder;

//    private NNCategoryAdapter mListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories,
                container, false);

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mListAdapter = new NNCategoryAdapter(getActivity(),
//                android.R.layout.simple_list_item_1, mCategories);
//        setListAdapter(mListAdapter);
//
//        String categories = null;
//
//        try {
//            categories = ENEUtils.getStringFromFile(ENEUtils.getDataDir(
//                    getActivity()).toString()
//                    + "/" + NNConstant.FILE_CATEGORY);
//
//        } catch (Exception e) {
//            categories = null;
//            e.printStackTrace();
//        }
//
//        if (categories != null) {
//            mListAdapter.clear();
//            List<NNCategory> cats = gson.fromJson(categories,
//                    new TypeToken<List<NNCategory>>() {
//                    }.getType());
//
//            for (NNCategory cat : cats) {
//                mCategories.add(cat);
//                mListAdapter.notifyDataSetChanged();
//            }
//        } else
//            new NNBookCategoryLoader(new OnCategoriesLoadedCallback() {
//
//                @Override
//                public void onCategoriesLoaded(List<NNCategory> categories) {
//                    mCatHolder = categories;
//                }
//
//                @Override
//                public void onCategoryItemLoaded(NNCategory category) {
//                    // mListAdapter.addItem(category);
//
//                    mCategories.add(category);
//                    mListAdapter.notifyDataSetChanged();
//
//                    if (mCatHolder != null
//                            && mCategories.size() == mCatHolder.size()) {
//                        ENEUtils.saveObjectToFile(getActivity(), mCategories,
//                                NNConstant.FILE_CATEGORY);
//                    }
//                }
//            }).execute();
    }
}
