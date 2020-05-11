package cn.michael.travel.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Favorite implements Serializable {
    private static final long serialVersionUID = -2712872057795887124L;
    @Id
    private Integer rid;
    private Integer uid;
    private String date;
}
