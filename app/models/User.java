package models;

/**
 * Created by gervasiosantos on 25/11/14.
 */

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User extends Model {

    @Id
    public String username;

    public String email;
    public String password;
    public static Finder<String, User> find = new Finder<String , User>(String.class, User.class);

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

}
