package com.example.usermanagementmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginFragment extends Fragment {
    private EditText etUsername , etPassword ;
    private TextView tvSignupLink;
    private Button btnLogin, btnForgotPassword;
    private  FirebaseServices fbs ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        // connecting components
        fbs = FirebaseServices.getInstance() ;
        etUsername = getView().findViewById(R.id.etUsernameLogin);
        etPassword = getView().findViewById(R.id.etPasswordLogin);
        btnLogin = getView().findViewById(R.id.btnLoginLogin) ;
        tvSignupLink = getView().findViewById(R.id.tvSignupLinkLogin);
        btnForgotPassword = getView().findViewById(R.id.btnForgotPassword);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 gotoForgotPassword();
                                                 }
        });
        tvSignupLink.setOnClickListener(new View.OnClickListener() {
              @Override
                  public void onClick(View view) {
                        gotoSignupFragment();
                   }

        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Data validation
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.trim().isEmpty() && password.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Login procedure
                fbs.getAuth().signInWithEmailAndPassword(username, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity() , "You have successfully logged in!" , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity() , "Failed to login! check user or password!" , Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }
        });



    }


    private void gotoSignupFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelLayoutMain , new SignupFragment());
        ft.commit();
    }


    private void gotoForgotPassword() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelLayoutMain , new ForgotPasswordFragment());
        ft.commit();
    }
    }
