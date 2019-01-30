package org.plu.RESTSpringBoot.repository;

import org.plu.RESTSpringBoot.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    int countByUserId(int userId);

    @Query("select count(e) from Post e")
    int countAll();

    @Query(value = "Select post_path, username, user.id as userId, post.id as postId from post inner join user on post.user_id=user.id limit :limit offset :offset", nativeQuery = true)
    List<Object[]> selectAll(@Param("limit") int limit, @Param("offset") int offset);


}
