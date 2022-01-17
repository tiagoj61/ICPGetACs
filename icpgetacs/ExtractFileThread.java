package icpgetacs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
*
* @author Tiago M
*/

public class ExtractFileThread extends Thread {
	private IFutureCallback callback;

	private String pastaDosACs;

	public ExtractFileThread(String pastaDosACs) {
		this.pastaDosACs = pastaDosACs;
	}

	public void onFutureCallback(IFutureCallback callback) {
		this.callback = callback;
	}

	@Override
	public void run() {
		System.out.println("Iniciando -> ExtractFiles");
		try {
			/* via cmd - Process extractCertificados = Runtime.getRuntime()
					.exec("cmd /c cd " + localFolderCertificados + " && jar xf " + localDoZip);
				*/
			unzip();
				
			callback.onSuccess("Finalizado -> ExtractFiles");
		} catch (Exception ex) {
			if (callback != null) {
				callback.onError(ex);
			}
		}
	}
	   private void unzip() {
	        File pastaCertificados = new File(pastaDosACs+"\\Certificadaos");
	        FileInputStream leitor;
	        byte[] buffer = new byte[1024];
	        int len;
	        try {
	            leitor = new FileInputStream(pastaDosACs+"\\Certificados.zip");
	            ZipInputStream escritorZip = new ZipInputStream(leitor);
	            ZipEntry proximoZip = escritorZip.getNextEntry();
	            while(proximoZip != null){
	                String proximoArquivo = proximoZip.getName();
	                File novoArquivo = new File(pastaCertificados.toString()+ File.separator + proximoArquivo);
	                new File(novoArquivo.getParent()).mkdirs();
	                FileOutputStream escritor = new FileOutputStream(novoArquivo);
	             
	                while ((len = escritorZip.read(buffer)) > 0) {
	                escritor.write(buffer, 0, len);
	                }
	                escritor.close();
	                escritorZip.closeEntry();
	                proximoZip = escritorZip.getNextEntry();
	            }
	            escritorZip.closeEntry();
	            escritorZip.close();
	            leitor.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	    }

}
