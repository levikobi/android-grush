package com.comas.grush.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.comas.grush.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private static int state;
    private static final int REGISTER = 0;
    private static final int LOGIN = 1;

    private TextView mTitleTextView;
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSubmitButton;
    private TextView mQuestionTextView;
    private TextView mClickHereTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        initializeViewElements(root);
        initializeViewHandlers();

        return root;
    }

    private void initializeViewElements(View view) {
        mTitleTextView = view.findViewById(R.id.auth_frag_title_textview);
        mNameEditText = view.findViewById(R.id.auth_frag_name_edittext);
        mEmailEditText = view.findViewById(R.id.auth_frag_email_edittext);
        mPasswordEditText = view.findViewById(R.id.auth_frag_password_edittext);
        mSubmitButton = view.findViewById(R.id.auth_frag_submit_button);
        mQuestionTextView = view.findViewById(R.id.auth_frag_question_textview);
        mClickHereTextView = view.findViewById(R.id.auth_frag_clickhere_textview);
    }

    private void initializeViewHandlers() {
        mSubmitButton.setOnClickListener(this::handleSubmit);
        mClickHereTextView.setOnClickListener(this::handleClickHere);
    }

    private void handleSubmit(View view) {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        switch (state) {
            case LOGIN:     login(view, email, password);       break;
            case REGISTER:  register(view, email, password);    break;
        }
    }

    private void login(View view, String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "signInWithEmailAndPassword:success");
                        updateUI();
                        Navigation.findNavController(view).popBackStack();
                    } else {
                        Log.w("TAG", "signInWithEmailAndPassword:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void register(View view, String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "createUserWithEmail:success");
                        updateUI();
                        Navigation.findNavController(view).popBackStack();
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI() {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        Menu temp = navigationView.getMenu();
        MenuItem slideshow = temp.findItem(R.id.nav_slideshow);
        slideshow.setTitle("Logout");

        View headerView = navigationView.getHeaderView(0);
        TextView text = headerView.findViewById(R.id.nav_header_user_email);
        text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        slideshow.setOnMenuItemClickListener(item -> {
            FirebaseAuth.getInstance().signOut();
            slideshow.setTitle("Login");
            text.setText("Not logged in");
            return false;
        });
    }

    private void handleClickHere(View view) {
        switch (state) {
            case REGISTER:
                state = LOGIN;
                mQuestionTextView.setText("Don't have an account? ");
                mTitleTextView.setText("Login");
                mNameEditText.setVisibility(View.GONE);
                break;
            case LOGIN:
                state = REGISTER;
                mQuestionTextView.setText("Already have an account? ");
                mTitleTextView.setText("Register");
                mNameEditText.setVisibility(View.VISIBLE);
                break;
            default: break;
        }
    }
}