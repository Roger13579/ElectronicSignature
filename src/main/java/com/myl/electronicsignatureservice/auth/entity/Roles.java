package com.myl.electronicsignatureservice.auth.entity;

import com.myl.electronicsignatureservice.auth.constant.UserAuthority;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Enumerated(EnumType.STRING)
    private UserAuthority roleName;
}
