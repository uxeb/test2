package com.test.test.presentation.workersList;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.test.test.R;
import com.test.test.databinding.ActivityWorkersBinding;
import com.test.test.di.component.ActivityComponent;
import com.test.test.di.component.DaggerActivityComponent;
import com.test.test.domain.worker.Worker;
import com.test.test.presentation.BaseActivity;
import com.test.test.presentation.workerDetails.WorkerDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WorkersListActivity extends BaseActivity implements WorkersListView {
    private static final String EXTRA_SPECIALTY_ID = "extra_specialty_id";


    @InjectPresenter
    WorkersListPresenter presenter;

    @Inject
    WorkersListAdapter adapter;

    private ActivityWorkersBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static Intent getCallingIntent(Context context, int specialtyId) {
        Intent intent = new Intent(context, WorkersListActivity.class);
        intent.putExtra(EXTRA_SPECIALTY_ID, specialtyId);
        return intent;
    }

    private int getSpecialtyId() {
        return getIntent().getIntExtra(EXTRA_SPECIALTY_ID, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getIntent().hasExtra(EXTRA_SPECIALTY_ID)) {
            throw new IllegalStateException();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workers);

        setupRecyclerView();
        setupActionBar();

        if (!presenter.isInRestoreState(this)) {
            presenter.loadWorkers(getSpecialtyId());
        }
    }

    private void setupRecyclerView() {
        binding.workersRV.setLayoutManager(new LinearLayoutManager(this));
        binding.workersRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.workersRV.setAdapter(adapter);
        Disposable d = adapter.getItemClicks().subscribe(new ItemClicksConsumer());
        compositeDisposable.add(d);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.workers_screen_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showWorkers(List<Worker> workers) {
        adapter.setWorkers(workers);
    }


    @Override
    public void showEmpty() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.workersRV.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    protected void injectDependencies() {
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
        component.inject(this);
    }

    private class ItemClicksConsumer implements Consumer<Integer> {
        @Override
        public void accept(@NonNull Integer itemId) throws Exception {
            presenter.onWorkerSelected(itemId);
        }
    }
}
