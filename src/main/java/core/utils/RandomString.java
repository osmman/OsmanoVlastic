package core.utils;

public class RandomString
{

    private static char[] alphabet;

    private final static int alphabetLength = 36;

    public static String generateRandom(int length)
    {
        StringBuilder result = new StringBuilder();
        char[] alphabet = getAlphabet();
        int j;
        for (int i = 0; i < length; i++) {
            j = ((int) (Math.random() * 100)) % alphabetLength;
            result.append(alphabet[j]);
        }

        return result.toString();

    }

    public static char[] getAlphabet()
    {
        if (alphabet == null) {
            alphabet = generateAlphabet();
        }
        return alphabet;
    }

    private static char[] generateAlphabet()
    {
        char[] alphabet = new char[alphabetLength];
        int index = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet[index] = c;
            index++;
        }

        for (char c = '0'; c <= '9'; c++) {
            alphabet[index] = c;
            index++;
        }
        return alphabet;
    }
}
