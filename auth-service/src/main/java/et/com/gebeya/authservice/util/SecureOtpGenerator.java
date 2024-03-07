package et.com.gebeya.authservice.util;

import java.security.SecureRandom;

public class SecureOtpGenerator {
    private static final int OTP_LENGTH = 6; // Adjust length as needed
    private static final String CHAR_SET = "0123456789"; // Customize character set if needed

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(CHAR_SET.length());
            otp.append(CHAR_SET.charAt(index));
        }
        return otp.toString();
}}
