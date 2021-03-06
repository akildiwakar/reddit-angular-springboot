package com.akil.reddit.backend.mapper;

import com.akil.reddit.backend.dto.CommentsDto;
import com.akil.reddit.backend.model.Comment;
import com.akil.reddit.backend.model.Post;
import com.akil.reddit.backend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "description", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "text", source = "description")
    CommentsDto mapToDto(Comment comment);
}