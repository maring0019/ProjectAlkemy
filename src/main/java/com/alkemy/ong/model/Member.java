package com.alkemy.ong.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;



@Entity
@Table(name ="members")
@DynamicUpdate
@SQLDelete(sql = "UPDATE Member SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{member.error.empty.name}")
    private String name;

    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;

    @Column(name = "create_date", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "edit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date edited;

    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Member(String name, String facebookUrl, String instagramUrl, String linkedinUrl, String description) {
        this.name = name;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.linkedinUrl = linkedinUrl;
        this.description = description;
        this.created = new Date();
    }
}
