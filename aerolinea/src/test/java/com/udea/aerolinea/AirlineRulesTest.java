package com.udea.aerolinea;

import com.udea.aerolinea.config.DroolsConfig;
import com.udea.aerolinea.model.Flight;
import com.udea.aerolinea.model.Passenger;
import com.udea.aerolinea.model.ServiceResult;
import com.udea.aerolinea.service.AirlineEvaluationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Una prueba positiva y una negativa por cada una de las 10 reglas, mas los cruces entre reglas. */
class AirlineRulesTest {

    private static AirlineEvaluationService service;

    @BeforeAll
    static void setUp() {
        System.setProperty("mvel2.disable.jit", "true");
        service = new AirlineEvaluationService(new DroolsConfig().kieContainer());
    }

    // Pasajero "neutro": no activa ninguna regla por si solo
    private Passenger passenger(String membership, int age, double luggage,
                                boolean children, String seatPreference) {
        return new Passenger("Pasajero Prueba", age, membership, seatPreference, children, luggage);
    }

    private Flight flight(int delayMinutes, double durationHours, boolean emergencyExit) {
        return new Flight("UD100", delayMinutes, durationHours, emergencyExit);
    }

    private boolean fired(ServiceResult r, String rule) {
        return r.getAppliedRules().contains(rule);
    }

    @Test
    void regla1_ascensoAEjecutivaParaGoldYPlatinumConRetraso() {
        ServiceResult gold = service.evaluate(passenger("Gold", 30, 12, false, "Window"), flight(90, 3, false));
        assertTrue(gold.isUpgradedToBusiness());
        ServiceResult platinum = service.evaluate(passenger("Platinum", 30, 12, false, "Window"), flight(61, 3, false));
        assertTrue(platinum.isUpgradedToBusiness());
        // Negativas: otra membresia, o retraso de 60 minutos o menos
        assertFalse(service.evaluate(passenger("Silver", 30, 12, false, "Window"), flight(90, 3, false)).isUpgradedToBusiness());
        assertFalse(service.evaluate(passenger("Gold", 30, 12, false, "Window"), flight(60, 3, false)).isUpgradedToBusiness());
    }

    @Test
    void regla2_checkInPrioritarioParaMayoresDe65() {
        assertTrue(service.evaluate(passenger("Basic", 70, 12, false, "Window"), flight(0, 3, false)).isPriorityCheckIn());
        assertFalse(service.evaluate(passenger("Basic", 65, 12, false, "Window"), flight(0, 3, false)).isPriorityCheckIn());
    }

    @Test
    void regla3_descuentoPorEquipajeLiviano() {
        assertEquals(10.0, service.evaluate(passenger("Basic", 30, 8, false, "Window"), flight(0, 3, false)).getDiscountPercent());
        assertEquals(0.0, service.evaluate(passenger("Basic", 30, 10, false, "Window"), flight(0, 3, false)).getDiscountPercent());
    }

    @Test
    void regla4_equipajeExcesivoNoEsElegibleParaAscenso() {
        ServiceResult r = service.evaluate(passenger("Basic", 30, 25, false, "Window"), flight(0, 3, false));
        assertFalse(r.isEligibleForUpgrade());
        assertTrue(service.evaluate(passenger("Basic", 30, 23, false, "Window"), flight(0, 3, false)).isEligibleForUpgrade());
    }

    @Test
    void regla4_ganaSobreLaRegla1() {
        // Gold con 90 minutos de retraso, pero con 25 kg de equipaje: no debe ascender
        ServiceResult r = service.evaluate(passenger("Gold", 30, 25, false, "Window"), flight(90, 3, false));
        assertFalse(r.isEligibleForUpgrade());
        assertFalse(r.isUpgradedToBusiness());
        assertTrue(fired(r, "DenyUpgradeForOverweightLuggage"));
        assertFalse(fired(r, "UpgradeToBusinessClassForFrequentFlyersWithDelays"));
    }

