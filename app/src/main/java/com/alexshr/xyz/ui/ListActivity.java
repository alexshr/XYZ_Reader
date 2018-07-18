package com.alexshr.xyz.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alexshr.xyz.AppConstants;
import com.alexshr.xyz.R;
import com.alexshr.xyz.api.util.ConnectionChecker;
import com.alexshr.xyz.databinding.ActivityListBinding;
import com.alexshr.xyz.di.Injectable;

import javax.inject.Inject;

import timber.log.Timber;

public class ListActivity extends AppCompatActivity implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ConnectionChecker connectionChecker;

    ListActivityViewModel viewModel;
    SwipeRefreshLayout swipeRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        swipeRl = binding.swipeRl;

        ArticlesAdapter adapter = new ArticlesAdapter(this::openDetail);

        binding.articlesRv.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListActivityViewModel.class);

        viewModel.getArticles().observe(this, adapter::replace);

        viewModel.getProgressData().observe(this, this::refreshProgress);

        viewModel.getErrorData().observe(this, this::showError);

        viewModel.getNewData().observe(this, isNew -> {
            if (isNew) {
                showMessage(R.string.new_data);
            }
        });

        swipeRl.setOnRefreshListener(viewModel::requestApi);

        if (savedInstanceState == null && connectionChecker.isConnected()) viewModel.requestApi();

        if (!connectionChecker.isConnected()) showMessage(getString(R.string.no_internet));
    }

    private void openDetail(int pos) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(AppConstants.ARTICLE_POS_KEY, pos);
        startActivity(intent);
    }

    private void refreshProgress(boolean isProgress) {
        if (isProgress && !swipeRl.isRefreshing()) {
            swipeRl.post(() -> swipeRl.setRefreshing(true));
        } else if (!isProgress && swipeRl.isRefreshing()) {
            swipeRl.setRefreshing(false);
        }
    }

    public void showError(Throwable error) {
        Timber.e(error);
        if (connectionChecker.isConnected()) showMessage(error.getMessage());
    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View mView = snackbar.getView();

        TextView mTextView = mView.findViewById(android.support.design.R.id.snackbar_text);
        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        snackbar.show();
    }

    public void showMessage(int res) {
        showMessage(getString(res));
    }
}
