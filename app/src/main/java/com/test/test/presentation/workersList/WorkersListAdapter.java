package com.test.test.presentation.workersList;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.test.R;
import com.test.test.databinding.ItemWorkerBinding;
import com.test.test.domain.worker.Worker;
import com.test.test.utils.ImageLoader;
import com.test.test.utils.Utils;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class WorkersListAdapter extends RecyclerView.Adapter<WorkersListAdapter.ViewHolder>{

    private List<Worker> workers;
    private ImageLoader imageLoader;
    private Context context;
    private PublishSubject<Integer> itemClickSubject = PublishSubject.create();

    @Inject
    public WorkersListAdapter(Activity context, ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.context = context;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
        notifyDataSetChanged();
    }

    @Override
    public WorkersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemWorkerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_worker, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Worker worker = workers.get(position);
        final int workerId = worker.getId();
        holder.binding.firstNameTV.setText(Utils.formatName(worker.getFirstName()));
        holder.binding.lastNameTV.setText(Utils.formatName(worker.getLastName()));
        if(worker.getAvatarUrl() != null) {
            imageLoader.load(worker.getAvatarUrl(), holder.binding.avatarIV);
        }

        String ageText;
        if(worker.getBirthday() != null) {
            int age = Utils.getAge(worker.getBirthday());
            ageText = Integer.toString(age) + " " + context.getResources().getQuantityString(R.plurals.years, age, age);
        }
        else {
            ageText = context.getString(R.string.no_data_text);
        }
        holder.binding.ageTV.setText(String.format(context.getString(R.string.age), ageText));

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickSubject.onNext(workerId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workers != null ? workers.size() : 0;
    }

    public Observable<Integer> getItemClicks() {
        return itemClickSubject;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        ItemWorkerBinding binding;
        public ViewHolder(ItemWorkerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
