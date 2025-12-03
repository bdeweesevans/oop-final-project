import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesChartPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private TimeSeries series;
    private TimeSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public TimeSeriesChartPanel() {
        setLayout(new BorderLayout());

        series = new TimeSeries("Expenses Over Time");
        dataset = new TimeSeriesCollection(series);

        chart = ChartFactory.createTimeSeriesChart(
            "Daily Spending",
            "Date",
            "Amount ($)",
            dataset,
            true,
            true,
            false
        );

//        // visual configs (optional)
//        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setBackgroundPaint(Color.WHITE);
//        plot.setDomainGridlinePaint(Color.GRAY);
//        plot.setRangeGridlinePaint(Color.GRAY);

        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void updateData(ArrayList<Expense> expenses) {
        series.clear();

        // aggregate by date
        Map<LocalDate, Float> totals = new TreeMap<>();

        for (Expense e : expenses) {
            totals.merge(e.getDate(), e.getPrice(), Float::sum);
        }

        // Add dates to the series
        for (Map.Entry<LocalDate, Float> entry : totals.entrySet()) {
            LocalDate date = entry.getKey();
            float amount = entry.getValue();

            series.add(
                new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                amount
            );
        }
    }
}
