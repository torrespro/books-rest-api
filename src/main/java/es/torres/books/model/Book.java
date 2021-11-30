package es.torres.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Book {

    public interface Basic {};
    public interface Detail {};

    @JsonView(Basic.class)
    private Long id;
    @JsonView(Basic.class)
    @NotBlank
    @Size(min = 2, max = 200, message = "book title should have at least 2 characters and not more than 200")
    private String title;
    @JsonView(Detail.class)
    private String summary;
    @JsonView(Detail.class)
    private String author;
    @JsonView(Detail.class)
    private String publisher;
    @JsonView(Detail.class)
    private Integer published_year;
    @JsonIgnore
    private ConcurrentMap<Long, Comment> comments = new ConcurrentHashMap();

    public Book(Long id, String title, String summary, String author, String publisher, Integer published_year,
        ConcurrentMap<Long, Comment> comments) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.author = author;
        this.publisher = publisher;
        this.published_year = published_year;
        this.comments = comments;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublished_year() {
        return published_year;
    }

    public void setPublished_year(Integer published_year) {
        this.published_year = published_year;
    }

    @JsonView(Detail.class)
    public Collection<Comment> getComments() {return comments.values();}

    public ConcurrentMap<Long, Comment> getCommentsMap() {
        return comments;
    }

    public void setComments(ConcurrentMap<Long, Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        var that = (Book) obj;
        return Objects.equals(this.id, that.id) &&
            Objects.equals(this.title, that.title) &&
            Objects.equals(this.summary, that.summary) &&
            Objects.equals(this.author, that.author) &&
            Objects.equals(this.publisher, that.publisher) &&
            Objects.equals(this.published_year, that.published_year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, author, publisher, published_year);
    }

    @Override
    public String toString() {
        return "Book[" +
            "id=" + id + ", " +
            "title=" + title + ", " +
            "summary=" + summary + ", " +
            "author=" + author + ", " +
            "publisher=" + publisher + ", " +
            "published_year=" + published_year + ']';
    }

}
