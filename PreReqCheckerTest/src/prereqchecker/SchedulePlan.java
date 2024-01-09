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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with up to 3 space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }
        HashMap<String, Course> cs = new HashMap<String,Course>();
        StdIn.setFile(args[0]);
        int a = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < a; i++){
           
            String l = StdIn.readLine();
            cs.put(l, new Course(l));
        }
        int b = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0;i < b; i++){
            cs.get(StdIn.readString()).Prereqadd(cs.get(StdIn.readString()));
        }
        StdIn.setFile(args[1]);
        Course tgt = cs.get(StdIn.readLine());
        int d = StdIn.readInt();
        StdIn.readLine();
        for(int i=0;i<d;i++){
            String aa = StdIn.readLine();
           
            eligCheck(cs.get(aa));
        }
        HashMap<Course, Integer> plan = new HashMap<Course, Integer>();
        int max = SPCheck(plan, -1, tgt);
        plan.remove(tgt);
        ArrayList<ArrayList<Course>> dry = new ArrayList<ArrayList<Course>>();
        for(int i = 0;i <= max; i++){
            dry.add(new ArrayList<Course>());
        }
       
        for(Map.Entry<Course, Integer> room: plan.entrySet()){
           dry.get(room.getValue()).add(room.getKey());
        }
        StdOut.setFile(args[2]);
        StdOut.println(dry.size());
       for(int i= dry.size()-1;i>=0;i--){
            for(Course c: dry.get(i)){
                StdOut.print(c.name+" ");
            }
            StdOut.println();
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
     public static int SPCheck(HashMap<Course,Integer> plan, int sem, Course cour){
        cour.comp =true;
        int max = sem;
        plan.put(cour, sem);
        if(cour.prereqs.size()>0){
            for(Course prereq:cour.prereqs){  
                if(!prereq.comp||(plan.containsKey(prereq) && plan.get(prereq)<=sem))
                  max = Math.max(max, SPCheck(plan, sem+1,prereq));
            }
           
        }  
        return max;
    }
}
