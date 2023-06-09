package io.github.apedrina.web.vo;

import io.github.apedrina.web.model.ELogType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogVO {

    private Long idStudent;

    private Long id;

    private StudentVO student;

    private String description;

    private ELogType type;

}