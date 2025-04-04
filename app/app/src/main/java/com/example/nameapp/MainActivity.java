package com.example.nameapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "NamesList";
    private static final String NAMES_SET_KEY = "names_set";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        Button showNamesButton = findViewById(R.id.showNamesButton);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        showNamesButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString().trim();
            if (!newName.isEmpty()) {
                // Get existing names
                Set<String> namesSet = sharedPreferences.getStringSet(NAMES_SET_KEY, new HashSet<>());
                // Create a new set with existing names
                Set<String> newNamesSet = new HashSet<>(namesSet);
                // Add the new name
                newNamesSet.add(newName);
                
                // Save updated set
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(NAMES_SET_KEY, newNamesSet);
                editor.apply();

                // Clear input
                nameEditText.setText("");
                
                // Show all names
                showNamesDialog(newNamesSet);
            } else {
                Toast.makeText(this, "لطفا یک نام وارد کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNamesDialog(Set<String> names) {
        if (names.isEmpty()) {
            Toast.makeText(this, "هیچ نامی ثبت نشده است", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder namesBuilder = new StringBuilder();
        for (String name : names) {
            namesBuilder.append("• ").append(name).append("\n");
        }

        new AlertDialog.Builder(this)
            .setTitle("لیست تمام نام ها")
            .setMessage(namesBuilder.toString())
            .setPositiveButton("تایید", null)
            .show();
    }
}
