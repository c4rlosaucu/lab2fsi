package com.udea.aerolinea.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** Cuerpo de la peticion REST: un pasajero y el vuelo en el que viaja. */
public class FlightRequest {

    @NotNull(message = "El pasajero es obligatorio")
    @Valid
    private Passenger passenger;

    @NotNull(message = "El vuelo es obligatorio")
    @Valid
    private Flight flight;

    public FlightRequest() {
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
