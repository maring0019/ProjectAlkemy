package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true where id=?")
@FilterDef(name = "deletedOrganizationsFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
@Filter(name = "deletedOrganizationsFilter", condition = "deleted = :isDeleted")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 80)
    @NotBlank(message = "el campo Name no puede estar vacío")
    private String name;

    @Column(name = "image", nullable = false)
    @NotBlank(message = "el campo Image no puede estar vacío")
    private String image;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Email
    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @NotBlank(message = "el campo welcomeText no puede estar vacío")
    @Column(name = "welcomeText", nullable = false)
    private String welcomeText;

    @Column(name = "aboutUsText")
    private String aboutUsText;

    @Column(name = "deleted")
    private Boolean deleted=false;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;
}
