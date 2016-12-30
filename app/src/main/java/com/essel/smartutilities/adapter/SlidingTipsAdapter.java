package com.essel.smartutilities.adapter;

/**
 * Created by hp on 11/25/2016.
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingTipsAdapter extends PagerAdapter {

    private ArrayList<String> IMAGES;
    private ArrayList<String> tiptextdata;
    private LayoutInflater inflater;
    private Context context;


    public SlidingTipsAdapter(Context context, ArrayList<String> IMAGES, ArrayList<String> textdata) {
        this.context = context;
        this.tiptextdata=textdata;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }
//    public SlidingTipsAdapter(Context context, ArrayList<String> IMAGES) {
//        this.context = context;
//       // this.tiptextdata=textdata;
//        this.IMAGES=IMAGES;
//        inflater = LayoutInflater.from(context);
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingtips_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        final TextView textView = (TextView) imageLayout
               .findViewById(R.id.tiptextarea);

      //  imageView.setImageResource(Integer.parseInt(IMAGES.get(position)));

       // for(int i=0;i<tiptextdata.size();i++) {
            textView.setText(tiptextdata.get(position).trim());
       // }


         view.addView(imageLayout, 0);

       // for(int i=0;i<IMAGES.size();i++) {
            Picasso.with(context)
                    .load(IMAGES.get(position))

                    .into(imageView);
      //  }
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}