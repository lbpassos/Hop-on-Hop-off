package crypto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateKeys {
	// Based on https://www.mkyong.com/java/java-asymmetric-cryptography-example/

	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength, random); // 1024
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) throws Exception {
		GenerateKeys k;
		String keyFolder = "KeyPair/";
		try {
			k = new GenerateKeys(1024);
			k.createKeys();
			k.writeToFile(keyFolder + "publicKey", k.getPublicKey().getEncoded());
			k.writeToFile(keyFolder + "privateKey", k.getPrivateKey().getEncoded());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		/*	on crypto/	javac GenerateKeys.java
			on src/		java crypto/GenerateKeys or
						java crypto.GenerateKeys
		*/
	}
}