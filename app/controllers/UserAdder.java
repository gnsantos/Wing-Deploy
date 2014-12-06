package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by gervasiosantos on 05/12/14.
 */
public class UserAdder extends Controller {
    public static Result addUser() {
        User user = Form.form(User.class).bindFromRequest().get();

        User checkIfExists = User.find.byId(user.username);

        if(checkIfExists != null){
            return ok(index.render("AddUserError"));
        }

        user.save();
        return redirect(routes.Application.index());
    }
}
