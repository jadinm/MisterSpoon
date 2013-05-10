package spoon.misterspoon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class reservationClientActivityAdapter extends BaseAdapter {

	private ArrayList <reservationClientActivityItem> commande;
	private Context context;

	public reservationClientActivityAdapter(Context context, ArrayList <reservationClientActivityItem> commande) {

		this.commande=commande;
		this.context = context;
	}

	@Override
	public int getCount() {
		return commande.size();
	}

	@Override
	public Object getItem(int position) {
		return commande.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		if (convertView == null) {

			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.activity_reservation_client_list_element, null);
		}

		TextView tv = (TextView) convertView.findViewById(R.id.reservation_client_text);

		TextView quantite = (TextView) convertView.findViewById(R.id.reservation_client_stock);

		tv.setText(commande.get(position).getMealName());
		quantite.setText(commande.get(position).getMealQuantity());

		return convertView;
	}

}
