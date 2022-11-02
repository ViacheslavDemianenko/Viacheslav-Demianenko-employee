package bg.sirma.demianenko.service.impl;

import bg.sirma.demianenko.service.EmployeeService;
import bg.sirma.demianenko.service.IOService;
import bg.sirma.demianenko.service.RunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunnerServiceImpl implements RunnerService {

    private final EmployeeService employeeService;
    private final IOService ioService;

    @Override
    public void runTestTask(){
        var employeePair = employeeService.findEmployeePairsWithLongestPeriod();
        if(employeePair.getTotalDuration()!=0){
            ioService.outputString("Employees with EmpIDs = " + employeePair.getFirstEmployeeId() + ", " + employeePair.getSecondEmployeeId()
                    + " has longest period of time on project with ProjectID = "
                    + employeePair.getProjectId() + ", " + employeePair.getTotalDuration()
                    + "days.");
        } else {
            ioService.outputString("Can't find matching pair of employees");
        }
    }
}
