package com.mohammed.mosa.eg.egsim.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mohammed.mosa.eg.egsim.databinding.FragmentAboutUsBinding;


public class AboutUsFragment extends DialogFragment {

    private static final String MY_TWITTER = "https://twitter.com/M4A28";
    private static final String MY_FACEBOOK = "https://facebook.com/M4A28";
    private static final String MY_GITHUB = "https://github.com/M4A28";
    private static final String TWITTER_PACKAGE = "com.twitter.android";
    private static final String FACEBOOK_PACKAGE = "com.facebook.katana";
    FragmentAboutUsBinding binding;

    public AboutUsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAboutUsBinding.inflate(getActivity().getLayoutInflater());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding.ivFacebook.setOnClickListener(k ->{
            try {
                getActivity().getPackageManager().getPackageInfo(FACEBOOK_PACKAGE, 0);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_FACEBOOK));
                startActivity(intent);

            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(Intent.EXTRA_TEXT,MY_FACEBOOK);
                startActivity(intent);
            }
        });

        binding.ivTwitter.setOnClickListener(k ->{
            try {
                getActivity().getPackageManager().getPackageInfo(TWITTER_PACKAGE, 0);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MY_TWITTER));
                startActivity(intent);

            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(Intent.EXTRA_TEXT, MY_TWITTER);
                startActivity(intent);
            }
        });

        binding.ivGithub.setOnClickListener(k ->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra(Intent.EXTRA_TEXT, MY_GITHUB);
            startActivity(intent);
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(binding.getRoot());
        return alert.create();
    }


}