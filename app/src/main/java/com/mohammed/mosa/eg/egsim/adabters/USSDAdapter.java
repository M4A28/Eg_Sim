package com.mohammed.mosa.eg.egsim.adabters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mohammed.mosa.eg.egsim.R;
import com.mohammed.mosa.eg.egsim.databinding.CodeItemLayoutBinding;
import com.mohammed.mosa.eg.egsim.objects.USSD;

import java.util.ArrayList;

public class USSDAdapter extends RecyclerView.Adapter<USSDAdapter.CodeHolder> {
    private Context context;
    private ArrayList<USSD> USSDS;

    public USSDAdapter(Context context, ArrayList<USSD> USSDS) {
        this.context = context;
        this.USSDS = USSDS;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<USSD> getCodes() {
        return USSDS;
    }

    public void setCodes(ArrayList<USSD> USSDS) {
        this.USSDS = USSDS;
    }

    @NonNull
    @Override
    public CodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CodeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.code_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodeHolder holder, int position) {
        USSD USSD = USSDS.get(position);
        holder.bind(USSD);
    }

    @Override
    public int getItemCount() {
        return (USSDS != null)? USSDS.size(): 0;
    }

    public class CodeHolder extends RecyclerView.ViewHolder{
        CodeItemLayoutBinding binding;
        public CodeHolder(@NonNull View itemView) {
            super(itemView);
            binding = CodeItemLayoutBinding.bind(itemView);
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        public void bind(USSD USSD){
            binding.tvTitle.setText(USSD.getJob());
            binding.tvUssd.setText(USSD.getCode());
            binding.ivSideImage.setImageDrawable(context.getDrawable(USSD.getImage()));
            binding.cvCodeItem.setOnClickListener(USSD.getListener());
        }
    }
}
