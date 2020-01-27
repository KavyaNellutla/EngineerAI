package com.kavya.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kavya.myapplication.Adapter.PostsAdapter;
import com.kavya.myapplication.ItemClickListener.PostsItemClicklistener;
import com.kavya.myapplication.Model.PostsModel;
import com.kavya.myapplication.view.Model.PostsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView selected_count;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private PostsViewModel postsViewModel;
    private int lastPageIterated=0;
    private List<PostsModel> postsModelList;
    private PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        postsViewModel =  ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getPostsWithPageno(lastPageIterated);
        postsModelList= new ArrayList<>();
        getAdapter();
        getRecycler();
        addOnScroll();
        observablePostsLiveData();
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                postsModelList.clear();
                postsViewModel.getPostsWithPageno(lastPageIterated);
                selected_count.setText("0");

            }
        });
    }

    private void observablePostsLiveData() {
        postsViewModel.getPostsLiveData().observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                progressBar.setVisibility(View.GONE);
                postsModelList.addAll(postsModels);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getRecycler() {
        DividerItemDecoration decoration= new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(postsAdapter);
    }

    private void addOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!recyclerView.canScrollVertically(1)){
                    progressBar.setVisibility(View.VISIBLE);
                    postsViewModel.getPostsWithPageno(lastPageIterated++);
                }
            }
        });
    }

    private void getAdapter() {
        postsAdapter= new PostsAdapter(postsModelList, new PostsItemClicklistener() {
            @Override
            public void onItemClick(int position, int selectedcount) {
                selected_count.setText(String.valueOf(selectedcount));
            }
        });

    }

    private void initViews() {
        toolbar=findViewById(R.id.toolbar);
        selected_count=findViewById(R.id.selected_count);
        progressBar=findViewById(R.id.progress);
        swipeRefreshLayout=findViewById(R.id.swipe);
        recyclerView=findViewById(R.id.recycler);
    }
}
