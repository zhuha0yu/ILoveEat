package com.example.ILoveEat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.http.Url;


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

    private Button btn_mycomments;
    private Button mbtn_Email;
    private Button mbtn_Password;
    private StorageReference mStorageRef;
    private EditText mUsername;
    private ImageView mImg_user;
    private TextView mTextView_username;
    private Button btn_signinout;
    private Button btn_changeprofile;
    private Button btn_resend;
    private Button btn_submit;
    private View mProfileFormView;
    private View mVerifyemailView;
    private View mButtonsView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();
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
    public void onStart() {

        btn_signinout = getActivity().findViewById(R.id.btn_signin_out);
        mTextView_username = getActivity().findViewById(R.id.text_username);
        btn_signinout.setOnClickListener(view -> btn_signin_out());
        btn_changeprofile = (Button) getActivity().findViewById(R.id.button_change_profiles);
        btn_changeprofile.setOnClickListener(view -> showchangepref());
        btn_resend = getActivity().findViewById(R.id.button_resend);
        btn_resend.setOnClickListener(view -> sendverifyemail());
        btn_submit = getActivity().findViewById(R.id.btn_submitchange);
        btn_submit.setOnClickListener(v -> submitmodify());
        btn_mycomments = getActivity().findViewById(R.id.button_my_comments);
        btn_mycomments.setOnClickListener(v -> {

        });
        setlabels();
        setspi();
        mbtn_Email = getActivity().findViewById(R.id.button_changeemail);
        mbtn_Password = getActivity().findViewById(R.id.button_changepassword);
        mbtn_Email.setOnClickListener(v -> {

        });
        mbtn_Password.setOnClickListener(v -> {
            mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail());
            new AlertDialog.Builder(getContext())
                    .setTitle("E-mail sent!")
                    .setMessage("An E-mail contains a link for reseting password have been sent to " + mAuth.getCurrentUser().getEmail() + ". Please follow the instructions to reset your password.")
                    .setPositiveButton("OK", null)
                    .show();
        });
        mProfileFormView = getActivity().findViewById(R.id.loged_in_form);
        mVerifyemailView = getActivity().findViewById(R.id.layout_verifyemail);
        mButtonsView = getActivity().findViewById(R.id.linearlayout_buttons);
        mVerifyemailView.setVisibility(View.GONE);
        mImg_user=getActivity().findViewById(R.id.img_user);
        mImg_user.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),ChangeUserIconActivity.class);
            startActivity(intent);
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mUsername = getActivity().findViewById(R.id.textedit_username);
        /*
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("default/usericon.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri)

                        .build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
*/





        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
// TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
        if (hidden) {// 不在最前端界面显示

        } else {// 重新显示到最前端中


            setprofiles();


        }
    }

    @Override
    public void onResume() {


        setprofiles();
        super.onResume();
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            mTextView_username.setText("");
            mImg_user.setImageResource(R.drawable.usericon);
            return;
        }
        Uri photouri=currentUser.getPhotoUrl();
        String a=photouri.toString();
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(photouri.toString());
        File localFile= null;

            localFile = new File(getActivity().getCacheDir(),"usericon.png");

        File finalLocalFile = localFile;
        gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Uri uri=Uri.fromFile(finalLocalFile);
                mImg_user.setImageURI(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });




        mTextView_username.setText(currentUser.getDisplayName());

    }

    public void setspi() {
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spi_nationality_pro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.Nationality, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void setlabels() {
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

    public void setprofiles() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        mProfileFormView.setVisibility(View.GONE);

        if (currentUser == null) {
            btn_signinout.setText(R.string.action_sign_in);
            mButtonsView.setVisibility(View.GONE);
        } else {
            btn_signinout.setText(R.string.btn_logout);
            mButtonsView.setVisibility(View.VISIBLE);
            if (!currentUser.isEmailVerified()) {
                mVerifyemailView.setVisibility(View.VISIBLE);
            } else
                mVerifyemailView.setVisibility(View.GONE);

        }

    }

    public void sendverifyemail() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.sendEmailVerification();
        new AlertDialog.Builder(this.getContext())
                .setTitle("Verification email sent!")
                .setMessage("The verification email have been sent to your mailbox!")
                .setPositiveButton("Ok", null)

                .show();


    }

    public void submitmodify() {

        String username = mUsername.getText().toString();

        boolean usernamemodify = !(username.equals(""));
        FirebaseUser user = mAuth.getCurrentUser();

        if (usernamemodify) {
            user.updateProfile(new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)

                    .build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        tasksuccess();
                    } else return;
                }
            });

        }


        /*new  AlertDialog.Builder(this.getContext())
                .setTitle("Vreify your password")
                .setView(R.layout.simple_password_input_layout)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    clearchanges();
                    }
                    })
                .show();*/
    }

    public void tasksuccess() {
        new AlertDialog.Builder(this.getContext())
                .setTitle("Change succeed!")
                .setMessage("Your profile have been updated!")
                .setPositiveButton("OK", null)
                .show();
        super.onResume();
    }

    public void showchangepref() {
        mProfileFormView.setVisibility(View.VISIBLE);
    }

    public void btn_signin_out() {
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
            mAuth.signOut();

            new AlertDialog.Builder(this.getContext())
                    .setTitle("Log out successful!")
                    .setMessage("You have successfully log out  from Iloveeat!")
                    .setPositiveButton("Ok", null)

                    .show();


        } else {
            Intent intent_login = new Intent(getActivity(), LoginActivity.class);

            startActivity(intent_login);
        }
        setprofiles();
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
