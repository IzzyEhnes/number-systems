package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public class Hexadecimal
{
    private String hexString = "0.0";

    HashMap<Character, Integer> hexMap = new HashMap<>();


    public Hexadecimal()
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



    public Hexadecimal(String inString)
    {
        this.hexString = inString;

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



    public String getHexadecimal()
    {
        return this.hexString;
    }



    public void setHexadecimal(String inString)
    {
        this.hexString = inString;
    }



    public boolean isHexadecimal()
    {
        for (int i = 0; i < this.hexString.length(); i++)
        {
            if ((this.hexString.charAt(i) < 48 || this.hexString.charAt(i) > 57) &&
                    (this.hexString.charAt(i) < 65 || this.hexString.charAt(i) > 70) &&
                        (this.hexString.charAt(i) != '.') && (this.hexString.charAt(i) != '-'))
            {
                return false;
            }
        }

        return true;
    }



    public Hexadecimal addHexadecimal(Hexadecimal inHex)
    {
        Hexadecimal sum = new Hexadecimal();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);
        Hexadecimal addend = new Hexadecimal(inHex.hexString);

        currentHex.addPlaceholders(addend);
        addend.addPlaceholders(currentHex);

        // After addPlaceholders calls, radix point position is the same for
        // both currentHex and addend
        int radixPosition = currentHex.getPointPosition();

        currentHex = currentHex.removePoint();
        addend = addend.removePoint();

        // After addPlaceholders calls, hexString length is the same for
        // both currentHex and addend
        int length = currentHex.hexString.length();



        StringBuilder sb = new StringBuilder();

        sb.append(currentHex).reverse();

        String a = sb.toString();

        sb.setLength(0);

        sb.append(addend).reverse();

        String b = sb.toString();

        sb.setLength(0);



        StringBuilder sumBuilder = new StringBuilder();

        int tempSum = 0;
        int carry = 0;
        for (int i = 0; i < length; i++)
        {
            tempSum = hexMap.get(a.charAt(i)) + hexMap.get(b.charAt(i));

            if (carry == 1)
            {
                tempSum++;
                carry = 0;
            }

            if (tempSum > 15)
            {
                carry = 1;

                tempSum -= 16;

            }

            sumBuilder.append(getKeyFromValue(hexMap, tempSum));
        }

        if (carry != 0)
        {
            sumBuilder.append(carry);
        }

        sum.hexString = sumBuilder.reverse().toString();

        sum = sum.insertPointFromRight(radixPosition);

        return sum;
    }



    public void addPlaceholders(Hexadecimal inHex)
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        int aPointPosition = currentHex.getPointPosition();
        int bPointPosition = inHex.getPointPosition();

        int aDigitsBeforePoint = currentHex.getDigitsBeforePoint();
        int bDigitsBeforePoint = inHex.getDigitsBeforePoint();
        // If needed, add placeholder zeroes so both Octals
        // have same number of digits in front of point
        if (aDigitsBeforePoint > bDigitsBeforePoint)
        {
            sb.append("0".repeat(aDigitsBeforePoint - bDigitsBeforePoint));
            sb.append(inHex);

            inHex.hexString = sb.toString();

            sb.setLength(0);
        }

        else if (bDigitsBeforePoint > aDigitsBeforePoint) {
            sb.append("0".repeat(bDigitsBeforePoint - aDigitsBeforePoint));
            sb.append(currentHex);

            currentHex.hexString = sb.toString();

            sb.setLength(0);
        }



        // If needed, add placeholder zeroes so both Hexadecimals
        // have same number of digits behind point
        if (aPointPosition > bPointPosition)
        {
            sb.append(inHex.hexString);

            sb.append("0".repeat(aPointPosition - bPointPosition));

            inHex.hexString = sb.toString();
        }

        else
        {
            sb.append(this.hexString);

            sb.append("0".repeat(bPointPosition - aPointPosition));

            this.hexString = sb.toString();
        }
    }



    public int getDigitsBeforePoint()
    {
        int numDigits = 0;

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        while (numDigits < currentHex.hexString.length()
                && currentHex.hexString.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



    public int getPointPosition()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.hexString);

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



    public Hexadecimal removePoint()
    {
        Hexadecimal currentHex = this;

        int pointPosition = currentHex.getPointPosition();

        StringBuilder sb = new StringBuilder();

        sb.append(currentHex);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        currentHex.hexString = sb.toString();

        return currentHex;
    }



    public Hexadecimal insertPointFromRight(int pointPosition)
    {
        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        StringBuilder sb = new StringBuilder();

        sb.append(currentHex);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        currentHex.hexString = sb.toString();

        return currentHex;
    }



    public Object getKeyFromValue(HashMap inMap, Integer inValue)
    {
        for (Object i : inMap.keySet())
        {
            if (inMap.get(i).equals(inValue))
            {
                return i;
            }
        }
        return null;
    }



    @Override
    public String toString()
    {
        return this.hexString;
    }

}
