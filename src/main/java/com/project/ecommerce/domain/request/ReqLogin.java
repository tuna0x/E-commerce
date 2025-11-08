package com.project.ecommerce.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqLogin {
    @NotBlank(message = "username is not blank")
    private String  username;
    @NotBlank(message = "password is not blank")
    private String password;
}
