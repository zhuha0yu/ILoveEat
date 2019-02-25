package com.example.ILoveEat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleAdapter_Messages extends RecyclerView.Adapter<RecycleAdapter_Messages.myViewHodler> {
    private Context context;
    private ArrayList<Messages> messagesList;

    //创建构造函数
    public RecycleAdapter_Messages(Context context, ArrayList<Messages> messagesList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.messagesList = messagesList;//实体类数据ArrayList
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
        View itemView;
        itemView = View.inflate(context, R.layout.message_layout, null);
        return new myViewHodler(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return (int)messagesList.get(position).getMessagetype();
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
        Messages data = messagesList.get(position);
        switch ((int)data.getMessagetype()) {
            case 0:
                break;
            case 1:
                holder.mUserImg.setImageResource(R.drawable.u3);
                holder.mUserName.setBackgroundColor(0xFFFF8800);
                holder.mContent1.setText(R.string.text_commentreplied_default);
                holder.mContent2.setText(R.string.food_comment_reply_dafault);

                break;
            case 2:
                holder.mUserImg.setImageResource(R.drawable.systemmessage);
                holder.mUserName.setBackgroundColor(0xFF00DDFF);
                holder.mUserName.setText(data.getUsername());
                holder.mContent1.setText(R.string.system_message_default);
                holder.mContent2.setVisibility(View.GONE);
                break;
            default:
                holder.mUserImg.setImageResource(R.drawable.systemmessage);
                holder.mUserName.setBackgroundColor(0xFF00DDFF);
                holder.mContent1.setText(R.string.system_message_default);
                holder.mContent2.setVisibility(View.GONE);
                break;
        }


    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mUserImg;
        private TextView mUserName;
        private TextView mContent1;
        private TextView mContent2;

        public myViewHodler(View itemView) {
            super(itemView);
            mUserImg = (ImageView) itemView.findViewById(R.id.imageView_user);
            mUserName = (TextView) itemView.findViewById(R.id.textView_username);
            mContent1 = (TextView) itemView.findViewById(R.id.textView_content1);
            mContent2 = (TextView) itemView.findViewById(R.id.textView_content2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, messagesList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, Messages data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}