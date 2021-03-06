package local.culturalprogramation.domain.theaterAndProgramation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


 class WeeklyOpeningHours implements Serializable {
    private static final long serialVersionUID = 1L;
    private Hashtable<Integer,List<Integer>> dailyOpeningHours;
    private Hashtable<Integer,List<Integer>> dailyOpeningMinutes;
    

    WeeklyOpeningHours(){
        dailyOpeningHours = new Hashtable<Integer,List<Integer>>();
        dailyOpeningMinutes = new Hashtable<Integer,List<Integer>>();
        for(int day  = 1; day < 8 ; day++){
            ArrayList<Integer> alHour = new ArrayList<Integer>(2);
            alHour.add(0,0);
            alHour.add(1,0);
            dailyOpeningHours.put(day,alHour);
            ArrayList<Integer> alMin = new ArrayList<Integer>(2);
            alMin.add(0,0);
            alMin.add(1,0);
            dailyOpeningMinutes.put(day,alMin);
        }
    }

    /**
     * Set the opening hour
     * @param day Day of the week to set
     * @param hour Hour to set
     * @param min Minute to set
     */
    void setOpeningHour(DayOfWeek day,int hour,int min){
        int vday = day.getValue();
        if(hour < 25 && hour >= 0){
            if(min < 61 && min >= 0){
                (dailyOpeningHours.get(vday)).set(0,hour);
                (dailyOpeningMinutes.get(vday)).set(0,min);
                return;
            }
        }
        throw new RuntimeException("The parameters givent aren't corresponding to a day of the week, hour, or minute time");
    }

    /**
     * Set the closing hour
     * @param day Day of the week to set from DayOfWeek
     * @param hour Hour to set
     * @param min Minute to set
     */
    void setClosingHour(DayOfWeek day,int hour,int min){
        int vday = day.getValue();
        if(hour < 25 && hour >= 0){
            if(min < 61 && min >= 0){
                (dailyOpeningHours.get(vday)).set(1,hour);
                (dailyOpeningMinutes.get(vday)).set(1,min);
                return;
            }
        }
        throw new RuntimeException("The parameters givent aren't corresponding to a day of the week, hour, or minute time");
    }

    /**
     * Return the opening and clsoing hour for the given day of the week
     * @param day from DayOfWeek 
     * @return A String at format "[hh:mm,hh:mm]" with opening hour at left and closing hour at right
     */
     String getDayHours(DayOfWeek day){
        int vday = day.getValue();
        String ret = "[" + String.format("%02d",dailyOpeningHours.get(vday).get(0)) + ":" + String.format("%02d",dailyOpeningMinutes.get(vday).get(0)) + ","
        + String.format("%02d",dailyOpeningHours.get(vday).get(1)) + ":" +  String.format("%02d",dailyOpeningMinutes.get(vday).get(1)) + "]";
        return ret;
    }
    /**
     * Initialize weekly hours from a dtp file
     * @param path Path to the dtp file
     */
    void loadPlanningFile(String path){
        try{
            Path lPath = Paths.get(path).toAbsolutePath();
            BufferedReader reader = Files.newBufferedReader(lPath);
            String line = null;
            Boolean isEdited[] = new Boolean[7];
            for(int i = 0; i < 7 ; i ++){
                isEdited[i] = false;
            }
            WeeklyOpeningHours localWoh = new WeeklyOpeningHours();
            while((line = reader.readLine())!= null){
                if(!line.matches("[A-Z]{3}\\s\\[\\d{2}\\:\\d{2}\\,\\d{2}\\:\\d{2}\\]")){
                    System.err.println("File formart not matching a planning format: DAY [HH:MM,hh:mm]");
                    return;
                }
                line = line.replace("[", "");
                line = line.replace("]", "");
                String split[] = line.split("\\s|\\:|\\,");
                DayOfWeek day = null;
                switch (split[0]){
                    case "MON":
                        if(isEdited[0]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[0] = true;
                        day = DayOfWeek.MONDAY;
                    break;
                    case "TUE":
                        if(isEdited[1]){
                            System.err.println("TUE has already been loaded from this file!");
                            return;
                        }
                        isEdited[1] = true;
                        day = DayOfWeek.TUESDAY;
                    break;
                    case "WED":
                        if(isEdited[2]){
                            System.err.println("WED has already been loaded from this file!");
                            return;
                        }
                        isEdited[2] = true;
                        day = DayOfWeek.WEDNESDAY;
                    break;
                    case "THU":
                        if(isEdited[3]){
                            System.err.println("THU has already been loaded from this file!");
                            return;
                        }
                        isEdited[3] = true;
                        day = DayOfWeek.THURSDAY;
                    break;
                    case "FRI":
                        if(isEdited[4]){
                            System.err.println("FRI has already been loaded from this file!");
                            return;
                        }
                        isEdited[4] = true;
                        day = DayOfWeek.FRIDAY;
                    break;
                    case "SAT":
                        if(isEdited[5]){
                            System.err.println("SAT has already been loaded from this file!");
                            return;
                        }
                        isEdited[5] = true;
                        day = DayOfWeek.SATURDAY;
                    break;
                        case "SUN":
                        if(isEdited[6]){
                            System.err.println("SUN has already been loaded from this file!");
                            return;
                        }
                        isEdited[6] = true;
                        day = DayOfWeek.SUNDAY;
                    break;

                    default:
                    System.err.println(("Error: One of the day in the list is not a day of the week!"));
                }
                int intVal[] = new int[4];
                for(int i = 0; i < 4; i ++){
                    intVal[i] = Integer.parseInt(split[i+1]);
                }
                if(intVal[0]>24||intVal[1]>60||intVal[2]>24||intVal[3]>60){
                    throw new RuntimeException("One of the time information is not a time format");
                }

                if(intVal[0]>intVal[2]||(intVal[0]==intVal[2]&&intVal[1]>intVal[3])){
                    System.err.println(("Warning: hour are inversed in the file. Automaticaly reversing them"));
                    int tmp = intVal[2];
                    intVal[2] = intVal[0];
                    intVal[0] = tmp;
                    tmp = intVal[3];
                    intVal[3] = intVal[1];
                    intVal[1] = tmp;
                }

                localWoh.setOpeningHour(day, intVal[0], intVal[1]);
                localWoh.setClosingHour(day, intVal[2], intVal[3]);
            }
            for(Boolean b : isEdited){
                if(!b){
                    System.err.println("Warning: Not all day were loaded. Day not loaded will be set to [00:00,00:00]");
                    break;
                }
            }
            this.copyParameters(localWoh);


        }
        catch(IOException e){
            e.printStackTrace();
            System.err.println("File not loaded, value are still the same");
            return;
        }
    }

    /**
     * Set the hours at woh's hours
     * @param woh WeeklyOpeningHours to copy from
     */
    private void copyParameters(WeeklyOpeningHours woh){
        for( DayOfWeek day : DayOfWeek.values()){
            this.setOpeningHour(day, woh.dailyOpeningHours.get(day.getValue()).get(0),  woh.dailyOpeningMinutes.get(day.getValue()).get(0));
            this.setClosingHour(day, woh.dailyOpeningHours.get(day.getValue()).get(1),  woh.dailyOpeningMinutes.get(day.getValue()).get(1));

        }
    }

    /**
     * Get a copy of WeeklyOpeningHours
     * @return a copy of WeeklyOpeningHours
     */
    WeeklyOpeningHours copy(){
        WeeklyOpeningHours newWoh = new WeeklyOpeningHours();
        for( DayOfWeek day : DayOfWeek.values()){
            this.setOpeningHour(day, newWoh.dailyOpeningHours.get(day.getValue()).get(0),  newWoh.dailyOpeningMinutes.get(day.getValue()).get(0));
            this.setClosingHour(day, newWoh.dailyOpeningHours.get(day.getValue()).get(1),  newWoh.dailyOpeningMinutes.get(day.getValue()).get(1));

        }
        return newWoh;
    }
}
