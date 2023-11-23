package com.naruto.didi2.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.KeyStoreUtil;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class KeyStoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> aliasList = new ArrayList<>();
    private EditText editText;
    private TextView tvKey;
    private TextView tvCipher;

    private String plainText; //明文
    private String encryptData; //加密后字符串

    private String currentSelectedKeyAlias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_store);
        initViews();
        updateKeys();

        //验证数据签名
        String data = "1234";
        byte[] sign = KeyStoreUtil.get().sign(data.getBytes(), "qq");
//        System.out.println("verify: " + KeyStoreUtil.get().verify(data.getBytes(), sign, "qq"));
        LogUtils.e("verify: " + KeyStoreUtil.get().verify(data.getBytes(), sign, "qq"));
    }

    private void updateKeys() {
        aliasList.clear();
        Enumeration<String> aliases = KeyStoreUtil.get().getAliases();
        if (aliases != null) {
            while (aliases.hasMoreElements()) {
                String s = aliases.nextElement();
                aliasList.add(s);
                LogUtils.e("查询到密钥：" + s);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("StringFormatInvalid")
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL));
        adapter = new Adapter();
        adapter.setItemClickListener(itemClickListener);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.edit_text);
        tvKey = findViewById(R.id.tv_current);
        tvCipher = findViewById(R.id.tv_cipher);

        tvKey.setText(getString(R.string.current_key, ""));

        plainText = getString(R.string.plaintext);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {
            aliasList.clear();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onAddKey(View view) {
        String alias = editText.getText().toString();
        if (!TextUtils.isEmpty(alias)) {
            KeyStoreUtil.get().generateKey(getBaseContext(), alias);
            updateKeys();
        }
    }

    public void onDeleteKey(View view) {
        deleteKey(editText.getText().toString());
    }

    private void deleteKey(String alias) {
        if (!TextUtils.isEmpty(alias)) {
            KeyStoreUtil.get().deleteKey(alias);
            updateKeys();
        }
    }

    private OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            currentSelectedKeyAlias = aliasList.get(position);
            tvKey.setText("当前选择key:" + currentSelectedKeyAlias);
        }

        @Override
        public boolean onItemLongClick(View view, int position) {
            deleteKey(aliasList.get(position));
            return true;
        }
    };

    public void doEncrypt(View view) {
        if (currentSelectedKeyAlias == null) {
            Toast.makeText(getApplicationContext(), "请先选取alias", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] data = KeyStoreUtil.get().encrypt(plainText.getBytes(), currentSelectedKeyAlias);
        if (data != null) {
            encryptData = Base64.encodeToString(data, Base64.DEFAULT);
            tvCipher.setText(encryptData);
        }
    }

    public void doDecrypt(View view) {
        if (currentSelectedKeyAlias == null) {
            Toast.makeText(getApplicationContext(), "请先选取alias", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            byte[] data = KeyStoreUtil.get().decrypt(Base64.decode(encryptData, Base64.DEFAULT), currentSelectedKeyAlias);
            if (data != null) {
                String s = new String(data, "UTF-8");
                tvCipher.setText(s);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_name);
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private OnItemClickListener itemClickListener;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.layout_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.textView.setText(aliasList.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemClickListener != null) {
                        return itemClickListener.onItemLongClick(v, position);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return aliasList.size();
        }

        public void setItemClickListener(OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }
}
