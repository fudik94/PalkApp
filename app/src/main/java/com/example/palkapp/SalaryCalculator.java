package com.example.palkapp;

public class SalaryCalculator {

    public static final double PENSION_RATE               = 0.02;
    public static final double UNEMPLOYMENT_EMPLOYEE_RATE = 0.016;
    public static final double INCOME_TAX_RATE            = 0.22;
    public static final double TAX_FREE_MINIMUM           = 700.0;
    public static final double SOCIAL_TAX_EMPLOYER_RATE   = 0.33;
    public static final double UNEMPLOYMENT_EMPLOYER_RATE = 0.008;

    public final double gross;
    public final double pension;
    public final double unemploymentEmployee;
    public final double incomeTax;
    public final double netSalary;
    public final double socialTaxEmployer;
    public final double unemploymentEmployer;
    public final double totalEmployerCost;

    public SalaryCalculator(double gross) {
        this.gross                = gross;
        this.pension              = gross * PENSION_RATE;
        this.unemploymentEmployee = gross * UNEMPLOYMENT_EMPLOYEE_RATE;
        double taxable            = Math.max(0, gross - pension - unemploymentEmployee - TAX_FREE_MINIMUM);
        this.incomeTax            = taxable * INCOME_TAX_RATE;
        this.netSalary            = gross - pension - unemploymentEmployee - incomeTax;
        this.socialTaxEmployer    = gross * SOCIAL_TAX_EMPLOYER_RATE;
        this.unemploymentEmployer = gross * UNEMPLOYMENT_EMPLOYER_RATE;
        this.totalEmployerCost    = gross + socialTaxEmployer + unemploymentEmployer;
    }
}
