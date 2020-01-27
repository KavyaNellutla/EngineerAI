package com.kavya.myapplication.view.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kavya.myapplication.Model.PostsModel;
import com.kavya.myapplication.Repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    PostsRepository postsRepository;
    public PostsViewModel(@NonNull Application application) {
        super(application);
        postsRepository = new PostsRepository();
    }
    public LiveData<List<PostsModel>> getPostsLiveData(){
        return postsRepository.getPostsLiveData();
    }
    public void getPostsWithPageno(int Pageno){
        postsRepository.getPostswithpageno(Pageno);
    }
}
