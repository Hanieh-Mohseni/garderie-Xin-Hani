package com.sg.garderie.service;

import com.sg.garderie.dao.NewsDao;
import com.sg.garderie.dao.NewsException;
import com.sg.garderie.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GarderieServiceImpl implements GarderieService {

    @Autowired
    NewsDao newsDao;

    @Value( "${file.path}" )
    private String FILE_BASE_PATH;

    @Override
    public News addNews(News news) {
        newsDao.addNews(news);
        news.setPicPath("****");
        return news;
    }

    @Override
    public String saveFile(String fileName, byte[] bytes) throws IOException {
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        Path path = Paths.get(FILE_BASE_PATH + uuid + fileType);
        Files.write(path, bytes);
        return path.toString();
    }

    @Override
    public News getNewsById(int id) throws NewsException {
        News news = newsDao.getNewsById(id);
        if (news == null) throw new NewsException("No such news.");
        String fileName = news.getPicPath().replaceFirst(FILE_BASE_PATH, "");
        news.setPicPath("/download/" + fileName);
        return news;
    }

    @Override
    public List<News> getNewsByDate(LocalDate date) {
        List<News> list = newsDao.getNewsByDate(date);
        list.stream().forEach(news -> news.setPicPath("/download/" +
                news.getPicPath().replaceFirst(FILE_BASE_PATH, "")));
        return list;
    }

    @Override
    public List<News> getAllNews() {
        List<News> list = newsDao.getAllNews();
        list.stream().forEach(news -> news.setPicPath("/download/" +
                news.getPicPath().replaceFirst(FILE_BASE_PATH, "")));
        return list;
    }

    @Override
    public void editNews(News news) throws NewsException{
        News retrieveNews = getNewsById(news.getId());
        File file = new File(retrieveNews.getPicPath());
        if (file.exists()) {
            file.delete();
        }
        newsDao.editNews(news);
    }

    @Override
    public void deleteNewsById(int id) throws NewsException {
        News retrieveNews = getNewsById(id);
        if (retrieveNews == null) throw new NewsException("No such news.");
        File file = new File(retrieveNews.getPicPath());
        if (file.exists()) {
            file.delete();
        }
        newsDao.deleteNewsById(id);
    }
}
