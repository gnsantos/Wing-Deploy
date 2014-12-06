package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by gervasiosantos on 27/11/14.
 */

@Entity
@Table(name = "Ranking")
public class Ranking extends Model{

    @Id
    public Integer id;

    public String codeID;
    public boolean likes_code;
    @Column(columnDefinition = "TEXT")
    public String comment;
    public static Model.Finder<Integer, Ranking> finder = new Model.Finder<Integer, Ranking>(Integer.class, Ranking.class);


    public Ranking(Integer id, String codeID, boolean like, String comment){
        this.id = id;
        this.likes_code = like;
        this.comment = comment;
        this.codeID = codeID;
    }
}
