import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.users.routes;
import models.Post;
import models.report.DailyReport;
import models.user.User;
import org.joda.time.DateTime;
import org.junit.*;

import play.Logger;
import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;

import static play.test.Helpers.*;
import static org.junit.Assert.*;
import static org.fest.assertions.Assertions.*;


/**
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 */
public class ApplicationTest {

    @Before
    public void configureDatabase() {
        fakeApplication(inMemoryDatabase());
    }



    /**
     * Checks if user table is empty
     */
    @Test
    public void testDatabase() {
        List<User> users = User.findAll();
        assertNotNull(users);
    }

    /**
     * Test saving user in database.
     */
    @Test
    public void testSavingInDatabase() {
        User u = new User();
        u.setEmail("test@test.com");
        u.setPassword("passwordtest");
        u.setFirstName("name");
        u.save();
    }

    /**
     * Test if user saved in testSavingInDatabase
     * method can be loaded from database.
     */
    @Test
    public void testSavingAndLoadingFromDatabase() {
        User temp = new User();
        temp.setEmail("email@email.com");
        temp.setPassword("password");
        temp.setFirstName("username");
        temp.save();

        User u = User.findByEmail("email@email.com");
        assertNotNull(u);
    }


    @Test
    public void testSavingDaily() {
        DailyReport daily = new DailyReport();
        daily.setName("name");
        daily.setData("data");
        daily.setCreatedDate(new DateTime());

        daily.save();

        List<DailyReport> d = DailyReport.getFinder().all();
        assertNotNull(d);

    }

    /**
     * Expected not to find user with this id
     */
    @Test
    public void testNonexistentUser() {
        User u = User.findById(90000000000L);
        assertNull(u);
    }

    @Test
    public void testFindPostByID() {
        User u = new User();
        u.setEmail("email@email.com");
        u.setPassword("password");
        u.setFirstName("username");
        u.save();

        Post p = new Post();
        p.setTitle("title");
        p.setContent("content");
        p.setUser(u);
        p.setVisibleToMentors(true);
        p.save();

        Post post = Post.findPostById(1L);

        assertEquals(post, p);
    }

    @Test
    public void testFindPostByUser() {
        User u = new User();
        u.setEmail("email@email.com");
        u.setPassword("password");
        u.setFirstName("username");
        u.save();

        Post p = new Post();
        p.setTitle("title");
        p.setContent("content");
        p.setUser(u);
        p.setVisibleToMentors(true);
        p.save();

        List<Post> posts = Post.findPostsByUser(u);

        assertNotNull(posts);
        assertEquals(p, posts.get(0));
    }

    @Test
    public void testFindAllPosts() {
        User u = new User();
        u.setEmail("email@email.com");
        u.setPassword("password");
        u.setFirstName("username");
        u.save();

        Post p = new Post();
        p.setTitle("title");
        p.setContent("content");
        p.setUser(u);
        p.setVisibleToMentors(true);
        p.save();

        User u1 = new User();
        u1.setEmail("email1@email.com");
        u1.setPassword("password1");
        u1.setFirstName("username1");
        u1.save();

        Post p1 = new Post();
        p1.setTitle("title1");
        p1.setContent("content1");
        p1.setUser(u1);
        p1.setVisibleToMentors(false);
        p1.save();

        List<Post> posts = Post.findAllPosts();

        assertEquals(2, posts.size());
    }

    @Test
    public void testGetDate() {
        User u = new User();
        u.setEmail("email@email.com");
        u.setPassword("password");
        u.setFirstName("username");
        u.save();

        Post p = new Post();
        p.setTitle("title");
        p.setContent("content");
        p.setUser(u);
        p.setVisibleToMentors(true);
        p.save();
    }

    @Test
    public void testIndex() {
        running(fakeApplication(), () -> {
            Result result = route(routes.UserController.test());
            assertEquals(result.status(), SEE_OTHER);
            assertThat(result.status()).isEqualTo(SEE_OTHER);
            assertThat(result.redirectLocation()).isEqualTo("/login");
        });
    }



}