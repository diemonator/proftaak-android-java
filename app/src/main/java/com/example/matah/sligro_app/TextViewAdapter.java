package com.example.matah.sligro_app;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by Emil Karamihov on 12-Dec-17.
 */

public class TextViewAdapter extends BaseAdapter {
    private Context mContext;
    Typeface iconFont;

    public TextViewAdapter(Context c) {
        mContext = c;
        iconFont = FontManager.getTypeface(mContext, FontManager.FONTAWESOME);
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new TextView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (TextView) convertView;
        }
        FontManager.markAsIconContainer(imageView, iconFont);
        imageView.setTextSize(mContext.getResources().getDimension(R.dimen.fab_margin));
        imageView.setText(mContext.getResources().getString(mThumbIds[position]) + "This is the text for each image");
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.string.fa_icon_areachart,
            R.string.fa_icon_piechart,R.string.fa_icon_check_sign,R.string.fa_icon_search,R.string.fa_icon_sign_in,
            R.string.fa_icon_github_sign,R.string.fa_icon_slash,R.string.fa_icon_star,R.string.fa_icon_star_empty,

    };
}
