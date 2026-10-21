package com.udea.aerolinea.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Resultado de aplicar las reglas. Las reglas lo van llenando
 * (equivale al CreditResponse del laboratorio de creditos).
 */
public class ServiceResult {

    private boolean upgradedToBusiness;
    private boolean priorityCheckIn;
    private double discountPercent;
    private boolean eligibleForUpgrade = true;
    private String seatAssignment;
    private double compensation;
    private int loyaltyPoints;
    private boolean luggageAllowed = true;
    private boolean vipLoungeAccess;
    private List<String> appliedRules = new ArrayList<>();

    public ServiceResult() {
    }

    public void addAppliedRule(String ruleName) {
        appliedRules.add(ruleName);
    }

    public boolean isUpgradedToBusiness() {
        return upgradedToBusiness;
    }

    public void setUpgradedToBusiness(boolean upgradedToBusiness) {
        this.upgradedToBusiness = upgradedToBusiness;
    }

    public boolean isPriorityCheckIn() {
        return priorityCheckIn;
    }

    public void setPriorityCheckIn(boolean priorityCheckIn) {
        this.priorityCheckIn = priorityCheckIn;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public boolean isEligibleForUpgrade() {
        return eligibleForUpgrade;
    }

    public void setEligibleForUpgrade(boolean eligibleForUpgrade) {
        this.eligibleForUpgrade = eligibleForUpgrade;
    }

    public String getSeatAssignment() {
        return seatAssignment;
    }

    public void setSeatAssignment(String seatAssignment) {
        this.seatAssignment = seatAssignment;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isLuggageAllowed() {
        return luggageAllowed;
    }

    public void setLuggageAllowed(boolean luggageAllowed) {
        this.luggageAllowed = luggageAllowed;
    }

    public boolean isVipLoungeAccess() {
        return vipLoungeAccess;
    }

    public void setVipLoungeAccess(boolean vipLoungeAccess) {
        this.vipLoungeAccess = vipLoungeAccess;
    }

    public List<String> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(List<String> appliedRules) {
        this.appliedRules = appliedRules;
    }
}
