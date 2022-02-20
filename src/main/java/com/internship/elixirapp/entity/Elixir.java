package com.internship.elixirapp.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, setterPrefix = "with")
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "Elixir")
public class Elixir {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private Integer cost;

    private Integer level;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "Recipe",
            joinColumns = { @JoinColumn(name = "elixir_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    private List<Ingredient> ingredients;

    @ManyToMany(mappedBy = "elixirs", fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToMany(mappedBy = "availableElixirs", fetch = FetchType.LAZY)
    private List<User> availableToUsers;

}
