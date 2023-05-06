package com.example.individualproject;

public class Student {
    private String id;
    private String course_name;
    private String student_name;
    private String student_email;
    private String priority;

    // The class constructor takes in values for all five member variables, and initializes them accordingly.
    public Student(String id, String course_name, String student_name, String student_email, String priority) {
        this.id = id;
        this.course_name = course_name;
        this.student_name = student_name;
        this.student_email = student_email;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return course_name;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public String getStudentName() {
        return student_name;
    }

    public void setStudentName(String student_name) {
        this.student_name = student_name;
    }

    public String getStudentEmail() {
        return student_email;
    }

    public void setStudentEmail(String student_email) {
        this.student_email = student_email;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
