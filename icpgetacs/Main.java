package icpgetacs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils; 
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
	public static void main(String[] args) throws IOException {
		String[] nomesDosArquivosVencidos= {};

		String pastaDosACs = "D:\\Pc\\Downloads";
		String localDoChromeDrive = "D:\\chromedriver.exe";
		String linkAtualDaPagICP =  "https://antigo.iti.gov.br/repositorio/84-repositorio/489-certificados-das-acs-da-icp-brasil-arquivo-unico-compactado";
		ICPGetACs icpGetACs = new ICPGetACs(localDoChromeDrive,linkAtualDaPagICP,pastaDosACs,nomesDosArquivosVencidos);
		icpGetACs.extractFilesAndOpenssl();
	}
}