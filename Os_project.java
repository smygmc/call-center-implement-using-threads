
package os_project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author smygmc
 */
public class Os_project {

    public static void main(String[] args) {
        System.out.println("--Call Center Opened--");
        String[] side_a={"Sumeyye","Furkan","Serap","Emre","Gulsum","Seren"};
        String[] side_b={"A","B","C","D","E","F"};
        Semaphore ladies=new Semaphore(2); // since there are two ladies
        Semaphore cables=new Semaphore(2);//since there are two cables
        //Since for ex. person 1 and person 2 can not be talking to the person a at the same time they are a resource too and I can
        // define them as semaphores
        Dictionary<String, Semaphore> call_dictionary = new Hashtable<>();
        for(int i=0;i<side_b.length;i++){
           call_dictionary.put(side_b[i],new Semaphore(1));
        }
        Thread[] threads=new Thread[6];
        for(int i=0;i<side_a.length;i++){ // since there a 6 people to make a call i will create 6 threads
            threads[i]=new PersonThread(side_a[i],side_b,call_dictionary,ladies,cables);
        }
        
        for(int i=0;i<threads.length;i++){
           threads[i].start();
        }
        
        try{
        for(int i=0;i<threads.length;i++){
           threads[i].join();// waiting for each thread to complete.
        }
        }
        catch(Exception e){
            System.out.println("Exception occured during join.");
        }
        System.out.println("---Call Center closed. Thanks for calling!---");// closing statement after all threads are complete.
        
    }
    
}
class PersonThread extends Thread{
    
    public String name;
    Dictionary<String, Semaphore> call_dic;
    public List<String> call_list;
    public Semaphore ladies;
    public Semaphore cables;
    public PersonThread(String name,String[] call_list,Dictionary<String, Semaphore> call_dic,Semaphore ladies,Semaphore cables){
      this.name=name;
      this.call_list=new ArrayList<String>(Arrays.asList(call_list));// to create a mutable list from this array
      Collections.shuffle(this.call_list);// shuffle the list so all persons wont try to call person 1 
      this.call_dic=call_dic;
      this.ladies=ladies;
      this.cables=cables;
    }
   @Override
    public void run(){
      String current_callee;
      while(call_list.isEmpty()!=true){
        current_callee=call_list.get(0);
        if(ladies.tryAcquire()){
            // got the lady
            System.out.println(name+" catched a lady...");
            trySleep(500);// to imitate real life delays
            if(cables.tryAcquire()){
                if(call_dic.get(current_callee).tryAcquire()){// if the callee is not talking to another person get it
                 
                    System.out.println(name+" is now talking to "+current_callee);
                    trySleepRandom(1000,3000);//imitating phone call duration
                    System.out.println(name+" hang up the phone-Call Succeeded!");
                    call_dic.get(current_callee).release();
                    ladies.release();
                    cables.release();
                    call_list.remove(0);//removing the person from call list
                }
                else{ //if i get cable access but the other person is busy we cant talk
                    System.out.println(current_callee+" is busy. "+name+" hanged up.-Call Failed!");
                    cables.release();
                    ladies.release();
                    
                }
            }
            else{// if i get lady but no cable is avaliable at this moment releasing the lady
             ladies.release();
            }
            
          }
        trySleepRandom(500,1500);// before attempting to make another call wait some.
       }
      
      System.out.println(name+" called everyone in his/her list.");
     
    }
    
    public void trySleep(long millis) { // so i wont be writing this code block everytime i want to put my thread to sleep
    try {
        Thread.sleep(millis);
    } catch (InterruptedException e) {
        throw new RuntimeException("Interrupted during sleep", e);
    }
    
}
    public void trySleepRandom(int min,int max) { // so i wont be writing this code block everytime i want to put my thread to sleep
    try {
        Thread.sleep(ThreadLocalRandom.current().nextInt(min, max));
    } 
    catch (InterruptedException e) {
        throw new RuntimeException("Interrupted during sleep", e);
    }
}
}

