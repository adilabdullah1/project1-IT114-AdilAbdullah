package com.example.listprocessor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText etFilename;
    private Button btnAdd;
    private ItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        list = ItemList.getInstance();

        etFilename = findViewById(R.id.et_assets_filename);
        btnAdd = findViewById(R.id.btn_add_from_assets);

        etFilename.setText("a1-addemp.txt"); // default suggestion

        btnAdd.setOnClickListener(v -> {
            String fname = etFilename.getText().toString().trim();
            if (fname.isEmpty()) {
                Toast.makeText(this, "Enter assets filename", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(fname)));
                String name = br.readLine();
                String id = br.readLine();
                String salaryStr = br.readLine();
                String office = br.readLine();
                String extension = br.readLine();
                String perfStr = br.readLine();
                String startYearStr = br.readLine();
                br.close();

                if (name == null || id == null || salaryStr == null || office == null ||
                        extension == null || perfStr == null || startYearStr == null) {
                    Toast.makeText(this, "Malformed assets file (need 7 lines)", Toast.LENGTH_LONG).show();
                    return;
                }

                double salary = Double.parseDouble(salaryStr.trim());
                double perf = Double.parseDouble(perfStr.trim());
                int startYear = Integer.parseInt(startYearStr.trim());

                Employee e = new Employee(name.trim(), id.trim(), salary, office.trim(), extension.trim(), perf, startYear);
                list.add(e);

                Toast.makeText(this, "Employee added: " + e.getName(), Toast.LENGTH_LONG).show();
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Error reading assets file: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
