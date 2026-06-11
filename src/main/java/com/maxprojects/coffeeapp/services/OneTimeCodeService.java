package com.maxprojects.coffeeapp.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OneTimeCodeService {

    // OTC validity duration (5 minutes)
    private static final long EXPIRATION_MS = 5 * 60 * 1000;

    // Metadata for each OTC
    private static class CodeData {
        long createdAt;
        boolean used;

        CodeData(long createdAt) {
            this.createdAt = createdAt;
            this.used = false;
        }
    }

    // In‑memory store: code → metadata
    private final Map<String, CodeData> codeStore = new ConcurrentHashMap<>();


    // Store a new OTC
    public void storeCode(String code) {
        codeStore.put(code, new CodeData(System.currentTimeMillis()));
    }

    // Check if a code exists, is not expired, and not used
    public boolean isValid(String code) {
        CodeData data = codeStore.get(code);
        if (data == null) return false;

        if (isExpired(data)) {
            codeStore.remove(code);
            return false;
        }

        return !data.used;
    }

    // Mark a code as used
    public void consume(String code) {
        CodeData data = codeStore.get(code);
        if (data != null) data.used = true;
    }

    // Return all active (unused + unexpired) codes
    public List<String> getAllCodes() {
        return codeStore.entrySet().stream()
                .filter(e -> {
                    CodeData d = e.getValue();
                    return !d.used && !isExpired(d);
                })
                .map(Map.Entry::getKey)
                .toList();
    }

    // Remove a code manually
    public void revokeCode(String code) {
        codeStore.remove(code);
    }

    // Check expiration
    private boolean isExpired(CodeData data) {
        return System.currentTimeMillis() - data.createdAt > EXPIRATION_MS;
    }
}
