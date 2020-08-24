/**
 * The NumberSystem class is an abstract class that is the parent
 * of the Binary, Octal, Decimal, and Hexadecimal classes. Being abstract
 * it cannot be instantiated, and instead contains both abstract and
 * non-abstract methods and HashMaps that provide default implementations
 * utilized by its child classes.
 *
 * @ author Izzy Ehnes
 * @ author https://github.com/IzzyEhnes
 */



package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public abstract class NumberSystem<T>
{
    // For mapping Hex values to their corresponding Decimal values
    HashMap<Character, Integer> hexMap = new HashMap<>();

    // For mapping Hex values to their corresponding Binary values
    HashMap<Character, String> hexToBinaryMap = new HashMap<>();

    // For mapping Binary values to their corresponding Hex values
    HashMap<String, Character> binaryToHexMap = new HashMap<>();


    // The default constructor for the NumberSystem class, which
    // populates the HexMaps that are used by the child classes.
    NumberSystem()
    {
        hexMap.put('0', 0);
        hexMap.put('1', 1);
        hexMap.put('2', 2);
        hexMap.put('3', 3);
        hexMap.put('4', 4);
        hexMap.put('5', 5);
        hexMap.put('6', 6);
        hexMap.put('7', 7);
        hexMap.put('8', 8);
        hexMap.put('9', 9);
        hexMap.put('A', 10);
        hexMap.put('B', 11);
        hexMap.put('C', 12);
        hexMap.put('D', 13);
        hexMap.put('E', 14);
        hexMap.put('F', 15);

        hexToBinaryMap.put('-', "-");
        hexToBinaryMap.put('0', "0000");
        hexToBinaryMap.put('1', "0001");
        hexToBinaryMap.put('2', "0010");
        hexToBinaryMap.put('3', "0011");
        hexToBinaryMap.put('4', "0100");
        hexToBinaryMap.put('5', "0101");
        hexToBinaryMap.put('6', "0110");
        hexToBinaryMap.put('7', "0111");
        hexToBinaryMap.put('8', "1000");
        hexToBinaryMap.put('9', "1001");
        hexToBinaryMap.put('A', "1010");
        hexToBinaryMap.put('B', "1011");
        hexToBinaryMap.put('C', "1100");
        hexToBinaryMap.put('D', "1101");
        hexToBinaryMap.put('E', "1110");
        hexToBinaryMap.put('F', "1111");

        binaryToHexMap.put("-", '-');
        binaryToHexMap.put(".", '.');
        binaryToHexMap.put("0000", '0');
        binaryToHexMap.put("0001", '1');
        binaryToHexMap.put("0010", '2');
        binaryToHexMap.put("0011", '3');
        binaryToHexMap.put("0100", '4');
        binaryToHexMap.put("0101", '5');
        binaryToHexMap.put("0110", '6');
        binaryToHexMap.put("0111", '7');
        binaryToHexMap.put("1000", '8');
        binaryToHexMap.put("1001", '9');
        binaryToHexMap.put("1010", 'A');
        binaryToHexMap.put("1011", 'B');
        binaryToHexMap.put("1100", 'C');
        binaryToHexMap.put("1101", 'D');
        binaryToHexMap.put("1110", 'E');
        binaryToHexMap.put("1111", 'F');
    }

    // Public abstract methods
    public abstract T add(T addend);
    public abstract T subtract(T subtrahend);
    public abstract T multiply(T multiplier);
    public abstract T divide(T divisor, int scale);
    public abstract String toString();



    /**
     * getPointPosition returns the index of the radix point with
     * respect to the rightmost character.
     *
     * @param inString The String in which the radix point is being searched for
     * @return pointPosition The index of the radix point, from the right
     */
    public int getPointPosition(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        String a = sb.reverse().toString();

        // Find point position with respect to rightmost digit
        int pointPosition = -1;
        for (int i = 0; i < a.length(); i++)
        {
            if (a.charAt(i) == '.')
            {
                pointPosition = i;
            }
        }

        return pointPosition;
    }


    /**
     * This method gets the number of digits before the radix point,
     * i.e. the index of the radix point, from the left.
     *
     * @param inString The string being parsed
     * @return numDigits The number of digits in front of the radix point
     */
    public int getDigitsBeforePoint(String inString)
    {
        int numDigits = 0;

        while (numDigits < inString.length()
                && inString.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



    /**
     * Places a placeholder zero to the right and/or left of the radix point if it has
     * no other digits on either side ("naked").
     *
     * @param inString The incoming String
     * @return either a new String made up of the original inString with the added
     *         placeholders, or the original inString if the radix point was not naked.
     */
    public String fixNakedRadixPoint(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        int digitsBeforePoint = getDigitsBeforePoint(inString);
        int pointPosition = getPointPosition(inString);

        // If the radix point is the only char, place a zero in front and behind it
        if (digitsBeforePoint == 0 && pointPosition == 0)
        {
            sb.insert(0, '0').append('0');
            return sb.toString();
        }

        // If the radix point has nothing in front of it, insert a zero in the front
        else if (digitsBeforePoint == 0)
        {
            sb.insert(0, '0');
            return sb.toString();
        }

        // If the radix point has nothing behind it, append a zero
        else if (pointPosition == 0)
        {
            sb.append('0');
            return sb.toString();
        }

        // If the radix point isn't "naked" in the front or back or inString doesn't
        // have a radix point, return the original string
        else
        {
            return inString;
        }
    }



    /**
     * removePoint deletes the radix point from inString
     *
     * @param inString The incoming String from which the radix point will be deleted
     * @return outString inString with the radix point removed
     */
    public String removePoint(String inString)
    {
        int pointPosition = getPointPosition(inString);

        StringBuilder sb = new StringBuilder();

        sb.append(inString);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        String outString = sb.toString();

        return outString;
    }



    /**
     * This method places a radix point in inString at the index of pointPosition,
     * with respect to the right.
     *
     * @param inString The incoming String in which the radix point will be placed
     * @param pointPosition The index from the right where the point will be inserted
     * @return outString The String after the radix point has been inserted
     */
    public String insertPointFromRight(String inString, int pointPosition)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        String outString = sb.toString();

        return outString;
    }



    /**
     * This method places a radix point in inString at the index of pointPosition,
     * with respect to the left.
     *
     * @param inString The incoming String in which the radix point will be placed
     * @param pointPosition The index from the left where the point will be inserted
     * @return outString The String after the radix point has been inserted
     */
    public String insertPointFromLeft(String inString, int pointPosition)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        // Insert point at pointPosition
        sb.insert(pointPosition, '.');

        String outString = sb.toString();

        return outString;
    }



    /**
     * isNegative determines whether inString is negative (begins with a negative sign) or not.
     *
     * @param inString The String whose sign is to be determined
     * @return 'true' if inString is negative, and 'false' otherwise
     */
    public boolean isNegative(String inString)
    {
        if (inString.charAt(0) == '-')
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    /**
     * This method places a negative sign in the beginning of inString if
     * one is not already present.
     *
     * @param inString The String in which the negative sign may be placed
     * @return inString if a negative sign is already present, and -inString otherwise
     */
    public String insertNegativeSign(String inString)
    {
        if (!isNegative(inString))
        {
            StringBuilder sb = new StringBuilder();

            sb.append(inString).insert(0, '-');

            return sb.toString();
        }

        else
        {
            return  inString;
        }
    }



    /**
     * removeNegativeSign removes a negative sign in inString, if one is present.
     *
     * @param inString The incoming String in which a negative sign may be removed from
     * @return The String inString without a negative sign is returned
     */
    public String removeNegativeSign(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        if (sb.toString().charAt(0) == '-')
        {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }



    /**
     * This method removes any placeholder zeroes that are to the right of the radix point.
     *
     * @param inString The String from which the trailing zeroes will be removed
     * @return outString Returns outString, which is inString with the trailing zeroes removed
     */
    public String removeTrailingZeroes(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString).reverse();

        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }
        }

        String outString = sb.reverse().toString();

        return outString;
    }



    /**
     * This method removes any placeholder zeroes that are to the left of the radix point.
     *
     * @param inString The String from which the leading zeroes will be removed
     * @return outString Returns outString, which is inString with the leading zeroes removed
     */
    public String removeLeadingZeroes(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        if (sb.toString().charAt(0) == '0'
                || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }

            if (sb.toString().charAt(0) == '-')
            {
                while (sb.toString().charAt(1) == '0')
                {
                    sb.deleteCharAt(1);
                }
            }
        }

        String outString = sb.toString();

        return outString;
    }


    /**
     * appendZero adds a zero the the end of inString
     *
     * @param inString The String in which a zero will be appended to
     * @return outString The String inString, with an appended zero
     */
    public String appendZero(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString).append('0');

        String outString = sb.toString();

        return outString;
    }



    /**
     * This method moves the radix point of inString one index to the right
     *
     * @param inString The incoming String whose radix point is to be shifted
     * @return outString Returns outString, which is inString with the radix point shifted to
     *                   the right by one
     */
    public String shiftPointRightByOne(String inString)
    {
        int pointPosition = getPointPosition(inString);

        String outString;

        outString = removePoint(inString);
        outString = insertPointFromRight(outString, pointPosition - 1);

        return outString;
    }
}