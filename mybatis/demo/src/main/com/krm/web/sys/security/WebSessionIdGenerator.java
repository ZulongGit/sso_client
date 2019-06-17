package com.krm.web.sys.security;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
 
/**
 * sessionId生成工具类
 * @author Parker
 *
 */
public class WebSessionIdGenerator implements SessionIdGenerator {
 
    public Serializable generateId(Session session) {
        //自定义规则生成sessionidKey
        return UUID.randomUUID().toString().toUpperCase().trim();
    }
}
