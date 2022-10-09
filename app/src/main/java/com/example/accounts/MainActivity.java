package com.example.accounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.accounts.adapter.AccountsAdapter;
import com.example.accounts.bean.Account;
import com.example.accounts.database.config.AccountsSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Account> accounts = new ArrayList<>();
    private AccountsAdapter adapter;
    private AccountsSQLiteOpenHelper db = AccountsSQLiteOpenHelper.getaInstance(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void insertAccount(View view) {
        Intent intent = new Intent(MainActivity.this, InsertActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.this.startActivity(intent);
    }

    public void deleteAccount(View v) {
        Integer id = Integer.valueOf(v.getTag().toString());
        Log.i("delete->_id", id.toString());
        db.deleteAccount(id);
        refresh();
    }

    public void copyPassword(View v) {
        String password = String.valueOf(v.getTag());
        Log.i("copyPassword->password", password);
        ClipboardManager clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData label = ClipData.newPlainText("label", password);
        clip.setPrimaryClip(label);
    }

    public void copyUsername(View v) {
        String username = String.valueOf(v.getTag());
        Log.i("copyUsername->username", username);
        ClipboardManager clip = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData label = ClipData.newPlainText("label", username);
        clip.setPrimaryClip(label);

    }


    private void refresh() {
        accounts = db.getAccounts();

        RecyclerView listView = findViewById(R.id.listView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
        adapter = new AccountsAdapter(accounts, this);
        listView.setAdapter(adapter);
        adapter.setOnRecyclerItemClickListener(new AccountsAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {

            }
        });
    }
}