package com.akil.reddit.backend.service;

import com.akil.reddit.backend.dto.SubredditDto;
import com.akil.reddit.backend.model.SubReddit;
import com.akil.reddit.backend.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        SubReddit save = subredditRepository.save(mapDtoToSubreddit(subredditDto));
        subredditDto.setId(save.getSubRedditId());
        return subredditDto;
    }

    private SubReddit mapDtoToSubreddit(SubredditDto subredditDto){
        return SubReddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription()).build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapSubredditToDto(SubReddit subReddit) {
            return SubredditDto.builder().id(subReddit.getSubRedditId())
                    .name(subReddit.getName())
                    .description(subReddit.getDescription()).build();
    }
}
