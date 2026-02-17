package open.soft.hr_backend.utility;


import java.security.SecureRandom;

public class AdvancedUniqueKeyGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String DIGITS = "0123456789";
    private static final int RANDOM_STRING_LENGTH = 6; // Random string length for additional entropy
    private static final long START_EPOCH = System.currentTimeMillis(); // Epoch to subtract from nanoseconds

    // Generate a unique short key
    public static String generateUniqueKey() {
        // Get the current nano-time and adjust by subtracting a start epoch for better granularity
        long nanoTime = System.nanoTime() - START_EPOCH;

        // Generate a random alphanumeric string
        String randomString = generateRandomString(RANDOM_STRING_LENGTH);

        // Combine nano-time and random string
        String uniqueKey = encodeBase62(nanoTime) + randomString;

        return uniqueKey;
    }

    public static String generateOTPHash() {
        // Get the current nano-time and adjust by subtracting a start epoch for better granularity
        long nanoTime = System.nanoTime() - START_EPOCH;

        // Generate a random alphanumeric string
        String randomString = generateRandomString(32);

        // Combine nano-time and random string
        String uniqueKey = encodeBase62(nanoTime) + randomString;

        return uniqueKey;
    }

    public static String generateVendorKey() {
        // Get the current nano-time and adjust by subtracting a start epoch for better granularity
        long nanoTime = System.nanoTime() - START_EPOCH;

        // Generate a random alphanumeric string
        String randomString = generateRandomString(RANDOM_STRING_LENGTH)+nanoTime;

        // Combine nano-time and random string
        return randomString.toUpperCase();

    }


    // Generate a random alphanumeric string of a given length
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static  String generateOTP(Integer length){
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(index));
        }
        return sb.toString();
    }


    // Base62 encode a long number to a string (faster and shorter than Base64)
    private static String encodeBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.insert(0, ALPHABET.charAt((int) (value % 62)));
            value /= 62;
        }
        // If value is 0, then append a '0' to keep the result non-empty
        return sb.length() == 0 ? "0" : sb.toString();
    }


}
