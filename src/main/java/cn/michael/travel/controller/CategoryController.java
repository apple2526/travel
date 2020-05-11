package cn.michael.travel.controller;

import cn.michael.travel.api.CategoryApi;
import cn.michael.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class CategoryController implements CategoryApi {
    @Autowired
    CategoryService categoryService;
    @Autowired
    HttpServletResponse response;

    @GetMapping("/queryAllCategory")
    public void queryAllCategory() throws Exception {
        String categoryList = categoryService.queryAllCategory();
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(categoryList);
    }
}
