package spoon.misterspoon;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class CarteActivityListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<CarteActivityHeader> carte;

	public CarteActivityListAdapter(Context context, ArrayList<CarteActivityHeader> carte) {
		this.context = context;
		this.carte = carte;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
		return mealList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

		CarteActivityChild meal = (CarteActivityChild) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.activity_carte_child_row, null);
		}

		//CheckBox chekbox = (CheckBox) view.findViewById(R.id.carte_child_check);
		TextView textView = (TextView) view.findViewById(R.id.carte_child_text);
		textView.setText(meal.getMealName());

		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
		return mealList.size();

	}

	@Override
	public Object getGroup(int groupPosition) {
		return carte.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return carte.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

		CarteActivityHeader menu = (CarteActivityHeader) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.activity_carte_group_heading, null);
		}

		TextView menuName = (TextView) view.findViewById(R.id.carte_heading);
		menuName.setText(menu.getMenuName());
		menuName.setGravity(Gravity.CENTER);
		menuName.setTypeface(null, Typeface.BOLD);

		return view;
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
