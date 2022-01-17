package icpgetacs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author Tiago M
 */
public class ZipFromICPThread extends Thread {

	private IFutureCallback callback;
	private String pastaDosACs;
	private String localDoChromeDrive;
	private String linkAtualDaPagICP;

	public ZipFromICPThread(String pastaDosACs, String localDoChromeDrive, String linkAtualDaPagICP) {
		this.pastaDosACs = pastaDosACs;
		this.localDoChromeDrive = localDoChromeDrive;
		this.linkAtualDaPagICP = linkAtualDaPagICP;
	}

	public void onFutureCallback(IFutureCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		System.out.println("Iniciando ZipFromICPThread");
		try {

			String localDoWebDriver = localDoChromeDrive;
			String linkDoSite = linkAtualDaPagICP;
			String localDoHref = "tbody tr:nth-child(3) a";
			String localDoZip = pastaDosACs + "\\Certificados.zip";
			File zip = new File(localDoZip);

			System.setProperty("webdriver.chrome.driver", localDoWebDriver);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("disable-gpu");
			options.addArguments("no-sandbox");
			options.addArguments("window-size=1980,1080");
			WebDriver driver = new ChromeDriver(options);
			driver.get(linkDoSite);

			WebElement element = driver.findElement(By.cssSelector(localDoHref));
			String linkDoDownload = element.getAttribute("href");
			element.sendKeys(Keys.ENTER);
			driver.close();
			InputStream inputStream = new URL(linkDoDownload).openStream();
			FileOutputStream fileOS = new FileOutputStream(localDoZip);
			IOUtils.copy(inputStream, fileOS);

			fileOS.close();
			inputStream.close();

			callback.onSuccess("Finalizado ZipFromICPThread");
		} catch (Exception ex) {
			if (callback != null) {
				callback.onError(ex);
			}
		}
	}

}
