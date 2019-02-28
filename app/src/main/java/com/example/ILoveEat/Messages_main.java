package com.example.ILoveEat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Messages_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Messages_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Messages_main extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int number_of_messages=0;
    private ArrayList<String> messagedatalist=new ArrayList<>();
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mRecyclerView;//定义RecyclerView
    private ArrayList<Messages> messageList = new ArrayList<Messages>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecycleAdapter_Messages mRecyclerAdapter;
    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Messages_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Messages_main.
     */
    // TODO: Rename and change types and number of parameters
    public static Messages_main newInstance(String param1, String param2) {
        Messages_main fragment = new Messages_main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
        DocumentReference docref=db.collection("user").document(mAuth.getCurrentUser().getUid());

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                messagedatalist=(ArrayList<String>)task.getResult().get("messageid");
                number_of_messages=messagedatalist.size();
                initData();
                if(number_of_messages==0)
                    getActivity().findViewById(R.id.textView_recentmessage).setVisibility(View.VISIBLE);
            }
        });
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_messages_main, container, false);




        return view;
    }
@Override
public void onStart()
{

    super.onStart();
}
    private void initData() {
        for(int i=0;i<number_of_messages;i++)
        {
            DocumentReference docref=db.collection("message").document(messagedatalist.get(i));

                    docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful())
                    {

                        messageList.add(task.getResult().toObject(Messages.class));
                        if(messageList.size()>=number_of_messages)
                            initRecyclerView();
                        if(number_of_messages==0)
                            getActivity().findViewById(R.id.textView_recentmessage).setVisibility(View.VISIBLE);
                        else
                            getActivity().findViewById(R.id.textView_recentmessage).setVisibility(View.GONE);
                    }
                }
            });


        }
    }
@Override
public void onResume()
{
    if(number_of_messages==0)
        getActivity().findViewById(R.id.textView_recentmessage).setVisibility(View.VISIBLE);
    else
        getActivity().findViewById(R.id.textView_recentmessage).setVisibility(View.GONE);
    super.onResume();
}
    private void initRecyclerView() {
        //获取RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_messages);
        //创建adapter
        mRecyclerAdapter = new RecycleAdapter_Messages(getActivity(), messageList);
        //给RecyclerView设置adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mRecyclerAdapter.setOnItemClickListener(new RecycleAdapter_Messages.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Messages data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(), "我是item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
