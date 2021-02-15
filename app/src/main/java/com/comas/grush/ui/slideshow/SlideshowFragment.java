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

    private FirebaseAuth mAuth;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSubmitButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        initializeViewElements(root);
        initializeViewHandlers();

        return root;
    }

    private void initializeViewElements(View view) {
        mAuth = FirebaseAuth.getInstance();
        mEmailEditText = view.findViewById(R.id.auth_frag_email_edittext);
        mPasswordEditText = view.findViewById(R.id.auth_frag_password_edittext);
        mSubmitButton = view.findViewById(R.id.auth_frag_submit_button);
    }

    private void initializeViewHandlers() {
        mSubmitButton.setOnClickListener(this::handleSubmit);
    }

    private void handleSubmit(View view) {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmailAndPassword:success");
                        FirebaseUser user = mAuth.getCurrentUser();


                        Menu temp = navigationView.getMenu();
                        MenuItem slideshow = temp.findItem(R.id.nav_slideshow);
                        slideshow.setTitle("Logout");


                        View headerView = navigationView.getHeaderView(0);
                        TextView text = headerView.findViewById(R.id.nav_header_user_email);
                        text.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                        slideshow.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                FirebaseAuth.getInstance().signOut();
                                slideshow.setTitle("Login");
                                text.setText("Not logged in");


                                return false;
                            }
                        });

                        Navigation.findNavController(view).popBackStack();

                        // updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmailAndPassword:failure", task.getException());
                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        // updateUI(null);
                    }
                    // ...
                });
    }
}