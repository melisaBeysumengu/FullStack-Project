package obss.project.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FavoriteRequest {
    @NotBlank
    private String username;

    @NotBlank
    private Long product_id;
}
