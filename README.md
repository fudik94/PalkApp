# PalkApp — Estonian Salary Calculator

Android application for calculating net salary in Estonia. Enter your gross salary and instantly see the full tax breakdown with an animated chart.

---

## Features

- **3 languages** — Russian, English, Estonian
- **Estonia 2026 tax rules** — income tax 22%, pension 2%, unemployment insurance 1.6%
- **Animated donut chart** — visual breakdown of net pay vs taxes
- **Full breakdown table** — each deduction shown with amount and percentage
- **Employer cost** — total cost to employer including social tax (33%) and unemployment (0.8%)
- **Glassmorphism UI** — dark green theme with frosted glass cards

---

## Tax Calculation (Estonia 2026)

| Component | Rate |
|---|---|
| Income tax | 22% |
| Tax-free minimum | €700 flat |
| Pension (employee) | 2% |
| Unemployment insurance (employee) | 1.6% |
| Social tax (employer) | 33% |
| Unemployment insurance (employer) | 0.8% |

**Formula:**
```
Taxable income = Gross − Pension − Unemployment(employee) − €700
Net salary = Gross − Pension − Unemployment(employee) − Income tax
Total employer cost = Gross + Social tax + Unemployment(employer)
```

---

## Tech Stack

- **Language:** Java
- **Platform:** Android (min SDK 24 / Android 7.0)
- **Chart:** MPAndroidChart 3.1.0
- **Build:** Gradle + AGP

---

## Build

```bash
./gradlew assembleDebug
```

---

## Author

Fuad Ismayilbayli
