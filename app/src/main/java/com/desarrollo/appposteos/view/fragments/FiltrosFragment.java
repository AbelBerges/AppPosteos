package com.desarrollo.appposteos.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desarrollo.appposteos.R;

public class FiltrosFragment extends Fragment {

    public FiltrosFragment() {

    }


    public static FiltrosFragment newInstance(String param1, String param2) {
        return new FiltrosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtros, container, false);
    }
}