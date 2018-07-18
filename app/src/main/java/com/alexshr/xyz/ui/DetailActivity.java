package com.alexshr.xyz.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.alexshr.xyz.AppConfig;
import com.alexshr.xyz.R;
import com.alexshr.xyz.databinding.ActivityDetailBinding;
import com.alexshr.xyz.di.Injectable;
import com.alexshr.xyz.util.SmartFragmentStatePagerAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import static com.alexshr.xyz.AppConstants.ARTICLE_POS_KEY;

public class DetailActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    DetailActivityViewModel viewModel;

    private int currentItem;

    @Override
    protected void onCreate(Bundle inState) {
        super.onCreate(inState);

        currentItem = inState != null ?
                inState.getInt(ARTICLE_POS_KEY) :
                getIntent().getIntExtra(ARTICLE_POS_KEY, 0);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel.class);

        viewModel.getArticlesCount().observe(this, count -> {

            binding.viewPager.setOffscreenPageLimit(AppConfig.OFFSET_PAGE_LIMIT);

            FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
            binding.viewPager.setAdapter(adapter);

            binding.viewPager.setCurrentItem(currentItem);

            binding.viewPager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    currentItem = position;

                    adapter.getRegisteredFragment(position).applyStatusBarAndIconColor();
                    adapter.getRegisteredFragment(position).applyFabColor();
                }
            });
            viewModel.getArticlesCount().removeObservers(this);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARTICLE_POS_KEY, currentItem);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    class FragmentsAdapter extends SmartFragmentStatePagerAdapter<ArticleFragment> {

        public FragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public ArticleFragment getItem(int position) {
            return ArticleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return viewModel.getArticlesCount().getValue();
        }
    }
}
