package com.example.myexpenseapp.ui;



import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myexpenseapp.R;
import com.example.myexpenseapp.database.MyDbHelper;
import com.example.myexpenseapp.database.MyDbHelper.CategoryWeeklyExpense;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private MyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pieChart = findViewById(R.id.pieChart);
        dbHelper = new MyDbHelper(this);

        setupPieChart();
    }

    private void setupPieChart() {
        ArrayList<CategoryWeeklyExpense> expenses = dbHelper.getWeeklyCategoryExpenses();
        List<PieEntry> entries = new ArrayList<>();

        for (CategoryWeeklyExpense expense : expenses) {
            entries.add(new PieEntry((float) expense.getTotalAmount(), expense.getCategoryName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Weekly Expenses");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterTextSize(18f);
        pieChart.animateY(1000);
    }
}
