package com.alexshr.xyz.binding;

import android.databinding.DataBindingComponent;
import android.support.v7.graphics.Palette;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class ImageBindingComponent implements DataBindingComponent {
    private final ImageBindingAdapter adapter;

    public ImageBindingComponent() {
        adapter = new ImageBindingAdapter();
        Timber.d("created %s", this);
    }

    public ImageBindingComponent(Consumer<Palette> consumer) {
        adapter = new ImageBindingAdapter(consumer);
        Timber.d("created %s", this);
    }

    @Override
    public ImageBindingAdapter getImageBindingAdapter() {
        return adapter;
    }
}
