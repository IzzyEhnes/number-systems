package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;
import java.util.HashMap;

public class Hexadecimal extends NumberSystem<Hexadecimal>
{
    private String hexString = "0.0";

    HashMap<Character, String> hexToBinaryMap = new HashMap<>();



    public Hexadecimal()
    {
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
    }



    public Hexadecimal(String inString)
    {
        this.hexString = inString;

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

        currentHex.hexString = removePoint(currentHex.hexString);
        addend.hexString = removePoint(addend.hexString);

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

        sum.hexString = insertPointFromRight(sum.hexString, radixPosition);

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
            subtrahend.hexString = removeNegativeSign(subtrahend.hexString);

            difference = minuend.add(subtrahend);

            return difference;
        }

        // If the minuend is negative and the subtrahend is positive,
        // their difference is -1 * (|minuend| + subtrahend)
        else if (minuend.isNegative() && !subtrahend.isNegative())
        {
            minuend.hexString = removeNegativeSign(minuend.hexString);

            difference = minuend.add(subtrahend);
            difference = difference.removeLeadingZeroes();
            difference.hexString = insertNegativeSign(difference.hexString);

            return difference;
        }

        // If the subtrahend is negative and the minuend is positive,
        // their difference is (minuend + |subtrahend|)
        else if (subtrahend.isNegative() && !minuend.isNegative())
        {
            subtrahend.hexString = removeNegativeSign(subtrahend.hexString);

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

        minuend.hexString = removePoint(minuend.hexString);
        subtrahend.hexString = removePoint(subtrahend.hexString);

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

        difference.hexString = insertPointFromRight(difference.hexString, radixPosition);

        if (negative)
        {
            difference.hexString = insertNegativeSign(difference.hexString);
        }

        return difference;
    }



    public Hexadecimal multiply(Hexadecimal inHex)
    {
        Hexadecimal answer = new Hexadecimal();

        Hexadecimal zero = new Hexadecimal();

        Hexadecimal multiplicand = new Hexadecimal(this.hexString);
        Hexadecimal multiplier = new Hexadecimal(inHex.hexString);

        if (multiplicand.removeTrailingZeroes().removeLeadingZeroes().hexString.equals(".") ||
                multiplier.removeTrailingZeroes().removeLeadingZeroes().hexString.equals("."))
        {
            return zero;
        }

        boolean negative = false;
        if (multiplicand.isNegative() && !multiplier.isNegative())
        {
            negative = true;

            multiplicand.hexString = removeNegativeSign(multiplicand.hexString);
        }

        else if (!multiplicand.isNegative() && multiplier.isNegative())
        {
            negative = true;

            multiplier.hexString = removeNegativeSign(multiplier.hexString);
        }

        else if (multiplicand.isNegative() && multiplier.isNegative())
        {
            multiplicand.hexString = removeNegativeSign(multiplicand.hexString);
            multiplier.hexString = removeNegativeSign(multiplier.hexString);
        }

        int pointPosition = getPointPosition(multiplicand.hexString) +
                                getPointPosition(multiplier.hexString);

        multiplicand.hexString = removePoint(multiplicand.hexString);
        multiplier.hexString = removePoint(multiplier.hexString);

        StringBuilder sb = new StringBuilder();

        sb.append(multiplicand).reverse();
        multiplicand.hexString = sb.toString();
        sb.setLength(0);

        sb.append(multiplier).reverse();
        multiplier.hexString = sb.toString();
        sb.setLength(0);

        int multiplicandLength = multiplicand.hexString.length();
        int multiplierLength = multiplier.hexString.length();

        Hexadecimal[] sumArray = new Hexadecimal[multiplicandLength + multiplierLength];

        double doubleTemp = 0;

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
                    hexTemp = sumArray[currentIndex + 1];
                    hexSum = hexTemp.add(hexCarry);

                    sumArray[currentIndex + 1] = hexSum;
                }

