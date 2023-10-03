package com.example.fitzone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class frag_me extends Fragment {
    LinearLayout profile, setting,logout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_me, container, false);

        profile = view.findViewById(R.id.my_profile);
        setting = view.findViewById(R.id.setting);
        logout = view.findViewById(R.id.log_out);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihome = new Intent(getContext(), profile_page.class);
                startActivity(ihome);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iprofile = new Intent(getContext(), setting_page.class);
                startActivity(iprofile);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("flag", false);
                editor.apply();

                // After logging out, navigate to the LoginActivity
                Intent intent = new Intent(getContext(), login_page.class);
                startActivity(intent);
                getActivity().finish(); // Close the current activity
            }
        });


        return view;
    }
}
