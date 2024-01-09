package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }
        HashMap<String, Course> courses = new HashMap<String,Course>();
        StdIn.setFile(args[0]);
        int a = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < a; i++){
           
            String line = StdIn.readLine();
            courses.put(line, new Course(line));
        }
        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < b; i++){
            courses.get(StdIn.readString()).Prereqadd(courses.get(StdIn.readString()));
        }
        StdIn.setFile(args[1]);
        int num = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0;i < num; i++){
            eligCheck(courses.get(StdIn.readLine()));
        }
        StdOut.setFile(args[2]);
        for(Course aa: courses.values()){
            if(!aa.comp){
            boolean canDo = true;
            for(Course bb: aa.prereqs){
                if(!bb.comp){
                    canDo = false;
                    break;
                }
            }
            if(canDo){
                StdOut.println(aa.name);
            }
        }
    }
	// WRITE YOUR CODE HERE
    }
    public static void eligCheck(Course b){
        b.comp = true;
        if(b.prereqs.size()>0){
            for(Course prereq:b.prereqs){
                if(!prereq.comp){
                    eligCheck(prereq);
                }
            }
        }
    }
}
