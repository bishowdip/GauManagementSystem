/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author ASUS
 */
public class NewsAndNoticeContoller {
    private final List<Object[]> dataList = new ArrayList<>();

    public NewsAndNoticeContoller() {
        // Sample data
        dataList.add(new Object[]{"2021/22", "100000", "50000", "Ongoing", "First phase"});
        dataList.add(new Object[]{"2020/21", "80000", "80000", "Completed", "All done"});
        dataList.add(new Object[]{"2022/23", "120000", "30000", "Started", "Initial"});
    }

    public List<Object[]> getAll() {
        return new ArrayList<>(dataList);
    }

    public void add(Object[] row) {
        dataList.add(row);
    }

    public void update(int index, Object[] row) {
        dataList.set(index, row);
    }

    public void delete(int index) {
        dataList.remove(index);
    }

    public List<Object[]> search(String query) {
        List<Object[]> result = new ArrayList<>();
        for (Object[] row : dataList) {
            for (Object value : row) {
                if (value != null && value.toString().toLowerCase().contains(query.toLowerCase())) {
                    result.add(row);
                    break;
                }
            }
        }
        return result;
    }
}
