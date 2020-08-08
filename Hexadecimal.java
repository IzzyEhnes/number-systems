package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public class Hexadecimal
{
    private String hexString = "0.0";

    HashMap<Character, Integer> hexMap = new HashMap<>();


    public Hexadecimal()
    {
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
        Hexadecimal currentHex = new Hexadecimal(this.hexString);
        Hexadecimal addend = new Hexadecimal(inHex.hexString);

        return new Hexadecimal();
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



    @Override
    public String toString()
    {
        return this.hexString;
    }

}
