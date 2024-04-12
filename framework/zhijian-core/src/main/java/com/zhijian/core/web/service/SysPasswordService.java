package com.zhijian.core.web.service;

import java.util.concurrent.TimeUnit;

import com.zhijian.common.core.CaffeineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.zhijian.common.constant.CacheConstants;
import com.zhijian.common.constant.Constants;
import com.zhijian.common.core.domain.entity.SysUser;
//import com.zhijian.common.core.redis.RedisCache;
import com.zhijian.common.exception.user.UserPasswordNotMatchException;
import com.zhijian.common.exception.user.UserPasswordRetryLimitExceedException;
import com.zhijian.common.utils.MessageUtils;
import com.zhijian.common.utils.SecurityUtils;
import com.zhijian.core.manager.AsyncManager;
import com.zhijian.core.manager.factory.AsyncFactory;
import com.zhijian.core.security.context.AuthenticationContextHolder;

/**
 * 登录密码方法
 * 
 * @author qihang
 */
@Component
public class SysPasswordService
{
//    @Autowired
//    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     * 
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user)
    {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

//        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));
        Integer retryCount = (Integer) CaffeineUtil.get(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount, lockTime)));
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.count", retryCount)));
//            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            CaffeineUtil.put(getCacheKey(username), retryCount);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
//        if (redisCache.hasKey(getCacheKey(loginName)))
//        {
//            redisCache.deleteObject(getCacheKey(loginName));
//        }
    }
}
