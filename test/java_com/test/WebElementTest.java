package com.test;

import static com.core.DriverFactory.getDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.core.DriverFactory;

public class WebElementTest {		
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		getDriver().get("https://antoniotrindade.com.br/treinoautomacao/elementsweb.html");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		DriverFactory.killDriver();
	}
			
	@Test
	public void testInputTextFieldHelloWorld() {
		//identifica o elemento
		WebElement textField = getDriver().findElement(By.xpath("//input[@name='txtbox1']"));	
		//envia um valor de texto para o elemento		
		textField.sendKeys("Hello World!!");				
		
		//valida o resultado esperado é igual ao resultado atual
		assertEquals("Hello World!!", textField.getAttribute("value"));
	}
	
	@Test
	public void testValidaHabilitadoTextFields() {
		WebElement textField1 = getDriver().findElement(By.name("txtbox1"));
		WebElement textField2 = getDriver().findElement(By.name("txtbox2"));
		
		//elemento está habilitado?
		assertTrue(textField1.isEnabled());
		//elemento está desabilitado?
		assertFalse(textField2.isEnabled());					
	}
	
	@Test
	public void testValidaRadioButton() {
		//mapeia
		List<WebElement> radios = getDriver().findElements(By.name("radioGroup1"));	
		
		//faz a iteração
		for (WebElement radio : radios) {
			if (radio.getAttribute("value").equals("Radio 3")) {
				radio.click();
			}			
		}		
		//Outra forma de fazer por posição
		//radios.get(2).click();
				
		//faz a validação
		assertTrue(radios.get(2).isSelected());
		
		assertFalse(radios.get(0).isSelected());
		assertFalse(radios.get(1).isSelected());
		assertFalse(radios.get(3).isSelected());		
	}
	
	@Test
	public void testValidaCheckBox() {
		//mapeia
		List<WebElement> checkBoxes = getDriver().findElements(By.name("chkbox"));
		
		//valida se a lista tem 4 elementos
		assertEquals(4, checkBoxes.size());

		//faz a iteração com elemnto
		for (WebElement checkBox : checkBoxes) {
			if (checkBox.getAttribute("value").equals("Check 3") || 
					(checkBox.getAttribute("value").equals("Check 4"))) {
				checkBox.click();
			}			
		}		
				
		//faz a validação
		assertTrue(checkBoxes.get(2).isSelected());
		assertTrue(checkBoxes.get(3).isSelected());
		
		assertFalse(checkBoxes.get(0).isSelected());
		assertFalse(checkBoxes.get(1).isSelected());		
	}
	
	@Test
	public void testValidaSelectSingle() throws InterruptedException {
		//mapeia o elemento
		WebElement dropdownSingle = getDriver().findElement(By.name("dropdownlist"));		
		Select selectSingle = new Select(dropdownSingle);
		
		//faz a iteração
		selectSingle.selectByIndex(5);		
		selectSingle.selectByVisibleText("Item 7");
		
		//faz a validação
		assertEquals("Item 7", selectSingle.getFirstSelectedOption().getText());
	}
	
	@Test
	public void testValidaSelectMulti() {
		WebElement dropdownMulti = getDriver().findElement(By.name("multiselectdropdown"));
		
		Select selectMulti = new Select(dropdownMulti);
		
		selectMulti.selectByVisibleText("Item 5");
		selectMulti.selectByVisibleText("Item 8");
		selectMulti.selectByVisibleText("Item 9");				
		
		List<WebElement> optionsSelected = selectMulti.getAllSelectedOptions();
		
		//Valida 3 selecionados // Quantos
		assertEquals(3, optionsSelected.size());
		
		//Quais
		assertEquals("Item 5", optionsSelected.get(0).getText());
		assertEquals("Item 8", optionsSelected.get(1).getText());
		assertEquals("Item 9", optionsSelected.get(2).getText());
		
		selectMulti.deselectByVisibleText("Item 8");
		
		optionsSelected = selectMulti.getAllSelectedOptions();
		
		//Quantos
		assertEquals(2, optionsSelected.size());
		///Quais
		assertEquals("Item 5", optionsSelected.get(0).getText());
		assertEquals("Item 9", optionsSelected.get(1).getText());
	}
	
	@Test
	public void testValidaAlert() throws InterruptedException {
		WebElement btnAlert = getDriver().findElement(By.name("alertbtn"));
		btnAlert.click();
		
		Alert alert = getDriver().switchTo().alert();
		assertEquals("Eu sou um alerta!", alert.getText());
				
		alert.accept();			
		
	}
	
	@Test
	public void testValidaPrompt() throws InterruptedException {
		WebElement btnPrompt = getDriver().findElement(By.id("promptBtn"));
		btnPrompt.click();
		
		Alert alert01 = getDriver().switchTo().alert();
		assertEquals("Digite o ano:", alert01.getText());	
		alert01.sendKeys("2024");		
		
		alert01.accept();
		
		Alert alert02 = getDriver().switchTo().alert();
		assertEquals("O ano é 2024?", alert02.getText());		
		
		alert02.accept();
		
		Alert alert03 = getDriver().switchTo().alert();
		assertEquals("Feito!", alert03.getText());
		
		alert03.accept();				
	}
	
	@Test
	public void testValidaPromptCancel() throws InterruptedException {
		WebElement btnPrompt = getDriver().findElement(By.id("promptBtn"));
		btnPrompt.click();
		
		Alert alert01 = getDriver().switchTo().alert();
		assertEquals("Digite o ano:", alert01.getText());	
		alert01.sendKeys("2024");
			
		
		alert01.accept();
		
		Alert alert02 = getDriver().switchTo().alert();
		assertEquals("O ano é 2024?", alert02.getText());
		
		
		alert02.dismiss();
		
		Alert alert03 = getDriver().switchTo().alert();
		assertEquals("Nada feito!", alert03.getText());
		
		alert03.accept();				
	}	
	
	@Test
	public void testValidaIframe() {
		//Entra no iframe
		getDriver().switchTo().frame("frame1");
		
		WebElement tfIframe = getDriver().findElement(By.id("tfiframe"));
		tfIframe.sendKeys("Automação de testes com WebDriver");
		
		assertEquals("Automação de testes com WebDriver", tfIframe.getAttribute("value"));
		
		//Volta o foco do driver para a origem
		getDriver().switchTo().defaultContent();
	}
}