package mobi.audax.autadoc.icp.cert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import mobi.audax.autadoc.bean.ACsAutorizadasICP;

/**
 *
 * @author Tiago M
 */

public class ExtractSHAsFromFile {
	private String senha = "Ma13051997";
	private String caminhoArquivo = "Certisign_A1.pfx";
	private String caminhoCertificado = "D:/Downloads/";//pegar do propeties...

	public ExtractSHAsFromFile(String caminhoArquivo, String senha) {
		this.senha = senha;
		this.caminhoArquivo = caminhoArquivo;
	}

	public List<ACsAutorizadasICP> lerCertificadoCliente() {
		try {
			List<ACsAutorizadasICP> dadosCertificadoCliente = new ArrayList<ACsAutorizadasICP>();
			ACsAutorizadasICP entity = new ACsAutorizadasICP();
			File temp;
			String comando = "cmd /c D: && cd "+caminhoCertificado+" && echo " + senha + "| keytool -list -v -keystore "
					+ caminhoArquivo;
			String resultComando;

			Process process = Runtime.getRuntime().exec(comando);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((resultComando = stdInput.readLine()) != null) {
				if (resultComando.contains("Certificate[")) {
					entity.setNomeDoArquivoComExtensao(resultComando);
				} else if (resultComando.contains("Valid")) {
					entity.setNomeDoArquivoComExtensao(resultComando);
				} else if (resultComando.contains("SHA1")) {
					entity.setImpressaoDigital(resultComando);
					dadosCertificadoCliente.add(entity);
					entity = new ACsAutorizadasICP();
				}
			}
			stdInput.close();
			return dadosCertificadoCliente;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
