import org.apache.cxf.common.util.SortedArraySet;
import org.apache.tika.exception.TikaException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xml.sax.SAXException;
import util.AppConstants;
import util.Consulta;
import util.Tika;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import util.AppConstants.*;

import static util.AppConstants.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, TikaException, IOException, SAXException, ParseException {

        System.out.println("porque essa merda está com mérdas na mão ? boçal ");
        System.out.println(Charset.defaultCharset().displayName());


        inicio();
    }

    public static void inicio() throws InterruptedException, TikaException, IOException, SAXException, ParseException {

        List<String[]> tros = Tika.getFromExcel();

        for (String[] x: tros){
            System.out.println(x[0]);
            System.out.println(x[1]);
            System.out.println(x[2]);
        }

        System.out.println("expressão corpórea");


    System.setProperty("webdriver.chrome.driver", AppConstants.DRIVERPATH);

    ChromeOptions option = new ChromeOptions();
        option.setHeadless(false);

    WebDriver driver = new ChromeDriver(option);

    WebDriverWait wait = new WebDriverWait(driver, 50);

    JavascriptExecutor js = (JavascriptExecutor) driver;

    Consulta consulta = new Consulta(driver, wait, js);

        System.out.println("abrindo chrome");

        driver.get("https://appweb1.antt.gov.br/fisn/Site/TRO/Cadastrar.aspx");
        System.out.println("abrindo pagina do Sifama");

    WebElement usuario = driver.findElement(By.id("ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_TextBoxUsuario"));
    WebElement senha = driver.findElement(By.id("ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_TextBoxSenha"));
    WebElement entrar = driver.findElement(By.id("ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ButtonOk"));

        usuario.sendKeys(AppConstants.USER);
        senha.sendKeys(AppConstants.PASSWORD);

        System.out.println("entrando com senha");

        entrar.click();

        consulta.waitForProcessBar();


        consulta.waitForJQueryAndJS();


        for (String[] tro: tros) {
            inicioVerificacao(driver, consulta, tro);
        }

}

    public static void inicioVerificacao(WebDriver driver, Consulta consulta, String[] tro) throws InterruptedException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

        Date trodateformated = sdfHora.parse(tro[1]);

        String today = sdf.format(new Date());

        String troNumber = tro[0];
        String troHora = sdfHora.format(trodateformated);

        System.out.println(troHora);

        String troText = tro[2];


        consulta.waitAndClickElementByClassName("wingsBtnMyTasks");
        Thread.sleep(1000);
        consulta.waitForJQueryAndJS();


        //click link dos tros pendentes

        driver.findElement(By.xpath("//td[contains(text() , 'Analisar Execução do')]")).click();

        Thread.sleep(1000);
        consulta.waitForJQueryAndJS();

        ///list
        List<WebElement> listaTros = driver.findElements(By.xpath("//div[@class='wingsDivNomeTarefa']"));
        SortedSet<String> listaTrosEmOrdem = new SortedArraySet<>();

        listaTros.forEach(x -> {
            listaTrosEmOrdem.add(x.getText().substring(25).trim());
        });

        System.out.println("Lista dos Tros disponíveis para análise em ordem Crescente:");

        listaTrosEmOrdem.forEach(System.out::println);

        for (WebElement x : listaTros) {
            if (x.getText().contains(troNumber + "2021")) {
                WebElement getTBody = x.findElement(By.xpath("../../.."));

                WebElement divToClick = getTBody.findElement(By.xpath("./tr[5]/td/div[2]"));
                divToClick.click();
            }
        }


        String mainWindow = driver.getWindowHandle();

        consulta.waitForJQueryAndJS();
        consulta.waitForProcessBar();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
            Thread.sleep(500);
        }

        consulta.waitForJQueryAndJS();

        Thread.sleep(700);
        consulta.waitForJQueryAndJS();
        Thread.sleep(1000);

        WebElement dataCampo = driver.findElement(By.name(AppConstants.DATACAMPONAME));

        System.out.println("Insere data");
        dataCampo.sendKeys(today);
        Thread.sleep(2000);

        consulta.waitToBeClickableAndClickById(AppConstants.HORACAMPO);
        consulta.waitForJQueryAndJS();

        consulta.waitToBeClickableAndClickById(AppConstants.HORACAMPO);
        consulta.waitForJQueryAndJS();

        System.out.println("insere hora");

        consulta.enviaChaves(AppConstants.HORACAMPO, troHora);


        consulta.waitForJQueryAndJS();



        System.out.println("marca como atendido");

        consulta.jqueryScript(AppConstants.ATENDIDOCAMPOSELECT, "2");
        Thread.sleep(3000);
        consulta.waitForJQueryAndJS();

        consulta.waitToBeClickableButDontClickById(PASSWORDCAMPO);
        consulta.scriptToClick(PASSWORDCAMPO);

        Thread.sleep(3000);

        System.out.println("Insere senha");

        for (int i = 0; i < 3; i++){

            driver.findElement(By.id(PASSWORDCAMPO)).clear();
            consulta.enviaChaves(PASSWORDCAMPO, PASSWORD);
            Thread.sleep(700);
            consulta.consoleLog("tentativa de senha");

        }


        Thread.sleep(500);
        consulta.waitForJQueryAndJS();

        driver.switchTo().frame(AppConstants.IFRAMEOBS);

        consulta.waitForJQueryAndJS();


        System.out.println("insere texto na Observação");

        Thread.sleep(3000);
        consulta.enviaChaves(AppConstants.OBSCAMPO, troText);

        driver.switchTo().defaultContent();
        Thread.sleep(500);
        consulta.waitForJQueryAndJS();

        System.out.println("Envia formulario");

        consulta.scriptToClick(SALVARBUTTON);
        Thread.sleep(1000);


        consulta.scriptToClick("MessageBox_ButtonOk");
        Thread.sleep(1000);
        System.out.println("salva Tro n. " + troNumber + "/2021");


        for (String windowHandle : driver.getWindowHandles()) {
            if (windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
            Thread.sleep(500);
        }

    }

}
