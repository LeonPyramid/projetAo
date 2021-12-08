package local.culturalprogrammation.repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import local.culturalprogramation.domain.programtion.*;

public class ProgramationRepository {
    public static Programation loadProgramation(String filePath){
        try{
            File file =new File(filePath);
            if(!file.exists())
                throw new RuntimeException("The file doesn't exists");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);

            Programation prog =(Programation) o.readObject();

            o.close();
            f.close();

            return prog;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String saveProgramation(Programation programmation,String filePath){
        try{
            File file =new File(filePath);
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(programmation);

            o.close();
            f.close();
            return "Ok";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not ok";
    }
}
