package org.plu.RESTSpringBoot.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class Post implements Serializable {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    private String postPath;

    @Column(name = "date")
    private Long created;

    @ManyToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "likes")
    List<ApplicationUser> likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPostPath() {
        return postPath;
    }

    public void setPostPath(String postPath) {
        this.postPath = postPath;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
