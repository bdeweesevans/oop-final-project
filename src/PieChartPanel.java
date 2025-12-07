import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.*;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.swing.ChartPanel;
import org.jfree.data.general.*;

public class PieChartPanel extends JPanel {

    private static final long serialVersionUID = 1L;
	private DefaultPieDataset<String> dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public PieChartPanel() {
        setLayout(new BorderLayout());

        dataset = new DefaultPieDataset<>();

        chart = ChartFactory.createPieChart(
            "Expenses by Category",
            dataset,
            true,
            true,
            false
        );

        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    public void updateData(ArrayList<Expense> expenses) {
        dataset.clear();

        Map<String, Float> totals = new HashMap<>();

        for (Expense e : expenses) {
            String raw = e.getType().toString();   // e.g., "TRANSPORT"
            String formatted = raw.substring(0,1).toUpperCase() + raw.substring(1).toLowerCase();
            totals.merge(formatted, e.getPrice(), Float::sum);
        }

        for (var entry : totals.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
    }

}
