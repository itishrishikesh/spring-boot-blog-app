package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Category;
import com.hrishi.blog.exception.ResourceNotFoundException;
import com.hrishi.blog.payload.CategoryDto;
import com.hrishi.blog.repository.CategoryRepository;
import com.hrishi.blog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", Long.toString(id)));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", Long.toString(id)));

        category.setCategory(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryDto.getId());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category", "id", id.toString()));

        categoryRepository.delete(category);
    }
}
