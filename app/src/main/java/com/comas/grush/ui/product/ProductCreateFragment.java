package com.comas.grush.ui.product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public class ProductCreateFragment extends ProductFragment {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        runLoadingAnimation(false);
        mEditButton.setVisibility(View.GONE);
        mDeleteButton.setVisibility(View.GONE);
        return view;
    }
}