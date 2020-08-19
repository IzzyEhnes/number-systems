package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public abstract class NumberSystem<T>
{
    HashMap<Character, Integer> hexMap = new HashMap<>();

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
    }

    public abstract T add(T addend);
    public abstract T subtract(T subtrahend);
    public abstract T multiply(T multiplier);
    public abstract T divide(T divisor, int scale);


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



    public abstract String toString();
}

/*
    public int getPointPosition()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.binaryString);

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



    public Binary removePoint()
    {
        Binary currentBinary = new Binary(this.binaryString);

        int pointPosition = currentBinary.getPointPosition();

        StringBuilder sb = new StringBuilder();

        sb.append(currentBinary);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        currentBinary.binaryString = sb.toString();

        return currentBinary;
    }
 */