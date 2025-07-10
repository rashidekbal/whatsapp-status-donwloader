package com.rtech.statusdownloaderwa.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtech.statusdownloaderwa.R;
import com.rtech.statusdownloaderwa.adapters.MediaAdapter;
import com.rtech.statusdownloaderwa.databinding.FragmentAllBinding;
import com.rtech.statusdownloaderwa.models.MediaModel;
import com.rtech.statusdownloaderwa.viewModels.MediaViewModel;

import java.util.ArrayList;


public class VideosFragment extends Fragment {


    FragmentAllBinding mainXml;
    MediaViewModel mediaViewModel;
    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainXml=FragmentAllBinding.inflate(inflater,container,false);
        mediaViewModel=new ViewModelProvider(requireActivity()).get(MediaViewModel.class);
        ArrayList<MediaModel> list=new ArrayList<>();
        GridLayoutManager layoutManager=new GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false);
        MediaAdapter adapter=new MediaAdapter(requireActivity(),list);
        mainXml.recyclerView.setLayoutManager(layoutManager);
        mainXml.recyclerView.setAdapter(adapter);

        mediaViewModel.getVideosMedia().observe(getViewLifecycleOwner(), mediaModels -> {
            list.clear();
            list.addAll(mediaModels);
            adapter.notifyDataSetChanged();
        });




        return mainXml.getRoot();
    }
}