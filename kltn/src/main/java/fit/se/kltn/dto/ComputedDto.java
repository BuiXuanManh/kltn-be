package fit.se.kltn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputedDto {
    private double readCount;
    private int reviewCount;
    private long totalEmotion;
    private int commentCount;
    private int bookCount;
    private int activeUser;
}
