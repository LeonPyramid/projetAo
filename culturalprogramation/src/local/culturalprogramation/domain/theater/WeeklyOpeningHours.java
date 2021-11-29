package local.culturalprogramation.domain.theater;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

//TODO:Vérifier que les heures ne soient pas rangées dans le mauvais sens (fermeture<ouverture)
//TODO:Renvoyer des erreurs dansles cas où les valeurs sont pas bonnes

public class WeeklyOpeningHours {
    private Hashtable<Integer,List<Integer>> dailyOpeningHours;
    private Hashtable<Integer,List<Integer>> dailyOpeningMinutes;

    WeeklyOpeningHours(){
        dailyOpeningHours = new Hashtable<Integer,List<Integer>>();
        dailyOpeningMinutes = new Hashtable<Integer,List<Integer>>();
        for(int day  = 1; day < 8 ; day++){
            ArrayList<Integer> alHour = new ArrayList<Integer>(2);
            alHour.add(0,0);
            alHour.add(1,0);
            ArrayList<Integer> alMin = new ArrayList<Integer>(2);
            alMin.add(0,0);
            alMin.add(1,0);
            dailyOpeningMinutes.put(day,alMin);
        }
    }

    void setOpeningHour(int day,int hour,int min){
        if(day < 8 && day > 0){
            if(hour < 25 && hour >= 0){
                if(min < 61 && min >= 0){
                    (dailyOpeningHours.get(day)).set(0,hour);
                    (dailyOpeningMinutes.get(day)).set(0,min);
                }
            }
        }
    }

    void setClosingHour(int day,int hour,int min){
        if(day < 8 && day > 0){
            if(hour < 25 && hour >= 0){
                if(min < 61 && min >= 0){
                    (dailyOpeningHours.get(day)).set(1,hour);
                    (dailyOpeningMinutes.get(day)).set(1,min);
                }
            }
        }
    }

    /**
     * Return the opening and clsoing hour for the given day of the week
     * @param day from Calendar 
     * @return A String at format "[hh:mm,hh:mm]" with opening hour at left and closing hour at right
     */
    String getDayHours(int day){
        if(day < 8 && day > 0){
            String ret = "[" + dailyOpeningHours.get(day).get(0) + ":" + dailyOpeningMinutes.get(day).get(0) + ","
            + dailyOpeningHours.get(day).get(1) + ":" + dailyOpeningMinutes.get(day).get(1) + "]";
            return ret;
        }
        return null;
    }
}
