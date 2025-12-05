package com.example.listprocessor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;


import dagger.hilt.android.AndroidEntryPoint;

public class MainActivity extends AppCompatActivity {

    private ItemList the_list = ItemList.getInstance();
    // singleton list injected by Hilt (if you're using Hilt)
    // If you are not using Hilt, use: private ItemList the_list = ItemList.getInstance();

    private TextView tv;

    // Threshold for high-paid
    public static final double HIGH_PAID_THRESHOLD = 100000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("App Employee");

        tv = findViewById(R.id.text_main);

        // IMPORTANT: Do NOT auto-load employees here. The list should start empty.
        if (the_list == null) {
            // If Hilt injection not present, fallback:
            the_list = ItemList.getInstance();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // menu routing stays mostly the same
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_option1) {
            onOption1(item);
            return true;
        } else if (id == R.id.action_option2) {
            onOption2(item);
            return true;
        } else if (id == R.id.action_option3) {
            onOption3(item);
            return true;
        } else if (id == R.id.action_option4) {
            onOption4(item);
            return true;
        } else if (id == R.id.action_option5) {
            onOption5(item);
            return true;
        } else if (id == R.id.action_option6) {
            onOption6(item);
            return true;
        } else if (id == R.id.action_option7) {
            onOption7(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Option 1: Display list in order (name, id, startYear)
    public void onOption1(MenuItem i) {
        tv.setText("");
        for (Employee e : the_list) {
            tv.append(e.summaryLine() + "\n");
        }
        if (the_list.isEmpty()) tv.setText("List is empty. Use option 2 or 3 to load employees.");
    }

    // Option 2: Load from a web file (prompt the user via a simple input activity)
    // We'll start a small activity to ask for the URL; but for simplicity here we'll use a default dialog-like action:
    public void onOption2(MenuItem i) {
        // Start WebLoadActivity to prompt for URL and load.
        startActivity(new Intent(this, WebLoadActivity.class));
    }

    // Option 3: Add new employee (from an assets file) -> start AddEmployeeActivity
    public void onOption3(MenuItem i) {
        startActivity(new Intent(this, AddEmployeeActivity.class));
    }

    // Option 4: Show employee details (ask for id) -> start ShowEmployeeActivity
    public void onOption4(MenuItem i) {
        startActivity(new Intent(this, ShowEmployeeActivity.class));
    }

    // Option 5: Remove employee by id -> start RemoveEmployeeActivity
    public void onOption5(MenuItem i) {
        startActivity(new Intent(this, RemoveEmployeeActivity.class));
    }

    // Option 6: Show geometric average of salaries
    public void onOption6(MenuItem i) {
        double gm = the_list.geometricMeanSalary();
        if (gm <= 0.0) {
            tv.setText("No salaries available to compute geometric mean.");
        } else {
            tv.setText(String.format(Locale.US, "Geometric mean of salaries: $%,.2f", gm));
        }
    }

    // Option 7: Show number of high-paid employees (>= threshold)
    public void onOption7(MenuItem i) {
        int count = the_list.countHighPaid(HIGH_PAID_THRESHOLD);
        tv.setText("Number of high-paid employees (>= $100,000): " + count);
    }
}
