# Budget Allocation Feature - Hamro Smart Gaun

## Overview
The Budget Allocation feature provides a comprehensive view of project budgets categorized by different sectors. It displays data in both **pie chart** and **tabular** formats for better visualization and analysis.

## Features

### 📊 Visual Components
- **Interactive Pie Chart**: Shows budget distribution across categories with percentages
- **Data Table**: Displays detailed budget allocation information
- **Color-coded Legend**: Easy identification of different categories
- **Real-time Data Refresh**: Updates data from the database

### 📋 Data Display
- **Category-wise Budget**: Groups projects by category (Education, Health, Transportation, etc.)
- **Total Amount**: Shows sum of budget amounts per category
- **Project Count**: Number of projects in each category
- **Total Budget Summary**: Overall budget across all categories

## Database Schema

### Projects Table Structure
```sql
CREATE TABLE projects (
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
```

## Implementation Details

### 🏗️ Architecture
1. **Model Layer**: `BudgetAllocation.java` - Data structure
2. **DAO Layer**: `BudgetAllocationDao.java` - Database operations
3. **View Layer**: `BugdgetAllocations.java` - UI components
4. **Controller Layer**: Integrated with `DashboardController.java`

### 📊 Pie Chart Implementation
- **Custom Java Graphics2D**: No external charting library required
- **Responsive Design**: Adapts to window resizing
- **Anti-aliasing**: Smooth chart rendering
- **Percentage Labels**: Shows budget percentage for each category

### 🗄️ Database Integration
- **Dynamic Data Loading**: Fetches data from MySQL database
- **Fallback Mechanism**: Uses sample data if database is unavailable
- **Connection Management**: Proper resource handling
- **Error Handling**: Graceful failure management

## Usage Instructions

### 🚀 Setup
1. **Database Setup**:
   ```bash
   # Run the SQL script to create the projects table
   mysql -u root -p < database/create_projects_table.sql
   ```

2. **Database Configuration**:
   - Update database credentials in `MySqlConnection.java`
   - Ensure MySQL server is running
   - Database name: `gau_management`

3. **Project Integration**:
   - The feature is already integrated with the Dashboard
   - Click "Budget Allocations" button from the main menu

### 🎯 Navigation
1. **From Dashboard**: Click "Budget Allocations" button
2. **View Options**: 
   - Left panel: Interactive pie chart with legend
   - Right panel: Detailed table view
3. **Actions**:
   - **Refresh Data**: Updates data from database
   - **Back to Dashboard**: Returns to main menu

### 📈 Data Categories
The system supports the following budget categories:
- Education
- Health and Medical
- Housing and Rent  
- Transportation
- Food and Groceries
- Savings and Investments
- Entertainment and Leisure

## Key Methods

### BudgetAllocationDao.java
- `getBudgetAllocationsByCategory()`: Retrieves budget data grouped by category
- `getTotalBudgetAmount()`: Gets total budget across all projects
- `getBudgetAllocationsByWard()`: Groups budget data by ward
- `validateProjectsTable()`: Checks database schema

### BugdgetAllocations.java
- `loadBudgetData()`: Loads data from DAO
- `updatePieChart()`: Renders the pie chart visualization
- `updateTable()`: Populates the data table
- `PieChartPanel`: Custom component for pie chart rendering

## Customization Options

### 🎨 Visual Styling
- **Colors**: Modify the color array in `PieChartPanel`
- **Fonts**: Update font settings in chart and table components
- **Layout**: Adjust panel sizing and positioning

### 📊 Data Filtering
- **By Ward**: Use `getBudgetAllocationsByWard()` method
- **By Status**: Filter projects by status (Pending, Approved, In Progress, Completed)
- **By Date Range**: Add date filtering in DAO queries

### 🔧 Chart Enhancements
- **3D Effects**: Add depth to pie chart segments
- **Animation**: Implement chart loading animations
- **Tooltips**: Add hover information on chart segments
- **Export**: Save chart as image file

## Sample Data
The system includes sample project data for demonstration:
- 10 sample projects across different categories
- Total budget: ₹16,700,000
- Distributed across 8 wards
- Various project statuses

## Troubleshooting

### Common Issues
1. **Database Connection**: Ensure MySQL credentials are correct
2. **Empty Chart**: Check if projects table has data
3. **Display Issues**: Verify screen resolution compatibility

### Error Handling
- **Database Unavailable**: System shows sample data
- **Connection Timeout**: Graceful degradation to local data
- **Invalid Data**: Data validation and sanitization

## Future Enhancements
- **Export to PDF/Excel**: Budget reports generation
- **Time-based Analysis**: Budget trends over time
- **Ward-wise Comparison**: Comparative analysis by ward
- **Budget vs Actual**: Tracking actual expenditure
- **Alerts**: Budget threshold notifications

## Technical Requirements
- **Java Version**: Java 8 or higher
- **Database**: MySQL 5.7+
- **Memory**: Minimum 256MB heap space
- **Display**: Minimum 1024x768 resolution

---

## Contact
For technical support or feature requests, contact the development team.

**Happy Budget Management! 💰📊** 