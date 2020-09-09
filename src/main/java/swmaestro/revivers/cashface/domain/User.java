package swmaestro.revivers.cashface.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @NotEmpty
    @NotNull
    private String userType;

    @Setter
    @NotEmpty
    @NotNull
    private String name;

    @Setter
    @NotEmpty
    @NotNull
    private String email;

    @Setter
    @NotEmpty
    @NotNull
    private String password;

    @Setter
    @NotNull
    private Character gender;

    @Setter
    @NotNull
    private Integer age;

    @Setter
    @NotEmpty
    @NotNull
    private String nationality;

    @Setter
    private Integer totalPoints;

    @Setter
    private Date createdDate;

}
