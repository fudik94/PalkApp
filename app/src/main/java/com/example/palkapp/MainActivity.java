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
            put("net", "Чистыми");
            put("gross", "Брутто");
            put("your_contributions", "Ваши взносы");
            put("employer_contributions", "Взносы работодателя");
            put("income_tax", "Подоходный налог");
            put("net_income", "Чистый доход");
            put("footer", "© 2025 PalkApp. Все права защищены.\nВопросы или предложения: palkapp.info@gmail.com");
            put("error", "Ошибка: введите число");
            put("result_title", "Результаты расчета чистой зарплаты");
        }});

        // Английский
        translations.put("en", new HashMap<>() {{
            put("input_hint", "Enter gross salary (€)");
            put("calculate", "Calculate");
            put("net", "Net salary");
            put("gross", "Gross salary");
            put("your_contributions", "Your contributions");
            put("employer_contributions", "Contributions paid by the employer");
            put("income_tax", "Your income tax");
            put("net_income", "Net income");
            put("footer", "© 2025 PalkApp. All rights reserved.\nQuestions or feedback: palkapp.info@gmail.com");
            put("error", "Error: enter a number");
            put("result_title", "Results of net salary calculation");
        }});

        // Эстонский
        translations.put("et", new HashMap<>() {{
            put("input_hint", "Sisesta brutopalk (€)");
            put("calculate", "Arvuta");
            put("net", "Netopalk");
            put("gross", "Brutopalk");
            put("your_contributions", "Teie maksed");
            put("employer_contributions", "Tööandja maksed");
            put("income_tax", "Tulumaks");
            put("net_income", "Netosissetulek");
            put("footer", "© 2025 PalkApp. Kõik õigused kaitstud.\nKüsimused või tagasiside: palkapp.info@gmail.com");
            put("error", "Viga: sisesta number");
            put("result_title", "Netopalga arvutuse tulemused");
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

            // Ваши взносы
            double pensionContribution = gross * 0.02;
            double unemploymentInsurance = gross * 0.016;
            double yourContributions = pensionContribution + unemploymentInsurance;

            // Налог на доход
            double taxFreeIncome = gross <= 1200 ? 654 : 654 - (gross - 1200) * 654.0 / 900;
            double taxableIncome = Math.max(0, gross - taxFreeIncome);
            double incomeTax = taxableIncome * 0.20;

            // Взносы работодателя
            double employerContributions = gross * 0.33;

            // Чистая зарплата
            double netSalary = gross - incomeTax - yourContributions;

            // Формируем текст результата на выбранном языке
            String result = t.get("result_title") + "\n\n" +
                    t.get("net") + ": " + String.format("%,.2f", netSalary) + " EUR\n" +
                    t.get("gross") + ": " + String.format("%,.2f", gross) + " EUR\n" +
                    t.get("your_contributions") + ": " + String.format("%,.2f", yourContributions) + " EUR\n" +
                    t.get("employer_contributions") + ": " + String.format("%,.2f", employerContributions) + " EUR\n" +
                    t.get("income_tax") + ": " + String.format("%,.2f", incomeTax) + " EUR\n" +
                    t.get("net_income") + ": " + String.format("%,.2f", netSalary) + " EUR";

            resultText.setText(result);

        } catch (NumberFormatException e) {
            resultText.setText(t.get("error"));
        }
    }
}
