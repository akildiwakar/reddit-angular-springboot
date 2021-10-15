package com.akil.reddit.backend.exception;

public class SpringRedditException extends RuntimeException {

    private static final long serialVersionUID = -1832655528394298060L;

    public SpringRedditException(String s, Exception e) {
        super(s,e);
    }

    public SpringRedditException(String s){
        super(s);
    }

}
