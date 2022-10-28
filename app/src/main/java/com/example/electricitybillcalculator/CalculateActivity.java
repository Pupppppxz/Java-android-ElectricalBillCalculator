package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity {

    private final String Tag = "CalculateActivity";
    String[] months;
    AutoCompleteTextView month;
    EditText watt, hours;
    ImageView iron, air, fan, fridge, light, microwave, rice, tv, washing, back;
    Button cal;

    ItemCalculator[] items;
    CalculatorController c;
    Electrical e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        e = new Electrical();

        months = getResources().getStringArray(R.array.month_list);

        month = findViewById(R.id.input_month);
        watt = findViewById(R.id.input_watt);
        hours = findViewById(R.id.input_hour);

        iron = findViewById(R.id.input_iron);
        air = findViewById(R.id.input_air);
        fan = findViewById(R.id.input_fan);
        fridge = findViewById(R.id.input_fridge);
        light = findViewById(R.id.input_light);
        microwave = findViewById(R.id.input_micro);
        rice = findViewById(R.id.input_rice);
        tv = findViewById(R.id.input_tv);
        washing = findViewById(R.id.input_washing);

        cal = findViewById(R.id.button_cal);
        back = findViewById(R.id.cal_back);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months);
        month.setAdapter(adapter);

        this.initCalculateController();

        iron.setOnClickListener(view -> c.toggleItemClick("iron"));
        air.setOnClickListener(view -> c.toggleItemClick("air"));
        fan.setOnClickListener(view -> c.toggleItemClick("fan"));
        fridge.setOnClickListener(view -> c.toggleItemClick("fridge"));
        light.setOnClickListener(view -> c.toggleItemClick("light"));
        microwave.setOnClickListener(view -> c.toggleItemClick("microwave"));
        rice.setOnClickListener(view -> c.toggleItemClick("rice"));
        tv.setOnClickListener(view -> c.toggleItemClick("tv"));
        washing.setOnClickListener(view -> c.toggleItemClick("washer"));

        back.setOnClickListener(view -> BackToHome());
        
        cal.setOnClickListener(view -> Calculate());
    }

    private boolean isValidMonth(String monthSelected) {
        for (String m: months ) {
            if (m.equalsIgnoreCase(monthSelected)) {
                return true;
            }
        }
        return false;
    }

    private void Calculate() {
        String monthSelected = String.valueOf(month.getText());
        double wattUsed = Double.parseDouble(String.valueOf(watt.getText()));
        double hoursUsed = Double.parseDouble(String.valueOf(hours.getText()));
        String currentSelected = c.getCurrentSelected();
        if (!isValidMonth(monthSelected)) {
            Toast.makeText(this, "Invalid month", Toast.LENGTH_LONG).show();
        } else if (wattUsed <= 0) {
            Toast.makeText(this, "Invalid watt used", Toast.LENGTH_LONG).show();
        } else if (hoursUsed <= 0) {
            Toast.makeText(this, "Invalid hours", Toast.LENGTH_LONG).show();
        } else if (currentSelected == "") {
            Toast.makeText(this, "Please select electrical machine", Toast.LENGTH_LONG).show();
        } else {
            double unit = wattUsed * hoursUsed / 1000;
            double price = e.calculate(unit);
            GotoResult(monthSelected, unit, currentSelected, price);
        }
    }

    private void GotoResult(String monthSelected, double unit, String name, double price) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("month", monthSelected);
        intent.putExtra("unit", unit);
        intent.putExtra("name", name);
        intent.putExtra("price", price);
        startActivity(intent);
    }

    private void BackToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void initCalculateController() {
        items = new ItemCalculator[]{
                new ItemCalculator("iron", R.drawable.iron, iron),
                new ItemCalculator("air", R.drawable.air_conditioner, air),
                new ItemCalculator("fan", R.drawable.fan, fan),
                new ItemCalculator("fridge", R.drawable.fridge, fridge),
                new ItemCalculator("light", R.drawable.light_bulb, light),
                new ItemCalculator("microwave", R.drawable.oven, microwave),
                new ItemCalculator("rice", R.drawable.rice_cooker, rice),
                new ItemCalculator("tv", R.drawable.television, tv),
                new ItemCalculator("washer", R.drawable.washing_machine, washing),
            };
        c = new CalculatorController(items);
    }
}