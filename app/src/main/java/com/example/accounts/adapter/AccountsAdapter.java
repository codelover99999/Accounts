package com.example.accounts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounts.R;
import com.example.accounts.bean.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * user: wuzhongxuan
 * date: 2022/10/7
 */
public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountsViewHolder> {

    private List<Account> accounts = new ArrayList<>();
    private Context context;

    public AccountsAdapter(List<Account> accounts, Context context) {
        this.accounts = accounts;
        this.context = context;
    }

    @NonNull
    @Override
    public AccountsAdapter.AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.recyclerview_item, null);
        return new AccountsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsAdapter.AccountsViewHolder holder, int position) {
        holder.accountTv.setText(accounts.get(position).getAccountname());
        holder.usernameTv.setText(accounts.get(position).getUsername());
        holder.passwordTv.setText(accounts.get(position).getPassword());
        holder.deleteButton.setTag(accounts.get(position).get_id());
        holder.copyUsername.setTag(accounts.get(position).getUsername());
        holder.copyPassword.setTag(accounts.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return accounts == null ? 0 : accounts.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder {

        private TextView accountTv;
        private TextView usernameTv;
        private TextView passwordTv;
        private Button deleteButton;
        private Button copyPassword;
        private Button copyUsername;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            accountTv = itemView.findViewById(R.id.accountTv);
            usernameTv = itemView.findViewById(R.id.usernameTv);
            passwordTv = itemView.findViewById(R.id.passwordTv);
            deleteButton = itemView.findViewById(R.id.deleteAccount);
            copyPassword = itemView.findViewById(R.id.copyPassword);
            copyUsername = itemView.findViewById(R.id.copyUsername);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener == null) {
                        onRecyclerItemClickListener.OnRecyclerItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(
            OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position);
    }

}
