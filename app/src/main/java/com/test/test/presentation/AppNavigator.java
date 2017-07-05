package com.test.test.presentation;

import android.app.Activity;
import android.content.Intent;

import com.test.test.presentation.specialty.SpecialtiesListActivity;
import com.test.test.presentation.workerDetails.WorkerDetailsActivity;
import com.test.test.presentation.workersList.WorkersListActivity;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class AppNavigator implements Navigator {
    public static final String SPECIALTIES_SCREEN = "specialties";
    public static final String WORKERS_SCREEN = "workers";
    public static final String WORKER_SCREEN = "worker";
    Activity activity;

    public AppNavigator(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void applyCommand(Command command) {
        if(command instanceof Forward) {
            openScreen(((Forward) command).getScreenKey(), ((Forward) command).getTransitionData());
        }
        else if (command instanceof Back) {
            activity.onBackPressed();
        }
        else if (command instanceof Replace) {
            openScreen(((Replace) command).getScreenKey(), ((Replace) command).getTransitionData());
            activity.finish();
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    private void openScreen(String key, Object data) {
        Intent intent;
        switch (key) {
            case SPECIALTIES_SCREEN:
                intent = SpecialtiesListActivity.getCallingIntent(activity);
                break;
            case WORKERS_SCREEN:
                intent = WorkersListActivity.getCallingIntent(activity, (int) data);
                break;
            case WORKER_SCREEN:
                intent = WorkerDetailsActivity.getCallingIntent(activity, (int) data);
                break;
            default:
                throw new IllegalStateException();
        }
        activity.startActivity(intent);
    }
}
