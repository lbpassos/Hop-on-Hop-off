package crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.util.Base64;

public class Asymmetric {
	// Based on https://www.mkyong.com/java/java-asymmetric-cryptography-example/

	private Cipher cipher;

	public Asymmetric() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance("RSA");
	}

	public byte[] strToBytes(String filename) throws Exception {
		return Files.readAllBytes(new File(filename).toPath());
	}

	public PrivateKey getPrivate(String fn) throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(strToBytes(fn));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublic(String fn) throws Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(strToBytes(fn));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public void encryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		writeToFile(output, this.cipher.doFinal(input));
	}

	public void decryptFile(byte[] input, File output, PublicKey key) throws IOException, GeneralSecurityException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		writeToFile(output, this.cipher.doFinal(input));
	}

	private void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
		FileOutputStream fos = new FileOutputStream(output);
		fos.write(toWrite);
		fos.flush();
		fos.close();
	}

	public String encryptText(String msg, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] m = cipher.doFinal(msg.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(m);
	}

	public String decryptText(String msg, PublicKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = Base64.getDecoder().decode(msg);
		byte[] m = cipher.doFinal(bytes);
		return new String(m, "UTF-8");
	}

	public byte[] getFileInBytes(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] fbytes = new byte[(int) f.length()];
		fis.read(fbytes);
		fis.close();
		return fbytes;
	}

	public static void main(String[] args) throws Exception {
		Asymmetric as = new Asymmetric();
		PrivateKey privKey = as.getPrivate("KeyPair/privateKey");
		PublicKey pubKey = as.getPublic("KeyPair/publicKey");

		String msg = "Cryptography is fun!";
		String encrypted_msg = as.encryptText(msg, privKey);
		String decrypted_msg = as.decryptText(encrypted_msg, pubKey);
		System.out.println("Original Message: " + msg + 
			"\nEncrypted Message: " + encrypted_msg
			+ "\nDecrypted Message: " + decrypted_msg);

		if (new File("KeyPair/test.txt").exists()) {
			as.encryptFile(as.getFileInBytes(new File("KeyPair/test.txt")), 
				new File("KeyPair/test_encrypted.txt"), privKey);
			as.decryptFile(as.getFileInBytes(new File("KeyPair/test_encrypted.txt")),
				new File("KeyPair/test_decrypted.txt"), pubKey);
		} else {
			System.out.println("Create a file test.txt under folder KeyPair");
		}
	}
}