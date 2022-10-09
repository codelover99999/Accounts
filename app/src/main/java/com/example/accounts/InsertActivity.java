package com.example.accounts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accounts.bean.Account;
import com.example.accounts.database.config.AccountsSQLiteOpenHelper;

public class InsertActivity extends AppCompatActivity {

    private AccountsSQLiteOpenHelper db = AccountsSQLiteOpenHelper.getaInstance(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_insert);
        Button btn = findViewById(R.id.closeActivity);
        Button saveButton = findViewById(R.id.saveButton);
        EditText accountText = findViewById(R.id.account);
        EditText usernameText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertActivity.this.finish();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountText.getText().toString().trim();
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString();
                if (username == null || "".equals(username)) {
                    return ;
                }
                if (account == null || "".equals(account)) {
                    return ;
                }
                Account accountItem = new Account(account, username, password);
                int i = db.insertAccount(accountItem);
                Log.i("insert:", String.valueOf(i));
                InsertActivity.this.finish();
            }
        });

    }
}