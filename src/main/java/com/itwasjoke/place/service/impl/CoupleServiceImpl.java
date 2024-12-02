package com.itwasjoke.place.service.impl;

import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.entity.Couple;
import com.itwasjoke.place.entity.User;
import com.itwasjoke.place.exception.couple.CoupleAccessDeniedException;
import com.itwasjoke.place.exception.couple.CoupleNotFoundException;
import com.itwasjoke.place.repository.CoupleRepository;
import com.itwasjoke.place.security.HashGenerator;
import com.itwasjoke.place.service.CoupleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoupleServiceImpl implements CoupleService {

    private final CoupleRepository coupleRepository;

    public CoupleServiceImpl(CoupleRepository coupleRepository) {
        this.coupleRepository = coupleRepository;
    }

    @Override
    public Couple createCouple(User user) {
        Couple couple = new Couple();
        couple.setHash(HashGenerator.generateUniqueCode());
        couple.setPartner1(user);
        return coupleRepository.save(couple);
    }

    @Override
    public Couple confirmCouple(String hash, User user) {
        Couple couple = getCouple(hash);
        Couple couple1 = user.getCouple1();
        Couple couple2 = user.getCouple2();
        if (couple1 != null){
            if (!couple1.getHash().equals(hash)) {
                coupleRepository.delete(couple1);
            }
        }
        if (couple2 != null){
            if (!couple2.getHash().equals(hash)) {
                coupleRepository.delete(couple2);
            }
        }
        couple.setPartner2(user);
        return coupleRepository.save(couple);
    }

    @Override
    public Couple getCouple(String hash) {
        Optional<Couple> coupleOptional = coupleRepository.findCoupleByHash(hash);
        if (coupleOptional.isPresent()){
            return coupleOptional.get();
        } else {
            throw new CoupleNotFoundException("No couple found");
        }
    }

    @Override
    public CoupleResponseDTO getCoupleResponse(String hash, String login) {
        Couple couple = getCouple(hash);
        User partner1 = couple.getPartner1();
        User partner2 = couple.getPartner2();
        if (partner1.getLogin().equals(login) || partner2.getLogin().equals(login)){
            return new CoupleResponseDTO(
                    partner1.getName(),
                    partner2.getName(),
                    couple.getDate()
            );
        } else {
            throw new CoupleAccessDeniedException("No access for this user");
        }
    }

    @Override
    public boolean coupleConfirmed(String hash) {
            Couple couple = getCouple(hash);
            return couple.getPartner1() != null && couple.getPartner2() != null;
    }
}
