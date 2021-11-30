package es.torres.books.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Comment {

    public interface Basic {};

    @JsonView(Basic.class)
    private Long id;
    @JsonView(Basic.class)
    @NotBlank
    @Size(min = 2, max = 20, message = "username should have at least 2 characters and not more than 20")
    private String username;
    @JsonView(Basic.class)
    @NotBlank
    @Size(min = 3, max = 200, message = "comments should have at least 2 characters and not more than 200")
    private String text;
    @JsonView(Basic.class)
    @Min(0)
    @Max(5)
    private Short rating;

    public Comment(Long id, String username, String text, Short rating) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.rating = rating;
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Comment) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.username, that.username) &&
            Objects.equals(this.text, that.text) &&
            Objects.equals(this.rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, text, rating);
    }

    @Override
    public String toString() {
        return "Comment[" +
            "id=" + id + ", " +
            "username=" + username + ", " +
            "text=" + text + ", " +
            "rating=" + rating + ']';
    }


}
