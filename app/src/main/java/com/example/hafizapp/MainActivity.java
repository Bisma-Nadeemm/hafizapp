package com.example.hafizapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.FrameLayout;

import com.example.hafizapp.R;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editTextName, editTextAge, editTextClass, editTextSearch,editTextSabaq,editTextSabaqi,editTextManzil;

    private Button buttonAddStudent, buttonSearch;
    private com.example.hafizapp.DBHelper databaseHelper;
    private List<com.example.hafiz.Student> students;
    private ArrayAdapter<com.example.hafiz.Student> adapter;
    private FrameLayout frameLayoutStudentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextClass = findViewById(R.id.editTextClass);
        editTextSabaq = findViewById(R.id.editTextSabaq);
        editTextSabaqi = findViewById(R.id.editTextSabaqi);
        editTextManzil = findViewById(R.id.editTextManzil);

        buttonAddStudent = findViewById(R.id.buttonAddStudent);

        frameLayoutStudentDetails = findViewById(R.id.frameLayoutStudentDetails);

        databaseHelper = new com.example.hafizapp.DBHelper(this);


        loadStudentNames(); // Load student names into the ListView

        listView.setOnItemClickListener((parent, view, position, id) -> {
            com.example.hafiz.Student selectedStudent = students.get(position);
            Intent intent = new Intent(MainActivity.this, StudentDetailsActivity.class);
            intent.putExtra("name", selectedStudent.getName());
            intent.putExtra("age", selectedStudent.getAge());
            intent.putExtra("className", selectedStudent.getClassName());
            intent.putExtra("sabaq", selectedStudent.getSabaq());
            intent.putExtra("sabaqi", selectedStudent.getSabaqi());
            intent.putExtra("manzil", selectedStudent.getManzil());
            startActivity(intent);
        });

        buttonAddStudent.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String ageStr = editTextAge.getText().toString().trim();
            String className = editTextClass.getText().toString().trim();
            String sabaq = editTextSabaq.getText().toString().trim();
            String sabaqi = editTextSabaq.getText().toString().trim();
            String manzil = editTextManzil.getText().toString().trim();

            if (name.isEmpty() || ageStr.isEmpty() || className.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr);
            com.example.hafiz.Student newStudent = new com.example.hafiz.Student(name, age, className,sabaq,sabaqi,manzil);
            databaseHelper.addStudent(newStudent);

            loadStudentNames(); // Load updated student names into the ListView

            editTextName.setText("");
            editTextAge.setText("");
            editTextClass.setText("");

            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadStudentNames() {
        students = databaseHelper.getAllStudents();
        List<String> studentNames = new ArrayList<>();
        for (com.example.hafiz.Student student : students) {
            studentNames.add(student.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        listView.setAdapter(adapter);
    }
}