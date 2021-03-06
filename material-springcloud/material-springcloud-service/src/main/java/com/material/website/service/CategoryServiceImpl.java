package com.material.website.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.CategoryAPI;
import com.material.website.args.CategoryArgs;
import com.material.website.dao.ICategoryDao;
import com.material.website.dao.IGoodsDao;
import com.material.website.dto.CategoryDto;
import com.material.website.entity.Category;
import com.material.website.entity.Goods;
import com.material.website.system.Pager;
import com.material.website.systemcontext.ConverMapToSystemContext;
import com.material.website.util.JsonUtil;

/**
 * 分类业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class CategoryServiceImpl implements CategoryAPI {

	@Autowired
	private ICategoryDao categoryDao;
	@Autowired
	private IGoodsDao goodsDao;

	@Override
	public List<CategoryDto> queryCategoryList(Integer parentId) {
		List<CategoryDto>list = categoryDao.queryCategoryList(parentId);
		List<CategoryDto> resultList = new ArrayList<CategoryDto>();
		for(CategoryDto dto :list){
			 List<CategoryDto> childList = categoryDao.queryCategoryList(dto.getId());
			 if(childList != null && childList.size()>0){
				 dto.setIsParent(true);
			 }else{
				 dto.setIsParent(false);
			 }
			 resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public Pager<CategoryDto> queryCategoryPager(String categoryName,
			Integer parentId,Integer status,@RequestParam Map<String, Object>map) {
		ConverMapToSystemContext.convertSystemContext(map);
		return categoryDao.queryCategoryPager(categoryName, parentId,status);
	}
	
	@Override
	public boolean addCategory(String json) {
		try {
			CategoryArgs categoryArgs = (CategoryArgs) JsonUtil.newInstance().json2obj(json, CategoryArgs.class);
			Category category = new Category();
			BeanUtils.copyProperties(categoryArgs, category);
			category.setStatus(0);
			categoryDao.addEntity(category);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCategory(String json) {
		try {
			CategoryArgs categoryArgs =  (CategoryArgs) JsonUtil.newInstance().json2obj(json, CategoryArgs.class);
			Category category = new Category();
			BeanUtils.copyProperties(categoryArgs, category);
			categoryDao.updateEntity(category);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> delCategory(Integer categoryId) {
		Map<String, Object>map = new HashMap<String, Object>();
		try {
			Category category = categoryDao.get(categoryId);
			if(category.getParentId()  == 0){
				List<CategoryDto> list = categoryDao.queryCategoryList(categoryId);
				if(list != null && list.size() > 0){
					map.put("status", 500);
					map.put("msg", "该分类存在子级分类");
					return map;
				}	
			}else{
				List<Goods>resultList = goodsDao.queryGoodsByCategoryId(categoryId);
				if(resultList != null && resultList.size() > 0){
					map.put("status", 500);
					map.put("msg", "该分类已绑定商品");
					return map;
				}	
			} 
			categoryDao.updateCategoryStatus(categoryId);
			map.put("status", 200);
			map.put("msg", "移除成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", 500);
			map.put("msg", "移除失败");
			return map;
		}
	}

	@Override
	public Category loadCategory(Integer categoryId) {
		return categoryDao.get(categoryId);
	}

}
