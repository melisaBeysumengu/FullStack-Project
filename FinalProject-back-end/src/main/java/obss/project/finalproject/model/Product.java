package obss.project.finalproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product title is required.")
    private String title;

    @NotNull(message = "Product price is required.")
    private Double price;

    private String pictureUrl;

    @NotNull
    @OneToOne(targetEntity = User.class)
    private User seller;

    /*@NotNull
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean isDeleted;*/
}