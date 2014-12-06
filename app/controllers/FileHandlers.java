package controllers;

import models.Code;
import models.CodeSubmission;
import models.Description;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gervasiosantos on 05/12/14.
 */
public class FileHandlers extends Controller {

    public static Result addCode(){
        Form<CodeSubmission> codeSubmissionForm = Form.form(CodeSubmission.class).bindFromRequest();

        String submitterUsername = codeSubmissionForm.field("submitter").value();
        String password = codeSubmissionForm.field("password").value();
        String filename = codeSubmissionForm.field("filename").value();
        String language = codeSubmissionForm.field("language").value();
        String description = codeSubmissionForm.field("description").value();
        File submittedFile;

        User submittingUser = User.find.byId(submitterUsername);

        if(submittingUser == null){
            return ok(index.render("AddCodeError"));
        } else if(!password.equals(submittingUser.getPassword())){
            return ok(index.render("WrongPassword"));
        }

        Description info = new Description(codeSubmissionForm.hashCode(), description, language);

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("file");

        if(filePart != null){
            submittedFile = filePart.getFile();
        } else{
            return ok(index.render("InvalidFileError"));
        }

        Code code = new Code(filename, info.id, submitterUsername);
        code.initSource(submittedFile);
        info.save();
        code.save();

        return redirect(routes.Application.index());
    }


    public static Result downloadFile(String filename){
        Code download = Code.findByName.byId(filename);
        String filePath;

        if(download == null){
            return ok(index.render("DownloadError"));
        }

        try{
            filePath = "/tmp/" + download.name;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(download.source);
        } catch(IOException e){
            e.printStackTrace();
            return ok(index.render("DownloadError"));
        }

        return ok(new java.io.File(filePath));
    }
}
