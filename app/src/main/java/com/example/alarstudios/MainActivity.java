package com.example.alarstudios;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.alarstudios.api.AlarstudiosApi;
import com.example.alarstudios.utils.App;
import com.example.alarstudios.utils.ConstantManager;
import com.example.alarstudios.utils.MessageView;
import com.example.alarstudios.utils.NetworkStatusChecker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MessageView {
  public static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.login)
  AppCompatEditText mLogin;
  @BindView(R.id.password)
  AppCompatEditText mPassword;

  @Inject
  AlarstudiosApi mApi;

  private String code;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    App.getAppComponent().inject(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  private boolean checkInputField() {
    if (!TextUtils.isEmpty(mLogin.getText()) && !TextUtils.isEmpty(mPassword.getText())) {
      return true;
    } else {
      showMessage(R.string.login_form_toast_empty_fields);
      return false;
    }
  }

  @OnClick(R.id.enter_btn)
  public void enterClick() {
    if (checkInputField()) {
    Log.d(TAG, "enterClick: " + mLogin.getText().toString());
    Log.d(TAG, "enterClick: " +  mPassword.getText().toString());
      mApi.authentification(mLogin.getText().toString(), mPassword.getText().toString())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(authModel -> {
            if (authModel.getStatus().equals("error")) {
              showMessage(R.string.login_form_toast_error_fields);
            } else {
              code = authModel.getCode();
              Intent intent = new Intent(this, TableActivity.class);
              intent.putExtra(ConstantManager.CODE, code);
              startActivity(intent);
            }
          }, error -> {
            if (!NetworkStatusChecker.isNetworkAviable(getApplicationContext())) {

            } else {
              error.printStackTrace();
              showMessage(R.string.login_form_toast_error_text);
              showMessage(error.getMessage());
            }
          });
    }
  }

  @Override
  public void showMessage(int message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }

  @Override
  public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
  }
}
