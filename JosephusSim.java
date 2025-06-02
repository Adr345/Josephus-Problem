import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode circle;     // a PersonNode pointer that tracks first node
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination       
   private PersonNode track;      // a PersonNode pointer to help with elimination

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         size = 1;
         if (file.hasNext()) {
            circle = new PersonNode(file.next()); //store the first node as first string from the file
            track = circle; //starts with pointing track at first node
            while(file.hasNext()) {
               add(file.next()); //recall the helper method, stores next string from the file as node
            }
         }
         // make the ring circular by attaching last node's next to front
         // remember the last node as the one in front of the next to get eliminated
         track.next = circle; //after storing every node from the file, track.next should be null, make track.next become the front(circle)
         track = track.next;               
         // generate, print, and save the random elimination count
         Random r = new Random();
         eliminationCount = r.nextInt(size / 2) + 1;
         System.out.println("=== Elimination count is " + eliminationCount + "===");         
      } catch(FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }
   
   // optional helper method for constructing the circle
   private void add(String val) {
      track.next = new PersonNode(val); //store track.next as file.next()'s variable
      track = track.next; //re-point the tracker
      size++;
   }
   
   public void eliminate() {
      // count to the elimination count
      for(int i = 1; i < eliminationCount - 1; i++) { //for-loop to point the tracker till the one before we want to eliminate
         track = track.next;         
      }
      // print who will be eliminated
      System.out.println(track.next.name + " eliminated!");
      // eliminate the person and update "front" of the circle and size
      PersonNode change = track.next.next; //create a node "change" to store the node right after the one eliminated
      track.next = change; //points the track.next to "change", which skips the "track" from the ring like eliminating it
      circle = change; //re-point the front as the node after the one eliminated
      size--;
   }
   
   public boolean isOver() {
      // check if there's only one person left in the circle
      return (size == 1);
   }
   
   public String toString() {
      // if there's only one person left, print them as the last survivor
      String result = "";
      if(isOver()){
         result += circle.name + " is the last survivor!";
         return result;
      } else {
         track = circle; //re-point the tracker as the front we stored in eliminate()
         // print the remaining survivors (watch out for infinite loop since list is circular)
         for(int i = 1; i <= size; i++){ //create a for-loop, starts with printing the front "circle" changed from eliminate()
            result += i + "-";
            result += track.name + " ";
            track = track.next;
         }      
         return result;
      }
   }

}