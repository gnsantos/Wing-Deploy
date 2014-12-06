package controllers;

/**
 * Created by gervasiosantos on 27/11/14.
 */

import java.util.List;
import java.util.LinkedList;

import models.*;

import play.mvc.Result;

import views.html.showquery;
import views.html.show_comments;

import play.mvc.Controller;

import play.data.Form;



public class Consultas extends Controller {

    public static Result getAllCodes(){
        List<Code> codes = Code.findByName.all();
        return ok(showquery.render(codes));
    }

    public static Result searchCodesByDescription(){
        Search search = Form.form(Search.class).bindFromRequest().get();

        List<Code> codes = Code.findByName.all();
        List<Code> results = new LinkedList<Code>();

        for (Code source : codes){
            Description info = source.getDescription();
            if(info.description.contains(search.search)){
                results.add(source);
            }
        }
        return ok(showquery.render(results));
    }

    public static Result getAllComments(String codeName){
        Code code = Code.findByName.byId(codeName);
        List<Ranking> rankings = Ranking.finder.all();
        List<String> comments = new LinkedList<String>();
        for(Ranking r : rankings){
            if(r.codeID.equals(codeName))
                comments.add(r.comment);
        }
        return ok(show_comments.render(comments, code));
    }

}
