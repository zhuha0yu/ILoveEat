package com.example.ILoveEat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleAdapter_comments extends RecyclerView.Adapter<RecycleAdapter_comments.myViewHodler> {
    private Context context;
    private ArrayList<Comment> commentList;

    //创建构造函数
    public RecycleAdapter_comments(Context context, ArrayList<Comment> commentList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.commentList = commentList;//实体类数据ArrayList
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
        View itemView = View.inflate(context, R.layout.comment_layout_fooddetailpage, null);
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
        Comment data = commentList.get(position);
//        holder.mItemGoodsImg;
       // holder.mFoodName.setText(data.getFoodname());//获取实体类中的name字段并设置
     //   holder.mFoodPrice.setText(data.getFoodprice());//获取实体类中的price字段并设置

    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return commentList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mFoodImg;
        private TextView mFoodName;
        private TextView mFoodPrice;

        public myViewHodler(View itemView) {
            super(itemView);
  /*          mFoodImg = (ImageView) itemView.findViewById(R.id.imageView_food);
            mFoodName = (TextView) itemView.findViewById(R.id.textView_foodname);
            mFoodPrice = (TextView) itemView.findViewById(R.id.textView_foodprice);*/
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, commentList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, Comment data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}