    @Test
    void regla5_asientoDeSalidaDeEmergenciaParaJovenes() {
        Flight f = flight(0, 3, true);
        ServiceResult r = service.evaluate(passenger("Basic", 30, 12, false, "Any"), f);
        assertEquals("SALIDA_DE_EMERGENCIA", r.getSeatAssignment());
        assertFalse(f.isEmergencyExitSeatAvailable(), "El asiento debe quedar ocupado");
        // Negativas: edad fuera de 18 a 40, preferencia distinta de Any, o sin asiento disponible
        assertNull(service.evaluate(passenger("Basic", 41, 12, false, "Any"), flight(0, 3, true)).getSeatAssignment());
        assertNull(service.evaluate(passenger("Basic", 17, 12, false, "Any"), flight(0, 3, true)).getSeatAssignment());
        assertNull(service.evaluate(passenger("Basic", 30, 12, false, "Window"), flight(0, 3, true)).getSeatAssignment());
        assertNull(service.evaluate(passenger("Basic", 30, 12, false, "Any"), flight(0, 3, false)).getSeatAssignment());
    }

    @Test
    void regla6_compensacionPorRetrasosExtremos() {
        assertEquals(200.0, service.evaluate(passenger("Basic", 30, 12, false, "Window"), flight(181, 3, false)).getCompensation());
        assertEquals(0.0, service.evaluate(passenger("Basic", 30, 12, false, "Window"), flight(180, 3, false)).getCompensation());
    }

    @Test
    void regla7_puntosExtraEnVuelosLargos() {
        assertEquals(500, service.evaluate(passenger("Silver", 30, 12, false, "Window"), flight(0, 6, false)).getLoyaltyPoints());
        assertEquals(0, service.evaluate(passenger("Basic", 30, 12, false, "Window"), flight(0, 6, false)).getLoyaltyPoints());
        assertEquals(0, service.evaluate(passenger("Gold", 30, 12, false, "Window"), flight(0, 5, false)).getLoyaltyPoints());
    }

    @Test
    void regla8_equipajeRestringidoEnVuelosCortos() {
        assertFalse(service.evaluate(passenger("Basic", 30, 16, false, "Window"), flight(0, 1.5, false)).isLuggageAllowed());
        assertTrue(service.evaluate(passenger("Basic", 30, 16, false, "Window"), flight(0, 3, false)).isLuggageAllowed());
        assertTrue(service.evaluate(passenger("Basic", 30, 15, false, "Window"), flight(0, 1.5, false)).isLuggageAllowed());
    }

    @Test
    void regla9_salonVipParaPlatinum() {
        assertTrue(service.evaluate(passenger("Platinum", 30, 12, false, "Window"), flight(0, 3, false)).isVipLoungeAccess());
        assertFalse(service.evaluate(passenger("Gold", 30, 12, false, "Window"), flight(0, 3, false)).isVipLoungeAccess());
    }

    @Test
    void regla10_asientoPreferencialParaFamilias() {
        ServiceResult r = service.evaluate(passenger("Basic", 50, 12, true, "Any"), flight(0, 3, false));
        assertEquals("PREFERENCIAL_FAMILIAR", r.getSeatAssignment());
        assertNull(service.evaluate(passenger("Basic", 50, 12, true, "Window"), flight(0, 3, false)).getSeatAssignment());
        assertNull(service.evaluate(passenger("Basic", 50, 12, false, "Any"), flight(0, 3, false)).getSeatAssignment());
    }

    @Test
    void regla10_ganaSobreLaRegla5() {
        // Joven con ninos y salida de emergencia libre: recibe asiento familiar y la salida queda libre
        Flight f = flight(0, 3, true);
        ServiceResult r = service.evaluate(passenger("Basic", 30, 12, true, "Any"), f);
        assertEquals("PREFERENCIAL_FAMILIAR", r.getSeatAssignment());
        assertTrue(f.isEmergencyExitSeatAvailable());
        assertFalse(fired(r, "AssignEmergencyExitSeatToYoungAdults"));
    }

    @Test
    void variasReglasALaVez() {
        // Platinum de 70 anos, 8 kg, vuelo de 6 horas con 200 minutos de retraso
        ServiceResult r = service.evaluate(passenger("Platinum", 70, 8, false, "Aisle"), flight(200, 6, false));
        assertTrue(r.isUpgradedToBusiness());
        assertTrue(r.isPriorityCheckIn());
        assertEquals(10.0, r.getDiscountPercent());
        assertEquals(200.0, r.getCompensation());
        assertEquals(500, r.getLoyaltyPoints());
        assertTrue(r.isVipLoungeAccess());
        assertEquals(6, r.getAppliedRules().size());
    }
}
