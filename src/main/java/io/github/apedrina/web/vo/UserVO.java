package io.github.apedrina.web.vo;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserVO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Set<RoleVO> roles;

}