package com.internship.elixirapp.controller;


import com.internship.elixirapp.service.ElixirService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/elixir")
public class ElixirController {

    private ElixirService elixirService;

    @RequestMapping(value="/create/elixir/{ingredientIds}", method= RequestMethod.GET)
    @ResponseBody
    public String createElixir(@PathVariable String[] ingredientIds)
    {
        return "Dummy";
    }
}
