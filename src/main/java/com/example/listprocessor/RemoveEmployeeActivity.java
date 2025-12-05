package com.example.listprocessor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RemoveEmployeeActivity extends AppCompatActivity {

    private EditText etId;
    private Button btnRemove;
    private ItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_employee);

        list = ItemList.getInstance();

        etId = findViewById(R.id.et_remove_id);
        btnRemove = findViewById(R.id.btn_remove);

        btnRemove.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            if (id.isEmpty()) {
                Toast.makeText(this, "Enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean removed = list.removeById(id);
            if (removed) {
                Toast.makeText(this, "Employee removed", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Employee not found", Toast.LENGTH_LONG).show();
            }
        });
    }
}
