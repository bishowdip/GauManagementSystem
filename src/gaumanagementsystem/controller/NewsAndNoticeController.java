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
 * @author ASUS
 */
public class NewsAndNoticeController {
    private final List<NewsAndNotice> dataList = new ArrayList<>();

    public NewsAndNoticeController() {
        // Sample data
        dataList.add(new NewsAndNotice("2024-03-20", "All Citizens", "Road Maintenance", "Annual road maintenance work will begin next week", "2024-04-20", "Notice"));
        dataList.add(new NewsAndNotice("2024-03-19", "Students", "School Holiday", "School will remain closed for spring break", "2024-03-25", "Notice"));
        dataList.add(new NewsAndNotice("2024-03-18", "Farmers", "Agricultural Workshop", "Free workshop on modern farming techniques", "2024-03-30", "News"));
        dataList.add(new NewsAndNotice("2024-03-17", "Business Owners", "Tax Filing", "Last date for tax filing is approaching", "2024-03-31", "Notice"));
        dataList.add(new NewsAndNotice("2024-03-16", "All Citizens", "Water Supply", "Water supply will be affected due to maintenance", "2024-03-18", "News"));
        // ... add more as needed
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
