package com.android.umbrella;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] time;
    private final int[] Imageid;
    private final String[] temp;

    public GridViewAdapter(Context c,String[] time,int[] Imageid, String[] temp ) {
        mContext = c;
        this.Imageid = Imageid;
        this.time = time;
        this.temp = temp;
    }

    @Override
    public int getCount() {
        return time.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridviewcell, null);
            TextView textView = (TextView) grid.findViewById(R.id.time);
            ImageView imageView = (ImageView)grid.findViewById(R.id.image_weather);
            TextView textView1 = (TextView) grid.findViewById(R.id.temperature);
            textView.setText(time[position]);
            imageView.setImageResource(Imageid[position]);
            textView1.setText(temp[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }

}
