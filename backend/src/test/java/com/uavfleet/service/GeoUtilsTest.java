package com.uavfleet.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoUtilsTest {

    @Test
    void distanceBetweenIdenticalPointsIsZero() {
        double distance = GeoUtils.haversineDistanceKm(41.0, 29.0, 41.0, 29.0);
        assertEquals(0.0, distance, 0.0001);
    }

    @Test
    void distanceBetweenIstanbulAndAnkaraIsApproximatelyCorrect() {
        // Istanbul ~ 41.0082, 28.9784 ; Ankara ~ 39.9334, 32.8597
        double distance = GeoUtils.haversineDistanceKm(41.0082, 28.9784, 39.9334, 32.8597);
        // Real-world great-circle distance is ~349 km.
        assertEquals(349.0, distance, 10.0);
    }
}
