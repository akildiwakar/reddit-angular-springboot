package com.akil.reddit.backend.repository;

import com.akil.reddit.backend.model.Post;
import com.akil.reddit.backend.model.User;
import com.akil.reddit.backend.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
