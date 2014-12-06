import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import models.Code;
import models.Description;
import models.Ranking;
import models.User;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import scalaz.Ran;

import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {
    @Test
    public void signUpShouldInsertUserInDB(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                User mockUser = new User("mockName", "mock@email.com", "mock_password");
                mockUser.save();

                User userInDB = User.find.byId("mockName");

                assertTrue(userInDB != null);

                mockUser.delete();
            }
        });
    }

    @Test
    public void uploadingCodeShouldMakeItAppearInTheDB(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Code mockCode = new Code("mockName", 0 , "mockUserID");
                mockCode.save();

                Code codeInDB = Code.findByName.byId("mockName");
                assertTrue(codeInDB != null);

                mockCode.delete();
            }
        });
    }

    @Test
    public void commentingACodeShouldBeReflectedInDatabase(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                int mockId = 1 << 20;
                Ranking mockComment = new Ranking(mockId, "mockName", true, "This is a Mock Comment");
                mockComment.save();

                Ranking rankingInDB = Ranking.finder.byId(mockId);
                assertTrue(rankingInDB != null);

                mockComment.delete();
            }
        });
    }

    @Test
    public void likingACodeMakesItsScoreToGoUp(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Code mockCode = new Code("mockName", 0 , "mockUserID");
                int mockId = 1 << 20;
                Ranking mockComment = new Ranking(mockId, "mockName", true, "This is a Mock Comment");
                mockComment.save();
                assertTrue(mockCode.nota() == 1);
                mockComment.delete();
            }
        });
    }

    @Test
    public void gettingUserByIdInsideCodeReturnsActualCodeSubmitter(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                User mockUser = new User("mockUsername", "mock@email.com", "mock_password");
                mockUser.save();
                Code mockCode = new Code("mockName", 0 , "mockUsername");

                User objectRetrived = mockCode.getSubmitter();

                assertTrue(objectRetrived != null);
                assertTrue(objectRetrived.username.equals("mockUsername"));
                assertTrue(objectRetrived.getPassword().equals("mock_password"));

                mockUser.delete();
            }
        });
    }

    @Test
    public void gettingDescriptionReturnsActualCodeDescription(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                int mockId = 1 << 20;
                Code mockCode = new Code("mockName", mockId , "mockUsername");
                Description mockInfo = new Description(mockId, "this is the code's description", "programming language");

                mockInfo.save();

                Description retrievedDescription = mockCode.getDescription();

                assertTrue(retrievedDescription.description.equals("this is the code's description"));
                assertTrue(retrievedDescription.language.equals("programming language"));

                mockInfo.delete();
            }
        });
    }

}
