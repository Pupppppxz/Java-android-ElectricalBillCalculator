package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class HistoryActivity extends AppCompatActivity {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private String nameUser, emailUser;

    private ListView listView;
    private ImageView back;
    private TextView totalAmount, totalUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.his_list);
        back = findViewById(R.id.his_back);
        totalAmount = findViewById(R.id.his_total_amount);
        totalUnit = findViewById(R.id.his_total_unit);

        back.setOnClickListener(view -> BackToHome());

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            nameUser = account.getDisplayName();
            emailUser = account.getEmail();
        }

        initData();

    }

    private void initData() {
        ArrayList<ItemHistory> hisList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("history");
        ArrayList<Double> t = new ArrayList<>();
        ArrayList<Double> u = new ArrayList<>();
        myRef.child("email").child(emailUser.split("@")[0]).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot ds: task.getResult().getChildren()) {
                    ItemHistory item = ds.getValue(ItemHistory.class);
                    item.setIconByName();
                    t.add(item.getPrice());
                    u.add(item.getUnit());
                    hisList.add(item);
                    System.out.println(item.getPrice());
                }
                if (hisList.size() > 0) {
                    HistoryAdapter historyAdapter = new HistoryAdapter(this, hisList);
                    listView.setAdapter(historyAdapter);
                }
                this.initTotal(t, u);
            } else {
                System.out.println("Error get value");
                System.out.println(emailUser.split("@")[0]);
            }
        });
    }

    private void initTotal(ArrayList<Double> t, ArrayList<Double> u) {
        double totalT = t.stream().mapToDouble(a -> a).sum();
        double totalU = u.stream().mapToDouble(a -> a).sum();
        totalAmount.setText(df.format(totalT));
        totalUnit.setText("( " + df.format(totalU) + " unit" + (totalU > 0 ? "s )" : " )"));
    }

    private void BackToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}