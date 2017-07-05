package com.test.test.presentation.specialty;

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
import com.test.test.databinding.ActivitySpecialtiesBinding;
import com.test.test.di.component.ActivityComponent;
import com.test.test.di.component.DaggerActivityComponent;
import com.test.test.domain.worker.Specialty;
import com.test.test.presentation.BaseActivity;
import com.test.test.presentation.workersList.WorkersListActivity;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class SpecialtiesListActivity extends BaseActivity implements SpecialtiesListView{

    private static final String TAG = "SplashActivity";

    private SpecialtiesListAdapter adapter = new SpecialtiesListAdapter();

    @InjectPresenter
    SpecialtiesListPresenter presenter;

    private ActivitySpecialtiesBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, SpecialtiesListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.specialties_screen_title);
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_specialties);
        binding.specialtiesRV.setLayoutManager(new LinearLayoutManager(this));
        binding.specialtiesRV.setAdapter(adapter);
        binding.specialtiesRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Disposable d = adapter.getItemClicks().subscribe(new ItemClickConsumer());
        compositeDisposable.add(d);

        if(!presenter.isInRestoreState(this)) {
            presenter.load();
        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public void showSpecialties(List<Specialty> specialties) {
        adapter.setSpecialties(specialties);
    }

    @Override
    public void showEmpty() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.specialtiesRV.setVisibility(View.GONE);
    }

    @Override
    protected void injectDependencies() {
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
        component.inject(this);
    }

    private class ItemClickConsumer implements Consumer<Integer> {
        @Override
        public void accept(@NonNull Integer itemId) throws Exception {
            presenter.onSpecialtySelected(itemId);
        }
    }
}
