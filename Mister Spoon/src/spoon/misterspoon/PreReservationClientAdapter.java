package spoon.misterspoon;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PreReservationClientAdapter extends ArrayAdapter<PreReservationClientItem> {

	Context context; 
    int layoutResourceId;   
    List<PreReservationClientItem> data = null;
    
    public PreReservationClientAdapter (Context context, int layoutResourceId, List<PreReservationClientItem> data) {
    	super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PreReservationClientItemHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new PreReservationClientItemHolder();
            holder.txtRestaurant = (TextView)row.findViewById(R.id.prereservation_client_item_restaurant);
            holder.txtPrix = (TextView)row.findViewById(R.id.prereservation_client_item_prix);
            holder.txtPlat = (TextView)row.findViewById(R.id.prereservation_client_item_plat);
            
            row.setTag(holder);
        }
        else
        {
            holder = (PreReservationClientItemHolder)row.getTag();
        }
        
        PreReservationClientItem preReservationClientItem = data.get(position);
        holder.txtRestaurant.setText(preReservationClientItem.restaurant);
        holder.txtPrix.setText(preReservationClientItem.prix);
        holder.txtPlat.setText(preReservationClientItem.plat);
        
        return row;
    }
    
    static class PreReservationClientItemHolder
    {
        TextView txtRestaurant;
        TextView txtPrix;
        TextView txtPlat;
    }
}
