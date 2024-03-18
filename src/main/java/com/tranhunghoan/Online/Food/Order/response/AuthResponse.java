package com.tranhunghoan.Online.Food.Order.response;

import com.tranhunghoan.Online.Food.Order.model.enumeration.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
