package com.udea.aerolinea.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Pasajero. El campo membership se usa tanto para el "estatus" (Gold, Platinum)
 * como para la "membresia" (Basic, Platinum) que mencionan las reglas.
 */
public class Passenger {

    @NotBlank(message = "El nombre del pasajero es obligatorio")
    private String name;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 120, message = "La edad no puede ser mayor a 120")
    private Integer age;

    @NotBlank(message = "La membresia es obligatoria")
    @Pattern(regexp = "Basic|Silver|Gold|Platinum",
            message = "La membresia debe ser Basic, Silver, Gold o Platinum")
    private String membership;

    @Pattern(regexp = "Any|Window|Aisle",
            message = "La preferencia de asiento debe ser Any, Window o Aisle")
    private String seatPreference = "Any";

    private boolean travelingWithChildren;

    @NotNull(message = "El peso del equipaje es obligatorio")
    @Min(value = 0, message = "El peso del equipaje no puede ser negativo")
    private Double luggageWeight;

    public Passenger() {
    }

    public Passenger(String name, int age, String membership, String seatPreference,
                     boolean travelingWithChildren, double luggageWeight) {
        this.name = name;
        this.age = age;
        this.membership = membership;
        this.seatPreference = seatPreference;
        this.travelingWithChildren = travelingWithChildren;
        this.luggageWeight = luggageWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getSeatPreference() {
        return seatPreference;
    }

    public void setSeatPreference(String seatPreference) {
        this.seatPreference = seatPreference;
    }

    public boolean isTravelingWithChildren() {
        return travelingWithChildren;
    }

    public void setTravelingWithChildren(boolean travelingWithChildren) {
        this.travelingWithChildren = travelingWithChildren;
    }

    public Double getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }
}
