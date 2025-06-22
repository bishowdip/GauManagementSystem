/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl;
import gaumanagementsystem.model.NewsAndNotice;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bishodip
 */
public class NewsAndNoticeController {
    private final NewsAndNoticeDAOImpl newsAndNoticeDAO;

    public NewsAndNoticeController() {
        this.newsAndNoticeDAO = new NewsAndNoticeDAOImpl();
    }

    public List<NewsAndNotice> getAll() {
        try {
            return newsAndNoticeDAO.getAllNewsAndNotices();
        } catch (Exception e) {
            System.err.println("Error getting all news and notices: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean add(NewsAndNotice notice) {
        try {
            return newsAndNoticeDAO.createNewsAndNotice(notice);
        } catch (Exception e) {
            System.err.println("Error adding news/notice: " + e.getMessage());
            return false;
        }
    }

    public boolean update(int id, NewsAndNotice notice) {
        try {
            return newsAndNoticeDAO.updateNewsAndNotice(notice);
        } catch (Exception e) {
            System.err.println("Error updating news/notice: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            return newsAndNoticeDAO.deleteNewsAndNotice(id);
        } catch (Exception e) {
            System.err.println("Error deleting news/notice: " + e.getMessage());
            return false;
        }
    }

    public List<NewsAndNotice> search(String query, String type) {
        try {
            List<NewsAndNotice> allNews = newsAndNoticeDAO.getAllNewsAndNotices();
            List<NewsAndNotice> result = new ArrayList<>();
            
            for (NewsAndNotice notice : allNews) {
                boolean matchesSearch = query.isEmpty();
                boolean matchesType = type.isEmpty() || notice.getType().equalsIgnoreCase(type);

                // Check if any field contains the search text
                if (!query.isEmpty()) {
                    if (notice.getDate().toLowerCase().contains(query.toLowerCase()) ||
                        notice.getAudience().toLowerCase().contains(query.toLowerCase()) ||
                        notice.getSubject().toLowerCase().contains(query.toLowerCase()) ||
                        notice.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                        notice.getExpiryDate().toLowerCase().contains(query.toLowerCase()) ||
                        notice.getType().toLowerCase().contains(query.toLowerCase())) {
                        matchesSearch = true;
                    }
                }

                if (matchesSearch && matchesType) {
                    result.add(notice);
                }
            }
            return result;
        } catch (Exception e) {
            System.err.println("Error searching news/notices: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public List<NewsAndNotice> getByType(String type) {
        try {
            return newsAndNoticeDAO.getByType(type);
        } catch (Exception e) {
            System.err.println("Error getting news/notices by type: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
