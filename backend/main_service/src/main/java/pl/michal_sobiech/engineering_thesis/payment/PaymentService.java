package pl.michal_sobiech.engineering_thesis.payment;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.appointment.AppointmentService;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AppointmentService appointmentService;

}
