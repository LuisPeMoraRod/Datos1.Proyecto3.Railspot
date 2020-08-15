package com.Project3.BackEnd.TicketsManagement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MD5 {
	/**
	 * Public class. Creates encrypted password with MD5 algorithm
	 * 
	 * @author Luis Pedro Morales Rodriguez
	 * @version 10/8/2020
	 */
	private String password;

	public MD5(String password) {
		this.password = password;
	}

	/**
	 * Hash password using MD5 algorithm with salt added for security
	 * 
	 * @return securePassword : String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public String getMD5() {
		return getSecurePassword(password);
	}

	/**
	 * Generates new encrypted password
	 * 
	 * @param passwordToHash : String
	 * @return generatedPassword
	 */
	private String getSecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

}