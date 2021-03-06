package models;

import com.avaje.ebean.Model;
import models.user.User;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Medina Banjic
 */
@Entity
public class Post extends Model {

    public static final int ASSIGNMENT = 1;
    public static final int ANNOUNCEMENT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="title")
    private String title;
    @Column(columnDefinition = "text")
    @Constraints.Required
    private String content;
    @Column(name="post_type")
    private Integer postType;
    @Column(name="visible_mentors")
    private Boolean visibleToMentors;
    @Column(name = "date")
    private String date;
    @Column(name = "link")
    private String link;
    @Column(name="files")
    private List<String> files = new ArrayList<>();
    @ManyToOne
    private User user;

    public Post() {
    }

    public Post(String title, String content, Integer postType, Boolean visible, User user, String date) {
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.visibleToMentors = visible;
        this.user = user;
        this.date = date;
    }

    private static final Model.Finder<Long, Post> find = new Model.Finder<>(Post.class);

    public static List<Post> findPostsByUser(final User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

    public static Post findPostById(Long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }

    public static List<Post> findAllPosts() {
        return find.all();
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public String getDate() {
        String[] parts = date.split("-");
        return parts[2] + "." + parts[1] + "." + parts[0];
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Boolean getVisibleToMentors() {
        return visibleToMentors;
    }

    public void setVisibleToMentors(Boolean visibleToMentors) {
        this.visibleToMentors = visibleToMentors;
    }

    public String toString() {
        return "Post: " + title + " ["  +
                content + "]  " +
                " by: " + user.getFirstName();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public List<String> getFiles() {
        return files;
    }
}