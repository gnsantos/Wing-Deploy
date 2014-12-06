package controllers;

import models.Code;
import models.Ranking;
import models.RankingSubmission;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by gervasiosantos on 05/12/14.
 */
public class CommentAdder extends Controller {
    public static Result addRanking(){
        RankingSubmission rank = Form.form(RankingSubmission.class).bindFromRequest().get();
        Code code = Code.findByName.byId(rank.codename);
        if(code == null){
            return ok(index.render("AddCommentError"));
        }
        boolean like = false;
        if(rank.like.equals("like")) like = true;
        Ranking ranking = new Ranking(rank.hashCode(),code.name,like,rank.comment);
        ranking.save();
        return redirect(routes.Application.index());
    }

}
