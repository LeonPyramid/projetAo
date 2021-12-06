package local.culturalprogramation.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import local.culturalprogramation.domain.events.Concert;
import local.culturalprogramation.domain.events.Play;
import local.culturalprogramation.domain.programtion.Programation;

 class Console {
    private static Programation programation = Programation.getInstance();
    private static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd/MM/yy");

    public Console(){}

    public  void main(){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Welcome to your programming tool!");
        int year = YEAR(scan);
        if (year != LocalDate.now().getYear());
            programation.setYear(year);
        uiLoop();
    }




    private  void uiLoop(){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Boolean stay = true;
        while(stay){
            waitUserPrint();
            String opt = scan.next();
            switch(opt){
                case "ADD":
                    ADD(scan);
                    break;
                case "REMOVE":
                    REMOVE(scan);
                    break;
                case "PLANNING":
                    PLANNING(scan);
                    break;
                case "HOURS":
                    HOURS(scan);
                    break;
                case "SAVE":
                    SAVE();
                    break;
                case "LOAD":
                    LOAD(scan);
                    break;
                case "QUIT":
                    stay = false;
                    break;
                case "WIP":
                    WIP();
                    break;
                default:
                    System.err.println("No Corresponding Command");
            }
        }
    }
    private String THEATER(Scanner scan){
        System.out.println("Wich theater you want to show? (Atabal, Krakatoa, Galaxie, Arena");
        String name = scan.next();
        return name;
    }
    private  int  YEAR(Scanner scan){
        int year  = LocalDate.now().getYear();
        System.out.println("Say us the year you want to program");
        while(true){
            int desiredYear = scan.nextInt();
            if(desiredYear < year){
                System.err.println("Year must be supperior as the current year");
            }
            else{
                return desiredYear;
            }

        }
        
    }
    private  int WEEK(Scanner scan){
        System.out.println("On wich week, do you want to work");
        while (true){
            int week  = scan.nextInt();
            if(week>0 && week <= 52){
                return week;
            }
            System.out.println("Week number must be in [1;52]");
        }
        
    }
    private  void waitUserPrint(){
            System.out.println("Choose what to do (write CAPS word)");
            System.out.println("1. ADD an event in a theater ");
            System.out.println("2. REMOVE an event in a theater ");
            System.out.println("3. Display a theather weekly PLANNING ");
            System.out.println("4. Display a theater's weekly HOURS ");
            System.out.println("5. SAVE the programation ");
            System.out.println("6. LOAD a programation ");
            System.out.println("7. Set Day CLOSE ");
            System.out.println("8. CHANGE day hours ");
            System.out.println("9. QUIT the program");

    }
    private int lengthPlay(Scanner scan){
        System.out.println("Enter the number of representation you want : (maximun 7)");
        while (true){
            int length  = scan.nextInt();
            if(length>0 && length<=7){
                return length;
            }
            System.out.println("Length number must be in [1;7]");
        }
    }

    private  void WIP(){
        System.out.println("WORK IN PROGRESS");
    }

    private  void ADD(Scanner scan){
        int week  = WEEK(scan);
        System.out.println("Choose the type of event : PLAY or CONCERT");
        String opt = scan.next();
        switch(opt){
            case "PLAY":
                PLAY(scan,week);
                break;
            case "CONCERT":
                CONCERT(scan,week);
                break;

            
            default:
                System.err.println("No Corresponding Type");
        }

    }
    private  void PLAY(Scanner scan, int week){
        
        System.out.println("Enter the name of the play");
        String name = scan.next();
        System.out.println("Enter the desired capacity of the play");
        int desiredCapacity = scan.nextInt();
        int length = lengthPlay(scan);
        String done ="";
        try {
            done =programation.setPlay(name,desiredCapacity,length,week);
        } catch (RuntimeException e) {
            System.err.println("Year is full"); 
        }
        System.out.println(done); 

    }
    private  void CONCERT(Scanner scan,int week){
        System.out.println("Enter the name of the artist");
        String name = scan.next();
        System.out.println("Enter the desired capacity of the play");
        int desiredCapacity = scan.nextInt();
        String done ="";
        try {
            done =programation.setConcert(name,desiredCapacity,week);
        } catch (RuntimeException e) {
            System.err.println("Year is full"); 
        }
        System.out.println(done);  
    }


    private void PLANNING(Scanner scan){
        int week = WEEK(scan);
        String name = THEATER(scan);
        System.out.println(programation.displayTheater(name,week));
    }

    private void HOURS(Scanner scan){
        String name = THEATER(scan);
        System.out.println(programation.displayTheaterHours(name));
    }

    private void REMOVE(Scanner scan){
        System.out.println("Choose the type of event : PLAY or CONCERT");
        String opt = scan.next();
        switch(opt){
            case "PLAY":
                System.out.println("Enter the name of the play");
                String name = scan.next();
                System.out.println("Enter the date of the play *dd/MM/yy* (will remove all the consecutives dates)");
                String stringDate = scan.next();
                LocalDate date = LocalDate.parse(stringDate, formatter);
                boolean check = programation.removeEventPlay(name,date);
                if (check)
                    System.out.println("Remove done !");
                else
                    System.out.println("Play does not exist");
                break;

            case "CONCERT":
                System.out.println("Enter the name of the concert");
                name = scan.next();
                System.out.println("Enter the date of the concert *dd/MM/yy*");
                stringDate = scan.next();
                date = LocalDate.parse(stringDate, formatter);
                check = programation.removeEventConcert(name,date);
                if (check)
                    System.out.println("Remove done !");
                else
                    System.out.println("Concert does not exist");
                break;

            
            default:
                System.err.println("No Corresponding Type");
        }
    }

    private void SAVE(){
        String path = programation.save();
        System.out.println("Programtion saved in : " + path );
    }

    private void LOAD(Scanner scan){
        System.out.println("Give us the path to the programtion to load"); 
        String path =  scan.next();
        String ret  = programation.load(path);
        System.out.println(ret);
    }


}