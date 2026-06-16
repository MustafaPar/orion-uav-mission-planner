package com.uavfleet.service;

import com.uavfleet.dto.AssignmentResultDto;
import com.uavfleet.entity.*;
import com.uavfleet.exception.NoEligibleUavException;
import com.uavfleet.repository.AssignmentRepository;
import com.uavfleet.repository.MissionLogRepository;
import com.uavfleet.repository.MissionRepository;
import com.uavfleet.repository.UavRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    private UavRepository uavRepository;
    @Mock
    private MissionRepository missionRepository;
    @Mock
    private AssignmentRepository assignmentRepository;
    @Mock
    private MissionLogRepository missionLogRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private Mission mission;
    private Uav closeFullBattery;
    private Uav farFullBattery;
    private Uav closeLowBattery;
    private Uav outOfRange;

    @BeforeEach
    void setUp() {
        mission = new Mission();
        mission.setId(1L);
        mission.setTitle("Test Mission");
        mission.setLatitude(41.04);
        mission.setLongitude(29.00);
        mission.setPriority(MissionPriority.HIGH);
        mission.setType(MissionType.SURVEILLANCE);
        mission.setRequiredRangeKm(5.0);

        closeFullBattery = new Uav("Close-Full", "ModelA", 41.041, 29.001, 100.0, 50.0, UavStatus.AVAILABLE);
        closeFullBattery.setId(1L);

        farFullBattery = new Uav("Far-Full", "ModelB", 42.5, 30.5, 100.0, 500.0, UavStatus.AVAILABLE);
        farFullBattery.setId(2L);

        closeLowBattery = new Uav("Close-Low", "ModelC", 41.041, 29.001, 16.0, 50.0, UavStatus.AVAILABLE);
        closeLowBattery.setId(3L);

        outOfRange = new Uav("OutOfRange", "ModelD", 41.041, 29.001, 100.0, 1.0, UavStatus.AVAILABLE);
        outOfRange.setId(4L);
    }

    @Test
    void picksClosestEligibleUavOverFartherOne() {
        when(missionRepository.findById(1L)).thenReturn(Optional.of(mission));
        when(uavRepository.findByStatus(UavStatus.AVAILABLE)).thenReturn(List.of(closeFullBattery, farFullBattery));
        when(assignmentRepository.save(any())).thenAnswer(invocation -> {
            Assignment a = invocation.getArgument(0);
            a.setId(99L);
            return a;
        });

        AssignmentResultDto result = assignmentService.assignBestUav(1L);

        assertEquals("Close-Full", result.getUavName());
        assertEquals(UavStatus.ASSIGNED, closeFullBattery.getStatus());
    }

    @Test
    void rejectsUavWithInsufficientBatteryReserve() {
        when(missionRepository.findById(1L)).thenReturn(Optional.of(mission));
        when(uavRepository.findByStatus(UavStatus.AVAILABLE)).thenReturn(List.of(closeLowBattery, outOfRange));

        assertThrows(NoEligibleUavException.class, () -> assignmentService.assignBestUav(1L));
    }

    @Test
    void rejectsUavWithInsufficientRange() {
        when(missionRepository.findById(1L)).thenReturn(Optional.of(mission));
        when(uavRepository.findByStatus(UavStatus.AVAILABLE)).thenReturn(List.of(outOfRange));

        assertThrows(NoEligibleUavException.class, () -> assignmentService.assignBestUav(1L));
    }
}
