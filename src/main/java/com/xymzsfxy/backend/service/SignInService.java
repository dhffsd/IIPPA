package com.xymzsfxy.backend.service;

import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Service
public interface SignInService {
    boolean signIn(String accessToken, boolean isRepair, Long availablePoints);

    List<Date> getSignInDays(String accessToken, YearMonth month);

    int getStreak(String accessToken);
}
