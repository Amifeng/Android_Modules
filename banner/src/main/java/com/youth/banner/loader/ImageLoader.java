package com.youth.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.R;


public abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(R.drawable.ic_stub_ban);
        return imageView;
    }

}
