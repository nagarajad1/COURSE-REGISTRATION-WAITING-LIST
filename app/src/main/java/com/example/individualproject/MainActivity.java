package com.example.individualproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Declare necessary instance variables
    private StudentAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private DBHandler dbHandler;
    private List<Student> allstudents = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database handler
        dbHandler = new DBHandler(MainActivity.this);

        // Retrieve floating action button from layout
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {

            // Call a helper method to show a dialog for adding a new student
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        // Read all students from database
        allstudents = dbHandler.readAllStudent();

        noNotesView = findViewById(R.id.empty_notes_view);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new StudentAdapter(this, allstudents);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));

        // Set the adapter
        recyclerView.setAdapter(mAdapter);

        // Show/hide the empty view based on whether there are students to display
        toggleEmptyStudent();

        // Add an item touch listener to the recycler view
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    // Define a helper method to show a dialog for editing or deleting a student
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                allstudents = dbHandler.readAllStudent();
                if (which == 0) {
                    showNoteDialog(true, allstudents.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }

    // Define a helper method to deleting a student
    private void deleteNote(int position) {
        // deleting the note from db
        dbHandler.deleteStudent(allstudents.get(position));
        toggleEmptyStudent();

        // removing the note from the list
        allstudents.remove(position);
        mAdapter.notifyItemRemoved(position);
        restartActivity();
    }

    private void showNoteDialog(final boolean shouldUpdate, final Student note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_student_popup, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);
        alertDialogBuilderUserInput.setTitle("Edit Student");

        EditText sid = view.findViewById(R.id.editTextId);
        sid.setText(note.getId());
        sid.setEnabled(false);

        EditText courseName = view.findViewById(R.id.editTextCourseName);
        courseName.setText(note.getCourseName());

        EditText studentName = view.findViewById(R.id.editTextStudentName);
        studentName.setText(note.getStudentName());

        EditText studentEmail = view.findViewById(R.id.editTextStudentEmail);
        studentEmail.setText(note.getStudentEmail());

        EditText priority = view.findViewById(R.id.editTextPriority);
        priority.setText(note.getPriority());


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        Student student = new Student(
                                note.getId(),
                                courseName.getText().toString(),
                                studentName.getText().toString(),
                                studentEmail.getText().toString(),
                                priority.getText().toString()
                        );
                        dbHandler.updateStudent(student);

                        restartActivity();

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }

    private void updateNote(String note, int position) {
        Student n = allstudents.get(position);
        // updating note text
        n.setStudentName(note);

        // updating note in db
        dbHandler.updateStudent(n);

        // refreshing the list
        allstudents.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyStudent();
    }

    private void showDialog() {
        AddStudentDialogFragment dialog = new AddStudentDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddStudentDialogFragment");
    }

    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void toggleEmptyStudent() {
        // you can check notesList.size() > 0

        if (dbHandler.getStudentCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}