package com.internship.elixirapp.controller;


import com.internship.elixirapp.dto.ElixirDto;
import com.internship.elixirapp.dto.ElixirFilterDto;
import com.internship.elixirapp.service.ElixirService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/elixir")
public class ElixirController {

    private final ElixirService elixirService;

    @RequestMapping(value = "/create/elixir/{ingredientIds}", method = RequestMethod.GET)
    @ResponseBody
    public ElixirDto createElixir(@PathVariable String[] ingredientIds) {
        return elixirService.createElixir(ingredientIds);
    }


    @GetMapping("/get/all")
    public List<ElixirDto> getElixirs(@RequestBody @Valid ElixirFilterDto filter) {
        return elixirService.getElixirs(filter);
    }


}
