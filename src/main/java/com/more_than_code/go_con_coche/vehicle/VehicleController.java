package com.more_than_code.go_con_coche.vehicle;

import com.more_than_code.go_con_coche.vehicle.dtos.VehicleRequest;
import com.more_than_code.go_con_coche.vehicle.dtos.VehicleResponse;
import com.more_than_code.go_con_coche.vehicle.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Management of vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @Operation(summary = "Create vehicle", description = "Create a new vehicle and return the vehicle details")
    public ResponseEntity<VehicleResponse> createVehicle(@Valid @ModelAttribute VehicleRequest vehicleRequest) {
        VehicleResponse createdVehicle = vehicleService.createVehicle(vehicleRequest);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all vehicles", description = "Get list of all vehicle details")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        List<VehicleResponse> vehicles = vehicleService.getAllVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Get vehicles by owner id", description = "Get list of vehicles from an owner id and return the vehicles details")
    public ResponseEntity<List<VehicleResponse>> getVehiclesByOwnerId(@PathVariable Long ownerId) {
        List<VehicleResponse> vehicles = vehicleService.getVehicleByOwnerId(ownerId);
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/my")
    @Operation(summary = "Get own vehicles", description = "Get own list of vehicles and return the vehicles details")
    public ResponseEntity<List<VehicleResponse>> getMyVehicles() {
        List<VehicleResponse> vehicles = vehicleService.getMyVehicles();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vehicle", description = "Updates the details of an existing vehicle identified by its ID")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long id, @Valid @ModelAttribute VehicleRequest vehicleRequest) {
        VehicleResponse updatedVehicle = vehicleService.updateVehicle(id, vehicleRequest);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vehicle", description = "Deletes the vehicle identified by its ID.")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}