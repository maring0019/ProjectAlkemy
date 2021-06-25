package com.alkemy.ong.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Usuario
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
/*Borrado lógico, softDeletes*/
@DynamicUpdate
@SQLDelete(sql = "UPDATE Member SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "El campo Name no puede estar vacío.")
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
    /*timestamps*/
    @NotEmpty(message = "El campo createDate no puede estar vacío.")
    @Column(name = "create_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Column(name = "edit_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date editDate;
    
    @Column(name = "delete_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    
        
      
}
