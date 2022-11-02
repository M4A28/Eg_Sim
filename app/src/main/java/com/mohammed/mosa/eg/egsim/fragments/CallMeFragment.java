package com.mohammed.mosa.eg.egsim.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.mohammed.mosa.eg.egsim.databinding.FragmentCallMeBinding;

public class CallMeFragment extends DialogFragment {
    private static final String ARG_COM = "com";
    private static final String ARG_USSD = "ussd";
    private FragmentCallMeBinding binding;
    ActivityResultLauncher<Intent> resultLauncher;

    private String com;
    private String ussd;

    public CallMeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CallMeFragment newInstance(String com, String ussd) {
        CallMeFragment fragment = new CallMeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COM, com);
        args.putString(ARG_USSD, ussd);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("Range")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCallMeBinding.inflate(getActivity().getLayoutInflater());
        if (getArguments() != null) {
            com = getArguments().getString(ARG_COM);
            ussd = getArguments().getString(ARG_USSD);
        }
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                        Intent intent = result.getData();
                        if(intent != null){
                            Cursor query = getActivity().getContentResolver().query(intent.getData(), new String[]{"data1"}, null, null, null);
                            query.moveToFirst();
                            Log.d("q", query.getString(query.getColumnIndex("data1")));
                            binding.etPhoneNumber.setText(clearString(query.getString(query.getColumnIndex("data1"))));
                            query.close();
                        }
                        else
                            Toast.makeText(getContext(), "لم يتم اختيار رقم هاتف", Toast.LENGTH_SHORT).show();
                });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        customizeDialog(com);
        binding.btnCall.setOnClickListener(view -> {
            String phone = binding.etPhoneNumber.getText().toString();
            if(phone.isEmpty() || phone.length() < 10){
                Toast.makeText(getContext(), "الرقم غير صحيح", Toast.LENGTH_SHORT).show();
            } else {
                tellCode(ussd + phone + "#", view.getContext());
            }
        });
        binding.ivContcat.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            resultLauncher.launch(intent);
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("كلمني شكراً").setView(binding.getRoot());
        return alert.create();
    }


    public void customizeDialog(String com){
        int dark;
        int light;
        switch (com){

            case "etsalat":
                dark = Color.parseColor("#78a22f");
                light = Color.parseColor("#8DC479");
                customCallMeWindow(dark, light);
                break;

            case "we":
                dark = Color.parseColor("#FF3700B3");
                light = Color.parseColor("#FF6200EE");
                customCallMeWindow(dark, light);
                break;

            case "vodafone":
                dark = Color.parseColor("#BC261C");
                light = Color.parseColor("#ee3023");
                customCallMeWindow(dark, light);
                break;

            case "orange":
                dark = Color.parseColor("#CA5202");
                light = Color.parseColor("#FF6600");
                customCallMeWindow(dark, light);
                break;
        }
    }

    private void customCallMeWindow(int dark, int light){
        binding.btnCall.setBackgroundColor(dark);
        binding.tlOutline.setHintTextColor(ColorStateList.valueOf(dark));
        binding.tlOutline.setBoxStrokeColor(light);
    }

    private void tellCode(String code, Context context){
        Intent intent =  new Intent();
        Uri uri = Uri.parse("tel:" + getStringCode(code));
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        context.startActivity(intent);
    }

    private String getStringCode(String code){
        StringBuilder sb = new StringBuilder();
        for(char c: code.toCharArray()){
            if(c == '#')
                sb.append(Uri.encode(""+c));
            else
                sb.append(c);
        }
        return sb.toString();
    }

    private String clearString(String string){
        if(string.startsWith("+2")){
            return string.substring(2).replaceAll("\\s+", "");
        }
        return string.replaceAll("\\s+", "");
    }
}