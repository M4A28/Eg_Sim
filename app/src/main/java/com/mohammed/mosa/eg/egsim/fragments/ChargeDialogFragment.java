package com.mohammed.mosa.eg.egsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.widget.Toast;

import com.mohammed.mosa.eg.egsim.databinding.FragmentChargeDialogBinding;

public class ChargeDialogFragment extends DialogFragment {

    FragmentChargeDialogBinding binding;
    private static final String ARG_USSD = "ussd";
    private static final String ARG_COM = "com";
    private static String ussd = "";
    private static String com = "";
    public ChargeDialogFragment() {
        // Required empty public constructor
    }

    public static ChargeDialogFragment newInstance(String ussd, String com) {
        ChargeDialogFragment fragment = new ChargeDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USSD, ussd);
        args.putString(ARG_COM, com);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentChargeDialogBinding.inflate(getActivity().getLayoutInflater());
        Bundle bundle = getArguments();
        if(bundle != null) {
            ussd = bundle.getString(ARG_USSD);
            com = bundle.getString(ARG_COM);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        customizeDialog(com);
        binding.btnCharg.setOnClickListener(view -> {
            String cardNumber = binding.etCardNumber.getText().toString();
            if(cardNumber.isEmpty() || cardNumber.length() <= 11)
                Toast.makeText(view.getContext(), "الرجاء ادخال رقم صحيح", Toast.LENGTH_SHORT).show();
            else
                tellCode(ussd + cardNumber + "#", view.getContext());
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("شحن الرصيد").setView(binding.getRoot());
        return alert.create();
    }

    public static void tellCode(String code, Context context){
        Intent intent =  new Intent();
        Uri uri = Uri.parse("tel:" + getStringCode(code));
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static String getStringCode(String code){
        StringBuilder sb = new StringBuilder();
        for(char c: code.toCharArray()){
            if(c == '#')
                sb.append(Uri.encode(""+c));
            else
                sb.append(c);
        }
        return sb.toString();
    }

    public void customizeDialog(String com){
        int dark;
        int light;
        switch (com){
            case "etsalat":
                dark = Color.parseColor("#78a22f");
                light = Color.parseColor("#8DC479");
                customChargeFragmentWindow(dark, light);
                break;

            case "we":
                dark = Color.parseColor("#FF3700B3");
                light = Color.parseColor("#FF6200EE");
                customChargeFragmentWindow(dark, light);
                break;

            case "vodafone":
                dark = Color.parseColor("#BC261C");
                light = Color.parseColor("#ee3023");
                customChargeFragmentWindow(dark, light);
                break;

            case "orange":
                dark = Color.parseColor("#CA5202");
                light = Color.parseColor("#FF6600");
                customChargeFragmentWindow(dark, light);
                break;
        }
    }

    private void customChargeFragmentWindow(int dark, int light){
        binding.btnCharg.setBackgroundColor(dark);
        binding.tiOutline.setHintTextColor(ColorStateList.valueOf(dark));
        binding.tiOutline.setBoxStrokeColor(light);
    }
}