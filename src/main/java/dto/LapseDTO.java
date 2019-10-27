package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LapseDTO {
    int leechFails;
    int minInt;
    int[] delays;
    int leechAction;
    int mult;
}
