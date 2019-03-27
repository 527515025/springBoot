package cn.abel.service;

import java.util.List;
import java.util.Map;

import cn.abel.config.DynamicDataSource;
import cn.abel.config.DynamicDataSourceContextHolder;
import cn.abel.enums.DatabaseTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abel.dao.NewsDao;
import cn.abel.bean.News;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getByMap(Map<String, Object> map) {
        DynamicDataSourceContextHolder.resetDatabaseType();
        return newsDao.getByMap(map);
    }

    public News getById(Integer id) {
        DynamicDataSourceContextHolder.resetDatabaseType();
        return newsDao.getById(id);
    }

    public News create(News news) {
        DynamicDataSourceContextHolder.resetDatabaseType();
        newsDao.create(news);
        return news;
    }

    public News update(News news) {
        DynamicDataSourceContextHolder.resetDatabaseType();
        newsDao.update(news);
        return news;
    }

    public int delete(Integer id) {
        DynamicDataSourceContextHolder.resetDatabaseType();
        return newsDao.delete(id);
    }

}