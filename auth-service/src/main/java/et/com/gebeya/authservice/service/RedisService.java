package et.com.gebeya.authservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisService {
    private final RedisTemplate<String,String> otpRedisTemplate;

    public RedisService(@Qualifier("otp") RedisTemplate<String, String> otpRedisTemplate) {
        this.otpRedisTemplate = otpRedisTemplate;
    }
    public void setOtp(String key, String value, long expirationTimeMinutes) {
        try {
            otpRedisTemplate.opsForValue().set(key, value, expirationTimeMinutes, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException("Error setting OTP in Redis: " + e.getMessage(), e);
        }
    }

    public String getOtp(String key) {
        try {
            return otpRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new RuntimeException("Error getting OTP from Redis: " + e.getMessage(), e);
        }
    }
    public void deleteOtp(String key) {
        try {
            otpRedisTemplate.delete(key);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting OTP from Redis: " + e.getMessage(), e);
        }
    }
}
