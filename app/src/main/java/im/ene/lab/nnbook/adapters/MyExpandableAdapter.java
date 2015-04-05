package im.ene.lab.nnbook.adapters;

import im.ene.lab.nnbook.views.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableAdapter extends AnimatedExpandableListAdapter {

	private Context mContext;
	private List<Object> childtems;
	private List<String> parentItems, child;

	public MyExpandableAdapter(Context context, List<String> parents,
			List<Object> childern) {
		this.mContext = context;
		this.parentItems = parents;
		this.childtems = childern;
	}

	@Override
	public View getRealChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<String>) childtems.get(groupPosition);

		TextView textView = (TextView) convertView;

		if (textView == null) {
			textView = (TextView) LayoutInflater.from(mContext).inflate(
					android.R.layout.simple_list_item_1, null);
		}

		textView.setText(child.get(childPosition));

		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(mContext, child.get(childPosition),
						Toast.LENGTH_SHORT).show();
			}
		});

		return textView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		CheckedTextView cTextView = (CheckedTextView) convertView;

		if (cTextView == null) {
			cTextView = (CheckedTextView) LayoutInflater.from(mContext)
					.inflate(android.R.layout.simple_list_item_checked, null);
		}

		cTextView.setText(parentItems.get(groupPosition));
		cTextView.setChecked(isExpanded);

		return cTextView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getRealChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childtems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
