package com.example.individualproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddStudentDialogFragment extends DialogFragment {

    // Declare variables for database handler and input fields
    private DBHandler dbHandler;
    private EditText id;
    private EditText courseName;
    private EditText studentName;
    private EditText studentEmail;
    private EditText priority;

    // Reload the main activity after dismissing the dialog
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    // Create the dialog box
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a dialog box using the AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the view using a layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_student_popup, null);
        builder.setView(view);

        // Set the title of the dialog box
        builder.setTitle("Add New Student");

        // Create a new instance of the database handler class
        // and pass the context of the activity to it
        dbHandler = new DBHandler(getActivity());

        // Add a button to add the new student to the database
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Create a new student object using the input fields
                Student student = new Student(id.getText().toString(), courseName.getText().toString(), studentName.getText().toString(), studentEmail.getText().toString(), priority.getText().toString());

                // Add the new student to the database using the database handler
                dbHandler.addNewStudent(student);
            }
        });

        // Add a button to cancel the dialog box
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the cancel button click event
            }
        });

        // Get references to the input fields in the view
        id = view.findViewById(R.id.editTextId);
        courseName = view.findViewById(R.id.editTextCourseName);
        studentName = view.findViewById(R.id.editTextStudentName);
        studentEmail = view.findViewById(R.id.editTextStudentEmail);
        priority = view.findViewById(R.id.editTextPriority);

        // Return the created dialog box
        return builder.create();
    }
}
