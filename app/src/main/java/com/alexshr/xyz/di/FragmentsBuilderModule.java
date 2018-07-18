package com.alexshr.xyz.di;

import com.alexshr.xyz.ui.ArticleFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentsBuilderModule {

    @ContributesAndroidInjector
    abstract ArticleFragment contributeArticleFragment();
}
