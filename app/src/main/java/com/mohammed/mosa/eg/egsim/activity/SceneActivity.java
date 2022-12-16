package com.mohammed.mosa.eg.egsim.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mohammed.mosa.eg.egsim.R;
import com.mohammed.mosa.eg.egsim.adabters.USSDAdapter;
import com.mohammed.mosa.eg.egsim.databinding.ActivityScenBinding;
import com.mohammed.mosa.eg.egsim.fragments.CallMeFragment;
import com.mohammed.mosa.eg.egsim.fragments.ChargeDialogFragment;
import com.mohammed.mosa.eg.egsim.fragments.PUKFragment;
import com.mohammed.mosa.eg.egsim.fragments.TransformChargeFragment;
import com.mohammed.mosa.eg.egsim.objects.USSD;
import com.mohammed.mosa.eg.egsim.objects.UsedListener;

import java.util.ArrayList;
import java.util.Collections;

public class SceneActivity extends AppCompatActivity implements UsedListener {
//    public static final int REQ_PRE = 1;
    ActivityScenBinding binding;
    ArrayList<USSD> USSDS;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        USSDS = new ArrayList<>();
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.COM);
        customizer(name);
        binding.rvUssd.setAdapter(new USSDAdapter(this, USSDS));
        binding.rvUssd.setLayoutManager(new LinearLayoutManager(this));
        binding.rvUssd.setHasFixedSize(true);
    }

    private void customizer(String name){
        switch (name){
            case MainActivity.WE:
                USSDS = weData();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6200EE")));
                getWindow().setStatusBarColor(Color.parseColor("#FF3700B3"));
                break;

            case MainActivity.ETISALAT:
                USSDS = etsalatData();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7fdc32")));
                getWindow().setStatusBarColor(Color.parseColor("#65b329"));
                break;

            case MainActivity.VODAFONE:
                USSDS = vodafoneData();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ee3023")));
                getWindow().setStatusBarColor(Color.parseColor("#BC261C"));
                break;

            case MainActivity.ORANGE:
                USSDS = orangeData();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF6600")));
                getWindow().setStatusBarColor(Color.parseColor("#CA5202"));
                break;

            case MainActivity.EMERGENCE:
                USSDS = emergence();
                break;

            case MainActivity.SPECIAL:
                USSDS = special();
                break;
        }
    }


