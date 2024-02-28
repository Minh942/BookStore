package com.store.service;

import java.util.List;

import com.store.entity.Category;
import com.store.model.CategoryModel;

public interface CategoryService {

	CategoryModel createCategory(CategoryModel categoryModel);

	List<Category> findAll();

	void delete(Integer id);

	CategoryModel getOneCategoryById(Integer id);

	CategoryModel updateCategory(CategoryModel categoryModel);

	Category getCategoryByNameSearch(String nameSearch);

}
