package pl.michal_sobiech.engineering_thesis.appointment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMember;
import pl.michal_sobiech.engineering_thesis.enterprise_member.EnterpriseMemberService;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private EnterpriseMemberService enterpriseMemberService;

    public boolean canUserManageAppointment(long userId, long appointmentId) {
        // Appointment with given id must be an appointment of a service of an
        // enterprise where the user with given id is enterprise staff.
        List<Long> enterpriseMemberUserIds = enterpriseMemberService.getEnterpriseMembers(appointmentId)
                .stream()
                .map(EnterpriseMember::getUserId)
                .collect(Collectors.toList());

        return enterpriseMemberUserIds.contains(userId);
    }

}
