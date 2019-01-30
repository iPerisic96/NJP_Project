package org.plu.RESTSpringBoot.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "follower")
public class Followers implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "following_id")
    private int followingId;

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

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }
}
