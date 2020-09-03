package swmaestro.revivers.cashface.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    private Integer userId;

    @Setter
    @NotNull
    private String adsType;

    @Setter
    @NotNull
    private Integer transactionType;

    @Setter
    @NotNull
    private Integer amount;

    @Setter
    private Date date;

}
