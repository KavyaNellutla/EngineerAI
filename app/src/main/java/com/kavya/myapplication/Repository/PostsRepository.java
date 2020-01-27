package com.kavya.myapplication.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.kavya.myapplication.Model.PostsModel;
import com.kavya.myapplication.Retrofit.APIInterface;
import com.kavya.myapplication.Retrofit.RetrofitClient;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {
    private APIInterface apiInterface;
    private MutableLiveData<List<PostsModel>> mutableLiveData;
    private int lastPage =0;
    public PostsRepository(){
        apiInterface= RetrofitClient.getRetrofit().create(APIInterface.class);
    }

    public LiveData<List<PostsModel>> getPostsLiveData() {
        if (mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void getPostswithpageno(int pageno) {
        if(lastPage>=pageno){
            apiInterface.getPostsFromAPI("story",pageno).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if(response.isSuccessful()){
                        List<PostsModel> postsModelList = new ArrayList<>();
                        String title="";
                        String created_at="";
                        if (response.body() != null) {
                            lastPage=response.body().getAsJsonObject().get("nbPages").getAsInt();
                            JsonArray jsonArray = response.body().getAsJsonObject().get("hits").getAsJsonArray();
                            for (int i=0;i<jsonArray.size();i++){
                                title=jsonArray.get(i).getAsJsonObject().get("title").getAsString();
                                created_at=jsonArray.get(i).getAsJsonObject().get("created_at").getAsString();
                                final  PostsModel postsModel= new PostsModel();
                                postsModel.setTitle(title);
                                postsModel.setCreated_at(created_at);
                                postsModel.setSwitchChecked(false);
                                postsModelList.add(postsModel);
                            }
                            mutableLiveData.postValue(postsModelList);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    Log.e("error",t.getLocalizedMessage());

                }
            });

        }
    }
}
