package com.naruto.didi2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.AppCopy;
import com.naruto.didi2.util.LogUtils;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/11/16
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.VH> implements View.OnClickListener {
    private List<AppCopy> mList;
    private Context context;


    public AppAdapter(List<AppCopy> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.app_item, viewGroup, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, int position) {
        AppCopy app = mList.get(position);
        holder.title.setText(app.getAppName());
        //将position保存在itemView的Tag中
        holder.itemView.setTag(position);
        //条目点击事件
        holder.itemView.setOnClickListener(this);
    }

    private void removeItem(int position) {
        LogUtils.e("移除数据：" + mList.get(position));
        mList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = (int) v.getTag();
            listener.onClick(v, mList.get(position).getPkg());
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        private final TextView title;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_app_title);
        }
    }

    public void upView(RecyclerView mRecyclerView, int position, int firstItemPosition) {
        try {
            LogUtils.e("position:" + position + "==firstItemPosition:" + firstItemPosition);
            if (position - firstItemPosition >= 0) {
                //得到要更新的item的view
                View view = mRecyclerView.getChildAt(position - firstItemPosition);
                if (null != mRecyclerView.getChildViewHolder(view)) {
                    VH holder = (VH) mRecyclerView.getChildViewHolder(view);
//                    holder.txt.setText("你是猪");
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, String pkg);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
