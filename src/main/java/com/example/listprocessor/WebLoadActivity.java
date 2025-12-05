package com.example.listprocessor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebLoadActivity extends AppCompatActivity {

    private EditText etUrl;
    private Button btnLoad;
    private ItemList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webload);

        list = ItemList.getInstance();
        etUrl = findViewById(R.id.et_url);
        btnLoad = findViewById(R.id.btn_load);

        // Default URL provided by professor
        etUrl.setText("https://web.njit.edu/~halper/it114/aed.txt");

        btnLoad.setOnClickListener(v ->
                new LoadTask().execute(etUrl.getText().toString().trim())
        );
    }

    private class LoadTask extends AsyncTask<String, Void, String> {
        boolean success = false;

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(7000);

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                list.clear();

                String name;
                while (true) {
                    name = br.readLine();
                    if (name == null) break; // End of file
                    name = name.trim();
                    if (name.isEmpty()) continue; // Skip blank lines

                    String id = br.readLine();
                    String salaryStr = br.readLine();
                    String office = br.readLine();
                    String extension = br.readLine();
                    String perfStr = br.readLine();
                    String startYearStr = br.readLine();

                    if (id == null || salaryStr == null || office == null ||
                            extension == null || perfStr == null || startYearStr == null) {
                        return "Malformed data file: incomplete employee record.";
                    }

                    Employee e = new Employee(
                            name,
                            id.trim(),
                            Double.parseDouble(salaryStr.trim()),
                            office.trim(),
                            extension.trim(),
                            Double.parseDouble(perfStr.trim()),
                            Integer.parseInt(startYearStr.trim())
                    );

                    list.add(e);
                }
                br.close();
                success = true;
                return "Load Complete";
            } catch (Exception ex) {
                return "Error: " + ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String msg) {
            Toast.makeText(WebLoadActivity.this, msg, Toast.LENGTH_LONG).show();
            if (success) finish();
        }
    }
}
