package com.more_than_code.go_con_coche.owner_profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name="owner")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OwnerProfile {
}
