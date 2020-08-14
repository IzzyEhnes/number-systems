package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public class Hexadecimal extends NumberSystem<Hexadecimal>
{
    private String hexString = "0.0";

    //HashMap<Character, Integer> hexMap = new HashMap<>();


    public Hexadecimal()
    {
    }



    public Hexadecimal(String inString)
    {
        this.hexString = inString;
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



    public Hexadecimal add(Hexadecimal inHex)
    {
        Hexadecimal sum = new Hexadecimal();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);
        Hexadecimal addend = new Hexadecimal(inHex.hexString);

        currentHex.addPlaceholders(addend);
        addend.addPlaceholders(currentHex);

        // After addPlaceholders calls, radix point position is the same for
        // both currentHex and addend
        int radixPosition = getPointPosition(currentHex.hexString);

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



    public Hexadecimal subtract(Hexadecimal inHex)
    {
        Hexadecimal difference = new Hexadecimal(this.hexString);

        Hexadecimal minuend = new Hexadecimal(this.hexString);
        Hexadecimal subtrahend = new Hexadecimal(inHex.hexString);

        minuend.addPlaceholders(subtrahend);
        subtrahend.addPlaceholders(minuend);

        boolean negative = false;

        // If the minuend and subtrahend are the same, their difference is zero
        if (minuend.hexString.equals(subtrahend.hexString))
        {
            return new Hexadecimal("0.0");
        }

        // If both the minuend and subtrahend are negative
        if (subtrahend.isNegative() && minuend.isNegative())
        {
            subtrahend = subtrahend.removeNegativeSign();

            difference = minuend.add(subtrahend);

            return difference;
        }

        // If the minuend is negative and the subtrahend is positive,
        // their difference is -1 * (|minuend| + subtrahend)
        else if (minuend.isNegative() && !subtrahend.isNegative())
        {
            minuend = minuend.removeNegativeSign();

            difference = minuend.add(subtrahend);
            difference = difference.removeLeadingZeroes().insertNegativeSign();

            return difference;
        }

        // If the subtrahend is negative and the minuend is positive,
        // their difference is (minuend + |subtrahend|)
        else if (subtrahend.isNegative() && !minuend.isNegative())
        {
            subtrahend = subtrahend.removeNegativeSign();

            difference = minuend.add(subtrahend);

            return difference;
        }



        if (minuend.lessThanHexadecimal(subtrahend))
        {
            negative = true;

            Hexadecimal temp = new Hexadecimal();

            temp = minuend;
            minuend = subtrahend;
            subtrahend = temp;
        }



        // After addPlaceholders calls, radix point position is the same for
        // both currentHex and addend
        int radixPosition = getPointPosition(minuend.hexString);

        minuend = minuend.removePoint();
        subtrahend = subtrahend.removePoint();

        // After addPlaceholders calls, hexString length is the same for
        // both currentHex and addend
        int length = minuend.hexString.length();



        StringBuilder sb = new StringBuilder();

        sb.append(minuend).reverse();

        String a = sb.toString();

        sb.setLength(0);

        sb.append(subtrahend).reverse();

        String b = sb.toString();

        sb.setLength(0);



        StringBuilder differenceBuilder = new StringBuilder();

        int tempDiff = 0;
        int carry = 0;
        for (int i = 0; i < length; i++)
        {
            int tempA = hexMap.get(a.charAt(i));
            int tempB = hexMap.get(b.charAt(i));

            if (carry == 1)
            {
                tempA--;

                carry = 0;
            }

            tempDiff = tempA - tempB;

            if (tempDiff < 0)
            {
                carry = 1;

                tempA += 16;

                tempDiff = tempA - tempB;
            }

            differenceBuilder.append(getKeyFromValue(hexMap, tempDiff));
        }

        difference.hexString = differenceBuilder.reverse().toString();

        difference = difference.insertPointFromRight(radixPosition);

        if (negative)
        {
            difference = difference.insertNegativeSign();
        }

        return difference;
    }



    public Hexadecimal multiply(Hexadecimal inHex)
    {
        Hexadecimal answer = new Hexadecimal();

        StringBuilder sb = new StringBuilder();

        Hexadecimal multiplicand = new Hexadecimal(this.hexString);
        Hexadecimal multiplier = new Hexadecimal(inHex.hexString);

        multiplicand.addPlaceholders(multiplier);
        multiplier.addPlaceholders(multiplicand);

        multiplicand = multiplicand.removePoint();
        multiplier = multiplier.removePoint();

        sb.append(multiplicand).reverse();

        multiplicand.hexString = sb.toString();

        sb.setLength(0);

        sb.append(multiplier).reverse();

        multiplier.hexString = sb.toString();

        sb.setLength(0);

        int length = multiplicand.hexString.length();

        System.out.println("multiplicand");
        System.out.println(multiplicand);
        System.out.println("multiplier");
        System.out.println(multiplier);

        //Decimal product = new Decimal();

        Hexadecimal hexProduct = new Hexadecimal();

        double tempProduct = 0;

        Hexadecimal[] sumArray = new Hexadecimal[(length * 2) + 1];

        // Populate array with Hexadecimals with value zero
        Hexadecimal zero = new Hexadecimal();
        for (int i = 0; i < (length * 2) + 1; i++)
        {
            sumArray[i] = zero;
        }

        int carry = 0;

        for (int i = 0; i < length; i++)
        {
            System.out.println();

            for (int j = 0; j < length; j++)
            {
                double a  = hexMap.get(multiplicand.hexString.charAt(j));
                double b = hexMap.get(multiplier.hexString.charAt(i));

                tempProduct = (a * b) + carry;

                Decimal decimalProduct = new Decimal(tempProduct);

                hexProduct = decimalProduct.decimalToHexadecimal(1);

                System.out.println("\nhexProduct");
                System.out.println(hexProduct);
                System.out.print("\na: ");
                System.out.print(a);
                System.out.print("\nb: ");
                System.out.print(b);
                System.out.print("\n\nj: ");
                System.out.print(j);
                System.out.println();
                System.out.println();

                // If product has two digits, "carry" first digit
                if (hexProduct.hexString.length() > 3 && j != length - 1)
                {
                    carry = hexMap.get(hexProduct.hexString.charAt(0));

                    sb.append(hexProduct).deleteCharAt(0);

                    hexProduct.hexString = sb.toString();

                    /*
                    System.out.println("CARRY");
                    System.out.println(carry);
                     */

                    sb.setLength(0);

                    sumArray[j + i] = hexProduct;
                }

                else if (hexProduct.hexString.length() > 3 && j == length - 1)
                {
                    char secondDigit = hexProduct.hexString.charAt(0);

                    sb.append(hexProduct.hexString.charAt(1)).append(".0");

                    hexProduct.hexString = sb.toString();

                    sb.setLength(0);

                    sumArray[j + i] = hexProduct;

                    sb.append(secondDigit).append(".0");

                    Hexadecimal hexProduct2 = new Hexadecimal(sb.toString());

                    sb.setLength(0);

                    sumArray[j + i + 1] = hexProduct2;
                }

                else
                {
                    carry = 0;

                    sumArray[j + i] = hexProduct;
                }

                System.out.println("\n**********************");
            }

            for (int k = 0; k < (length * 2) + 1; k++)
            {
                System.out.println(sumArray[k]);
            }
        }

        System.out.println("\nAnswer");
        return answer;
    }




    public Hexadecimal divide(Hexadecimal inHex, int scale)
    {
        return new Hexadecimal();
    }



    public void addPlaceholders(Hexadecimal inHex)
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        int aPointPosition = getPointPosition(currentHex.hexString);
        int bPointPosition = getPointPosition(inHex.hexString);

        int aDigitsBeforePoint = currentHex.getDigitsBeforePoint();
        int bDigitsBeforePoint = inHex.getDigitsBeforePoint();
        // If needed, add placeholder zeroes so both Hexadecimals
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



    public Hexadecimal removePoint()
    {
        Hexadecimal currentHex = this;

        int pointPosition = getPointPosition(currentHex.hexString);

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



    public Hexadecimal insertPointFromLeft(int pointPosition)
    {
        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        StringBuilder sb = new StringBuilder();

        sb.append(currentHex);

        // Insert point at pointPosition
        sb.insert(pointPosition, '.');

        currentHex.hexString = sb.toString();

        return currentHex;
    }



    public boolean lessThanHexadecimal(Hexadecimal inHex)
    {
        Hexadecimal left = new Hexadecimal(this.hexString);
        Hexadecimal right = new Hexadecimal(inHex.hexString);

        left.addPlaceholders(right);
        right.addPlaceholders(left);

        if (left.hexString.equals(right.hexString))
        {
            return false;
        }

        else
        {
            for (int i = 0; i < left.hexString.length(); i++)
            {
                if (left.hexString.charAt(i) == '.')
                {
                    continue;
                }

                if (hexMap.get(left.hexString.charAt(i)) > hexMap.get(right.hexString.charAt(i)))
                {
                    return false;
                }

                else if (right.hexString.charAt(i) > left.hexString.charAt(i))
                {
                    return true;
                }
            }

            return true;
        }
    }



    public Hexadecimal insertNegativeSign()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this).insert(0, '-');

        return new Hexadecimal(sb.toString());
    }



    public boolean isNegative()
    {
        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        if (currentHex.hexString.charAt(0) == '-')
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    public Hexadecimal appendZero()
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        sb.append(currentHex).append('0');

        currentHex.hexString = sb.toString();

        return currentHex;
    }



    public Hexadecimal removeNegativeSign()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this).deleteCharAt(0);

        return new Hexadecimal(sb.toString());
    }



    public Hexadecimal removeLeadingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        sb.append(currentHex);

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

            currentHex.hexString = sb.toString();
        }

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
