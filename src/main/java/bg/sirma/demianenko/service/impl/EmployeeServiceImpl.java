package bg.sirma.demianenko.service.impl;

import bg.sirma.demianenko.dao.DataDao;
import bg.sirma.demianenko.model.Data;
import bg.sirma.demianenko.model.EmployeePair;
import bg.sirma.demianenko.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final DataDao dataDao;

    public EmployeePair findEmployeePairsWithLongestPeriod() {

        List<Data> allData = dataDao.getDataList();

        EmployeePair employeePair = new EmployeePair();
        for (int i = 0; i < allData.size() - 1; i++) {
            Data firstEmployee = allData.get(i);
            for (int j = i + 1; j < allData.size(); j++) {
                Data secondEmployee = allData.get(j);
                long crossingDays;
                if (firstEmployee.getProjectId().equals(secondEmployee.getProjectId())
                        && hasCrossingDays(firstEmployee, secondEmployee)){
                    crossingDays = calculateCrossingDays(firstEmployee, secondEmployee);
                    if (crossingDays > 0) {
                        employeePair = updateEmployeePair(employeePair, firstEmployee, secondEmployee, crossingDays);
                    }
                }
            }
        }
        return employeePair;
    }

    private long calculateCrossingDays(Data firstEmployee, Data secondEmployee) {
        LocalDate periodStartDate = firstEmployee.getDateFrom().isBefore(secondEmployee.getDateFrom())
                ? secondEmployee.getDateFrom()
                : firstEmployee.getDateFrom();

        LocalDate periodEndDate = firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo())
                ? firstEmployee.getDateTo()
                : secondEmployee.getDateTo();

        return Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
    }

    private boolean hasCrossingDays(Data firstEmployee, Data secondEmployee) {
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo())
                || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo()))
                && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom())
                || firstEmployee.getDateTo().isEqual(secondEmployee.getDateFrom()));
    }

    private EmployeePair updateEmployeePair(EmployeePair employeePair,
                                            Data firstEmployee,
                                            Data secondEmployee,
                                            long crossingDays){
        if (crossingDays > employeePair.getTotalDuration()){
            employeePair.setFirstEmployeeId(firstEmployee.getEmployeeId());
            employeePair.setSecondEmployeeId(secondEmployee.getEmployeeId());
            employeePair.setProjectId(firstEmployee.getProjectId());
            employeePair.setTotalDuration(crossingDays);
        }
        return employeePair;
    }
}