package eu.chrost.springcache.author;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("customAuthorKeyGenerator")
class CustomAuthorKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        return "AUTHOR-" + params[0];
    }
}
