package com.itwasjoke.place.controller;

import com.itwasjoke.place.DTO.CoupleConfirmedDTO;
import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.service.CoupleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/couples", produces = "application/json; charset=utf-8")
@Tag(name = "Пользователь", description = "Работа с информацией об участнике")
public class CoupleController {
    private final CoupleService coupleService;

    public CoupleController(CoupleService coupleService) {
        this.coupleService = coupleService;
    }

    @GetMapping("/{hash}")
    @Operation(
            summary = "Получение пары",
            description = "Имена, фотографии и дата начала отношений"
    )
    public CoupleResponseDTO getCouple(@PathVariable String hash, HttpServletRequest request){
        var authentication = (Authentication) request.getUserPrincipal();
        var userDetails = (UserDetails) authentication.getPrincipal();
        return coupleService.getCoupleResponse(hash, userDetails.getUsername());
    }

    @GetMapping("/confirm/{hash}")
    @Operation(
            summary = "Получение подтверждения создания пары",
            description = "Возвращает значение, говорящее о текущем статусе пары"
    )
    public CoupleConfirmedDTO coupleConfirmed(@PathVariable String hash){
        return new CoupleConfirmedDTO(coupleService.coupleConfirmed(hash));
    }
}
