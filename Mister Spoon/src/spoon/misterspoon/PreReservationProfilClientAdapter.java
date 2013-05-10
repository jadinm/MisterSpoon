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


public class PreReservationProfilClientAdapter extends ArrayAdapter<PreReservationProfilClientItem> {

	Context context; 
    int layoutResourceId;   
    List<PreReservationProfilClientItem> data = null;
    
    public PreReservationProfilClientAdapter(Context context, int layoutResourceId, List<PreReservationProfilClientItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PreReservationProfilClientItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new PreReservationProfilClientItemHolder();
            holder.txtRestaurant = (TextView)row.findViewById(R.id.prereservation_client_profil_item_restaurant);
            holder.spinnerPlat = (Spinner)row.findViewById(R.id.prereservation_client_profil_item_quantite);
            
            
            row.setTag(holder);
        }
        else
        {
            holder = (PreReservationProfilClientItemHolder)row.getTag();
        }
        
        PreReservationProfilClientItem preReservationProfilClientItem = data.get(position);
        holder.txtRestaurant.setText(preReservationProfilClientItem.restaurant);
        ArrayAdapter<String> arrayAdapterQuantites = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,preReservationProfilClientItem.plat);
        arrayAdapterQuantites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerPlat.setAdapter(arrayAdapterQuantites);
        
        return row;
    }
    
    static class PreReservationProfilClientItemHolder
    {
        TextView txtRestaurant;
        Spinner spinnerPlat;
    }
}
