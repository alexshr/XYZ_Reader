package com.alexshr.xyz.ui;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alexshr.xyz.R;
import com.alexshr.xyz.binding.DataBoundListAdapter;
import com.alexshr.xyz.binding.ImageBindingComponent;
import com.alexshr.xyz.data.Article;
import com.alexshr.xyz.databinding.ListItemBinding;
import com.alexshr.xyz.util.PaletteTask;
import com.alexshr.xyz.util.SimpleConsumer;

import java.util.Objects;

public class ArticlesAdapter extends DataBoundListAdapter<Article, ListItemBinding> {
    private final SimpleConsumer<Integer> clickCallback;

    private ListItemBinding binding;
    private ImageBindingComponent bindingComponent;

    public ArticlesAdapter(
            SimpleConsumer<Integer> clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    protected ListItemBinding createBinding(ViewGroup parent) {

        bindingComponent = new ImageBindingComponent();

        binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item,
                        parent, false, bindingComponent);
        return binding;
    }

    @Override
    protected void bind(ListItemBinding binding, Article item, int pos) {
        binding.setArticle(item);
        binding.setClickCallback(clickCallback);
        binding.setPos(pos);

        bindingComponent.getImageBindingAdapter().setPaletteConsumer(palette -> {
            new PaletteTask.Builder().swatchTarget(android.support.v7.graphics.Target.MUTED)
                    .addRgbBackground(binding.getRoot())
                    .addBodyText(binding.titleTv)
                    .addTitleText(binding.authorTv)
                    .build()
                    .apply(palette);
        });
    }

    @Override
    protected boolean areItemsTheSame(Article oldItem, Article newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(Article oldItem, Article newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle());
    }
}
