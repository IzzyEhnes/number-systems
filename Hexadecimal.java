package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;
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


/*
    public Hexadecimal multiply(Hexadecimal inHex)
    {
        Hexadecimal answer = new Hexadecimal();

        StringBuilder sb = new StringBuilder();

        Hexadecimal multiplicand = new Hexadecimal(this.hexString);
        Hexadecimal multiplier = new Hexadecimal(inHex.hexString);

        // If either the multiplicand or multiplier is zero, their product will be zero
        if (multiplicand.hexString.equals("0.0") || multiplier.hexString.equals("0.0"))
        {
            return new Hexadecimal();
        }

        boolean negative = false;
        if (multiplicand.isNegative() && !multiplier.isNegative())
        {
            negative = true;

            multiplicand = multiplicand.removeNegativeSign();
        }

        else if (!multiplicand.isNegative() && multiplier.isNegative())
        {
            negative = true;

            multiplier = multiplier.removeNegativeSign();
        }

        else if (multiplicand.isNegative() && multiplier.isNegative())
        {
            multiplicand = multiplicand.removeNegativeSign();
            multiplier = multiplier.removeNegativeSign();
        }

        multiplicand = multiplicand.removeTrailingZeroes();
        multiplier = multiplier.removeTrailingZeroes();

        // The position of the radix point in the answer
        int pointPosition = getPointPosition(multiplicand.hexString) +
                getPointPosition(multiplier.hexString);

        int totalDigitsBeforePoint =  multiplicand.getDigitsBeforePoint() + multiplier.getDigitsBeforePoint();

        //multiplicand.addPlaceholders(multiplier);
        //multiplier.addPlaceholders(multiplicand);

        multiplicand = multiplicand.removePoint();
        multiplier = multiplier.removePoint();

        //multiplicand = multiplicand.removeLeadingZeroes();
        //multiplier = multiplier.removeLeadingZeroes();

        sb.append(multiplicand).reverse();

        multiplicand.hexString = sb.toString();

        sb.setLength(0);

        sb.append(multiplier).reverse();

        multiplier.hexString = sb.toString();

        sb.setLength(0);

        int length = multiplicand.hexString.length();

        Hexadecimal hexProduct = new Hexadecimal();
        Hexadecimal sum = new Hexadecimal();
        Hexadecimal tempHex = new Hexadecimal();

        double tempProduct = 0;

        Hexadecimal[] sumArray = new Hexadecimal[(length * 2) + 1];

        System.out.println(length*2 + 1);

        // Populate array with Hexadecimals with value zero
        Hexadecimal zero = new Hexadecimal();
        for (int i = 0; i < (length * 2) + 1; i++)
        {
            sumArray[i] = zero;
        }

        int carry = 0;

        for (int i = 0; i < multiplier.hexString.length(); i++)
        {
            for (int j = 0; j < multiplicand.hexString.length(); j++)
            {
                double a  = hexMap.get(multiplicand.hexString.charAt(j));
                double b = hexMap.get(multiplier.hexString.charAt(i));

                tempProduct = (a * b) + carry;

                Decimal decimalProduct = new Decimal(tempProduct);

                hexProduct = decimalProduct.decimalToHexadecimal(1);

                // If product has two digits, "carry" first digit and append the second
                if (hexProduct.hexString.length() > 3 && j != length - 1)
                {
                    carry = hexMap.get(hexProduct.hexString.charAt(0));

                    sb.append(hexProduct).deleteCharAt(0);

                    hexProduct.hexString = sb.toString();

                    sb.setLength(0);

                    tempHex = sumArray[j + i];

                    sum = hexProduct.add(tempHex);

                    sumArray[j + i] = sum;
                }

                else if (hexProduct.hexString.length() > 3 && j == length - 1)
                {
                    char secondDigit = hexProduct.hexString.charAt(0);

                    sb.append(hexProduct.hexString.charAt(1)).append(".0");

                    hexProduct.hexString = sb.toString();

                    sb.setLength(0);

                    tempHex = sumArray[j + i];

                    sum = hexProduct.add(tempHex);

                    sumArray[j + i] = sum;

                    sb.append(secondDigit).append(".0");

                    Hexadecimal hexProduct2 = new Hexadecimal(sb.toString());

                    sb.setLength(0);

                    System.out.println("j + i + 1");
                    System.out.println(j + i + 1);

                    tempHex = sumArray[j + i + 1];

                    sum = hexProduct2.add(tempHex);

                    sumArray[j + i + 1] = sum;

                    carry = 0;
                }

                else
                {
                    carry = 0;

                    tempHex = sumArray[j + i];

                    sum = hexProduct.add(tempHex);

                    sumArray[j + i] = sum;
                }
            }
        }

        for (int i = 0; i < (length * 2) + 1; i++)
        {
            System.out.println(sumArray[i]);
        }

        StringBuilder answerBuilder = new StringBuilder();

        Hexadecimal carryHex = new Hexadecimal();

        for (int i = 0; i < (length * 2); i++)
        {
            if (sumArray[i].hexString.length() == 4)
            {
                carryHex.hexString = sumArray[i].hexString.charAt(0) + ".0";
                tempHex.hexString = sumArray[i].hexString.charAt(1) + ".0";

                answerBuilder.append(tempHex).reverse().delete(0, 2).reverse();

                sumArray[i + 1] = sumArray[i + 1].add(carryHex);
            }

            else
            {
                answerBuilder.append(sumArray[i]).reverse().delete(0, 2).reverse();
            }
        }

        answer.hexString = answerBuilder.reverse().toString();

        answer = answer.removeTrailingZeroes().removeLeadingZeroes().insertPointFromRight(pointPosition);

        System.out.println("answer");
        System.out.println(answer);

        // Append a placeholder zero if there are no digits to the right of the radix point
        if (getPointPosition(answer.hexString) == 0)
        {
            answer = answer.appendZero();
        }

        if (answer.getDigitsBeforePoint() > totalDigitsBeforePoint)
        {
            sb.append(answer).delete(0, answer.getDigitsBeforePoint() - totalDigitsBeforePoint);

            answer.hexString = sb.toString();
        }

        if (negative)
        {
            answer = answer.insertNegativeSign();
        }

        return answer;
    }
 */

    public Hexadecimal multiply(Hexadecimal inHex)
    {
        Hexadecimal answer = new Hexadecimal();

        Hexadecimal multiplicand = new Hexadecimal(this.hexString);
        Hexadecimal multiplier = new Hexadecimal(inHex.hexString);

        multiplicand = multiplicand.removePoint();
        multiplier = multiplier.removePoint();

        StringBuilder sb = new StringBuilder();

        sb.append(multiplicand).reverse();
        multiplicand.hexString = sb.toString();
        sb.setLength(0);

        sb.append(multiplier).reverse();
        multiplier.hexString = sb.toString();
        sb.setLength(0);

        System.out.println("\nmultiplicand");
        System.out.println(multiplicand);
        System.out.println("multiplier");
        System.out.println(multiplier);

        int multiplicandLength = multiplicand.hexString.length();
        int multiplierLength = multiplier.hexString.length();

        Hexadecimal[] sumArray = new Hexadecimal[multiplicandLength + multiplierLength];

        double doubleTemp = 0;

        Hexadecimal zero = new Hexadecimal();
        Hexadecimal hexProduct = new Hexadecimal();
        Hexadecimal hexCarry = new Hexadecimal();
        Hexadecimal hexSum = new Hexadecimal();
        Hexadecimal hexTemp = new Hexadecimal();

        int currentIndex = 0;

        // Initialize all elements in array to the zero Hexadecimal ("0.0")
        for (int i = 0; i < sumArray.length; i++)
        {
            sumArray[i] = zero;
        }

        for (int i = 0; i < multiplierLength; i++)
        {
            currentIndex = i;
            hexCarry.hexString = "0.0";

            for (int j = 0; j < multiplicandLength; j++)
            {
                doubleTemp = hexMap.get(multiplicand.hexString.charAt(j)) * hexMap.get(multiplier.hexString.charAt(i));
                Decimal decimalProduct = new Decimal(doubleTemp);

                hexProduct = decimalProduct.decimalToHexadecimal(1);

                hexProduct = hexProduct.add(hexCarry);

                System.out.println("\nhexProduct");
                System.out.println(hexProduct);
                System.out.println("hexCarry");
                System.out.println(hexCarry);

                // If the Hexadecimal has two digits in front of the radix point, carry the leading digit
                if (hexProduct.hexString.length() == 4)
                {
                    sb.append(hexProduct.hexString.charAt(0)).append(".0");
                    hexCarry.hexString = sb.toString();
                    sb.setLength(0);

                    sb.append(hexProduct).deleteCharAt(0);
                    hexProduct.hexString = sb.toString();
                    sb.setLength(0);
                }

                else
                {
                    hexCarry.hexString = "0.0";
                }

                hexTemp = sumArray[currentIndex];
                hexSum = hexTemp.add(hexProduct);

                sumArray[currentIndex] = hexSum;

                if (j == multiplicandLength - 1 && !hexCarry.hexString.equals("0.0"))
                {
                    System.out.println(hexCarry);

                    hexTemp = sumArray[currentIndex + 1];
                    hexSum = hexTemp.add(hexCarry);

                    sumArray[currentIndex + 1] = hexSum;
                }

                currentIndex++;
            }

            System.out.println();

            currentIndex = 0;
            for (int k = 0; k < sumArray.length; k++)
            {
                System.out.println(sumArray[k]);
            }

            System.out.println("******************************");
        }

        System.out.println();
        System.out.println();
        return answer;
    }



    public Hexadecimal divide(Hexadecimal divisor, int scale)
    {
        StringBuilder sb = new StringBuilder();

        boolean negative = false;

        Hexadecimal quotient = new Hexadecimal();
        Hexadecimal remainder = new Hexadecimal();
        Hexadecimal dividend = new Hexadecimal(this.hexString);
        Hexadecimal multiplier = new Hexadecimal();
        Hexadecimal product = new Hexadecimal();

        if (divisor.isNegative())
        {
            sb.append(divisor);
            sb.deleteCharAt(0);

            divisor.hexString = sb.toString();

            // Reset sb
            sb.setLength(0);

            negative = true;
        }

        /*
        if (Double.parseDouble(divisor.hexString) == 0)
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else if (Double.parseDouble(dividend.hexString) < 0)
        {
            throw new InvalidParameterException("Error: Cannot divide a negative Octal.");
        }

         */

        // Add placeholder zeroes, if needed
        divisor.addPlaceholders(dividend);
        dividend.addPlaceholders(divisor);

        // Remove points from both the divisor and dividend to treat as whole numbers
        dividend = dividend.removePoint();
        divisor = divisor.removePoint();

        // Add point to end of dividend and divisor so in Octal format
        dividend = dividend.insertPointFromRight(0);
        divisor = divisor.insertPointFromRight(0);

        // Add a zero to the end of dividend and divisor so in Octal format
        dividend = dividend.appendZero();
        divisor = divisor.appendZero();

        System.out.print("\n\ndivisor: ");
        System.out.print(divisor);
        System.out.print("\ndividend: ");
        System.out.print(dividend);

        // Find the biggest base-eight integer that, when multiplied by the divisor,
        // is closest to the dividend
        multiplier = getLargestMultiplier(divisor, dividend);

        System.out.print("\nmultiplier: ");
        System.out.print(multiplier);

        product = multiplier.multiply(divisor);

        remainder = dividend.subtract(product);

        // If the divisor divides into the dividend evenly (i.e. remainder is 0)
        if (remainder.hexadecimalToDecimal().getDecimal() == 0)
        {
            quotient = multiplier;
        }

        else
        {
            // Place the multiplier in front of the point, removing any trailing zeroes
            sb.append(multiplier);
            quotient.hexString = sb.toString();
            quotient = quotient.removeTrailingZeroes();
            sb.setLength(0);

            System.out.print("\nquotient: ");
            System.out.print(quotient);

            int digitsAfterPoint = 0;
            // While there is still a remainder and digitsAfterPoint is less
            // than the desired scale of the final answer
            while (remainder.hexadecimalToDecimal().getDecimal() != 0 &&
                    digitsAfterPoint < scale)
            {
                remainder = remainder.shiftPointRightByOne();

                multiplier = getLargestMultiplier(divisor, remainder);

                // If the divisor doesn't 'fit' into the remainder,
                // place a zero to the correct place in the quotient
                if (multiplier.hexadecimalToDecimal().getDecimal() == 0)
                {
                    sb.append(quotient).append('0');
                    quotient.hexString = sb.toString();

                    // Reset sb
                    sb.setLength(0);
                }

                else
                {
                    product = multiplier.multiply(divisor);

                    remainder = remainder.subtract(product);

                    multiplier = multiplier.removePoint();
                    sb.append(quotient).append(multiplier);
                    quotient.hexString = sb.toString();
                    quotient = quotient.removeTrailingZeroes();

                    // Reset sb
                    sb.setLength(0);
                }

                digitsAfterPoint++;
            }
        }

        if (negative)
        {
            sb.append(quotient).insert(0, '-');

            quotient.hexString = sb.toString();
        }

        System.out.println();
        System.out.println();

        return quotient;
    }


