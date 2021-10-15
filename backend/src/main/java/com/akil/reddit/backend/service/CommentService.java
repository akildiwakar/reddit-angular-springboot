package com.akil.reddit.backend.service;

import com.akil.reddit.backend.dto.CommentsDto;
import com.akil.reddit.backend.exception.PostNotFoundException;
import com.akil.reddit.backend.mapper.CommentMapper;
import com.akil.reddit.backend.model.Comment;
import com.akil.reddit.backend.model.NotificationEmail;
import com.akil.reddit.backend.model.Post;
import com.akil.reddit.backend.model.User;
import com.akil.reddit.backend.repository.CommentRepository;
import com.akil.reddit.backend.repository.PostRepository;
import com.akil.reddit.backend.repository.UserRepository;
import com.akil.reddit.backend.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private static final String POST_URL = "";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}

