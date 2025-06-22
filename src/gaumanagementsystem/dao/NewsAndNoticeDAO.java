package gaumanagementsystem.dao;

import gaumanagementsystem.model.NewsAndNotice;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface NewsAndNoticeDAO {
    
    /**
     * Create a new news or notice
     * @param newsAndNotice NewsAndNotice object to be created
     * @return true if news/notice was created successfully, false otherwise
     */
    boolean createNewsAndNotice(NewsAndNotice newsAndNotice);
    
    /**
     * Find news/notice by ID
     * @param id News/Notice ID
     * @return Optional containing NewsAndNotice if found, empty Optional otherwise
     */
    Optional<NewsAndNotice> findById(int id);
    
    /**
     * Get all news and notices
     * @return List of all news and notices
     */
    List<NewsAndNotice> getAllNewsAndNotices();
    
    /**
     * Get news and notices by type
     * @param type Type (News, Notice)
     * @return List of news/notices with specified type
     */
    List<NewsAndNotice> getByType(String type);
    
    /**
     * Get news and notices by status
     * @param status Status (Active, Inactive, Draft)
     * @return List of news/notices with specified status
     */
    List<NewsAndNotice> getByStatus(String status);
    
    /**
     * Get news and notices by priority
     * @param priority Priority (High, Medium, Low)
     * @return List of news/notices with specified priority
     */
    List<NewsAndNotice> getByPriority(String priority);
    
    /**
     * Get active news and notices (not expired)
     * @return List of active news and notices
     */
    List<NewsAndNotice> getActiveNewsAndNotices();
    
    /**
     * Get expired news and notices
     * @return List of expired news and notices
     */
    List<NewsAndNotice> getExpiredNewsAndNotices();
    
    /**
     * Search news/notices by title
     * @param title Title or part of title to search
     * @return List of news/notices matching the search criteria
     */
    List<NewsAndNotice> searchByTitle(String title);
    
    /**
     * Search news/notices by content
     * @param content Content or part of content to search
     * @return List of news/notices matching the search criteria
     */
    List<NewsAndNotice> searchByContent(String content);
    
    /**
     * Get news/notices published in date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of news/notices published between dates
     */
    List<NewsAndNotice> getByPublicationDateRange(Date startDate, Date endDate);
    
    /**
     * Get news/notices expiring in date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of news/notices expiring between dates
     */
    List<NewsAndNotice> getByExpiryDateRange(Date startDate, Date endDate);
    
    /**
     * Get news/notices expiring soon (within specified days)
     * @param days Number of days
     * @return List of news/notices expiring within specified days
     */
    List<NewsAndNotice> getExpiringSoon(int days);
    
    /**
     * Get latest news and notices
     * @param limit Number of latest items to retrieve
     * @return List of latest news and notices
     */
    List<NewsAndNotice> getLatestNewsAndNotices(int limit);
    
    /**
     * Update news/notice information
     * @param newsAndNotice NewsAndNotice object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateNewsAndNotice(NewsAndNotice newsAndNotice);
    
    /**
     * Update news/notice status
     * @param id News/Notice ID
     * @param newStatus New status
     * @return true if status updated successfully, false otherwise
     */
    boolean updateStatus(int id, String newStatus);
    
    /**
     * Update news/notice priority
     * @param id News/Notice ID
     * @param newPriority New priority
     * @return true if priority updated successfully, false otherwise
     */
    boolean updatePriority(int id, String newPriority);
    
    /**
     * Extend expiry date
     * @param id News/Notice ID
     * @param newExpiryDate New expiry date
     * @return true if expiry date updated successfully, false otherwise
     */
    boolean extendExpiryDate(int id, Date newExpiryDate);
    
    /**
     * Delete news/notice by ID
     * @param id News/Notice ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteNewsAndNotice(int id);
    
    /**
     * Get total count of news and notices
     * @return Total number of news and notices
     */
    int getNewsAndNoticeCount();
    
    /**
     * Get count of news/notices by type
     * @param type Type (News, Notice)
     * @return Number of news/notices with specified type
     */
    int getCountByType(String type);
    
    /**
     * Get count of news/notices by status
     * @param status Status
     * @return Number of news/notices with specified status
     */
    int getCountByStatus(String status);
    
    /**
     * Get count of active news and notices
     * @return Number of active news and notices
     */
    int getActiveCount();
    
    /**
     * Get count of expired news and notices
     * @return Number of expired news and notices
     */
    int getExpiredCount();
    
    /**
     * Archive old news and notices
     * @param cutoffDate Date before which items should be archived
     * @return Number of items archived
     */
    int archiveOldNewsAndNotices(Date cutoffDate);
} 