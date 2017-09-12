package com.twauth.api.login;

import com.twauth.api.tweet.HashtagTweetStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import twitter4j.TwitterStream;

@Component
public class LoginTweetStreamRunner {

    private Logger logger = LoggerFactory.getLogger(LoginTweetStreamRunner.class);

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
        logger.info("Context refreshed, creating twitter stream");
        loginTweetStream = hashtagTweetStreamFactory.create(hashtag);
    }
}
