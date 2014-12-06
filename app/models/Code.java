package models;

/**
 * Created by gervasiosantos on 27/11/14.
 */

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Entity
@Table(name = "Code")
public class Code extends Model{

    @Id
    public String name;

    @Column(columnDefinition = "BYTEA")
    public byte[] source;

    public int descriptionID;
    public String submitterID;
    public static Model.Finder<String, Code> findByName = new Model.Finder<String , Code>(String.class, Code.class);

    public Code(String name, int descriptionID, String submitter){
        this.name = name;
        this.descriptionID = descriptionID;
        this.submitterID = submitter;
    }

    public void initSource(File file){
        this.source = fileToByteArray(file);
    }

    public Description getDescription(){
        return Description.finder.byId(descriptionID);
    }

    public User getSubmitter(){
        return User.find.byId(submitterID);
    }

    public int nota(){
        List<Ranking> opinioes = Ranking.finder.all();
        int nota = 0;
        for(Ranking r : opinioes){
            if(r.codeID.equals(this.name)){
                if(r.likes_code) nota++;
                else nota--;
            }
        }
        return  nota;
    }


    private static byte[] fileToByteArray(File file){
        FileInputStream fileInputStream = null;
        byte[] source = new byte[(int) file.length()];
        try{
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(source);
            fileInputStream.close();

        }catch(Exception e){
            System.out.print("Error in reading file.\n");
            e.printStackTrace();
        }
        return source;
    }

}
