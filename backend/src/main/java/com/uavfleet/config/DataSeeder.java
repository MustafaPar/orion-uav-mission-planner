package com.uavfleet.config;

import com.uavfleet.entity.*;
import com.uavfleet.repository.MissionRepository;
import com.uavfleet.repository.UavRepository;
import com.uavfleet.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a demo admin user and a handful of UAVs/missions on first boot so the
 * dashboard and map aren't empty in a fresh environment. No-ops if data already exists.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UavRepository uavRepository;
    private final MissionRepository missionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, UavRepository uavRepository,
                       MissionRepository missionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.uavRepository = uavRepository;
        this.missionRepository = missionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "ADMIN"));
        }

        if (uavRepository.count() == 0) {
            uavRepository.save(new Uav("Falcon-1", "DJI Matrice 300", 41.015137, 28.979530, 95.0, 60.0, UavStatus.AVAILABLE));
            uavRepository.save(new Uav("Hawk-2", "Skydio X10", 41.025, 29.01, 70.0, 40.0, UavStatus.AVAILABLE));
            uavRepository.save(new Uav("Eagle-3", "WingtraOne", 40.99, 28.95, 40.0, 100.0, UavStatus.MAINTENANCE));
        }

        if (missionRepository.count() == 0) {
            Mission m1 = new Mission();
            m1.setTitle("Coastal Surveillance Sweep");
            m1.setLatitude(41.04);
            m1.setLongitude(29.00);
            m1.setPriority(MissionPriority.HIGH);
            m1.setType(MissionType.SURVEILLANCE);
            m1.setRequiredRangeKm(10.0);
            m1.setEstimatedDurationMinutes(45);
            missionRepository.save(m1);

            Mission m2 = new Mission();
            m2.setTitle("Industrial Zone Mapping");
            m2.setLatitude(40.97);
            m2.setLongitude(28.93);
            m2.setPriority(MissionPriority.MEDIUM);
            m2.setType(MissionType.MAPPING);
            m2.setRequiredRangeKm(15.0);
            m2.setEstimatedDurationMinutes(60);
            missionRepository.save(m2);
        }
    }
}
