package com.example.test2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Profile_main.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Profile_main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile_main extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Boolean LoginExists=false;
    private SharedPreferences sp;
    private  String useremail;
    private View  mProfileFormView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Profile_main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile_main.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile_main newInstance(String param1, String param2) {
        Profile_main fragment = new Profile_main();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_main, container, false);
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
public void onStart()
{

    Button btn_signinout=(Button)getActivity().findViewById(R.id.btn_signin_out) ;
    btn_signinout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            btn_signin_out();
        }
    });
    setlabels();
    setspi();
    setprofiles();
    super.onStart();
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

    public void setspi()
    {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spi_nationality_pro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.Nationality, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void setlabels()
    {
        LabelsView labelsView;
        labelsView = (LabelsView) getActivity().findViewById(R.id.label_sweet_p);
        ArrayList<String> label = new ArrayList<>();
        label.add("No");
        label.add("Little");
        label.add("Very");
        labelsView.setLabels(label);

        labelsView = (LabelsView) getActivity().findViewById(R.id.label_spicy_p);
        label = new ArrayList<>();
        label.add("No");
        label.add("Little");
        label.add("Very");
        labelsView.setLabels(label);

        labelsView = (LabelsView) getActivity().findViewById(R.id.label_salty_p);
        label = new ArrayList<>();
        label.add("No");
        label.add("Little");
        label.add("Very");

        labelsView.setLabels(label);
    }
    public void setprofiles()
    {
        mProfileFormView = getActivity().findViewById(R.id.loged_in_form);
       if(!LoginExists)
       {
           mProfileFormView.setVisibility(View.GONE);
       }
       else
       {
           mProfileFormView.setVisibility(View.VISIBLE);
       }

    }
    public void btn_signin_out()
    {

        SharedPreferences sp =getActivity().getSharedPreferences ("LoginDetails", getContext().MODE_PRIVATE);
        this.LoginExists=sp.getBoolean("IfLogin",false);
        this.useremail=sp.getString("UserEmail","");
        Button btn_signinout=(Button)getActivity().findViewById(R.id.btn_signin_out) ;
        if(this.LoginExists)
        {
            SharedPreferences.Editor editor = sp.edit();
            LoginExists=false;
            editor.putBoolean("IfLogin",false);
            editor.commit();
            new  AlertDialog.Builder(this.getContext())
                    .setTitle("Log out successful!")
                    .setMessage("You have successfully log out  from Iloveeat!" )
                    .setPositiveButton("Ok" ,  null )

                    .show();

            btn_signinout.setText(R.string.action_sign_in);
        }
        else
            {
            Intent intent_login = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent_login);
            btn_signinout.setText(R.string.btn_logout);
            LoginExists=true;
        }
        setprofiles();
    }
}