//    this function for testing only
//    private void getPermission() {
//        String[] permission = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS};
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
//                != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
//                != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED ) {
//            ActivityCompat.requestPermissions(this, permission, REQ_PRE);
//        }
//    }

    private void dialUSSD(String code){
        Intent intent =  new Intent();
        Uri uri = Uri.parse("tel:" + getStringCode(code));
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    private  View.OnClickListener ussdCall(String ussd){
        return view -> dialUSSD(ussd);
    }


    private void sendSMS(String msg, String to){
        Intent intent =  new Intent();
        Uri uri = Uri.parse("smsto:" + getStringCode(to));
        intent.setAction(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body", msg);
        intent.setData(uri);
        startActivity(intent);
    }

    private  View.OnClickListener ussdSend(String msg, String to){
        return view -> sendSMS(msg, to);
    }

    private String getStringCode(String code){
        StringBuilder sb = new StringBuilder();
        char[] chars = code.toCharArray();
        for(char c : chars){
            if(c == '#')
                sb.append(Uri.encode(String.format("%s", c)));
            else
                sb.append(c);
        }
        return sb.toString();
}

    // TODO: add some more stuff ;)
    private ArrayList<USSD> emergence(){
        ArrayList<USSD> USSDS = new ArrayList<>();
        USSDS.add(new USSD("123", "الاسعاف", R.drawable.ic_ambulance, ussdCall("123")));
        USSDS.add(new USSD("122", "الشرطة", R.drawable.ic_police, ussdCall("122")));
        USSDS.add(new USSD("180", "المطافي", R.drawable.ic_fire_truck, ussdCall("180")));
        USSDS.add(new USSD("121", "طوارئ الكهرباء", R.drawable.ic_sustainable_energy, ussdCall("121")));
        USSDS.add(new USSD("129", "طوارئ الغاز", R.drawable.ic_gas, ussdCall("129")));
        USSDS.add(new USSD("125", "طوارئ الماء", R.drawable.ic_recycling_water, ussdCall("125")));
        USSDS.add(new USSD("175", "طوارئ الصرف الصحي", R.drawable.ic_sewer, ussdCall("175")));
        USSDS.add(new USSD("155", "شكاوى الانترنت و المحمول", R.drawable.ic_worldwide, ussdCall("155")));
        Collections.sort(USSDS);
        return USSDS;
    }

    private ArrayList<USSD> special() {
        ArrayList<USSD> USSDS = new ArrayList<>();
        USSDS.add(new USSD("*#06#", "IMEI", R.drawable.ic_scanner, ussdCall("*#06#")));
        USSDS.add(new USSD("*#0*#", "اختبارات للهاتف", R.drawable.ic_handyman, ussdCall("*#0*#")));
        USSDS.add(new USSD("*#*#4636#*#*", "معلومات عن البطارية و الWIFI", R.drawable.ic_device_information, ussdCall("*#*#4636#*#*")));
        USSDS.add(new USSD("*#*#7780#*#*", "اعادة ضبط المصنع", R.drawable.ic_restore, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert("*#*#7780#*#*##");
            }
        }));
        return USSDS;
    }

    private void alert(String ussd){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("هل انت متاكد من اعادة ضبط المصنع");
        dialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialUSSD(ussd);
            }
        });
        dialog.setNegativeButton("الغاء", null);
        dialog.show();

    }

    // TODO: add more code
    private ArrayList<USSD> etsalatData() {
        ArrayList<USSD> USSDS = new ArrayList<>();
        USSDS.add(new USSD("*556*رقم الكرت#", "شحن الرصيد", R.drawable.ic_phone_et, K -> {
                ChargeDialogFragment chargeDialogFragment = ChargeDialogFragment.newInstance("*556*", MainActivity.ETISALAT);
                chargeDialogFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*111*رقم الهاتف#", "كلمني شكرا", R.drawable.ic_phone_et, K -> {
                CallMeFragment callMeFragment = CallMeFragment.newInstance(MainActivity.ETISALAT, "*111*");
                callMeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*557*الرقم*المبلغ#", "تحويل الرصيد", R.drawable.ic_phone_et, K -> {
            TransformChargeFragment transformChargeFragment = TransformChargeFragment.newInstance(MainActivity.ETISALAT, "*557*");
            transformChargeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*911#", " سلفني شكرا", R.drawable.ic_phone_et, ussdCall("*911#")));

        USSDS.add(new USSD("*947#", "معرفة رقم هاتفك", R.drawable.ic_phone_et, ussdCall("*947#")));

        USSDS.add(new USSD("*011#", "العروض اليومية", R.drawable.ic_phone_et, ussdCall("*011#")));

        USSDS.add(new USSD("*80*1*1#", "لمعرفة اخر 5 معاملات", R.drawable.ic_phone_et, ussdCall("*80*1*1#")));

        USSDS.add(new USSD("*558*5#", "استهلاك الميجا بايتس", R.drawable.ic_phone_et, ussdCall("*558*5#")));

        USSDS.add(new USSD("*811#", "عروض اقوى كرت في مصر", R.drawable.ic_phone_et, ussdCall("*811#")));

        USSDS.add(new USSD("*2002#", "الديون التي عليك", R.drawable.ic_phone_et, ussdCall("*2002#")));

        USSDS.add(new USSD("*8888#", "معرفة الرصيد", R.drawable.ic_phone_et, ussdCall("*8888#")));

        USSDS.add(new USSD("*838#", "معرفة الاستهلاك", R.drawable.ic_phone_et, ussdCall("*838#")));

        USSDS.add(new USSD("*566#", "باقات الانترنت", R.drawable.ic_phone_et, ussdCall("*566#")));

        USSDS.add(new USSD("*689#", "باقات  المكالمات", R.drawable.ic_phone_et, ussdCall("*689#")));

        USSDS.add(new USSD("*807#", "تجديد باقات  المكالمات", R.drawable.ic_phone_et, ussdCall("*807#")));

        USSDS.add(new USSD("*770#", "التحويل لنظام الفاتورة", R.drawable.ic_phone_et, ussdCall("*770#")));

        USSDS.add(new USSD("*100#", "رسالة ضبط الانترنت", R.drawable.ic_phone_et, ussdCall("*100#")));

        USSDS.add(new USSD("0235346333", "خدمة العملاء من الخط الارضي", R.drawable.ic_phone_et, ussdCall("0235346333")));

        USSDS.add(new USSD("500", "معرفة رمز PUK", R.drawable.ic_phone_et,ussdCall("500")));

        USSDS.add(new USSD("555", "معرفة نظام خطك", R.drawable.ic_phone_et, ussdCall("555")));

        USSDS.add(new USSD("525", "تغير النظام", R.drawable.ic_phone_et, ussdCall("525")));

        USSDS.add(new USSD("333", "خدمة العملاء", R.drawable.ic_phone_et, ussdCall("333")));

        return USSDS;

    }

    // TODO: add more code
    private ArrayList<USSD> weData() {
        ArrayList<USSD> USSDS = new ArrayList<>();

        // TODO
        USSDS.add(new USSD("*555*رقم الكرت#", "شحن الرصيد", R.drawable.ic_phone_we, K -> {
            ChargeDialogFragment chargeDialogFragment = ChargeDialogFragment.newInstance("*555*", MainActivity.WE);
            chargeDialogFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*515*رقم الهاتف#", "كلمني شكرا", R.drawable.ic_phone_we, K -> {
            CallMeFragment callMeFragment = CallMeFragment.newInstance(MainActivity.WE, "*515*");
            callMeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*504#", "سلفني شكرا", R.drawable.ic_phone_we, ussdCall("*504#")));

        USSDS.add(new USSD("*323*رقم الهاتف*المبلغ#", "تحويل الرصيد", R.drawable.ic_phone_we, K -> {
            TransformChargeFragment transformChargeFragment = TransformChargeFragment.newInstance(MainActivity.WE, "*323*");
            transformChargeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*688#", "معرفة رقم هاتفك", R.drawable.ic_phone_we, ussdCall("*688#")));

        USSDS.add(new USSD("*550#", "معرفة الرصيد", R.drawable.ic_phone_we, ussdCall("*550#")));

        USSDS.add(new USSD("*999#", "باقات و خدمات الانترنت", R.drawable.ic_phone_we, ussdCall("*999#")));

        USSDS.add(new USSD("*600*6#", "اضافة رقم للمفضلة", R.drawable.ic_phone_we, ussdCall("*600*6#")));

        USSDS.add(new USSD("*800#", "حظر الارقام المزعجة", R.drawable.ic_phone_we, ussdCall("*800#")));

        USSDS.add(new USSD("19777", "خدمة العملاء DSL", R.drawable.ic_phone_we, ussdCall("19777")));

        USSDS.add(new USSD("111", "رسالة الضبط", R.drawable.ic_phone_we, ussdCall("111")));

        USSDS.add(new USSD("111", "خدمة العملاء", R.drawable.ic_phone_we, ussdCall("111")));

        return USSDS;
    }

    // TODO: add more code
    private ArrayList<USSD> vodafoneData() {
        ArrayList<USSD> USSDS = new ArrayList<>();
        // TODO
        USSDS.add(new USSD("*858*رقم الكرت#", "شحن الرصيد", R.drawable.ic_phone_vf, K -> {
            ChargeDialogFragment chargeDialogFragment = ChargeDialogFragment.newInstance("*858*", "vodafone");
            chargeDialogFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*505*رقم الهاتف#", "كلمني شكرا(فودفون)", R.drawable.ic_phone_vf, K -> {
            CallMeFragment callMeFragment = CallMeFragment.newInstance(MainActivity.VODAFONE, "*505*");
            callMeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*506*رقم الهاتف#", "كلمني شكرا(بقية الشبكات)", R.drawable.ic_phone_vf, K -> {
            CallMeFragment callMeFragment = CallMeFragment.newInstance(MainActivity.VODAFONE, "*506*");
            callMeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*868*رقم الهاتف*المبلغ#", "تحويل الرصيد", R.drawable.ic_phone_vf, K -> {
            TransformChargeFragment transformChargeFragment = TransformChargeFragment.newInstance(MainActivity.VODAFONE, "*868*2*");
            transformChargeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*868*3#", "سلفني شكرا", R.drawable.ic_phone_vf, ussdCall("*868*3#")));

        USSDS.add(new USSD("*878#", "معرفة رقم هاتفك", R.drawable.ic_phone_vf, ussdCall("*878#")));

        USSDS.add(new USSD("*868*1#", "معرفة الرصيد", R.drawable.ic_phone_vf,ussdCall("*868*1#")));

        USSDS.add(new USSD("*888*رقم الهاتف#", "معرفة رقم PUK", R.drawable.ic_phone_vf, K -> {
            PUKFragment pukFragment = PUKFragment.newInstance(MainActivity.VODAFONE, "*888*");
            pukFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("*60#", "الاستعلام عن الفليكسات", R.drawable.ic_phone_vf,ussdCall("*60#")));

        USSDS.add(new USSD("*225#", "لتجديد باقة المكالمات", R.drawable.ic_phone_vf, ussdCall("*225#")));

        USSDS.add(new USSD("*818*2#", "لمعرفة نقاط فودفون", R.drawable.ic_phone_vf, ussdCall("*818*2#")));

        USSDS.add(new USSD("*550*0#", "الغاء الكول تون", R.drawable.ic_phone_vf, ussdCall("*550*0#")));

        USSDS.add(new USSD("*9*5#", "تشغيل خدمة فودفون كاش", R.drawable.ic_phone_vf, ussdCall("*9*5#")));

        USSDS.add(new USSD("888", "خدمة العملاء", R.drawable.ic_phone_vf, ussdCall("888")));

        return USSDS;
    }

    // TODO: add more code
    private ArrayList<USSD> orangeData() {
        ArrayList<USSD> USSDS = new ArrayList<>();

        USSDS.add(new USSD("#102#*رقم الكرت#", "شحن الرصيد", R.drawable.ic_phone_orange, K -> {
            ChargeDialogFragment chargeDialogFragment = ChargeDialogFragment.newInstance("#102#*", MainActivity.ORANGE);
            chargeDialogFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("#121*رقم الهاتف#", "كلمني شكرا", R.drawable.ic_phone_orange, K -> {
            CallMeFragment callMeFragment = CallMeFragment.newInstance(MainActivity.ORANGE, "#121*");
            callMeFragment.show(getSupportFragmentManager(), null);}));

        USSDS.add(new USSD("#100#2#", "سلفني شكرا", R.drawable.ic_phone_orange, ussdCall("#100#2#")));

        USSDS.add(new USSD("#119#", "معرفة رقم هاتفك", R.drawable.ic_phone_orange, ussdCall("#119#")));

        USSDS.add(new USSD("#100#3#", "معرفة الرصيد", R.drawable.ic_phone_orange, ussdCall("#100#3#")));

        USSDS.add(new USSD("#100#2#", "تحويل الرصيد", R.drawable.ic_phone_orange, ussdCall("#100#2#")));

        USSDS.add(new USSD("#12#", "العروض اليومية", R.drawable.ic_phone_orange, ussdCall("#12#")));

        USSDS.add(new USSD("#100#", "باقة GO الشهرية", R.drawable.ic_phone_orange, ussdCall("#100#")));

        USSDS.add(new USSD("#100#2#", "حظر الارقام المزعجة", R.drawable.ic_phone_orange, ussdCall("#100#2#")));

        USSDS.add(new USSD("999", "الغاء الكول تون", R.drawable.ic_phone_orange, ussdSend("إلغاء", "999")));

        USSDS.add(new USSD("600", "رسالة الضبط", R.drawable.ic_phone_orange, ussdSend(" ", "600")));

        USSDS.add(new USSD("115", "خدمة العملاء اورنج كاش", R.drawable.ic_phone_orange, ussdCall("115")));

        USSDS.add(new USSD("110", "خدمة العملاء", R.drawable.ic_phone_orange, ussdCall("110")));

        USSDS.add(new USSD("16333", "خدمة العملاء DSL", R.drawable.ic_phone_orange, ussdCall("16333")));
        return USSDS;
    }

    @Override
    public void onCall(String code) {
        Intent intent =  new Intent();
        Uri uri = Uri.parse("tel:" + getStringCode(code));
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onSendSms(String msg, String to) {
        Intent intent =  new Intent();
        Uri uri = Uri.parse("smsto:" + getStringCode(to));
        intent.setAction(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body", msg);
        intent.setData(uri);
        startActivity(intent);
    }
}
