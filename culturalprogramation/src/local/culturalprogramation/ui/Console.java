
package local.culturalprogramation.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import local.culturalprogramation.domain.theaterAndProgramation.Programation;
import local.culturalprogramation.repository.ProgramationRepository;

public class Console {
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
            String opt = scan.nextLine();
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
                    SAVE(scan);
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
                case "CLOSE":
                    CLOSE(scan);
                    break;
                case "CHANGE":
                    CHANGE(scan);
                    break;
                default:
                    System.err.println("No Corresponding Command");
            }
        }
    }
    private String THEATER(Scanner scan){
        System.out.println("Wich theater you want? (Atabal, Krakatoa, Galaxie, Arena, ALL)");
        String name = scan.nextLine();;
        return name;
    }
    private  int  YEAR(Scanner scan){
        int year  = LocalDate.now().getYear();
        System.out.println("Tell us the year you want to program");
        while(true){
            try{
                int desiredYear = Integer.parseInt(scan.nextLine());
                if(desiredYear < year){
                    System.err.println("Year must be supperior as the current year");
                }
                else{
                    return desiredYear;
                }
            }
            catch(NumberFormatException e){
                System.err.println("This is not an integer!");
            }

        }
        
    }
    private  int WEEK(Scanner scan){
        System.out.println("On wich week, do you want to work");
        while (true){
            try{
                int week  = Integer.parseInt(scan.nextLine());
                if(week>0 && week <= 52){
                    return week;
                }
                System.out.println("Week number must be in [1;52]");
            }
            catch(NumberFormatException e){
                System.err.println("This is not an integer!");
            }
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
            try{
                int length  = Integer.parseInt(scan.nextLine());
                if(length>0 && length<=7){
                    return length;
                }
                System.out.println("Length number must be in [1;7]");
            }
            catch(NumberFormatException e){
                System.err.println("This is not an integer!");
            }
        }
    }
    private LocalDate DATE(Scanner scan){
        while(true){
            System.out.println("Choose a date *dd/MM/yy*");
            String sdate = scan.nextLine();
            try{
                LocalDate date = LocalDate.parse(sdate,formatter);
                return date;
            }
            catch(DateTimeParseException e){
                System.err.println("This is not a date, or at a wrong format");
            }
        }

    }

    private  void WIP(){
        System.out.println("WORK IN PROGRESS");
    }

    private  void ADD(Scanner scan){
        int week  = WEEK(scan);
        System.out.println("Choose the type of event : PLAY or CONCERT");
        String opt = scan.nextLine();
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
        String name = scan.nextLine();
        int desiredCapacity;
        while(true){
            System.out.println("Enter the desired capacity of the play");
            try{
                desiredCapacity = Integer.parseInt(scan.nextLine());
                if(desiredCapacity< 1){
                    System.err.println("Capacity must be positive");
                }
                else{
                    break;
                }
            }
            catch(NumberFormatException e){
                System.err.println("This is not an integer!");
            }
        }

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
        String name = scan.nextLine();
        int desiredCapacity;
        while(true){
            System.out.println("Enter the desired capacity of the play");
            try{
                desiredCapacity = Integer.parseInt(scan.nextLine());
                if(desiredCapacity< 1){
                    System.err.println("Capacity must be positive");
                }
                else{
                    break;
                }
            }
            catch(NumberFormatException e){
                System.err.println("This is not an integer!");
            }
        }
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
        //System.out.println(programation.displayTheaterHours(name));
    }

    private void REMOVE(Scanner scan){
        System.out.println("Choose the type of event : PLAY or CONCERT");
        String opt = scan.nextLine();
        switch(opt){
            case "PLAY":
                System.out.println("Enter the name of the play");
                String name = scan.nextLine();
                System.out.print("Enter the date of the play, ");
                LocalDate date = DATE(scan);
                boolean check = programation.removeEventPlay(name,date);
                if (check)
                    System.out.println("Remove done !");
                else
                    System.out.println("Play does not exist");
                break;

            case "CONCERT":
                System.out.println("Enter the name of the concert");
                name = scan.nextLine();
                System.out.print("Enter the date of the concert, ");
                date = DATE(scan);
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

    private void SAVE(Scanner scan){
        System.out.println("Give us the path to the programation to save"); 
        String path =  scan.nextLine();
        try{
            ProgramationRepository.saveProgramation(programation, path);
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Could not save the file");
        }
        System.out.println("Programation saved in : " + path );
    }

    private void LOAD(Scanner scan){
        System.out.println("Give us the path to the programation to load"); 
        String path =  scan.nextLine();
        Programation tmpProg = null;
    
        try{
            tmpProg = ProgramationRepository.loadProgramation(path);
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println("Could not load the file");
        }
        if (tmpProg != null)
            programation = tmpProg;
    }

    private void CLOSE(Scanner scan){
        String theater = THEATER(scan);
        LocalDate date = DATE(scan);
        programation.close(theater,date);
    }

    private void CHANGE(Scanner scan){
        String theater = THEATER(scan);
        LocalDate date = DATE(scan);
        String line;
        while(true){
            System.out.println("Please enter the new opening hour and closing hour *HH:MM,HH:MM*");
            line = scan.nextLine();
            if(!line.matches("\\d{2}\\:\\d{2}\\,\\d{2}\\:\\d{2}")){
                System.err.println("Entry format not matching a planning format: HH:MM,HH:MM");
            }
            else{
                break;
            }
        }
        String split[] = line.split("\\:|\\,");

        
        programation.change(theater,date,Integer.parseInt(split[0]),Integer.parseInt(split[1])
            ,Integer.parseInt(split[2]),Integer.parseInt(split[3]));
    }

}