package com.example.inclass03;

import java.io.Serializable;

public class Student implements Serializable {
    public static final int[] avatars = {R.drawable.avatar_f_1,R.drawable.avatar_f_2,R.drawable.avatar_f_3,R.drawable.avatar_m_1,R.drawable.avatar_m_2,R.drawable.avatar_m_3};
    String firstName;
    String LastName;
    String Dept;
    String Studentid;
    int imageid;

    public Student(String firstName, String lastName, String dept, String studentid, int imageid) {
        this.firstName = firstName;
        LastName = lastName;
        Dept = dept;
        Studentid = studentid;
        this.imageid = imageid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Dept='" + Dept + '\'' +
                ", Studentid='" + Studentid + '\'' +
                ", imageid=" + imageid +
                '}';
    }

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getStudentid() {
        return Studentid;
    }

    public void setStudentid(String studentid) {
        Studentid = studentid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }
}
