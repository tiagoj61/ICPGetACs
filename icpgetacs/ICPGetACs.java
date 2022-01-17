package icpgetacs;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Tiago M
 */
public class ICPGetACs {

	private String pastaDosACs;
	private String localDoChromeDrive;
	private String linkAtualDaPagICP;
	private File creatorFiles;
	private String[] filesName;

	public ICPGetACs(String localDoChromeDrive,String linkAtualDaPagICP,String pastaDosACs, String[] filesName) throws IOException {
		this.localDoChromeDrive = localDoChromeDrive;
		this.linkAtualDaPagICP = linkAtualDaPagICP;
		this.filesName = filesName;
		this.pastaDosACs = pastaDosACs;
		creatorFiles = new File(this.pastaDosACs + "\\Certificadaos");
		creatorFiles.mkdir();
		creatorFiles = new File(this.pastaDosACs + "\\Certificados.Zip");
		creatorFiles.createNewFile();
		creatorFiles = new File(this.pastaDosACs + "\\ACsData.txt");
		creatorFiles.createNewFile();

	}

	public void extractFilesAndOpenssl() throws IOException {

		ZipFromICPThread thread = new ZipFromICPThread(pastaDosACs,localDoChromeDrive,linkAtualDaPagICP);

		thread.onFutureCallback(new IFutureCallback() {

			@Override
			public void onError(Exception exception) {
				exception.printStackTrace();
			}

			@Override
			public void onSuccess(String retorno) {
				System.out.println(retorno);
				ExtractFileThread thread = new ExtractFileThread(pastaDosACs);
				thread.onFutureCallback(new IFutureCallback() {

					@Override
					public void onError(Exception exception) {
						exception.printStackTrace();
					}

					@Override
					public void onSuccess(String retorno) {
						System.out.println(retorno);
						if (filesName.length == 0) {
							filesName = (new File(pastaDosACs + "\\Certificadaos")).list();
						}
						System.out.println(filesName.length);
						OpenSSLFilesThread thread = new OpenSSLFilesThread(pastaDosACs, filesName);

						thread.onFutureCallback(new IFutureCallback() {

							@Override
							public void onError(Exception exception) {
								System.out.println("ERROR, arquivo não bem formado "+ exception);
							}

							@Override
							public void onSuccess(String retorno) {
								System.out.println(retorno);
								System.out.println("SUCESS, arquivo gerado");
							}
						});
						thread.start();

					}
				});
				thread.start();
			}
		});
		thread.start();

	}

	public String[] getFilesName() {
		return filesName;
	}

	public void setFilesName(String[] filesName) {
		this.filesName = filesName;
	}

}
