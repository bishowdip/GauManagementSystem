-- Create projects table for budget allocation functionality
-- This table will store project information with category and amount for budget tracking

CREATE DATABASE IF NOT EXISTS gau_management;
USE gau_management;

CREATE TABLE IF NOT EXISTS projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    request_id VARCHAR(50) UNIQUE,
    project_name VARCHAR(255) NOT NULL,
    started_date DATE,
    ward VARCHAR(50),
    category VARCHAR(100) NOT NULL,
    expected_end_date DATE,
    description TEXT,
    status VARCHAR(50) DEFAULT 'Pending',
    amount DECIMAL(15,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data for demonstration
INSERT INTO projects (request_id, project_name, started_date, ward, category, expected_end_date, description, status, amount) VALUES
('REQ001', 'School Building Construction', '2024-01-15', 'Ward 1', 'Education', '2024-06-15', 'Building new primary school', 'In Progress', 2500000.00),
('REQ002', 'Community Health Center', '2024-02-01', 'Ward 2', 'Health and Medical', '2024-08-01', 'Establishing health center', 'Approved', 1800000.00),
('REQ003', 'Housing Project Phase 1', '2024-01-20', 'Ward 3', 'Housing and Rent', '2024-12-20', 'Affordable housing project', 'In Progress', 3200000.00),
('REQ004', 'Road Construction', '2024-03-01', 'Ward 4', 'Transportation', '2024-09-01', 'Main road construction', 'Approved', 1500000.00),
('REQ005', 'Food Distribution Center', '2024-02-15', 'Ward 5', 'Food and Groceries', '2024-07-15', 'Community food center', 'Completed', 800000.00),
('REQ006', 'Library and Learning Center', '2024-01-10', 'Ward 1', 'Education', '2024-05-10', 'Digital library setup', 'In Progress', 1200000.00),
('REQ007', 'Medical Equipment', '2024-02-20', 'Ward 2', 'Health and Medical', '2024-06-20', 'Medical equipment purchase', 'Approved', 900000.00),
('REQ008', 'Community Hall', '2024-03-05', 'Ward 6', 'Entertainment and Leisure', '2024-10-05', 'Multipurpose community hall', 'Pending', 2000000.00),
('REQ009', 'Water Supply System', '2024-01-25', 'Ward 7', 'Savings and Investments', '2024-07-25', 'Water infrastructure', 'In Progress', 1800000.00),
('REQ010', 'Bus Terminal', '2024-03-10', 'Ward 8', 'Transportation', '2024-11-10', 'Public transport facility', 'Approved', 2200000.00);

-- Create indexes for better performance
CREATE INDEX idx_projects_category ON projects(category);
CREATE INDEX idx_projects_ward ON projects(ward);
CREATE INDEX idx_projects_status ON projects(status);
CREATE INDEX idx_projects_amount ON projects(amount);

-- Sample query to test budget allocation by category
SELECT 
    category, 
    SUM(amount) as total_amount, 
    COUNT(*) as project_count,
    ROUND((SUM(amount) / (SELECT SUM(amount) FROM projects)) * 100, 2) as percentage
FROM projects 
WHERE amount IS NOT NULL AND amount > 0 
GROUP BY category 
ORDER BY total_amount DESC; 