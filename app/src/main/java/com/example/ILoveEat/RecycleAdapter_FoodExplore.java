package com.example.ILoveEat;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecycleAdapter_FoodExplore extends RecyclerView.Adapter<RecycleAdapter_FoodExplore.myViewHodler> {
    private Context context;
    private Fragment fragment;
    private ArrayList<Food> FoodList;

    //创建构造函数
    public RecycleAdapter_FoodExplore(Context context, Fragment fragment,ArrayList<Food> foodList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.fragment=fragment;
        this.FoodList = foodList;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.sample_food_explore, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根据点击位置绑定数据
        Food data = FoodList.get(position);
//        holder.mItemGoodsImg;
        holder.mFoodName.setText(data.getFoodname());//获取实体类中的name字段并设置
        holder.mFoodPrice.setText(data.getFoodprice().toString()+"kr");//获取实体类中的price字段并设置
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(data.getImageurl());
        holder.mFoodStars.setRating((float)data.getOverall());
        File localFile= new File(context.getCacheDir(),"food"+data.getFoodid()+"image");

                Uri uri=Uri.fromFile(localFile);
                holder.mFoodImg.setImageURI(uri);

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return FoodList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mFoodImg;
        private TextView mFoodName;
        private TextView mFoodPrice;
        private RatingBar mFoodStars;
        public myViewHodler(View itemView) {
            super(itemView);
            mFoodStars=itemView.findViewById(R.id.ratingBar_explore);
            mFoodImg = (ImageView) itemView.findViewById(R.id.imageView_food);
            mFoodName = (TextView) itemView.findViewById(R.id.textView_foodname);
            mFoodPrice = (TextView) itemView.findViewById(R.id.textView_foodprice);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, FoodList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, Food data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}