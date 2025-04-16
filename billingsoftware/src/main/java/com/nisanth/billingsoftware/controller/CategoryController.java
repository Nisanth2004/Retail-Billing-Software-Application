package com.nisanth.billingsoftware.controller;

import com.nisanth.billingsoftware.io.CategoryRequest;
import com.nisanth.billingsoftware.io.CategoryResponse;
import com.nisanth.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest request)
    {
       return categoryService.add(request);
    }

    // Get all categories
    @GetMapping
    public List<CategoryResponse> fetchCategories()
    {
        return categoryService.read();

    }


    // delete the category
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String categoryId)
    {
        try{
            categoryService.delete(categoryId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found: "+categoryId);
        }

    }
}
