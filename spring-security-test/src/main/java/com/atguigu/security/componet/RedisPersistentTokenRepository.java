package com.atguigu.security.componet;

import java.util.Date;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

/**
 * 我们可以自己实现PersistentTokenRepository来完成将记住我令牌crud的功能
 *
 * @author lfy
 */
@Service
public class RedisPersistentTokenRepository implements PersistentTokenRepository {

    //jedis

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        // TODO Auto-generated method stub
        // String key = JSON.toJSON(token)
        // jedis.set("hahahahahaha",key)

    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        // TODO Auto-generated method stub
        // String key = JSON.toJSON(token)
        // jedis.update("hahahahahaha",key)

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        // TODO Auto-generated method stub
        // String key = JSON.toJSON(token)
        // jedis.get("hahahahahaha",key)
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        // TODO Auto-generated method stub
        // String key = JSON.toJSON(token)
        // jedis.del("hahahahahaha",key)

    }

}
