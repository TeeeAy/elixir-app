package com.internship.elixirapp.repository;

import com.internship.elixirapp.entity.Elixir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ElixirRepository extends JpaRepository<Elixir, String>, JpaSpecificationExecutor<Elixir> {


}
