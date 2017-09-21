package com.android.umbrella.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.umbrella.R;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] time;
    private final int[] Imageid;
    private final int[] temp;
    private final int cold;
    private final int hot;

    public GridViewAdapter(Context c,String[] time,int[] Imageid, int[] temp,int poshot, int poscold ) {
        mContext = c;
        this.Imageid = Imageid;
        this.time = time;
        this.temp = temp;
        this.hot=poshot;
        this.cold=poscold;
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

            int coldColor = mContext.getResources().getColor(R.color.weather_cool);
            int hotColor = mContext.getResources().getColor(R.color.weather_warm);


            grid = new View(mContext);
            grid = inflater.inflate(R.layout.gridviewcell, null);
            TextView textView = (TextView) grid.findViewById(R.id.time);
            ImageView imageView = (ImageView)grid.findViewById(R.id.image_weather);
            TextView textView1 = (TextView) grid.findViewById(R.id.temperature);
            textView.setText(time[position]);
            imageView.setImageResource(Imageid[position]);
            if(cold==position) {
                textView.setTextColor(coldColor);
                textView1.setTextColor(coldColor);
                imageView.setColorFilter(coldColor, PorterDuff.Mode.SRC_IN);
            }else if(hot==position){
                textView.setTextColor(hotColor);
                textView1.setTextColor(hotColor);
                imageView.setColorFilter(hotColor, PorterDuff.Mode.SRC_IN);
            }
            if(temp[position]!=0) {
                textView1.setText(String.valueOf(temp[position]));
            }
            } else {
            grid = (View) convertView;
        }

        return grid;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}