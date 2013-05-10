//package spoon.misterspoon;
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//
//public class CarteBuilderActivityListAdapter extends BaseExpandableListAdapter {
//
//	private Context context;
//	private ArrayList<CarteActivityHeader> carte;
//
//	public CarteBuilderActivityListAdapter(Context context, ArrayList<CarteActivityHeader> carte) {
//		this.context = context;
//		this.carte = carte;
//	}
//
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
//		return mealList.get(childPosition);
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		return childPosition;
//	}
//
//	@Override
//	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
//
//		CarteActivityChild meal = (CarteActivityChild) getChild(groupPosition, childPosition);
//		if (view == null) {
//			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = infalInflater.inflate(R.layout.activity_carte_child_row, null);
//			
//			isCheckedList.add(false);
//
//			int count = 0;
//			for (int i=0; i<groupPosition-1; i++) {
//				count = count + getChildrenCount(i);
//			}
//			count = count + childPosition;
//			
//			CheckBox checkbox = (CheckBox) view.findViewById(R.id.carte_child_check);
//			checkbox.setTag(Integer.valueOf(count));
//			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener () {
//
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
//					
//					final Integer position = (Integer) buttonView.getTag();
//					
//		            isCheckedList.set(position, isChecked);
//					
//				}
//				
//			});
//			
//			TextView textView = (TextView) view.findViewById(R.id.carte_child_text);
//			textView.setText(meal.getMealName());
//			
//			TextView price = (TextView) view.findViewById(R.id.carte_child_price);
//			price.setText(meal.getMealPrice() + " €");
//		}
//
//		return view;
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//
//		ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
//		return mealList.size();
//
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		return carte.get(groupPosition);
//	}
//
//	@Override
//	public int getGroupCount() {
//		return carte.size();
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		return groupPosition;
//	}
//
//	@Override
//	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
//
//		CarteActivityHeader menu = (CarteActivityHeader) getGroup(groupPosition);
//		if (view == null) {
//			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = inf.inflate(R.layout.activity_carte_builder_group_heading, null);
//			
//			isCheckedMenuList.add(false);
//			
//			TextView menuName = (TextView) view.findViewById(R.id.carte_heading);
//			menuName.setText(menu.getMenuName());
//			menuName.setGravity(Gravity.CENTER);
//			menuName.setTypeface(null, Typeface.BOLD);
//			
//			CheckBox check = (CheckBox) view.findViewById(R.id.carte_heading_check);
//			check.setTag(Integer.valueOf(groupPosition));
//			check.setOnCheckedChangeListener(new OnCheckedChangeListener () {
//
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView,	boolean isChecked) {
//					
//					final Integer position = (Integer) buttonView.getTag();
//					
//		            isCheckedMenuList.set(position, isChecked);
//					
//				}
//				
//			});
//			
//			if (groupPosition==0) {//We don't show the first divider
//				ImageView image = (ImageView) view.findViewById(R.id.carte_divider);
//				image.setVisibility(View.GONE);
//			}
//		}
//
//		return view;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		return true;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		return true;
//	}
//
//}
