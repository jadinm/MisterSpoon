package spoon.misterspoon;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class PreReservationRestaurantAdapter extends ArrayAdapter<PreReservationRestaurantItem> {

	Context context; 
    int layoutResourceId;   
    List<PreReservationRestaurantItem> data = null;
    
    public PreReservationRestaurantAdapter(Context context, int layoutResourceId, List<PreReservationRestaurantItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PreReservationRestaurantItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new PreReservationRestaurantItemHolder();
            holder.txtNom = (TextView)row.findViewById(R.id.prereservation_restaurant_item_nom);
            holder.txtEmail = (TextView)row.findViewById(R.id.prereservation_restaurant_item_email);
            holder.txtTelephone = (TextView)row.findViewById(R.id.prereservation_restaurant_item_telephone);
            holder.spinnerPlat = (Spinner)row.findViewById(R.id.prereservation_restaurant_item_quantite);
            
            
            row.setTag(holder);
        }
        else
        {
            holder = (PreReservationRestaurantItemHolder)row.getTag();
        }
        
        PreReservationRestaurantItem preReservationRestaurantItem = data.get(position);
        holder.txtNom.setText(preReservationRestaurantItem.nom);
        holder.txtEmail.setText(preReservationRestaurantItem.email);
        holder.txtTelephone.setText(preReservationRestaurantItem.telephone);
        ArrayAdapter<String> arrayAdapterQuantites = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,preReservationRestaurantItem.plat);
        arrayAdapterQuantites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerPlat.setAdapter(arrayAdapterQuantites);
        
        return row;
    }
    
    static class PreReservationRestaurantItemHolder
    {
        TextView txtNom;
        TextView txtEmail;
        TextView txtTelephone;
        Spinner spinnerPlat;
    }
}