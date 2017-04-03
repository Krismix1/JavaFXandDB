package domain;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Chris on 24-Mar-17.
 */
public class Student
{
    /*public String name;
    public String email;

    public Student(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    public Student()
    {

    }*/

    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private int student_id;


    public Student(String name, String email, int student_id) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.student_id = student_id;
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getStudent_id()
    {
        return student_id;
    }

    public Student copy()
    {
        return new Student(name.get(), email.get(), student_id);
    }
}
