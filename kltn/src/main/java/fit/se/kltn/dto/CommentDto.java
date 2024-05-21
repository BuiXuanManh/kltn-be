package fit.se.kltn.dto;

import fit.se.kltn.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Comment comment;
    private Double rate;
}
