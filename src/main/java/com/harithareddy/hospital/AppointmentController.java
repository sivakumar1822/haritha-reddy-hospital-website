package com.harithareddy.hospital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final List<Map<String, Object>> bookings = new CopyOnWriteArrayList<>();

    @GetMapping("/info")
    public Map<String, Object> hospitalInfo() {
        return Map.of(
                "name", "Haritha Reddy Hospital",
                "city", "Salmanpuram",
                "emergencyPhone", "9494347000",
                "leadDoctor", "Dr. A. Sai Kumar Reddy, MBBS",
                "insurance", List.of("Vidal Health", "Cashless Mediclaim", "State Health Scheme")
        );
    }

    @GetMapping("/appointments")
    public List<Map<String, Object>> listAppointments() {
        return bookings;
    }

    @PostMapping("/appointments")
    public ResponseEntity<Map<String, Object>> createAppointment(@RequestBody AppointmentRequest req) {
        if (req.patientName() == null || req.patientName().isBlank()
                || req.patientPhone() == null || req.patientPhone().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Patient name and phone are required."));
        }
        String refId = "APT-" + ThreadLocalRandom.current().nextInt(100000, 999999);
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("referenceId", refId);
        record.put("department", req.department());
        record.put("doctor", req.doctor());
        record.put("preferredDate", req.preferredDate());
        record.put("timeSlot", req.timeSlot());
        record.put("patientName", req.patientName());
        record.put("patientPhone", req.patientPhone());
        record.put("visitType", req.visitType() != null ? req.visitType() : "In-Person Visit");
        bookings.add(record);
        return ResponseEntity.ok(record);
    }
}
