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
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mohammed.mosa.eg.egsim.databinding.FragmentTransformChargeBinding;

public class TransformChargeFragment extends DialogFragment {

    FragmentTransformChargeBinding binding;
    private static final String ARG_COM = "com";
    private static final String ARG_USSD = "ussd";
    ActivityResultLauncher<Intent> resultLauncher;
    private String com;
    private String ussd;

    public TransformChargeFragment() {
        // Required empty public constructor
    }

    public static TransformChargeFragment newInstance(String com, String ussd) {
        TransformChargeFragment fragment = new TransformChargeFragment();
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
        binding = FragmentTransformChargeBinding.inflate(getActivity().getLayoutInflater());
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
            String money = binding.etCash.getText().toString();

            if(phone.isEmpty() || money.isEmpty() ){
                Toast.makeText(getContext(), "الرجاء تعباء جميع الخانات", Toast.LENGTH_SHORT).show();
            }
            else if(phone.length() < 11)
                Toast.makeText(getContext(), "الرجاء ادخال رقم صحيح", Toast.LENGTH_SHORT).show();
            else {
                tellCode(ussd + phone + "*" + money +  "#", view.getContext());
            }
        });
        binding.ivContcat.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            resultLauncher.launch(intent);
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("تحويل الرصيد").setView(binding.getRoot());
        return alert.create();
    }

    public void customizeDialog(String com){
        int dark;
        int light;
        switch (com){
            case "etsalat":
                dark = Color.parseColor("#78a22f");
                light = Color.parseColor("#8DC479");
                customTransformFragment(dark, light);
                break;

            case "we":
                dark = Color.parseColor("#FF3700B3");
                light = Color.parseColor("#FF6200EE");
                customTransformFragment(dark, light);
                break;

            case "vodafone":
                dark = Color.parseColor("#BC261C");
                light = Color.parseColor("#ee3023");
                customTransformFragment(dark, light);
                break;

            case "orange":
                dark = Color.parseColor("#CA5202");
                light = Color.parseColor("#FF6600");
                customTransformFragment(dark, light);
                break;
        }
    }

    private void customTransformFragment(int dark, int light){
        binding.btnCall.setBackgroundColor(dark);
        binding.tlOutline.setHintTextColor(ColorStateList.valueOf(dark));
        binding.tlOutline.setBoxStrokeColor(light);
        binding.tlOutline2.setHintTextColor(ColorStateList.valueOf(dark));
        binding.tlOutline2.setBoxStrokeColor(light);
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