/*
    public Hexadecimal divide(Hexadecimal divisor, int scale)
    {
        Hexadecimal dividend = new Hexadecimal(this.hexString);
        Hexadecimal product = new Hexadecimal();
        Hexadecimal multiplier = new Hexadecimal();
        Hexadecimal remainder = new Hexadecimal();

        // Add placeholder zeroes, if needed
        divisor.addPlaceholders(dividend);
        dividend.addPlaceholders(divisor);

        // Remove points from both the divisor and dividend to treat as whole numbers
        dividend = dividend.removePoint();
        divisor = divisor.removePoint();

        // Add point to end of dividend and divisor so in Octal format
        dividend = dividend.insertPointFromRight(0);
        divisor = divisor.insertPointFromRight(0);

        // Add a zero to the end of dividend and divisor so in Octal format
        dividend = dividend.appendZero();
        divisor = divisor.appendZero();

        System.out.println("dividend");
        System.out.println(dividend);
        System.out.println("divisor");
        System.out.println(divisor);

        if (dividend.hexString.equals(divisor.hexString))
        {
            return new Hexadecimal("1.0");
        }

        StringBuilder quotientBuilder = new StringBuilder();
        StringBuilder dividendBuilder = new StringBuilder();

        Hexadecimal currentDividend = new Hexadecimal();

        dividendBuilder.append(dividend.hexString.charAt(0)).append(".0");

        currentDividend.hexString = dividendBuilder.toString();

        if (divisor.lessThanHexadecimal(currentDividend))
        {
            multiplier = getLargestMultiplier(divisor, currentDividend);

            multiplier = multiplier.removeTrailingZeroes().removePoint();

            quotientBuilder.append(multiplier);

            product = multiplier.multiply(divisor);

            remainder = product.subtract(dividend);
        }

        else
        {
            quotientBuilder.append("0");

            remainder
        }

        return new Hexadecimal();
    }

 */


    public Decimal hexadecimalToDecimal()
    {
        String inString = this.hexString;

        double sum = 0;
        int currentInt = 0;

        int n = this.getDigitsBeforePoint() - 1;

        for (int i = 0; i < inString.length(); i++)
        {
            if (inString.charAt(i) == '.')
            {
                continue;
            }

            currentInt = hexMap.get(inString.charAt(i));

            sum += currentInt * Math.pow(16, n);

            n--;
        }

        return new Decimal(sum);
    }



    public Hexadecimal getLargestMultiplier(Hexadecimal divisor, Hexadecimal dividend)
    {
        Hexadecimal multiplier = new Hexadecimal();

        Hexadecimal one = new Hexadecimal("1.0");

        Hexadecimal product = new Hexadecimal();

        product = divisor.multiply(multiplier);

        System.out.println("\ndivisor");
        System.out.println(divisor);
        System.out.println("dividend");
        System.out.println(dividend);

        while (product.lessThanHexadecimal(dividend))
        {
            multiplier = multiplier.add(one);

            product = divisor.multiply(multiplier);

            if (product.hexString.equals(dividend.hexString))
            {
                multiplier = multiplier.add(one);

                break;
            }
        }

        multiplier = multiplier.subtract(one);

        return multiplier;
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



    public Hexadecimal removeTrailingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        sb.append(currentHex).reverse();

        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }
        }

        currentHex.hexString = sb.reverse().toString();

        return currentHex;
    }



    public Hexadecimal shiftPointRightByOne()
    {
        StringBuilder sb = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        int pointPosition = currentHex.getDigitsBeforePoint();
        if (pointPosition == currentHex.hexString.length() - 2)
        {
            sb.append(currentHex).append('0');

            currentHex.hexString = sb.toString();
        }

        currentHex.insertPoint(1);

        return currentHex;
    }



    public Hexadecimal insertPoint(int pointPosition)
    {
        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        StringBuilder sb = new StringBuilder();

        currentHex.removePoint();

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