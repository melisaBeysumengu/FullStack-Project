package obss.project.finalproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import obss.project.finalproject.model.User;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String title;

    @Column(precision = 10, scale = 2)
    @NotNull(message = "Please enter a price.")
    private Double price;
/*
    @NotBlank
    @Valid
    private User seller;*/

    private String pictureUrl;
}
