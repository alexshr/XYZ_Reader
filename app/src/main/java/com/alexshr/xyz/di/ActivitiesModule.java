package com.alexshr.xyz.di;

import com.alexshr.xyz.ui.DetailActivity;
import com.alexshr.xyz.ui.ListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract ListActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentsBuilderModule.class)
    abstract DetailActivity contributeDetailActivity();
}
