package bg.sirma.demianenko.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class EmployeePair {
    private long firstEmployeeId;
    private long secondEmployeeId;
    private long projectId;
    private long totalDuration;
}
