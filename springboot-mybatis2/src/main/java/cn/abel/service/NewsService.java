package cn.abel.service;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abel.dao.NewsDao;
import cn.abel.bean.News;

@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getByMap(Map<String, Object> map) {
        return newsDao.getByMap(map);
    }

    public News getById(Integer id) {
        return newsDao.getById(id);
    }

    public News create(News news) {
        newsDao.create(news);
        return news;
    }

    public News update(News news) {
        newsDao.update(news);
        return news;
    }

    public int delete(Integer id) {
        return newsDao.delete(id);
    }

}