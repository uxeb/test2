package com.test.test.presentation.specialty;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.test.R;
import com.test.test.databinding.ItemSpecialtyBinding;
import com.test.test.domain.worker.Specialty;
import com.test.test.presentation.BaseRecyclerViewAdapter;

import java.util.List;

class SpecialtiesListAdapter
        extends BaseRecyclerViewAdapter<SpecialtiesListAdapter.ViewHolder> {

    private List<Specialty> specialties;

    SpecialtiesListAdapter() {

    }

    @Override
    public SpecialtiesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSpecialtyBinding binding
                = DataBindingUtil.inflate(inflater, R.layout.item_specialty, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Specialty specialty = specialties.get(position);
        final int specialtyId = specialty.getId();
        holder.binding.specialtyTV.setText(specialty.getName());
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onItemClick(specialtyId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialties != null ? specialties.size() : 0;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSpecialtyBinding binding;
        ViewHolder(ItemSpecialtyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
