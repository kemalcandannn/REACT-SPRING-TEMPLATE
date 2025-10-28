package com.my_company.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    public List<Integer> getRandomIntegerList() {
        int size = new SecureRandom().nextInt(2, 10);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(new SecureRandom().nextInt(100000, 999999));
        }
        return result;
    }
}
