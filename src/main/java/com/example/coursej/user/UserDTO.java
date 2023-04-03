package com.example.coursej.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
    private Long id;

    @NotNull
    private String username;
    @NotNull
    @JsonIgnore
    private String password;
    @NotNull
    private String email;
    private  UserRole role;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

}
