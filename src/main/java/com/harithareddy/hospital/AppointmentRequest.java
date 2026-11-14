package com.harithareddy.hospital;

public record AppointmentRequest(
        String department,
        String doctor,
        String preferredDate,
        String timeSlot,
        String patientName,
        String patientPhone,
        String visitType
) {}
