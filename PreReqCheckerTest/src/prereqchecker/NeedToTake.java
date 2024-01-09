package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }
        HashMap<String, Course> courses = new HashMap<String,Course>();
        StdIn.setFile(args[0]);
        int a = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0;i < a; i++){
           
            String l = StdIn.readLine();
            courses.put(l, new Course(l));
        }
        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0;i < b; i++){
            courses.get(StdIn.readString()).Prereqadd(courses.get(StdIn.readString()));
        }
        StdIn.setFile(args[1]);
        Course target = courses.get(StdIn.readLine());
        int d = StdIn.readInt();
        StdIn.readLine();
        for(int i=0;i<d;i++){
            eligCheck(courses.get(StdIn.readLine()));
        }
        StdOut.setFile(args[2]);
        target.comp = true;
        NTTCheck(target);
	// WRITE YOUR CODE HERE
    }
    public static void eligCheck(Course b){
        b.comp = true;
        if(b.prereqs.size() > 0){
            for(Course prereq:b.prereqs){
                if(!prereq.comp){
                    eligCheck(prereq);
                }
            }
        }
    }
    public static void NTTCheck(Course a){
        if(!a.comp){
            StdOut.println(a.name);
        }
        a.comp = true;
        if(a.prereqs.size() > 0){
            for(Course prereq:a.prereqs){
                if(!prereq.comp){
                    NTTCheck(prereq);
                }
            }
        }
    }
}
