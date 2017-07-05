package com.test.test.presentation.workerDetails;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.test.test.R;
import com.test.test.databinding.ActivityWorkerBinding;
import com.test.test.di.component.ActivityComponent;
import com.test.test.di.component.DaggerActivityComponent;
import com.test.test.domain.worker.Worker;
import com.test.test.presentation.BaseActivity;
import com.test.test.utils.ImageLoader;
import com.test.test.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

public class WorkerDetailsActivity extends BaseActivity implements WorkerDetailsView {
    private static final String EXTRA_WORKER_ID = "extra_worker_id";

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy' г.'");
    @InjectPresenter
    WorkerDetailsPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    ActivityWorkerBinding binding;

    public static Intent getCallingIntent(Context context, int workerId) {
        Intent intent = new Intent(context, WorkerDetailsActivity.class);
        intent.putExtra(EXTRA_WORKER_ID, workerId);
        return intent;
    }

    private int getWorkerId() {
        return getIntent().getIntExtra(EXTRA_WORKER_ID, 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_worker);
        if(!getIntent().hasExtra(EXTRA_WORKER_ID)) {
            throw new IllegalStateException();
        }

        setupActionBar();
        if (!presenter.isInRestoreState(this)) {
            presenter.load(getWorkerId());
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.worker_screen_title);
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
    public void showWorker(Worker worker) {
        binding.firstNameTV.setText(Utils.formatName(worker.getFirstName()));
        binding.lastNameTV.setText(Utils.formatName(worker.getLastName()));
        if(worker.getAvatarUrl() != null) {
            imageLoader.load(worker.getAvatarUrl(), binding.avatarIV);
        }
        String birthdayText;
        String ageText;
        if(worker.getBirthday() != null) {
            birthdayText = dateFormat.format(worker.getBirthday());
            int age = Utils.getAge(worker.getBirthday());
            ageText = Integer.toString(age) + " " + this.getResources().getQuantityString(R.plurals.years, age, age);
        }
        else {
            ageText = getString(R.string.no_data_text);
            birthdayText = getString(R.string.no_data_text);
        }
        binding.ageTV.setText(String.format(getString(R.string.age), ageText));
        binding.birthdayTV.setText(String.format(getString(R.string.birthday), birthdayText));
    }


    @Override
    protected void injectDependencies() {
        ActivityComponent component = DaggerActivityComponent.builder()
                .activityModule(getActivityModule())
                .appComponent(getAppComponent())
                .build();
        component.inject(this);
    }
}
