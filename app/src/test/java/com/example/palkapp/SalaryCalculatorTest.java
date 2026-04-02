package com.example.palkapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class SalaryCalculatorTest {

    @Test
    public void testGross1000_matchesReferenceValues() {
        SalaryCalculator c = new SalaryCalculator(1000.0);
        assertEquals(20.0,     c.pension,              0.01);
        assertEquals(16.0,     c.unemploymentEmployee, 0.01);
        assertEquals(58.08,    c.incomeTax,            0.01);
        assertEquals(905.92,   c.netSalary,            0.01);
        assertEquals(330.0,    c.socialTaxEmployer,    0.01);
        assertEquals(8.0,      c.unemploymentEmployer, 0.01);
        assertEquals(1338.0,   c.totalEmployerCost,    0.01);
    }

    @Test
    public void testBelowTaxFreeMinimum_noIncomeTax() {
        // gross=700: taxable = max(0, 700-14-11.2-700) = 0
        SalaryCalculator c = new SalaryCalculator(700.0);
        assertEquals(0.0,   c.incomeTax, 0.01);
        assertEquals(674.8, c.netSalary, 0.01);
    }

    @Test
    public void testZeroGross() {
        SalaryCalculator c = new SalaryCalculator(0.0);
        assertEquals(0.0, c.netSalary,        0.01);
        assertEquals(0.0, c.totalEmployerCost, 0.01);
        assertEquals(0.0, c.incomeTax,         0.01);
    }

    @Test
    public void testHighSalary_fullTax() {
        // gross=3000: taxable = 3000 - 60 - 48 - 700 = 2192
        // incomeTax = 2192 * 0.22 = 482.24
        // net = 3000 - 60 - 48 - 482.24 = 2409.76
        SalaryCalculator c = new SalaryCalculator(3000.0);
        assertEquals(60.0,    c.pension,              0.01);
        assertEquals(48.0,    c.unemploymentEmployee, 0.01);
        assertEquals(482.24,  c.incomeTax,            0.01);
        assertEquals(2409.76, c.netSalary,            0.01);
    }
}
