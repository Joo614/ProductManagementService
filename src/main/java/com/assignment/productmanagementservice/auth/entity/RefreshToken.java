package com.assignment.productmanagementservice.auth.entity;

import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "REFRESH_TOKEN")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RefreshToken extends Auditable {
    @Id
    @Column( unique = true)
    @NotNull
    private String email;
    @NotNull
    private String token;
    private Date expiryDate;
}
