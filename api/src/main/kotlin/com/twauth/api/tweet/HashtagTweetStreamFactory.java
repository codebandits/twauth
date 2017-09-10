package com.twauth.api.tweet;

import com.twauth.api.login.LoginTweetProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.validation.constraints.NotNull;

@Component
public class HashtagTweetStreamFactory {

    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;
    private final LoginTweetProcessor loginTweetProcessor;

    public HashtagTweetStreamFactory(
            @Value("${twitter.consumer-key}") String consumerKey,
            @Value("${twitter.consumer-secret}") String consumerSecret,
            @Value("${twitter.access-token}") String accessToken,
            @Value("${twitter.access-token-secret}") String accessTokenSecret,
            LoginTweetProcessor loginTweetProcessor
    ) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        this.loginTweetProcessor = loginTweetProcessor;
    }

    public TwitterStream create(@NotNull String hashtag) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        cb.setOAuthAccessToken(accessToken);
        cb.setOAuthAccessTokenSecret(accessTokenSecret);
        Configuration configuration = cb.build();

        TwitterStream stream = new TwitterStreamFactory(configuration).getInstance();
        stream.addListener(loginTweetProcessor);
        String filter = "#" + hashtag;
        stream.filter(new FilterQuery(filter));
        return stream;
    }
}
