package com.maxprojects.coffeeapp.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OneTimeCodeService {

    private static class CodeData {
        long createdAt;
        boolean used;

        CodeData(long createdAt) {
            this.createdAt = createdAt;
            this.used = false;
        }
    }

    private final Map<String, CodeData> codeStore = new ConcurrentHashMap<>();


    // Staff calls this when generating a code for a user
    public void storeCode(String code) {
        codeStore.put(code, new CodeData(System.currentTimeMillis()));
    }

    // Validate the code
    public boolean isValid(String code) {
        CodeData data = codeStore.get(code);
        if (data == null) return false;

        // Expired after 5 minutes
        long ageMs = System.currentTimeMillis() - data.createdAt;
        if (ageMs > 5 * 60 * 1000) {
            codeStore.remove(code);
            return false;
        }

        return !data.used;
    }


    // Optional: remove code after use
    public void consume(String code) {
        CodeData data = codeStore.get(code);
        if (data != null) {
            data.used = true;
        }
    }

}
