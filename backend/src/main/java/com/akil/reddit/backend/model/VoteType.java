package com.akil.reddit.backend.model;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1),
    ;

    private Integer direction;

    VoteType(Integer direction){
    }

    public static VoteType lookUp(Integer direction){
        return Arrays.stream(VoteType.values()).filter(val -> val.getDirection().equals(direction))
                .findAny().orElseThrow(()-> new NoSuchElementException("Vote Not Found"));
    }

    public Integer getDirection(){
        return direction;
    }
}
