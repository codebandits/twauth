package com.twauth.api.login;

import com.twauth.api.tweet.HashtagTweetStreamFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.TwitterStream;

@Configuration
public class LoginTweetStreamConfiguration {

    @Bean
    public TwitterStream loginTweetStream(
            HashtagTweetStreamFactory hashtagTweetStreamFactory,
            @Value("${hashtag}") String hashtag
    ) {
        return hashtagTweetStreamFactory.create(hashtag);
    }
}
