package com.example.electricitybillcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;

    private Button save, cancel;
    private TextView priceText, unitText;
    private ImageView image;

    private Intent mIntent;
    private String name, month;
    private double price, unit;

    private final DecimalFormat df = new DecimalFormat("0.00");
    private CalculatorController c;
    private ItemCalculator[] items;
    private ItemCalculator currentItem;

    private String nameUser, emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);

        priceText = findViewById(R.id.result_price);
        unitText = findViewById(R.id.result_unit);
        image = findViewById(R.id.result_icon);

        mIntent = getIntent();

        name = mIntent.getStringExtra("name");
        month = mIntent.getStringExtra("month");
        price = mIntent.getDoubleExtra("price", 0);
        unit = mIntent.getDoubleExtra("unit", 0);

        this.initCalculateController();

        currentItem = this.c.getItemByName(name);

        this.initResult();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            nameUser = account.getDisplayName();
            emailUser = account.getEmail();
        }

        save.setOnClickListener(view -> saveToDB());
        cancel.setOnClickListener(view -> GotoHome());
    }

    private void initResult() {
        priceText.setText(df.format(price));
        unitText.setText(df.format(unit));
        image.setImageResource(currentItem.getIcon());
    }

    private void GotoHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void GotoHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    private void initCalculateController() {
        items = new ItemCalculator[]{
                new ItemCalculator("iron", R.drawable.iron),
                new ItemCalculator("air", R.drawable.air_conditioner),
                new ItemCalculator("fan", R.drawable.fan),
                new ItemCalculator("fridge", R.drawable.fridge),
                new ItemCalculator("light", R.drawable.light_bulb),
                new ItemCalculator("microwave", R.drawable.oven),
                new ItemCalculator("rice", R.drawable.rice_cooker),
                new ItemCalculator("tv", R.drawable.television),
                new ItemCalculator("washer", R.drawable.washing_machine),
        };
        c = new CalculatorController(items);
    }

    private String capitalized(String m) {
        return m.substring(0,1).toUpperCase() + m.substring(1);
    }

    private void saveToDB() {
        ItemHistory itemHistory = new ItemHistory(name, unit, price, capitalized(month));
        System.out.println(itemHistory);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("history");
        myRef.child("email").child(emailUser.split("@")[0]).child(capitalized(month) + "_" + name).setValue(itemHistory)
                .addOnCompleteListener(task -> {
                    Toast.makeText(this, "Save data to database!", Toast.LENGTH_SHORT).show();
                    GotoHistory();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error while save to database", Toast.LENGTH_SHORT).show();
                });
    }
}