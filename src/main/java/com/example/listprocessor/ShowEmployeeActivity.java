package com.example.listprocessor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowEmployeeActivity extends AppCompatActivity {

    private EditText etId;
    private Button btnShow;
    private TextView tvDetails;
    private ItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employee);

        list = ItemList.getInstance();

        etId = findViewById(R.id.et_show_id);
        btnShow = findViewById(R.id.btn_show);
        tvDetails = findViewById(R.id.tv_details);

        btnShow.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            if (id.isEmpty()) {
                Toast.makeText(this, "Enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Employee e = list.findById(id);
            if (e == null) {
                tvDetails.setText("");
                Toast.makeText(this, "Employee not found", Toast.LENGTH_SHORT).show();
            } else {
                tvDetails.setText(e.details());
            }
        });
    }
}
