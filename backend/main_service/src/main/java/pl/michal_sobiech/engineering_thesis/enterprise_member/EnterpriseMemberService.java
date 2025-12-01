package pl.michal_sobiech.engineering_thesis.enterprise_member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.michal_sobiech.engineering_thesis.employee.Employee;
import pl.michal_sobiech.engineering_thesis.employee.EmployeeService;
import pl.michal_sobiech.engineering_thesis.entrepreneur.Entrepreneur;
import pl.michal_sobiech.engineering_thesis.entrepreneur.EntrepreneurService;

@Service
@RequiredArgsConstructor
public class EnterpriseMemberService {

    private final EntrepreneurService entrepreneurService;
    private final EmployeeService employeeService;

    public List<EnterpriseMember> getEnterpriseMembers(long appointmentId) {
        List<EnterpriseMember> out = new ArrayList<>();

        Entrepreneur enterpriseOwner = entrepreneurService.getEnterpriseOwner(appointmentId);

        List<Employee> employees = employeeService.getEnterpriseEmployees(appointmentId);

        out.add(enterpriseOwner);
        out.addAll(employees);

        return out;
    }

}