                currentIndex++;
            }
        }

        for (int i = 0; i < sumArray.length; i++)
        {
            if (sumArray[i].hexString.length() == 4)
            {
                hexCarry.hexString = sumArray[i].hexString.charAt(0) + ".0";

                Hexadecimal tempHex = new Hexadecimal(sumArray[i].hexString.charAt(1) + ".0");

                sb.append(tempHex).reverse().delete(0, 2).reverse();

                sumArray[i + 1] = sumArray[i + 1].add(hexCarry);
            }

            else
            {
                sb.append(sumArray[i]).reverse().delete(0, 2).reverse();
            }
        }

        if (negative)
        {
            answer.hexString = insertNegativeSign(answer.hexString);
        }

        answer.hexString = sb.reverse().toString();

        answer.hexString = insertPointFromRight(answer.hexString, pointPosition);
        answer = answer.removeLeadingZeroes();

        return answer;
    }



    public Hexadecimal divide(Hexadecimal divisor, int scale)
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder quotientBuilder = new StringBuilder();

        Hexadecimal quotient = new Hexadecimal();
        Hexadecimal product = new Hexadecimal();
        Hexadecimal remainder = new Hexadecimal();
        Hexadecimal multiplier = new Hexadecimal();

        Hexadecimal dividend = new Hexadecimal(this.hexString);

        dividend.addPlaceholders(divisor);
        divisor.addPlaceholders(dividend);

        dividend.hexString = insertPointFromRight(dividend.hexString, 0);
        divisor.hexString = insertPointFromRight(divisor.hexString, 0);
        dividend = dividend.appendZero();
        divisor = divisor.appendZero();

        dividend.hexString = removePoint(dividend.hexString);
        divisor.hexString = removePoint(divisor.hexString);

        int quotientRadixPosition = getDigitsBeforePoint(dividend.hexString);

        sb.append(dividend.hexString.charAt(0)).append(".0");
        remainder.hexString = sb.toString();

        multiplier = getLargestMultiplier(divisor, remainder);

        product = divisor.multiply(multiplier);

        if (!multiplier.hexString.equals("0.0"))
        {
            quotientBuilder.append(multiplier.hexString.charAt(0));
        }

        else
        {
            quotientBuilder.append(multiplier.hexString.charAt(0));
            sb.insert(1, dividend.hexString.charAt(1));
            remainder.hexString = sb.toString();
        }

        sb.setLength(0);

        remainder = remainder.subtract(product);

        Hexadecimal tempRemainder = new Hexadecimal(remainder.hexString);

        int digitCount = 0;
        while (digitCount < (scale + quotientRadixPosition))
        {
            multiplier = getLargestMultiplier(divisor, remainder).removeLeadingZeroes();
            multiplier.hexString = fixNakedRadixPoint(multiplier.hexString);

            quotientBuilder.append(multiplier.hexString.charAt(0));

            product = multiplier.multiply(divisor);

            remainder = remainder.subtract(product);

            int radixPosition = getPointPosition(remainder.hexString);

            sb.append(dividend).reverse().insert(radixPosition + 1,"0").reverse();
            dividend.hexString = sb.toString();

            sb.setLength(0);

            sb.append(remainder).reverse().insert(radixPosition + 1, dividend.hexString.charAt(digitCount + 2)).reverse();

            remainder.hexString = sb.toString();

            tempRemainder = remainder;

            tempRemainder = tempRemainder.removeLeadingZeroes().removeTrailingZeroes();

            sb.setLength(0);

            digitCount++;
        }

        quotient.hexString = quotientBuilder.toString();
        quotient.hexString = insertPointFromLeft(quotient.hexString, quotientRadixPosition);

        quotient = quotient.removeLeadingZeroes().removeTrailingZeroes();
        quotient.hexString = fixNakedRadixPoint(quotient.hexString);

        return quotient;
    }



    public Decimal hexadecimalToDecimal()
    {
        String inString = this.hexString;

        double sum = 0;
        int currentInt = 0;

        int n = getDigitsBeforePoint(this.hexString) - 1;

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



    public Binary hexadecimalToBinary()
    {
        StringBuilder answerBuilder = new StringBuilder();

        StringBuilder integerBuilder = new StringBuilder();
        StringBuilder fractionalBuilder = new StringBuilder();

        Hexadecimal currentHex = new Hexadecimal(this.hexString);

        boolean negative = false;
        if (currentHex.isNegative())
        {
            currentHex.hexString = removeNegativeSign(currentHex.hexString);

            negative = true;
        }

        boolean decimalFlag = false;
        for (int i = 0; i < currentHex.hexString.length(); i++)
        {
            if (this.hexString.charAt(i) == '.')
            {
                decimalFlag = true;
                continue;
            }

            if (!decimalFlag)
            {
                integerBuilder.append(this.hexString.charAt(i));
            }

            else
            {
                fractionalBuilder.append(this.hexString.charAt(i));
            }
        }

        String integerString = integerBuilder.toString();
        String fractionString = fractionalBuilder.toString();

        for (int i = 0; i < integerString.length(); i++)
        {
            answerBuilder.append(hexToBinaryMap.get(integerString.charAt(i)));
        }

        answerBuilder.append('.');

        for (int i = 0; i < fractionalBuilder.length(); i++)
        {
            answerBuilder.append(hexToBinaryMap.get(fractionString.charAt(i)));
        }

        String tempString = answerBuilder.toString();

        if (negative)
        {
            tempString = insertNegativeSign(tempString);
        }

        Binary answer = new Binary(tempString);

        return answer;
    }



    public Octal hexadecimalToOctal()
    {
        Binary binaryTemp = new Binary();

        binaryTemp = this.hexadecimalToBinary();

        Octal answer = new Octal();

        answer = binaryTemp.binaryToOctal();

        return answer;
    }



    public Hexadecimal getLargestMultiplier(Hexadecimal divisor, Hexadecimal dividend)
    {
        Hexadecimal product = new Hexadecimal();
        Hexadecimal multiplier = new Hexadecimal("1.0");
        Hexadecimal one = new Hexadecimal("1.0");

        product = divisor.multiply(multiplier);

        while (product.lessThanHexadecimal(dividend))
        {
            multiplier = multiplier.add(one);

            product = divisor.multiply(multiplier);

            product = product.removeLeadingZeroes().removeTrailingZeroes();
            dividend = dividend.removeLeadingZeroes().removeTrailingZeroes();

            if (product.hexString.equals(dividend.hexString))
            {
                multiplier = multiplier.add(one);
            }

            product = product.appendZero();
            dividend = dividend.appendZero();
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

        int aDigitsBeforePoint = getDigitsBeforePoint(currentHex.hexString);
        int bDigitsBeforePoint = getDigitsBeforePoint(inHex.hexString);
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

        int pointPosition = getDigitsBeforePoint(currentHex.hexString);
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

        currentHex.hexString = removePoint(currentHex.hexString);

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