package com.example.electricitybillcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    TextView name, email;
    Button signOutButton;
    ImageButton calculate, history;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.home_name);
        email = findViewById(R.id.home_email);
        signOutButton = findViewById(R.id.sign_out_button);
        calculate = findViewById(R.id.to_calculate_page);
        history = findViewById(R.id.to_history_page);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String Name = account.getDisplayName();
            String Email = account.getEmail();

            name.setText(Name);
            email.setText(Email);
        }

        signOutButton.setOnClickListener(view -> SignOut());
        calculate.setOnClickListener(view -> Calculate());
        history.setOnClickListener(view -> History());
    }

    private void SignOut() {

        gsc.signOut().addOnCompleteListener(task -> {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    private void Calculate() {
        Intent intent = new Intent(this, CalculateActivity.class);
        startActivity(intent);
    }

    private void History() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}