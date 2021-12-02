package local.culturalprogramation.domain.theater;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

//TODO:Vérifier que les heures ne soient pas rangées dans le mauvais sens (fermeture<ouverture)
//TODO:Renvoyer des erreurs dansles cas où les valeurs sont pas bonnes

public class WeeklyOpeningHours {
    private Hashtable<Integer,List<Integer>> dailyOpeningHours;
    private Hashtable<Integer,List<Integer>> dailyOpeningMinutes;
    
    public WeeklyOpeningHours(){
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

    public void setOpeningHour(int day,int hour,int min){
        if(day < 8 && day > 0){
            if(hour < 25 && hour >= 0){
                if(min < 61 && min >= 0){
                    (dailyOpeningHours.get(day)).set(0,hour);
                    (dailyOpeningMinutes.get(day)).set(0,min);
                    return;
                }
            }
        }
        //TODO Erreur Borne pas bonnes
    }

    public void setClosingHour(int day,int hour,int min){
        
        if(day < 8 && day > 0){
            if(hour < 25 && hour >= 0){
                if(min < 61 && min >= 0){
                    (dailyOpeningHours.get(day)).set(1,hour);
                    (dailyOpeningMinutes.get(day)).set(1,min);
                    return;
                }
            }
        }
        //TODO Erreur Borne pas bonnes
    }

    /**
     * Return the opening and clsoing hour for the given day of the week
     * @param day from Calendar 
     * @return A String at format "[hh:mm,hh:mm]" with opening hour at left and closing hour at right
     */
    public String getDayHours(int day){
        if(day < 8 && day > 0){
            String ret = "[" + String.format("%02d",dailyOpeningHours.get(day).get(0)) + ":" + String.format("%02d",dailyOpeningMinutes.get(day).get(0)) + ","
            + String.format("%02d",dailyOpeningHours.get(day).get(1)) + ":" +  String.format("%02d",dailyOpeningMinutes.get(day).get(1)) + "]";
            return ret;
        }
        return null;
    }

    public void loadPlanningFile(String path){
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
                int day = -1;
                switch (split[0]){
                    case "MON":
                        if(isEdited[0]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[0] = true;
                        day = Calendar.MONDAY;
                    break;
                    case "TUE":
                        if(isEdited[1]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[1] = true;
                        day = Calendar.TUESDAY;
                    break;
                    case "WED":
                        if(isEdited[2]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[2] = true;
                        day = Calendar.WEDNESDAY;
                    break;
                    case "THU":
                        if(isEdited[3]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[3] = true;
                        day = Calendar.THURSDAY;
                    break;
                    case "FRI":
                        if(isEdited[4]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[4] = true;
                        day = Calendar.FRIDAY;
                    break;
                    case "SAT":
                        if(isEdited[5]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[5] = true;
                        day = Calendar.SATURDAY;
                    break;
                        case "SUN":
                        if(isEdited[6]){
                            System.err.println("MON has already been loaded from this file!");
                            return;
                        }
                        isEdited[6] = true;
                        day = Calendar.SUNDAY;
                    break;

                    default:
                    System.err.println(("One of the day in the list is not a day of the week!"));
                }
                localWoh.setOpeningHour(day, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                localWoh.setClosingHour(day, Integer.parseInt(split[3]), Integer.parseInt(split[4]));
            }
            for(Boolean b : isEdited){
                if(!b){
                    System.err.println("Warning: Not all day were loaded. Day not loaded will be set to [00:00,00:00]");
                    break;
                }
            }
            this.getCopy(localWoh);


        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }

    public void savePlanningFile(String path){
        //TODO:Save a file copy of a PlanningFile
    }

    private void getCopy(WeeklyOpeningHours woh){
        for(int day = 1; day < 8; day ++){
            this.setOpeningHour(day, woh.dailyOpeningHours.get(day).get(0),  woh.dailyOpeningMinutes.get(day).get(0));
            this.setClosingHour(day, woh.dailyOpeningHours.get(day).get(1),  woh.dailyOpeningMinutes.get(day).get(1));
        }
    }

    public WeeklyOpeningHours copy(){
        WeeklyOpeningHours newWoh = new WeeklyOpeningHours();
        for(int day = 1; day < 8; day ++){
            newWoh.setOpeningHour(day, this.dailyOpeningHours.get(day).get(0),  this.dailyOpeningMinutes.get(day).get(0));
            newWoh.setClosingHour(day, this.dailyOpeningHours.get(day).get(1),  this.dailyOpeningMinutes.get(day).get(1));
        }
        return newWoh;
    }
}
