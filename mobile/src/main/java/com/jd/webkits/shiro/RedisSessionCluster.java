package com.jd.webkits.shiro;

import com.jd.core.utils.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * 用于集群环境中的shiro session 持久化
 * 保存在reids中
 *
 * @author Guoqw
 * @since 2015-07-09 14:15
 */
public class RedisSessionCluster extends AbstractSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionCluster.class);

    /**
     * 默认shiro session key
     */
    private static final String SHIROSESSIONKEY = "shiro.session";

    /**
     * 项目设置的shiro session key,默认为SHIROSESSIONKEY
     * 最大可能的减少项目间的session id重复造成的影响
     */
    private String projectShiroSessionKey = SHIROSESSIONKEY;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        update(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("read shiro session={} form redis.", sessionId);
        return RedisUtils.hget(projectShiroSessionKey, (String) sessionId, Session.class);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.debug("update shiro session={} to redis.", session.getId());
        RedisUtils.hput(projectShiroSessionKey, (String) session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            logger.debug("delete shiro session={} form redis.", session.getId());
            RedisUtils.hremove(projectShiroSessionKey, (String) session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.debug("get all active shiro sessions.");
        return RedisUtils.hgetAll(projectShiroSessionKey, Session.class);
    }

    public void setProjectShiroSessionKey(String projectShiroSessionKey) {
        this.projectShiroSessionKey = projectShiroSessionKey;
    }
}
