package com.mohammed.mosa.eg.egsim.adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.mosa.eg.egsim.R;
import com.mohammed.mosa.eg.egsim.databinding.PhoneCompnyLayoutBinding;
import com.mohammed.mosa.eg.egsim.objects.Company;
import com.mohammed.mosa.eg.egsim.objects.Company;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MenuHolder> {
    private Context context;
    private ArrayList<Company> companies;

    public CompanyAdapter(Context context, ArrayList<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_compny_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        Company company = companies.get(position);
        holder.bind(company);
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {
        PhoneCompnyLayoutBinding binding;
        public MenuHolder(@NonNull View itemView) {
            super(itemView);
            binding = PhoneCompnyLayoutBinding.bind(itemView);
        }
        public void bind(Company company){
            binding.tvTitle.setText(company.getTitle());
            binding.tvDescription.setText(company.getDescription());
            binding.ivSideImage.setImageDrawable(context.getDrawable(company.getImage()));
            binding.cvMenuItem.setOnClickListener(company.getListener());
        }
    }
}
