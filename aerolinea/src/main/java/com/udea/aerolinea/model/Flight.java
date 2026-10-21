package com.udea.aerolinea.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Flight {

    private String flightNumber;

    @NotNull(message = "El retraso del vuelo es obligatorio")
    @Min(value = 0, message = "El retraso no puede ser negativo")
    private Integer delayMinutes;

    @NotNull(message = "La duracion del vuelo es obligatoria")
    @DecimalMin(value = "0.1", message = "La duracion del vuelo debe ser mayor a 0")
    private Double durationHours;

    private boolean emergencyExitSeatAvailable;

    public Flight() {
    }

    public Flight(String flightNumber, int delayMinutes, double durationHours,
                  boolean emergencyExitSeatAvailable) {
        this.flightNumber = flightNumber;
        this.delayMinutes = delayMinutes;
        this.durationHours = durationHours;
        this.emergencyExitSeatAvailable = emergencyExitSeatAvailable;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Integer getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(Integer delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public Double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isEmergencyExitSeatAvailable() {
        return emergencyExitSeatAvailable;
    }

    public void setEmergencyExitSeatAvailable(boolean emergencyExitSeatAvailable) {
        this.emergencyExitSeatAvailable = emergencyExitSeatAvailable;
    }
}
