package org.renault.repository;

import org.renault.model.Accessory;
import org.renault.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByGarageId(Long garageId);

    @Query("SELECT v FROM Vehicle v WHERE LOWER(v.brand) = LOWER(:brand)")
    List<Vehicle> findAllByBrand(@Param("brand") String brand);
}
