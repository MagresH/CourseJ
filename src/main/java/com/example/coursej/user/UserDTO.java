package com.example.coursej.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserRole role;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> coursesIds;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> enrollmentsIds;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void setPassword(String password) {
        this.password = password;
    }

}
