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
                    //TODO ENLEVER LE WIP QUAND FINI
                    WIP();
                    break;
                case "PLANNING":
                    PLANNING(scan);
                    //TODO ENLEVER LE WIP QUAND FINI
                    WIP();
                    break;
                case "HOURS":
                     //TODO ENLEVER LE WIP QUAND FINI
                    WIP();
                    break;
                case "SAVE":
                    //TODO ENLEVER LE WIP QUAND FINI
                    WIP();
                    break;
                case "QUIT":
                    stay = false;
                    break;
                default:
                    System.err.println("No Corresponding Command");
            }
        }
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
            System.out.println("6. QUIT the program");
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
        System.out.println("Enter the starting date of the play *dd/MM/yy*");
        String start = scan.next();
        LocalDate Dstart = LocalDate.parse(start, formatter);
        System.out.println("Enter the ending date of the play *dd/MM/yy* ");
        String end = scan.next();
        LocalDate Dend = LocalDate.parse(end, formatter);
        System.out.println("Enter the desired capacity of the play");
        int desiredCapacity = scan.nextInt();
        boolean done =programation.setEvent(new Play(name, Dstart, Dend,desiredCapacity),week);
        if(done)
            System.out.println("Concert set!");
        else
            System.out.println("Week is full");  

    }
    private  void CONCERT(Scanner scan,int week){
        System.out.println("Enter the name of the artist");
        String name = scan.next();
        System.out.println("Enter the date of the concert *dd/MM/yy*");
        String stringDate = scan.next();
        LocalDate date = LocalDate.parse(stringDate, formatter);
        System.out.println("Enter the desired capacity of the play");
        int desiredCapacity = scan.nextInt();
        boolean  done  = programation.setEvent(new Concert(name,date,desiredCapacity),week);
        if(done)
            System.out.println("Concert set!");
        else
            System.out.println("Week is full");  
    }


    private void PLANNING(Scanner scan){
        WEEK(scan);
        System.out.println("dfshsdjfk");
    }

}