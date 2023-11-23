package com.naruto.didi2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.naruto.didi2.R;
import com.naruto.didi2.activity.TuActivity;
import com.naruto.didi2.bean.MeiTu;
import com.naruto.didi2.bean.MeiTuBean;
import com.naruto.didi2.util.LogUtils;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/10/16/0016
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.VH> {
    private List<MeiTuBean.NewslistBean> list;
    private List<MeiTu.NewslistBean> mList;
    private Context context;

//    public PicAdapter(List<MeiTuBean.NewslistBean> list) {
//        this.list = list;
//    }

    public PicAdapter(List<MeiTu.NewslistBean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.pic_item, viewGroup, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, int position) {
//        vh.title.setText(list.get(i));
//        final MeiTuBean.NewslistBean newslistBean = list.get(position);
        final MeiTu.NewslistBean newslistBean = mList.get(position);
//        String title = newslistBean.getTitle();
//        holder.title.setText(position+"");
        String picUrl = newslistBean.getPicUrl();
        Glide.with(context).load(picUrl)
                .thumbnail(0.1f)
                .into(holder.pic);
        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TuActivity.class);
                intent.putExtra("url", newslistBean.getPicUrl());
                context.startActivity(intent);
            }
        });
    }

    private void removeItem(int position) {
        LogUtils.e("移除数据：" + list.get(position));
        list.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView pic;

        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            pic = itemView.findViewById(R.id.iv_pic);
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
}
