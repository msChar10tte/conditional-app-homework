package ru.netology.conditionalapp; // Или ваш пакет

public class ProductionProfile implements SystemProfile {
    @Override
    public String getProfile() {
        return "Current profile is production";
    }
}