package com.alexshr.xyz.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.alexshr.xyz.ui.DetailActivityViewModel;
import com.alexshr.xyz.ui.ArticleFragmentViewModel;
import com.alexshr.xyz.ui.ListActivityViewModel;
import com.alexshr.xyz.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

//creating Map<Class<? extends ViewModel>, Provider<ViewModel>> (multibinding)
//to insert to ViewModelFactory as constructor parameter (to single factory for many ViewModels)
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListActivityViewModel.class)
    abstract ViewModel bindMainViewModel(ListActivityViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailActivityViewModel.class)
    abstract ViewModel bindDetailActivityViewModel(DetailActivityViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ArticleFragmentViewModel.class)
    abstract ViewModel bindArticleFragmentViewModel(ArticleFragmentViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
