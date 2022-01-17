package icpgetacs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Tiago M
 */
public class OpenSSLFilesThread extends Thread {
	// nota: lembrando que o readLine() do BufferedReader pula linha apos a leitura
	// !!!!!!
	private IFutureCallback callback;

	private String pastaDosACs;
	private String[] filesName;

	public OpenSSLFilesThread(String pastaDosACs, String[] filesName) {
		this.pastaDosACs = pastaDosACs;
		this.filesName = filesName;
	}

	public void onFutureCallback(IFutureCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		System.out.println("Iniciando OpenSSLFilesThread");
		try {
			String linhaParaEscrever;
			File arquivoResultado =new File(this.pastaDosACs + "\\ACsData.txt");

			FileWriter escreveNoArquivo = new FileWriter(arquivoResultado);
			System.out.println(arquivoResultado);
			BufferedWriter escritor = new BufferedWriter(escreveNoArquivo);
			for (String file : filesName) {
				String caminhoArquivoComAspas = "\"" + pastaDosACs+"\\Certificadaos\\" + file + "\"";
				Process sha1 = Runtime.getRuntime().exec(
						"cmd /c openssl x509 -noout -fingerprint -sha1 -inform pem -in " + caminhoArquivoComAspas);
				Process dataVenc = Runtime.getRuntime()
						.exec("cmd /c openssl x509 -enddate -noout -in " + caminhoArquivoComAspas);
				BufferedReader stdInputSha = new BufferedReader(new InputStreamReader(sha1.getInputStream()));
				BufferedReader stdInputData = new BufferedReader(new InputStreamReader(dataVenc.getInputStream()));
				if ((linhaParaEscrever = stdInputSha.readLine()) != null) {
					escritor.newLine();
					escritor.append("File name	->	" + file);
					escritor.newLine();
					escritor.append("Impresão digital	->	" + linhaParaEscrever);
					escritor.newLine();
				}
				if ((linhaParaEscrever = stdInputData.readLine()) != null) {
					escritor.append("Data vencimento	->	" + linhaParaEscrever);
					escritor.newLine();
				}
				stdInputSha.close();
				stdInputData.close();

			}
			escritor.close();

			callback.onSuccess("Finalizado OpenSSLFilesThread");
		} catch (Exception ex) {
			if (callback != null) {
				callback.onError(ex);
			}
		}
	}

}
