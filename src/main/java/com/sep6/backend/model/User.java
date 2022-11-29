package com.sep6.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @NonNull
    private String email;
    private String username;
    @NonNull
    private String password;
}
