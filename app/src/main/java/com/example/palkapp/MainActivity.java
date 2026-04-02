package com.example.palkapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.animation.Easing;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText salaryInput;
    private TextView calcButton;
    private TextView langRu, langEn, langEt;
    private TextView tvInputLabel, tvInputUnit, tvResultLabel, tvEmployerLabel, footerText;
    private TextView tvNet, tvTotalEmployer;
    private TextView tvLegendNet, tvLegendSocial, tvLegendTax, tvLegendContrib;
    private TextView tvLegendNetLabel, tvLegendSocialLabel, tvLegendTaxLabel, tvLegendContribLabel;
    private LinearLayout cardResults;
    private PieChart pieChart;

    private View[] rowViews = new View[6];
    private int[] rowDotColors;
    private TextView[] rowAmounts = new TextView[6];
    private TextView[] rowPcts    = new TextView[6];
    private View[]     rowBars    = new View[6];

    private String currentLang = "et";
    private HashMap<String, HashMap<String, String>> translations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        initTranslations();
        changeLanguage(currentLang);

        langRu.setOnClickListener(v -> changeLanguage("ru"));
        langEn.setOnClickListener(v -> changeLanguage("en"));
        langEt.setOnClickListener(v -> changeLanguage("et"));
        calcButton.setOnClickListener(v -> calculateAndDisplay());
    }

    private void bindViews() {
        salaryInput     = findViewById(R.id.salaryInput);
        calcButton      = findViewById(R.id.calcButton);
        langRu          = findViewById(R.id.langRu);
        langEn          = findViewById(R.id.langEn);
        langEt          = findViewById(R.id.langEt);
        tvInputLabel    = findViewById(R.id.tvInputLabel);
        tvInputUnit     = findViewById(R.id.tvInputUnit);
        tvResultLabel   = findViewById(R.id.tvResultLabel);
        tvEmployerLabel = findViewById(R.id.tvEmployerLabel);
        footerText      = findViewById(R.id.footerText);
        tvNet           = findViewById(R.id.tvNet);
        tvTotalEmployer = findViewById(R.id.tvTotalEmployer);
        tvLegendNet         = findViewById(R.id.tvLegendNet);
        tvLegendSocial      = findViewById(R.id.tvLegendSocial);
        tvLegendTax         = findViewById(R.id.tvLegendTax);
        tvLegendContrib     = findViewById(R.id.tvLegendContrib);
        tvLegendNetLabel    = findViewById(R.id.tvLegendNetLabel);
        tvLegendSocialLabel = findViewById(R.id.tvLegendSocialLabel);
        tvLegendTaxLabel    = findViewById(R.id.tvLegendTaxLabel);
        tvLegendContribLabel= findViewById(R.id.tvLegendContribLabel);
        cardResults     = findViewById(R.id.cardResults);

        rowDotColors = new int[]{
            Color.parseColor("#6366F1"),
            Color.parseColor("#8B5CF6"),
            Color.parseColor("#F59E0B"),
            Color.parseColor("#3B82F6"),
            Color.parseColor("#EF4444"),
            Color.parseColor("#00FF87")
        };

        int[] rowIds = {
            R.id.rowSocialTax, R.id.rowUnemplEmployer,
            R.id.rowPension,   R.id.rowUnemplEmployee,
            R.id.rowIncomeTax, R.id.rowNet
        };

        for (int i = 0; i < 6; i++) {
            View row      = findViewById(rowIds[i]);
            rowViews[i]   = row;
            row.findViewById(R.id.rowDot).setBackgroundColor(rowDotColors[i]);
            rowAmounts[i] = row.findViewById(R.id.rowAmount);
            rowPcts[i]    = row.findViewById(R.id.rowPct);
            rowBars[i]    = row.findViewById(R.id.rowBar);
        }
        // Net row: larger green text
        rowAmounts[5].setTextColor(Color.parseColor("#00FF87"));
        rowAmounts[5].setTextSize(16);

        pieChart = findViewById(R.id.pieChart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setTransparentCircleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(62f);
        pieChart.setTransparentCircleRadius(65f);
        pieChart.setDrawCenterText(true);
        pieChart.setCenterTextColor(Color.parseColor("#00FF87"));
        pieChart.setCenterTextSize(13f);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setTouchEnabled(false);
    }

    private void initTranslations() {
        translations.put("ru", new HashMap<>() {{
            put("input_hint",          "Введите брутто-зарплату (€)");
            put("input_label",         "БРУТТО-ЗАРПЛАТА");
            put("input_unit",          "/ мес");
            put("calculate",           "⚡ Рассчитать");
            put("result_label",        "РЕЗУЛЬТАТЫ");
            put("employer_label",      "РАБОТОДАТЕЛЬ ВСЕГО");
            put("legend_net",          "Нетто");
            put("legend_social",       "Соцналог");
            put("legend_tax",          "Налог");
            put("legend_contrib",      "Взносы");
            put("row_social",          "Социальный налог (33%)");
            put("row_unempl_employer", "Страховка безработица (работодатель, 0.8%)");
            put("row_pension",         "Накопит. пенсия II ступень (2%)");
            put("row_unempl_employee", "Страховка безработица (работник, 1.6%)");
            put("row_income_tax",      "Подоходный налог (22%)");
            put("row_net",             "Нетто-зарплата");
            put("footer",              "© 2026 PalkApp · palkapp.info@gmail.com");
            put("error",               "Ошибка: введите число");
        }});

        translations.put("en", new HashMap<>() {{
            put("input_hint",          "Enter gross salary (€)");
            put("input_label",         "GROSS SALARY");
            put("input_unit",          "/ month");
            put("calculate",           "⚡ Calculate");
            put("result_label",        "RESULTS");
            put("employer_label",      "TOTAL EMPLOYER COST");
            put("legend_net",          "Net salary");
            put("legend_social",       "Social tax");
            put("legend_tax",          "Income tax");
            put("legend_contrib",      "Your contributions");
            put("row_social",          "Social tax (employer, 33%)");
            put("row_unempl_employer", "Unemployment insurance (employer, 0.8%)");
            put("row_pension",         "Funded pension 2nd tier (2%)");
            put("row_unempl_employee", "Unemployment insurance (employee, 1.6%)");
            put("row_income_tax",      "Income tax (22%)");
            put("row_net",             "Net salary");
            put("footer",              "© 2026 PalkApp · palkapp.info@gmail.com");
            put("error",               "Error: enter a number");
        }});

        translations.put("et", new HashMap<>() {{
            put("input_hint",          "Sisesta brutopalk (€)");
            put("input_label",         "BRUTOPALK");
            put("input_unit",          "/ kuus");
            put("calculate",           "⚡ Arvuta");
            put("result_label",        "TULEMUSED");
            put("employer_label",      "TÖÖANDJA KOKKU");
            put("legend_net",          "Netopalk");
            put("legend_social",       "Sotsiaalmaks");
            put("legend_tax",          "Tulumaks");
            put("legend_contrib",      "Teie maksed");
            put("row_social",          "Sotsiaalmaks (tööandja, 33%)");
            put("row_unempl_employer", "Töötuskindlustus (tööandja, 0.8%)");
            put("row_pension",         "Kogumispension II sammas (2%)");
            put("row_unempl_employee", "Töötuskindlustus (töötaja, 1.6%)");
            put("row_income_tax",      "Tulumaks (22%)");
            put("row_net",             "Netopalk");
            put("footer",              "© 2026 PalkApp · palkapp.info@gmail.com");
            put("error",               "Viga: sisesta number");
        }});
    }

    private void changeLanguage(String lang) {
        currentLang = lang;
        HashMap<String, String> t = translations.get(lang);

        salaryInput.setHint(t.get("input_hint"));
        tvInputLabel.setText(t.get("input_label"));
        tvInputUnit.setText(t.get("input_unit"));
        calcButton.setText(t.get("calculate"));
        tvResultLabel.setText(t.get("result_label"));
        tvEmployerLabel.setText(t.get("employer_label"));
        tvLegendNetLabel.setText(t.get("legend_net"));
        tvLegendSocialLabel.setText(t.get("legend_social"));
        tvLegendTaxLabel.setText(t.get("legend_tax"));
        tvLegendContribLabel.setText(t.get("legend_contrib"));
        footerText.setText(t.get("footer"));

        String[] rowKeys = {
            "row_social", "row_unempl_employer", "row_pension",
            "row_unempl_employee", "row_income_tax", "row_net"
        };
        for (int i = 0; i < 6; i++) {
            ((TextView) rowViews[i].findViewById(R.id.rowName)).setText(t.get(rowKeys[i]));
        }

        updatePillStyle(langRu, lang.equals("ru"));
        updatePillStyle(langEn, lang.equals("en"));
        updatePillStyle(langEt, lang.equals("et"));
    }

    private void updatePillStyle(TextView pill, boolean active) {
        if (active) {
            pill.setBackgroundResource(R.drawable.bg_lang_pill_active);
            pill.setTextColor(Color.parseColor("#00FF87"));
        } else {
            pill.setBackgroundResource(R.drawable.bg_lang_pill_inactive);
            pill.setTextColor(Color.parseColor("#66FFFFFF"));
        }
    }

    private void calculateAndDisplay() {
        HashMap<String, String> t = translations.get(currentLang);
        String input = salaryInput.getText().toString().trim();
        try {
            double gross = Double.parseDouble(input);
            SalaryCalculator c = new SalaryCalculator(gross);
            displayResults(c, t);
        } catch (NumberFormatException e) {
            salaryInput.setError(t.get("error"));
        }
    }

    void displayResults(SalaryCalculator c, HashMap<String, String> t) {
        double pct = c.totalEmployerCost / 100.0;

        tvNet.setText(String.format("%.2f €", c.netSalary));
        tvTotalEmployer.setText(String.format("%.2f €", c.totalEmployerCost));

        tvLegendNet.setText(String.format("%.2f", c.netSalary));
        tvLegendSocial.setText(String.format("%.2f", c.socialTaxEmployer));
        tvLegendTax.setText(String.format("%.2f", c.incomeTax));
        tvLegendContrib.setText(String.format("%.2f", c.pension + c.unemploymentEmployee));

        double[] amounts = {
            c.socialTaxEmployer,
            c.unemploymentEmployer,
            c.pension,
            c.unemploymentEmployee,
            c.incomeTax,
            c.netSalary
        };
        for (int i = 0; i < 6; i++) {
            rowAmounts[i].setText(String.format("%.2f €", amounts[i]));
            rowPcts[i].setText(String.format("%.2f%%", amounts[i] / pct));
            int maxBarPx = (int)(80 * getResources().getDisplayMetrics().density);
            android.view.ViewGroup.LayoutParams lp = rowBars[i].getLayoutParams();
            lp.width = (int)(maxBarPx * amounts[i] / c.totalEmployerCost);
            rowBars[i].setBackgroundColor(rowDotColors[i]);
            rowBars[i].setLayoutParams(lp);
        }

        updatePieChart(c);
        animateResultsEntrance();
    }

    protected void updatePieChart(SalaryCalculator c) {
        double employeeContribs = c.pension + c.unemploymentEmployee;
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) c.netSalary,         "net"));
        entries.add(new PieEntry((float) c.socialTaxEmployer, "social"));
        entries.add(new PieEntry((float) c.incomeTax,         "tax"));
        entries.add(new PieEntry((float) employeeContribs,    "contrib"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(
            Color.parseColor("#00FF87"),
            Color.parseColor("#6366F1"),
            Color.parseColor("#EF4444"),
            Color.parseColor("#F59E0B")
        );
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(2f);

        pieChart.setData(new PieData(dataSet));

        double netPct = c.netSalary / c.totalEmployerCost * 100.0;
        pieChart.setCenterText(String.format("%.1f%%\nneto", netPct));

        pieChart.animateY(800, Easing.EaseInOutCubic);
        pieChart.invalidate();
    }

    // Placeholder — implemented in Task 7
    protected void animateResultsEntrance() {
        cardResults.setVisibility(View.VISIBLE);
    }
}
