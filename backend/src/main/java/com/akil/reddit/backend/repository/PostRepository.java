package com.akil.reddit.backend.repository;

import com.akil.reddit.backend.model.Post;
import com.akil.reddit.backend.model.SubReddit;
import com.akil.reddit.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubReddit(SubReddit subreddit);

    List<Post> findByUser(User user);
}
