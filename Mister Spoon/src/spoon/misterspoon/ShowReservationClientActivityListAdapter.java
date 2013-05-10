
package spoon.misterspoon;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ShowReservationClientActivityListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<ShowReservationClientActivityHeader> reservation;
	
	public ShowReservationClientActivityListAdapter(Context context, ArrayList<ShowReservationClientActivityHeader> reservation){
		this.context = context;
		this.reservation = reservation;
	}
	
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<ShowReservationClientItem> bookList = reservation.get(groupPosition).getReservationList();
		return bookList.get(childPosition);
	}
	
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

		ShowReservationClientItem book = (ShowReservationClientItem) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.reservation_client_list_item_view, null);
		}

		TextView txtHeure = (TextView) view.findViewById(R.id.reservation_client_item_heure);
		TextView txtNom = (TextView) view.findViewById(R.id.reservation_client_item_nom);
		TextView txtTelephone = (TextView) view.findViewById(R.id.reservation_client_item_telephone);
		TextView txtPlaces = (TextView) view.findViewById(R.id.reservation_client_item_places);
		Spinner spinnerPlat = (Spinner) view.findViewById(R.id.reservation_client_item_quantite);
		
		txtHeure.setText(book.getHeure());
		txtNom.setText(book.getNomRest());
		txtTelephone.setText(book.getTelephone());
		txtPlaces.setText(book.getPlaces());
		ArrayAdapter<String> arrayAdapterQuantites = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,book.getPlat());
        arrayAdapterQuantites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlat.setAdapter(arrayAdapterQuantites);

		return view;
	}
	
	public int getChildrenCount(int groupPosition) {

		ArrayList<ShowReservationClientItem> bookList = reservation.get(groupPosition).getReservationList();
		return bookList.size();

	}
	
	public Object getGroup(int groupPosition) {
		return reservation.get(groupPosition);
	}
	
	public int getGroupCount() {
		return reservation.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	
	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

		ShowReservationClientActivityHeader dateHeader = (ShowReservationClientActivityHeader) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.activity_reservation_client_group_heading, null);
		}

		TextView dateString = (TextView) view.findViewById(R.id.reservation_client_group_heading);
		dateString.setText(dateHeader.getDate());

		return view;
	}
	
	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
