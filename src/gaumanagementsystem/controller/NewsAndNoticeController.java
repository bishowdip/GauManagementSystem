/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.model.NewsAndNotice;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bishodip
 */
public class NewsAndNoticeController {
    private final List<NewsAndNotice> dataList = new ArrayList<>();

    public NewsAndNoticeController() {
        // No sample data - data should be loaded from database via DAO
    }

    public List<NewsAndNotice> getAll() {
        return new ArrayList<>(dataList);
    }

    public void add(NewsAndNotice notice) {
        dataList.add(notice);
    }

    public void update(int index, NewsAndNotice notice) {
        dataList.set(index, notice);
    }

    public void delete(int index) {
        dataList.remove(index);
    }

    public List<NewsAndNotice> search(String query, String type) {
        List<NewsAndNotice> result = new ArrayList<>();
        for (NewsAndNotice notice : dataList) {
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
    }
}
