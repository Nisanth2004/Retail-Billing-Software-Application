package com.nisanth.billingsoftware.service;

import com.nisanth.billingsoftware.io.CategoryRequest;
import com.nisanth.billingsoftware.io.CategoryResponse;

import java.util.List;

public interface CategoryService {

   CategoryResponse add(CategoryRequest request);
   List<CategoryResponse> read();

   void delete(String categoryId);
}
