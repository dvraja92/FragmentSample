package com.anhad.fleetgaurd.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anhad on 05-01-2017.
 */
public abstract class BaseFragment extends Fragment {
    public abstract String getTitle();

    private void setTitle(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }
}
