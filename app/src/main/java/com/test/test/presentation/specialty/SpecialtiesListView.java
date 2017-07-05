package com.test.test.presentation.specialty;

import com.arellomobile.mvp.MvpView;
import com.test.test.domain.worker.Specialty;

import java.util.List;

public interface SpecialtiesListView extends MvpView {
    void showSpecialties(List<Specialty> specialties);
    void showEmpty();
    void showError();
}
