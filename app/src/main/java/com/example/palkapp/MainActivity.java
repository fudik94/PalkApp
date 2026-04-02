//btw its my first amateur project thats why dont judge!bir sozle qinamayin!
package com.example.palkapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText salaryInput;
    private Button calcButton, langRu, langEn, langEt;
    private TextView resultText, footerText;

    private String currentLang = "et"; // Эстонский язык по умолчанию
    private HashMap<String, HashMap<String, String>> translations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salaryInput = findViewById(R.id.salaryInput);
        calcButton = findViewById(R.id.calcButton);
        langRu = findViewById(R.id.langRu);
        langEn = findViewById(R.id.langEn);
        langEt = findViewById(R.id.langEt);
        resultText = findViewById(R.id.resultText);
        footerText = findViewById(R.id.footerText);

        initTranslations();
        changeLanguage(currentLang);

        // Переключение языка
        langRu.setOnClickListener(v -> changeLanguage("ru"));
        langEn.setOnClickListener(v -> changeLanguage("en"));
        langEt.setOnClickListener(v -> changeLanguage("et"));

        // Кнопка расчета
        calcButton.setOnClickListener(v -> calculateSalary());
    }

    private void initTranslations() {
        // Русский
        translations.put("ru", new HashMap<>() {{
            put("input_hint", "Введите брутто-зарплату (€)");
            put("calculate", "Рассчитать");
            put("total_employer_cost", "Сумма затрат работодателя");
            put("social_tax_employer", "Социальный налог (работодатель, 33%)");
            put("unemployment_employer", "Страхование от безработицы (работодатель, 0.8%)");
            put("gross", "Брутто-зарплата");
            put("pension", "Накопительная пенсия (II ступень, 2%)");
            put("unemployment_employee", "Страхование от безработицы (работник, 1.6%)");
            put("income_tax", "Подоходный налог (22%)");
            put("net", "Нетто-зарплата");
            put("footer", "© 2026 PalkApp. Все права защищены.\nВопросы или предложения: palkapp.info@gmail.com");
            put("error", "Ошибка: введите число");
            put("result_title", "Результаты расчета");
        }});

        // Английский
        translations.put("en", new HashMap<>() {{
            put("input_hint", "Enter gross salary (€)");
            put("calculate", "Calculate");
            put("total_employer_cost", "Total employer cost");
            put("social_tax_employer", "Social tax (employer, 33%)");
            put("unemployment_employer", "Unemployment insurance (employer, 0.8%)");
            put("gross", "Gross salary");
            put("pension", "Funded pension (2nd tier, 2%)");
            put("unemployment_employee", "Unemployment insurance (employee, 1.6%)");
            put("income_tax", "Income tax (22%)");
            put("net", "Net salary");
            put("footer", "© 2026 PalkApp. All rights reserved.\nQuestions or feedback: palkapp.info@gmail.com");
            put("error", "Error: enter a number");
            put("result_title", "Calculation results");
        }});

        // Эстонский
        translations.put("et", new HashMap<>() {{
            put("input_hint", "Sisesta brutopalk (€)");
            put("calculate", "Arvuta");
            put("total_employer_cost", "Tööandja kulud kokku");
            put("social_tax_employer", "Sotsiaalmaks (tööandja, 33%)");
            put("unemployment_employer", "Töötuskindlustus (tööandja, 0.8%)");
            put("gross", "Brutopalk");
            put("pension", "Kogumispension (II sammas, 2%)");
            put("unemployment_employee", "Töötuskindlustus (töötaja, 1.6%)");
            put("income_tax", "Tulumaks (22%)");
            put("net", "Netopalk");
            put("footer", "© 2026 PalkApp. Kõik õigused kaitstud.\nKüsimused või tagasiside: palkapp.info@gmail.com");
            put("error", "Viga: sisesta number");
            put("result_title", "Arvutuse tulemused");
        }});
    }

    private void changeLanguage(String lang) {
        currentLang = lang;
        HashMap<String, String> t = translations.get(lang);

        salaryInput.setHint(t.get("input_hint"));
        calcButton.setText(t.get("calculate"));
        resultText.setText("");
        footerText.setText(t.get("footer"));
    }

    private void calculateSalary() {
        HashMap<String, String> t = translations.get(currentLang);

        try {
            double gross = Double.parseDouble(salaryInput.getText().toString());

            // Взносы работника (вычитаются до расчёта подоходного налога)
            double pension = gross * 0.02;           // 2%
            double unemploymentEmployee = gross * 0.016; // 1.6%

            // Необлагаемый минимум 2026: €700/мес фиксированно
            double taxFreeMinimum = 700.0;

            // Налогооблагаемая база = брутто − пенсия − безработица − вычет
            double taxableIncome = Math.max(0, gross - pension - unemploymentEmployee - taxFreeMinimum);
            double incomeTax = taxableIncome * 0.22; // 22%

            // Нетто = брутто − пенсия − безработица − подоходный
            double netSalary = gross - pension - unemploymentEmployee - incomeTax;

            // Взносы работодателя
            double socialTaxEmployer = gross * 0.33;       // 33%
            double unemploymentEmployer = gross * 0.008;   // 0.8%
            double totalEmployerCost = gross + socialTaxEmployer + unemploymentEmployer;

            // Процент от затрат работодателя
            double pct = totalEmployerCost / 100.0;

            String result = t.get("result_title") + "\n\n" +
                    t.get("total_employer_cost") + ": " + String.format("%.2f", totalEmployerCost) + " EUR\n" +
                    t.get("social_tax_employer") + ": " + String.format("%.2f", socialTaxEmployer) + " EUR  (" + String.format("%.2f", socialTaxEmployer / pct) + "%)\n" +
                    t.get("unemployment_employer") + ": " + String.format("%.2f", unemploymentEmployer) + " EUR  (" + String.format("%.2f", unemploymentEmployer / pct) + "%)\n" +
                    t.get("gross") + ": " + String.format("%.2f", gross) + " EUR\n" +
                    t.get("pension") + ": " + String.format("%.2f", pension) + " EUR  (" + String.format("%.2f", pension / pct) + "%)\n" +
                    t.get("unemployment_employee") + ": " + String.format("%.2f", unemploymentEmployee) + " EUR  (" + String.format("%.2f", unemploymentEmployee / pct) + "%)\n" +
                    t.get("income_tax") + ": " + String.format("%.2f", incomeTax) + " EUR  (" + String.format("%.2f", incomeTax / pct) + "%)\n" +
                    t.get("net") + ": " + String.format("%.2f", netSalary) + " EUR  (" + String.format("%.2f", netSalary / pct) + "%)";

            resultText.setText(result);

        } catch (NumberFormatException e) {
            resultText.setText(t.get("error"));
        }
    }
}
