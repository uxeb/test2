package com.test.test.presentation.workersList;

import com.arellomobile.mvp.MvpView;
import com.test.test.domain.worker.Worker;

import java.util.List;

public interface WorkersListView extends MvpView {
    void showWorkers(List<Worker> workers);
    void showEmpty();
    void showError();
}
