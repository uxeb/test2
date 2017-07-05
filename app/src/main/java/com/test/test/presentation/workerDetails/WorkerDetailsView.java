package com.test.test.presentation.workerDetails;

import com.arellomobile.mvp.MvpView;
import com.test.test.domain.worker.Worker;

public interface WorkerDetailsView extends MvpView {
    void showWorker(Worker worker);
    void showError();
}
