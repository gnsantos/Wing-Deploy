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
@Table(name = "Description")
public class Description extends Model{

    @Id
    public Integer id;

    @Column(columnDefinition = "TEXT")
    public String description;

    public String language;

    public static Model.Finder<Integer, Description> finder = new Model.Finder<Integer, Description>(Integer.class, Description.class);

    public Description(int id, String description, String language) {
        this.description = description;
        this.language = language;
        this.id = new Integer(id);
    }
}
