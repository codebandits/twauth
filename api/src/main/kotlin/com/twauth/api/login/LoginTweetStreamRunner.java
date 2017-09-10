package com.twauth.api.login;

import com.twauth.api.tweet.HashtagTweetStreamFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import twitter4j.TwitterStream;

@Component
public class LoginTweetStreamRunner {

    private HashtagTweetStreamFactory hashtagTweetStreamFactory;
    private String hashtag;

    private TwitterStream loginTweetStream;

    public LoginTweetStreamRunner(
            HashtagTweetStreamFactory hashtagTweetStreamFactory,
            @Value("${hashtag}") String hashtag
    ) {
        this.hashtagTweetStreamFactory = hashtagTweetStreamFactory;
        this.hashtag = hashtag;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void connectToStream() {
        loginTweetStream = hashtagTweetStreamFactory.create(hashtag);
    }
}
