package prereqchecker;

import java.util.HashMap;
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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {   
    public static void main(String[] args) {
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        HashMap<String, Course> courses = new HashMap<String,Course>();
        StdIn.setFile(args[0]);
        int a = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < a; i++){
           
            String l = StdIn.readLine();
            courses.put(l, new Course(l));
        }
        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < b; i++){
            courses.get(StdIn.readString()).Prereqadd(courses.get(StdIn.readString()));
        }
        StdOut.setFile(args[1]);
        for(Course course: courses.values()){
            StdOut.print(course.name+" ");
            for(Course prereq: course.prereqs){
                StdOut.print(prereq.name+" ");
            }
            StdOut.println();
        }
	// WRITE YOUR CODE HERE
    }
}
