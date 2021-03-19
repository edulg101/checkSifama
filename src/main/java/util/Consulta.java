package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Consulta {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public Consulta(WebDriver driver, WebDriverWait wait, JavascriptExecutor js) {
        this.driver = driver;
        this.wait = wait;
        this.js = js;
    }

    public Consulta() {
    }

    public void changeValueInSelect(String id, String value) throws InterruptedException {

        Thread.sleep(500);

        waitForJStoLoad();

        WebElement processando = driver.findElement(By.id("Progress_LabelProcessando"));

        wait.until(ExpectedConditions.invisibilityOf(processando));

        waitForElementById(id);

        Select select = new Select(driver.findElement(By.id(id)));

        waitForJStoLoad();

        waitForOptionInSelect(select, id);

        while (true) {
            try {
                select.selectByValue(value);
                Thread.sleep(500);
                break;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("tentando novamente");
                e.printStackTrace();
                Thread.sleep(1000);
            }

        }
    }

    public void changeValueInSelectByTexts(String id, String kmInicial, String kmFinal) throws InterruptedException {

        Select select = new Select(driver.findElement(By.id(id)));
        while (true) {
            try {
                List<WebElement> elementList = select.getOptions();
                for (WebElement element : elementList) {
                    String elementStr = element.getText();
                    String str = kmInicial + " - " + kmFinal;
                    if (elementStr.contains(str)) {
                        select.selectByVisibleText(elementStr);
                    }
                }
                Thread.sleep(2000);
                break;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("tentando novamente");
                System.out.println(e.getMessage());
                waitToBeClickableAndClickById("ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_txtDescricaoOcorrencia");
                Thread.sleep(1000);
            }

        }
    }

    public void changeValueInSelectByTexts(String id, String text) throws InterruptedException {

        Select select = new Select(driver.findElement(By.id(id)));
        while (true) {
            try {
                List<WebElement> elementList = select.getOptions();
                for (WebElement element : elementList) {
                    String elementStr = element.getText();

                    if (elementStr.contains(text)) {
                        select.selectByVisibleText(elementStr);
                    }
                }
                Thread.sleep(2000);
                break;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("tentando novamente");
                System.out.println(e.getMessage());
                waitToBeClickableAndClickById("ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_txtDescricaoOcorrencia");
                Thread.sleep(1000);
            }

        }
    }

    public void enviaChaves(String id, String value) throws InterruptedException {

        waitForJQueryAndJS();
        driver.findElement(By.id(id)).sendKeys(value);
        waitForJQueryAndJS();
    }

    public void script(String id, String value) {

        js.executeScript("document.getElementById(arguments[0]).setAttribute('value', arguments[1])", id, value);
    }

    public void jqueryScript(String id, String value) throws InterruptedException {
        js.executeScript("$(`#${arguments[0]} option[value='${arguments[1]}']`).prop('selected', true)", id, value);
    }

    public void scriptToClick(String id){
        waitForJQueryAndJS();
        waitForElementById(id);
        js.executeScript("document.getElementById(arguments[0]).click()", id);
        waitForJQueryAndJS();
    }



    public void jqueryScriptWithChange(String id, String value) throws InterruptedException {
        js.executeScript("$(`#${arguments[0]} option[value='${arguments[1]}']`).prop('selected', true).change()", id, value);
        WebElement processando = driver.findElement(By.id("Progress_LabelProcessando"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Progress_LabelProcessando")));
        wait.until(ExpectedConditions.invisibilityOf(processando));
        Thread.sleep(500);
    }

    public void selectSctipt(String id, String value) {
        js.executeScript("document.getElementById(arguments[0]).getElementsByTagName('option')[1].selected = 'selected'", id);
    }

    public void waitToBeClickableAndClickById(String id) {
        try {
            waitForJStoLoad();
            waitForJQueryToLoad();
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
            driver.findElement(By.id(id)).click();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("esperei e nao clicou");
        }

    }

    public void waitToBeClickableButDontClickById(String id) {
        try {
            waitForJQueryAndJS();
            wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("esperei e nao clicou");
        }

    }

    public void waitAndClickElementByClassName(String className) throws InterruptedException {
        waitForJStoLoad();
        waitForJQueryToLoad();
        wait.until(ExpectedConditions.elementToBeClickable(By.className(className)));
        Thread.sleep(500);
        driver.findElement(By.className(className)).click();
    }




    public void waitForElementById(String id) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("esperei e nao clicou");
        }
    }

    public void waitForOptionInSelect(Select select, String id) throws InterruptedException {
        waitForJStoLoad();
        int i = 0;
        while (select.getOptions().size() < 2) {
            Thread.sleep(500);
            i++;
            if (i > 80) {
                System.out.println("Aguardei mais de 40 segundos as oções " + select.toString());
                System.out.println("Execute novamente o programa, Tchau !");
                System.out.close();
            }
        }

    }

    private boolean waitForJStoLoad() {

        js.executeScript("console.log(document.readyState)");
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                for(int i = 0; i < 5 ; i++) {
                    try {
                        return ((Long) js.executeScript("return jQuery.active") == 0);
                    } catch (Exception e) {
                        System.out.println("tentou vez " + i + "jQuery");
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        };


        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    public void waitForProcessBar() throws InterruptedException {

        WebDriverWait longWait = new WebDriverWait(driver, 55000);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Progress_LabelProcessando")));
        } catch (NoSuchElementException e){
            System.out.println("consulta linha 209");
        }
        Thread.sleep(500);
        boolean processDisplayed = false;
        try {
            processDisplayed = driver.findElement(By.id("Progress_LabelProcessando")).isDisplayed();
        }
        catch (StaleElementReferenceException e){
            System.out.println("deu rolo no process displayed");
        }
        if(processDisplayed) {
            longWait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("Progress_LabelProcessando"))));
        }
        Thread.sleep(500);

    }

    private boolean waitForJQueryToLoad() {

        WebDriverWait longWait = new WebDriverWait(driver, 120);

        try {
           js.executeScript("console.log(jQuery.active)");
       }
       catch (org.openqa.selenium.JavascriptException e){
           System.out.println("não aceitou jquery print");
       }

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) js.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    System.out.println("exceção");
                    return true;
                }
            }

        };
        return longWait.until(jQueryLoad);
    }

    public void waitForJQueryAndJS(){
        waitForJQueryToLoad();
        waitForJStoLoad();
    }

    public void consoleLog(String message){
        js.executeScript("console.log(arguments[0])", message);
    }

}
