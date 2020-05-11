package cn.michael.travel.service;

import cn.michael.travel.dao.CategoryRepository;
import cn.michael.travel.pojo.Category;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    public String queryAllCategory() {
        String categoryJson = redisTemplate.opsForValue().get("HEIMA65_CATEGORY_LIST");
        if (categoryJson == null) {
            List<Category> categoryList = categoryRepository.findAll();
            categoryJson = JSON.toJSONString(categoryList);
            redisTemplate.opsForValue().set("HEIMA65_CATEGORY_LIST", categoryJson);
        }
        return categoryJson;
    }
}
