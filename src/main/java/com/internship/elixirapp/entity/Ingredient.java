package com.internship.elixirapp.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, setterPrefix = "with")
@Getter
@Setter
@Table(name = "Ingredient")
@EqualsAndHashCode(of = {"id", "name", "type", "cost"})
public class Ingredient {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private IngredientType type;

    private Integer cost;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<Elixir> elixirs;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private List<User> users;

}
