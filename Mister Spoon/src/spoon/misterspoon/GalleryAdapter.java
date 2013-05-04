package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends ArrayAdapter<Integer> {
	
	Context context;
	int layoutResourceId;
	Integer data[] = null;
	
	public GalleryAdapter(Context context, int layoutResourceId, Integer[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GalleryHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
           
            holder = new GalleryHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.gallery_restaurant_item);
           
            row.setTag(holder);
        }
        else
        {
            holder = (GalleryHolder)row.getTag();
        }
       
        holder.imgIcon.setImageResource(data[position]);
       
        return row;
    }
   
    static class GalleryHolder
    {
        ImageView imgIcon;
    }
}

