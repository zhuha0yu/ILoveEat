package com.example.ILoveEat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Explore_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Explore_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Explore_main extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private ArrayList<Food> foodListoriginal = new ArrayList<Food>();
    private FirebaseFirestore db;
    //自定义recyclerveiw的适配器
    private RecycleAdapter_FoodExplore mRecyclerAdapter;
    private Handler mHandler;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Explore_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Explore_main.
     */
    // TODO: Rename and change types and number of parameters
    public static Explore_main newInstance(String param1, String param2) {
        Explore_main fragment = new Explore_main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseFirestore.getInstance();
        mHandler = new Handler(){
            //继承实现Handler的handleMessage方法，该方法在UI线程中执行
            @Override
            public void handleMessage(Message msg) {
                //根据线程回传的的表示信息做相应的UI处理
                switch (msg.what){
                    case 0:
                        initRecyclerView();
                        break;
                    case 1:

                        break;
                }

            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_explore_main, container, false);


        setData2();
        getData();
        return view;

    }

private void setData()
{
    ArrayList<String> comments=new ArrayList<>();
    comments.add("0");
    comments.add("1");
    comments.add("2");
    comments.add("3");

    for (Integer i = 0; i < 10; i++)
    {
        //Food food = new Food("gs://iloveeat-bf44e.appspot.com/default/foodimg.png", i.toString(),"Food"+i.toString(),"10"+i.toString()+"kr",comments,(float)i%3+2,(float)(i+1)%3+2,(float)(i+2%3+2),(float)i%2+3);
        //db.collection("dishes").document(i.toString()).set(food);
    }
}
    private void setData2()
    {
        String[] content={"I love it!","It is spicy","i hate it","So sweet!"};


        for (Integer i = 0; i < 4; i++)
        {


            Comment comment=new Comment(i*7+4,"hsxslEmCgBQqhCJaoB5BiavvQJ92",i.toString(),"gs://iloveeat-bf44e.appspot.com/default/foodimg.png",content[i],"10"+i.toString()+"kr", System.currentTimeMillis(),i%3+2,(i+1)%3+2,(i+2%3+2),i%2+3);
            db.collection("comment").document(i.toString()).set(comment);
        }
    }
private void getData() {

        for (Integer i = 0; i < 10; i++) {


            Integer finalI = i;
            db.collection("dishes").document(i.toString())
                    .get()
                    .addOnCompleteListener((OnCompleteListener<DocumentSnapshot>) task -> {
                        if (task.isSuccessful()) {
                                Food food=task.getResult().toObject(Food.class);
                                foodList.add(food);
                                foodListoriginal.add(food);
                                if(foodList.size()>=10)
                                    startload(foodList);
                            }
                        }
                    );



        }




    }
    private void startload(ArrayList<Food> foodlist)
    {
        String[] ids=new String[10];
        String[] urls=new String[10];
        for(int i=0;i<foodlist.size();i++)
        {
            ids[i]=foodlist.get(i).getFoodid();
            urls[i]=foodlist.get(i).getImageurl();
        }
        Thread mthread=new LoadThread(urls,ids,mHandler,getActivity().getCacheDir());
        mthread.start();
    }


    private void initRecyclerView() {
        //获取RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_explore);
        //创建adapter
        mRecyclerAdapter = new RecycleAdapter_FoodExplore(getActivity(),this, foodList);
        //给RecyclerView设置adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mRecyclerAdapter.setOnItemClickListener(new RecycleAdapter_FoodExplore.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Food data) {
                //此处进行监听事件的业务处理
                Intent intent = new Intent(getActivity(), Food_detailActivity.class);
                intent.putExtra("food",data);
                startActivity(intent);
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onStart() {
        super.onStart();

        setSpi();


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

    public void setSpi() {

        Spinner spinner1 = (Spinner) getActivity().findViewById(R.id.spi_nationality);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.Nationality, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(mRecyclerAdapter!=null)mRecyclerAdapter.notifyDataSetChanged();
switch (position)
{
    case 1:foodList.sort(new CompareAsia(true));break;
    case 3:foodList.sort(new CompareEurope(true));break;
    default:foodList.sort(new CompareAll(true));
}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner2 = (Spinner) getActivity().findViewById(R.id.spi_prefer);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.Prefer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        foodList.sort(new CompareAll(true));
                        break;
                    case 1:
                        foodList.sort(new CompareSalty(true));
                        break;
                    case 2:
                        foodList.sort(new CompareSweet(true));
                        break;
                    case 3:
                        foodList.sort(new CompareSpicy(true));
                        break;
                }
                if(mRecyclerAdapter!=null)mRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner3 = (Spinner) getActivity().findViewById(R.id.spi_price);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.Price, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodList.clear();
                foodList.addAll(foodListoriginal);
                int low=0;int high=1000;
                switch (position)
                {
                    case 0:
                        low=0;
                        high=Integer.MAX_VALUE;
                        break;
                    case 1:
                        low=0;
                        high=50;
                        break;
                    case 2:
                        low=50;
                        high=100;
                        break;
                    case 3:
                        low=100;
                        high=200;
                        break;
                    case 4:
                        low=200;
                        high=300;
                        break;
                    case 5:
                        low=300;
                        high=Integer.MAX_VALUE;
                        break;
                }
                for(int i=foodList.size()-1;i>=0;i--)
                    if(foodList.get(i).getFoodprice()<low||foodList.get(i).getFoodprice()>=high)
                        foodList.remove(i);
                if(mRecyclerAdapter!=null)mRecyclerAdapter.notifyDataSetChanged();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                foodList.clear();
                foodList.addAll(foodListoriginal);
            }
        });

    }

    private final class CompareSpicy implements Comparator<Food> {
        boolean is_Ascend;

        public CompareSpicy(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getSpicy().compareTo(o2.getSpicy());
            else
                return o2.getSpicy().compareTo(o1.getSpicy());
        }
    }
    private final class CompareSweet implements Comparator<Food> {
        boolean is_Ascend;

        public CompareSweet(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getSweet().compareTo(o2.getSweet());
            else
                return o2.getSweet().compareTo(o1.getSweet());
        }
    }
    private final class CompareSalty implements Comparator<Food> {
        boolean is_Ascend;

        public CompareSalty(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getSalty().compareTo(o2.getSalty());
            else
                return o2.getSalty().compareTo(o1.getSalty());
        }
    }
    private final class CompareAll implements Comparator<Food> {
        boolean is_Ascend;

        public CompareAll(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getOverall().compareTo(o2.getOverall());
            else
                return o2.getOverall().compareTo(o1.getOverall());
        }
    }
    private final class CompareAsia implements Comparator<Food> {
        boolean is_Ascend;

        public CompareAsia(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getAsia().compareTo(o2.getAsia());
            else
                return o2.getAsia().compareTo(o1.getAsia());
        }
    }
    private final class CompareEurope implements Comparator<Food> {
        boolean is_Ascend;

        public CompareEurope(boolean b) {
            // TODO Auto-generated constructor stub
            is_Ascend = b;
        }

        @Override
        public int compare(Food o1, Food o2) {
            // TODO Auto-generated method stub
            if (is_Ascend)
                return o1.getEurope().compareTo(o2.getEurope());
            else
                return o2.getEurope().compareTo(o1.getEurope());
        }
    }



}

