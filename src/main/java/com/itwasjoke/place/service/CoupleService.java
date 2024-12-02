package com.itwasjoke.place.service;

import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.entity.Couple;
import com.itwasjoke.place.entity.User;

public interface CoupleService {
    Couple createCouple(User user);
    Couple confirmCouple(String hash, User user);

    Couple getCouple(String hash);

    CoupleResponseDTO getCoupleResponse(String hash, String login);

    boolean coupleConfirmed(String hash);
}
