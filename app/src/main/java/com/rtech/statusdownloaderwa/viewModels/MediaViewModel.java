package com.rtech.statusdownloaderwa.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtech.statusdownloaderwa.filesProvider.Provider;
import com.rtech.statusdownloaderwa.models.MediaModel;

import java.util.ArrayList;

public class MediaViewModel extends AndroidViewModel {
    public MediaViewModel(@NonNull Application application) {
        super(application);
    }
    MutableLiveData<ArrayList<MediaModel>> MutableMediaArrayAll=new MutableLiveData<>();
    public LiveData<ArrayList<MediaModel>> getAllMedia(){
        if(MutableMediaArrayAll.getValue()==null||MutableMediaArrayAll.getValue().isEmpty()){
            loadAllMedia();
        }
        return MutableMediaArrayAll;
    }


    MutableLiveData<ArrayList<MediaModel>> MutableMediaArrayImage=new MutableLiveData<>();
    public LiveData<ArrayList<MediaModel>> getImageMedia(){
        if(MutableMediaArrayImage.getValue()==null||MutableMediaArrayImage.getValue().isEmpty()){
            loadImageMedia();
        }
        return MutableMediaArrayImage;
    }
    MutableLiveData<ArrayList<MediaModel>> MutableMediaArrayVideos=new MutableLiveData<>();
    public LiveData<ArrayList<MediaModel>> getVideosMedia(){
        if(MutableMediaArrayVideos.getValue()==null||MutableMediaArrayVideos.getValue().isEmpty()){
            loadVideoMedia();
        }

        return MutableMediaArrayVideos;
    }

    private void loadVideoMedia() {
        MutableMediaArrayVideos.postValue(Provider.getMediaVideos());

    }


    private void loadImageMedia() {
        MutableMediaArrayImage.postValue(Provider.getMediaImages());
    }


    private void loadAllMedia() {
        MutableMediaArrayAll.postValue(Provider.getMediaAll());
    }

}