package com.rtech.statusdownloaderwa.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rtech.statusdownloaderwa.R;
import com.rtech.statusdownloaderwa.adapters.MediaAdapter;
import com.rtech.statusdownloaderwa.databinding.FragmentAllBinding;
import com.rtech.statusdownloaderwa.models.MediaModel;
import com.rtech.statusdownloaderwa.viewModels.MediaViewModel;

import java.util.ArrayList;


public class ImagesFragment extends Fragment {
    FragmentAllBinding mainXml;
    MediaViewModel mediaViewModel;

    public ImagesFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainXml=FragmentAllBinding.inflate(inflater,container,false);
        mediaViewModel=new ViewModelProvider(requireActivity()).get(MediaViewModel.class);
        ArrayList<MediaModel> list=new ArrayList<>();
        GridLayoutManager layoutManager=new GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false);
        MediaAdapter adapter=new MediaAdapter(requireActivity(),list);
        mainXml.recyclerView.setLayoutManager(layoutManager);
        mainXml.recyclerView.setAdapter(adapter);
        mediaViewModel.getImageMedia().observe(getViewLifecycleOwner(), mediaModels -> {
            if(mediaModels.size()==0){
                mainXml.recyclerView.setVisibility(View.GONE);
                mainXml.emptyPostLayout.setVisibility(View.VISIBLE);
            }else{
                mainXml.recyclerView.setVisibility(View.VISIBLE);
                mainXml.emptyPostLayout.setVisibility(View.GONE);
            }
            list.clear();
            list.addAll(mediaModels);
            adapter.notifyDataSetChanged();
        });
        mainXml.howToUseBtn.setOnClickListener(v->{
            showGuide();
        });


        return mainXml.getRoot();
    }
    private void showGuide() {
        Dialog dialog=new Dialog(requireActivity());
        dialog.setContentView(R.layout.no_content_dialog_layout);
        ImageView closeBtn=dialog.findViewById(R.id.closebtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
//

    }
}