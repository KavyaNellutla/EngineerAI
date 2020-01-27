package com.kavya.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView selected_count;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar=findViewById(R.id.toolbar);
        selected_count=findViewById(R.id.selected_count);
        progressBar=findViewById(R.id.progress);
        swipeRefreshLayout=findViewById(R.id.swipe);
        recyclerView=findViewById(R.id.recycler);
    }
}
