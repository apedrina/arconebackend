package io.github.apedrina.web.vo;

import io.github.apedrina.web.model.ERole;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleVO {

    private Integer id;

    private ERole name;

}