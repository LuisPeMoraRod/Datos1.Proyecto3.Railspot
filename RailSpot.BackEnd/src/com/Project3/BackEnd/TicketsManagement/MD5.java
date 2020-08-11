package com.Project3.BackEnd.TicketsManagement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MD5 
{
	/**
	 * Public class. Creates encrypted password with MD5 algorithm
	 * @author Luis Pedro Morales Rodriguez
	 * @version 10/8/2020
	 */
	private String password;
	public MD5(String password) {
		this.password = password;
	}
	
	/**
	 * Hash password using MD5 algorithm with salt added for security
	 * @return securePassword : String
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
    public String getMD5() throws NoSuchAlgorithmException, NoSuchProviderException 
    {
       String securePassword = getSecurePassword(password);
       return securePassword;
    }
     
    /**
     * Generates new encrypted password
     * @param passwordToHash : String
     * @return generatedPassword
     */
    private  String getSecurePassword(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            //Get the hash's bytes 
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
  
}