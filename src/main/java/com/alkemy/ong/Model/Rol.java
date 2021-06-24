package com.alkemy.ong.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE Rol SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 80)
    private String name;

    private String description;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    private boolean deleted;


}
