package com.itwasjoke.place.DTO.mapper;


import com.itwasjoke.place.DTO.UserRequestDTO;
import com.itwasjoke.place.DTO.CoupleResponseDTO;
import com.itwasjoke.place.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {
    User userRequestToUser(UserRequestDTO userRequestDTO);
}
