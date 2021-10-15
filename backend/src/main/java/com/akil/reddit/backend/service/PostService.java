package com.akil.reddit.backend.service;

import com.akil.reddit.backend.dto.PostRequest;
import com.akil.reddit.backend.dto.PostResponse;
import com.akil.reddit.backend.exception.SpringRedditException;
import com.akil.reddit.backend.mapper.PostMapper;
import com.akil.reddit.backend.model.Post;
import com.akil.reddit.backend.model.SubReddit;
import com.akil.reddit.backend.model.User;
import com.akil.reddit.backend.repository.PostRepository;
import com.akil.reddit.backend.repository.SubredditRepository;
import com.akil.reddit.backend.repository.UserRepository;
import com.akil.reddit.backend.security.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        SubReddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException(postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        SubReddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SpringRedditException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubReddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
