package de.maximal.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.maximal.effectcamera.R;

/**
 * Created by Max Kapsecker on 15.09.2015.
 */


public class NavDrawerItem extends BaseAdapter {

    private Context context;
    private String[] mTitle;
    private String[] mSubTitle;
    private int[] mIcon;
    private LayoutInflater inflater;

    public NavDrawerItem(Context pContext, String[] pTitle, int[] pIcon) {
        context = pContext;
        mTitle = pTitle;
        mIcon = pIcon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.navigation_drawer, parent, false);

        TextView txtTitle = (TextView) itemView.findViewById(R.id.title);
        ImageView imgIcon = (ImageView) itemView.findViewById(R.id.icon);

        txtTitle.setText(mTitle[position]);
        imgIcon.setImageResource(mIcon[position]);

        return itemView;
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return mTitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
