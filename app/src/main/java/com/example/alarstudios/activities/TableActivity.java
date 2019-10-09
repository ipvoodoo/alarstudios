package com.example.alarstudios.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.alarstudios.R;
import com.example.alarstudios.adapters.DataAdapter;
import com.example.alarstudios.adapters.DataAdapter.ItemClickListener;
import com.example.alarstudios.api.AlarstudiosApi;
import com.example.alarstudios.model.Datum;
import com.example.alarstudios.utils.App;
import com.example.alarstudios.utils.ConstantManager;
import com.example.alarstudios.utils.NetworkStatusChecker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class TableActivity extends BaseActivity implements ItemClickListener, OnRefreshListener {

  @Inject
  AlarstudiosApi mApi;

  @BindView(R.id.recycler)
  RecyclerView mRecycler;
  @BindView(R.id.refresher)
  SwipeRefreshLayout mRefresher;

  private DataAdapter mDataAdapter = new DataAdapter(this);
  private String code;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.getAppComponent().inject(this);
    ButterKnife.bind(this);
    mRefresher.setOnRefreshListener(this);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      code = bundle.getString(ConstantManager.CODE);
    }
    onRefresh();
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.activity_table;
  }

  private void setupRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mRecycler.setLayoutManager(layoutManager);
    mRecycler.setItemAnimator(new DefaultItemAnimator());
    mRecycler.setAdapter(mDataAdapter);
  }

  @Override
  public void onItemClick(Datum model) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(ConstantManager.DATA, model);
    Intent intent = new Intent(this, CardActivity.class);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void onRefresh() {
    getData();
  }

  public void getData() {
    mDisposable = mApi.getData(code)
        .subscribeOn(Schedulers.io())
        .doOnSuccess(data -> mRefresher.isRefreshing())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(data -> {
          if (data.getStatus().equals("ok")) {
            mRefresher.setRefreshing(false);
            mDataAdapter.addData(data.getData());
            setupRecycler();
          } else {
            showMessage(R.string.table_toast_error_data);
          }
        }, error -> {
          if (!NetworkStatusChecker.isNetworkAviable(getApplicationContext())) {
            showMessage(R.string.network_error);
          } else {
            error.printStackTrace();
            showMessage(R.string.login_form_toast_error_text);
            showMessage(error.getMessage());
          }
        });
  }

}
