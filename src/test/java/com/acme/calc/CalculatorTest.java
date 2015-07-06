package com.acme.calc;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

	private Calculator calculator = new Calculator();

	@Test
	public void additionShouldReturnCorrectResult() {
		// given
		double firstNumber = 5.0;
		double secondNumber = 6.0;
		// when
		Double result = calculator.add(firstNumber, secondNumber);
		// then
		Assert.assertTrue(result == 11);
		
	}

	@Test
	public void subtractionShouldReturnCorrectResult() {
		double firstNumber = 5.0;
		double secondNumber = 6.0;
		// when
		Double result = calculator.subtract(secondNumber, firstNumber);
		// then
		Assert.assertTrue(result == 1);	
	}
	@Test
	public void multiplyShouldReturnCorrectResult() {
		double firstNumber = 5.0;
		double secondNumber = 6.0;
		// when
		Double result = calculator.multiply(firstNumber, secondNumber);
		// then
		Assert.assertTrue(result == 30);	
	}
	@Test
	public void DivideShouldReturnCorrectResult() {
		double firstNumber = 6.0;
		double secondNumber = 6.0;
		// when
		Double result = calculator.divide(firstNumber, secondNumber);
		// then
		Assert.assertTrue(result == 1.0);	
	}

}
