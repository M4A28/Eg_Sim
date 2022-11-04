package com.mohammed.mosa.eg.egsim.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mohammed.mosa.eg.egsim.R;
import com.mohammed.mosa.eg.egsim.adabters.CompanyAdapter;
import com.mohammed.mosa.eg.egsim.databinding.ActivityMainBinding;
import com.mohammed.mosa.eg.egsim.fragments.AboutUsFragment;
import com.mohammed.mosa.eg.egsim.objects.Company;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static final String WE = "we";
    public static final String ETISALAT = "etsalat";
    public static final String VODAFONE = "vodafone";
    public static final String ORANGE = "orange";
    public static final String EMERGENCE = "emergence";
    public static final String SPECIAL = "special";
    public static final String COM = "com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<Company> companies = fillMenu();
        CompanyAdapter adapter = new CompanyAdapter(this, companies);
        binding.rvMenu.setAdapter(adapter);
        binding.rvMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMenu.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public void aboutUS(MenuItem item){
        AboutUsFragment aboutFragment = new AboutUsFragment();
        aboutFragment.show(getSupportFragmentManager(), null);
    }

    public void shareApp(MenuItem item){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "حمل هذا التطبيق الان: github.com/M4A28");
        startActivity(Intent.createChooser(intent, "مشاركة باستخدام"));
    }

    public ArrayList<Company> fillMenu(){
        ArrayList<Company> companies = new ArrayList<>();

        companies.add(new Company("المصرية للاتصالات | WE", "وي قبل اي حد", R.drawable.ic_we, K ->{
                Intent intent = new Intent(getBaseContext(), SceneActivity.class);
                intent.putExtra(COM, WE);
                startActivity(intent);
            }));

        companies.add(new Company("اتصالات | Etisalat", "ديما مع بعض", R.drawable.ic_etisalat, k ->{
                Intent intent = new Intent(getBaseContext(), SceneActivity.class);
                intent.putExtra(COM, ETISALAT);
                startActivity(intent);
        }));

        companies.add(new Company("فودافون | Vodafone", "الي جاي اقوى", R.drawable.ic_vodafone, k ->{
                Intent intent = new Intent(getBaseContext(), SceneActivity.class);
                intent.putExtra(COM, VODAFONE);
                startActivity(intent);
        }));

        companies.add(new Company("Orange | اورنج", "اول مشغل محمول في مصر", R.drawable.ic_orange, K ->{
                Intent intent = new Intent(getBaseContext(), SceneActivity.class);
                intent.putExtra(COM, ORANGE);
                startActivity(intent);
        }));

        companies.add(new Company("الطوارئ", "الطوارئ: الاسعاف, الشرطة الخ…", R.drawable.warning, K ->{
            Intent intent = new Intent(getBaseContext(), SceneActivity.class);
            intent.putExtra(COM, EMERGENCE);
            startActivity(intent);
        }));
//        companies.add(new Company("اكواد خاصة", "IMEI, حالة الهاتف", R.drawable.ic_star, K ->{
//            Intent intent = new Intent(getBaseContext(), SceneActivity.class);
//            intent.putExtra(COM, SPECIAL);
//            startActivity(intent);
//        }));

        return companies;
    }


}