package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ReservationRestaurantAdapter extends ArrayAdapter<ReservationRestaurantItem> {

	Context context; 
    int layoutResourceId;   
    ReservationRestaurantItem data[] = null;
    
    public ReservationRestaurantAdapter(Context context, int layoutResourceId, ReservationRestaurantItem data[]) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ReservationRestaurantItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ReservationRestaurantItemHolder();
            holder.txtHeure = (TextView)row.findViewById(R.id.reservation_restaurant_item_heure);
            holder.txtNom = (TextView)row.findViewById(R.id.reservation_restaurant_item_nom);
            holder.txtTelephone = (TextView)row.findViewById(R.id.reservation_restaurant_item_telephone);
            holder.txtPlaces = (TextView)row.findViewById(R.id.reservation_restaurant_item_places);
            holder.spinnerPlat = (Spinner)row.findViewById(R.id.reservation_restaurant_item_quantite);
            
            
            row.setTag(holder);
        }
        else
        {
            holder = (ReservationRestaurantItemHolder)row.getTag();
        }
        
        ReservationRestaurantItem reservationRestaurantItem = data[position];
        holder.txtHeure.setText(reservationRestaurantItem.heure);
        holder.txtNom.setText(reservationRestaurantItem.nom);
        holder.txtTelephone.setText(reservationRestaurantItem.telephone);
        holder.txtPlaces.setText(reservationRestaurantItem.places);
        ArrayAdapter<String> arrayAdapterQuantites = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,reservationRestaurantItem.plat);
        arrayAdapterQuantites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerPlat.setAdapter(arrayAdapterQuantites);
        
        return row;
    }
    
    static class ReservationRestaurantItemHolder
    {
        TextView txtHeure;
        TextView txtNom;
        TextView txtTelephone;
        TextView txtPlaces;
        Spinner spinnerPlat;
    }
}
