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

    public abstract T add(T addend);
    public abstract T subtract(T subtrahend);
    public abstract T multiply(T multiplier);
    public abstract T divide(T divisor, int scale);
    public abstract String toString();


    public int getPointPosition(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        String a = sb.reverse().toString();

        // Find point position with respect to rightmost digit
        int pointPosition = 0;
        for (int i = 0; i < a.length(); i++)
        {
            if (a.charAt(i) == '.')
            {
                pointPosition = i;
            }
        }

        return pointPosition;
    }



    public int getDigitsBeforePoint(String inString)
    {
        int numDigits = 0;

        //Hexadecimal currentHex = new Hexadecimal(this.hexString);

        while (numDigits < inString.length()
                && inString.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



// Places a placeholder zero to the right and/or left of the radix point if it has
// no other digits on either side ("naked").
// Precondition: The incoming String contains a radix point
// Postcondition: One or two zeroes have been added to inString so the radix point has
//                digits on both sides.
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



    public String removePoint(String inString)
    {
        int pointPosition = getPointPosition(inString);

        StringBuilder sb = new StringBuilder();

        sb.append(inString);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        String outString = sb.toString();

        return outString;
    }



    public String insertPointFromRight(String inString, int pointPosition)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        String outString = sb.toString();

        return outString;
    }



    public String insertPointFromLeft(String inString, int pointPosition)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString);

        // Insert point at pointPosition
        sb.insert(pointPosition, '.');

        String outString = sb.toString();

        return outString;
    }



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



    public String insertNegativeSign(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString).insert(0, '-');

        return sb.toString();
    }



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



    public String appendZero(String inString)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(inString).append('0');

        String outString = sb.toString();

        return outString;
    }



    public String shiftPointRightByOne(String inString)
    {
        int pointPosition = getPointPosition(inString);

        inString = removePoint(inString);
        inString = insertPointFromRight(inString, pointPosition - 1);

        return inString;
    }
}