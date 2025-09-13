package eu.chrost.springcache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("isbnYearKeyGenerator")
class IsbnYearKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // expects: params[0] = isbn, params[1] = year
        return params[0] + "-" + params[1];
    }
}
