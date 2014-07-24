package com.jscn.commons.core.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis java client
 * 
 * @author 袁兵 2012-7-5
 */
@Component
public class JedisCache {

    private Logger      logger = LoggerFactory.getLogger(JedisCache.class);

    @Qualifier("jedisPool")
    @Autowired
    protected JedisPool jedisPool;

    private int         maxInactiveInterval;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @return the maxInactiveInterval
     */
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    /**
     * @param maxInactiveInterval the maxInactiveInterval to set
     */
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    /**
     * 获得一个对象
     * 
     * @param key cache key
     * 
     * @return obj cache对象
     */
    public Object get(String key) {
        Object obj = null;
        Jedis jedis = jedisPool.getResource();
        try {
            obj = byte2Object(jedis.get(getKey(key)));
            logger.debug("get key[{}] val[{}]",key,obj);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return null;
        } finally {
            this.jedisPool.returnResource(jedis);
        }

        return obj;
    }

    /**
     * 判断对象是否存在
     * 
     * @param key
     * 
     * @return
     */
    public boolean isExist(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        } finally {
            this.jedisPool.returnResource(jedis);
        }
    }

    /**
     * 保存一个对象
     * 
     * @param key
     * @param value
     */
    public void save(String key, Object value) {
        save(key, value, maxInactiveInterval);
    }

    /**
     * 
     * @param key
     * @param value
     */
    public void lpush(String key, Object value) {
        lpush(key, value, maxInactiveInterval);
    }

    /**
     * 向list头部插入数据
     * @param key
     * @param value
     * @param maxInactiveInterval2
     */
    public void lpush(String key, Object value, int outTime) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.lpush(getKey(key), object2Bytes(value));
            if (outTime != 0) {
                jedis.expire(getKey(key), outTime);// 设置过期时间
            }
            logger.debug("lpush key[{}] val[{}]",key,value);
        } catch (Exception e) {
            logger.error("===lpush key===" + key + "===" + e.getLocalizedMessage());
        } finally {
            this.jedisPool.returnResource(jedis);

        }
    }

    /**
     * 保留指定长度的list
     * @param key
     * @param start
     * @param end
     */
    public void ltrim(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.ltrim(getKey(key), start, end);
            logger.debug("ltrim key[{}] start[{}] end[{}]",new Object[]{key,start,end});
        } catch (Exception e) {
            logger.error("===ltrim key===" + key + "===" + e.getLocalizedMessage());
        } finally {
            this.jedisPool.returnResource(jedis);

        }
    }

    /**
     * 取指定长度的list
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lrange(String key, int start, int end) {
        Jedis jedis = jedisPool.getResource();
        try {
            List<byte[]> blist = jedis.lrange(getKey(key), start, end);
            List<Object> olist = new ArrayList<Object>();
            for (byte[] bytes : blist) {
                olist.add(byte2Object(bytes));
            }
            logger.debug("lrange key[{}] start[{}] end[{}] list[{}]",new Object[]{key,start,end,olist});
            return olist;
        } catch (Exception e) {
            logger.error("===ltrim key===" + key + "===" + e.getLocalizedMessage());
            return new ArrayList<Object>();
        } finally {
            this.jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 增加n
     * @param key
     * @param n
     */
    public void incrBy(String key,int n){
        incrBy(key,n,maxInactiveInterval);
    }

    /**
     * 增加n
     * @param key
     * @param n
     * @param outTime
     */
    public void incrBy(String key, int n, int outTime) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.incrBy(key, n);
            if (outTime != 0) {
                jedis.expire(getKey(key), outTime);// 设置过期时间
            }
            logger.debug("incrBy key[{}] n[{}]",key,n);
        } catch (Exception e) {
            logger.error("===incrBy key===" + key + "===" + e.getLocalizedMessage());
        } finally {
            this.jedisPool.returnResource(jedis);
        }
    }
    
    /**
     * 减小n
     * @param key
     * @param n
     */
    public void decrBy(String key,int n){
        decrBy(key,n,maxInactiveInterval);
    }

    /**
     * 减小n
     * @param key
     * @param n
     * @param outTime
     */
    public void decrBy(String key, int n, int outTime) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.decrBy(key, n);
            if (outTime != 0) {
                jedis.expire(getKey(key), outTime);// 设置过期时间
            }
            logger.debug("decrBy key[{}] n[{}]",key,n);
        } catch (Exception e) {
            logger.error("===decrBy key===" + key + "===" + e.getLocalizedMessage());
        } finally {
            this.jedisPool.returnResource(jedis);
        }
    }

    /**
     * 保存一个对象
     * 
     * @param key
     * @param value
     * @param outTime 过期时间
     */
    public void save(String key, Object value, int outTime) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(getKey(key), object2Bytes(value));
            if (outTime != 0) {
                jedis.expire(getKey(key), outTime);// 设置过期时间
            }
            logger.debug("save key[{}] val[{}]",key,value);
        } catch (Exception e) {
            logger.error("===save key===" + key + "===" + e.getLocalizedMessage());
        } finally {
            this.jedisPool.returnResource(jedis);

        }
    }

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            logger.debug("del key[{}]",key);
            return jedis.del(key);
        } catch (Exception e) {
            logger.error("===del key===" + key + "===" + e.getLocalizedMessage());
            return null;
        } finally {
            this.jedisPool.returnResource(jedis);

        }
    }

    /**
     * 字节转化为对象
     * 
     * @param bytes
     * 
     * @return
     */
    private Object byte2Object(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;

        try {
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object obj = inputStream.readObject();

            return (Object) obj;
        } catch (IOException e) {
            logger.error("", e);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }

        return null;
    }

    /**
     * 对象转化为字节
     * 
     * @param value
     * 
     * @return
     */
    private byte[] object2Bytes(Object value) {
        if (value == null)
            return null;

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(arrayOutputStream);

            outputStream.writeObject(value);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                arrayOutputStream.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }

        return arrayOutputStream.toByteArray();
    }

    private byte[] getKey(String key) {
        return key.getBytes();
    }

}
