package com.example.ILoveEat;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
        holder.mCommentcontent.setText(data.getCommentcontent());
        holder.mPrice.setText(data.getPrice());
        holder.mRatingbar_sweet.setRating(data.getSweet());
        holder.mRatingbar_salty.setRating(data.getSalty());
        holder.mRatingbar_spicy.setRating(data.getSpicy());
        holder.mRatingbar_overall.setRating(data.getOverall());
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

        private TextView mCommentcontent;
        private TextView mUsername;
        private TextView mNationality;
        private TextView mTimestamp;
        private TextView mPrice;
        private EditText mReplyComment;
        private AppCompatImageButton mButton_reply;
        private RatingBar mRatingbar_sweet;
        private RatingBar mRatingbar_spicy;
        private RatingBar mRatingbar_salty;
        private RatingBar mRatingbar_overall;
        private View view;
        public myViewHodler(View itemView) {
            super(itemView);
            this.view=itemView;
            mReplyComment=itemView.findViewById(R.id.editText_replycomment);
            mButton_reply=itemView.findViewById(R.id.imageButton_reply);
            mCommentcontent=itemView.findViewById(R.id.textView_commentcontent);
            mUsername=itemView.findViewById(R.id.textView_username_comments);
            mNationality=itemView.findViewById(R.id.textView_nationality);
            mTimestamp=itemView.findViewById(R.id.textView_time_comments);
            mPrice= itemView.findViewById(R.id.textView_foodprice_comment);
            mReplyComment.setVisibility(View.INVISIBLE);
            mRatingbar_salty=itemView.findViewById(R.id.ratingBar_salty);
            mRatingbar_spicy=itemView.findViewById(R.id.ratingBar_spicy);
            mRatingbar_sweet=itemView.findViewById(R.id.ratingBar_sweet);
            mRatingbar_overall=itemView.findViewById(R.id.ratingBar_overall);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, commentList.get(getLayoutPosition()));
                    }
                }
            });
            mButton_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showorhide(mReplyComment);

                }
            });

        }

        public void showorhide(EditText editText)
        {
            if(editText.getVisibility()==View.VISIBLE)
                editText.setVisibility(View.INVISIBLE);
            else
            if(editText.getVisibility()==View.INVISIBLE)
            {
                editText.setVisibility(View.VISIBLE);
            editText.requestFocus();
            }
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