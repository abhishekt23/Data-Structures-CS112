package prereqchecker;

import java.util.ArrayList;

public class Course {
    String name;
    ArrayList<Course> prereqs;
    boolean comp = false;
    public Course(String a){
        name = a;
        prereqs = new ArrayList<Course>();
    }
    public void Prereqadd(Course a){
        prereqs.add(a);
    }
}

