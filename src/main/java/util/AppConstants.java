package util;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AppConstants {

    private static final String operatinSystemSystem = System.getProperty("os.name");

    public static final Scanner SCANNER = getScanner();
    public static final String PASSWORD = "";
    public static final String USER = "";

    public static final String DRIVERPATH = getDriverpath();
    public static final String ORIGINIMAGESFOLDER = "/home/eduardo/Documentos/projetos/sifamaSources/imageszipped";
    public static final String IMGPATHGPS = getImageGpsPath();
    public static final String IMGPATH = getImagePath();
    public static final String SPREADSHEETPATH = getSpreadsheetPath();


    public static final String HORACAMPO = "ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_txtHoraVerificacao";
    public static final String DATACAMPONAME = "ctl00$ctl00$ctl00$ContentPlaceHolderCorpo$ContentPlaceHolderCorpo$ContentPlaceHolderCorpo$txtDataVerificacao";
    public static final String ATENDIDOCAMPOSELECT = "ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ddlResultadoAnaliseExecucao";

    public static final String IFRAMEOBS = "ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ucEditorHTMLObservacaoExecucao_txbComEditorHTML_ifr";
    public static final String OBSCAMPO = "tinymce";
    public static final String PASSWORDCAMPO = "ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_SenhaCertificadoDigital_txbSenhaCertificadoDigital";
    public static final String SALVARBUTTON = "ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_ContentPlaceHolderCorpo_btnSalvar";


    private static String getDriverpath() {
        if (operatinSystemSystem.toLowerCase().contains("linux")) {
            return "/home/eduardo/automation/chromedriver";
        } else if (operatinSystemSystem.toLowerCase().contains("windows")) {
            return "D:\\chromedriver.exe";
        }
        return "Error";
    }

    private static String getImagePath() {
        if (operatinSystemSystem.toLowerCase().contains("linux")) {
            return "/home/eduardo/Documentos/projetos/sifamaSources/images";
        } else if (operatinSystemSystem.toLowerCase().contains("windows")) {
            return "D:\\sifamadocs\\imagens";
        }
        return "Error";
    }

    private static String getImageGpsPath() {
        if (operatinSystemSystem.toLowerCase().contains("linux")) {
            return "/home/eduardo/Documentos/projetos/sifamaSources/imageGPS";
        } else if (operatinSystemSystem.toLowerCase().contains("windows")) {
            return "D:\\sifamadocs\\imagensGPS";
        }
        return "Error";
    }

    private static String getSpreadsheetPath() {
        if (operatinSystemSystem.toLowerCase().contains("linux")) {
            return "/home/eduardo/Documentos/projetos/sifamaSources/verificacao.xlsx";
        } else if (operatinSystemSystem.toLowerCase().contains("windows")) {
            return "D:\\sifamadocs\\planilha\\verificacao.xlsx";
        }
        return "Error";
    }

    private static Scanner getScanner() {
//        Charset charsetUTF = StandardCharsets.UTF_8;
        if (operatinSystemSystem.toLowerCase().contains("linux")) {
            Charset charsetUTF = StandardCharsets.UTF_8;
            return new Scanner(new InputStreamReader(System.in, charsetUTF));
        }
        Charset charsetIBM = Charset.forName("IBM850");
        return new Scanner(new InputStreamReader(System.in, charsetIBM));
    }



}
