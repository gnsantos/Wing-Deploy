package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.comments;
import views.html.signup;
import views.html.submission;

/**
 * Created by gervasiosantos on 05/12/14.
 */
public class Redirect extends Controller {
    public static Result signUp() {
        return ok(signup.render());
    }

    public static Result submissions() {
        return ok(submission.render());
    }

    public static Result comments() {
        return ok(comments.render());
    }
}
