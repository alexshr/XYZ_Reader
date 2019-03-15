package com.alexshr.xyz.binding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.alexshr.xyz.GlideApp;
import com.alexshr.xyz.R;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.reactivex.functions.Consumer;

public class ImageBindingAdapter {

    private Consumer<Palette> paletteConsumer;

    public ImageBindingAdapter(Consumer<Palette> consumer) {
        paletteConsumer = consumer;
    }

    public ImageBindingAdapter() {
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {

        GlideApp.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .placeholder(R.drawable.loading)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (bitmap != null && paletteConsumer != null) {
                            Palette palette = Palette.from(bitmap).generate();
                            try {
                                paletteConsumer.accept(palette);
                            } catch (Exception ignored) {
                            }
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public void setPaletteConsumer(Consumer<Palette> consumer) {
        paletteConsumer = consumer;
    }
}

