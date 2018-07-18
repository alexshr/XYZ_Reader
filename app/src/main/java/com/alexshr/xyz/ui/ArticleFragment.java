package com.alexshr.xyz.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Target;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexshr.xyz.R;
import com.alexshr.xyz.binding.ImageBindingAdapter;
import com.alexshr.xyz.binding.ImageBindingComponent;
import com.alexshr.xyz.databinding.FragmentDetailBinding;
import com.alexshr.xyz.di.Injectable;
import com.alexshr.xyz.util.HeaderBehavior;
import com.alexshr.xyz.util.PaletteTask;

import javax.inject.Inject;

import timber.log.Timber;

import static com.alexshr.xyz.AppConfig.TITLE_BACKGROUND_TRANSPARENCY;
import static com.alexshr.xyz.AppConstants.ARTICLE_POS_KEY;

public class ArticleFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ArticleFragmentViewModel viewModel;

    FragmentDetailBinding binding;
    private HeaderBehavior behavior;

    private Palette palette;
    private Drawable homeAsUpDrawable;

    public ArticleFragment() {
        Timber.d("DetailFragment created");
    }

    public static ArticleFragment newInstance(int position) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARTICLE_POS_KEY, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUserVisibleHint(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ImageBindingComponent bindingComponent = new ImageBindingComponent(this::onPaletteCreated);
        ImageBindingAdapter bindingAdapter = bindingComponent.getImageBindingAdapter();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false, bindingComponent);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        homeAsUpDrawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);

        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.widgetToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(homeAsUpDrawable);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.setFabListener(this::share);

        binding.widgetToolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());

        int articlePos = getArguments().getInt(ARTICLE_POS_KEY);

        ArticleFragmentViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(articlePos + "", ArticleFragmentViewModel.class);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) binding.floatToolbar.getLayoutParams();
        behavior = (HeaderBehavior) params.getBehavior();

        viewModel.getArticle(getArguments().getInt(ARTICLE_POS_KEY)).observe(this, article -> {
            if (article != null) {
                binding.setArticle(article);
                new Handler().postDelayed(this::callBehavior, 0);
            }
        });
    }

    private void share() {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(String.format("%s (%s)", binding.pinTitle.getText(), binding.pinSubtitle.getText()))
                .getIntent(), getString(R.string.action_share)));
    }

    public void callBehavior() {
        behavior.onDependentViewChanged(binding.coordinatorLayout, binding.floatToolbar, binding.appBarLayout);
    }

    private boolean isFragmentVisible() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        int[] loc = new int[2];
        binding.getRoot().getLocationOnScreen(loc);

        //boolean res = loc[0] < displayMetrics.widthPixels;
        boolean res = loc[0] == 0;

        Timber.d("screen width=%d, root view left=%d, res=%s", displayMetrics.widthPixels, loc[0], res);

        return res;
    }

    public void onPaletteCreated(Palette palette) {
        this.palette = palette;
        applyPalette();
        if (isFragmentVisible()) {
            applyStatusBarAndIconColor();
            applyFabColor();
        }
    }

    private void applyPalette() {
        new PaletteTask.Builder().swatchTarget(Target.MUTED)
                .addCollapsingToolbarLayout(binding.collapsingToolbar)
                .addRgbBackgroundWithTransparency(TITLE_BACKGROUND_TRANSPARENCY, binding.floatTitle, binding.floatSubtitle)
                .addBodyText(binding.pinTitle, binding.floatTitle)
                .addTitleText(binding.pinSubtitle, binding.floatSubtitle)

                .build()
                .apply(palette);

        new PaletteTask.Builder().swatchTarget(Target.VIBRANT)
                .addFAB(binding.shareFab, getContext().getResources().getDrawable(R.drawable.ic_share_black_24dp))
                .build()
                .apply(palette);
    }

    public void applyStatusBarAndIconColor() {
        new PaletteTask.Builder().swatchTarget(Target.MUTED)
                .addStatusBar(getActivity().getWindow())
                .addIcons(homeAsUpDrawable)
                .build()
                .apply(palette);
    }

    public void applyFabColor() {
        new PaletteTask.Builder().swatchTarget(Target.VIBRANT)
                .addFAB(binding.shareFab, getContext().getResources().getDrawable(R.drawable.ic_share_black_24dp))
                .build()
                .apply(palette);
        Timber.d("position=%d", getArguments().getInt(ARTICLE_POS_KEY));
    }
}