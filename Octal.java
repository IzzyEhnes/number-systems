/**
 * The Octal class performs calculations on and can modify Octals. Octals are made up of
 * a String, octalString, which is comprised of a radix point surrounded by at least one digit on each side.
 *
 *
 * @ author Izzy Ehnes
 * @ author https://github.com/IzzyEhnes
 */

package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;

public class Octal extends NumberSystem<Octal>
{

    private String octalString = "0.0";



    /**
     * Default constructor initializes new Octal with the value "0.0".
     */
    public Octal()
    {
    }



    /**
     * Parameterized constructor creates an Octal with the specified value.
     *
     * @param inString The incoming octalString value
     */
    public Octal(String inString)
    {
        this.octalString = inString;
    }



    /**
     * The getter for a Octal object's octalString.
     *
     * @return this.octalString The value of the calling Octal's octalString
     */
    public String getOctal()
    {
        return this.octalString;
    }



    /**
     * The setter for a Octal object's octalString.
     *
     * @param inString The calling Octal object's octalString will be set to inString
     */
    public void setOctal(String inString)
    {
        this.octalString = inString;
    }



    /**
     * isOctal checks if the calling object is a valid Octal, i.e. contains only
     * the digits 0-7, a radix point, and possibly a negative sign.
     *
     * @return "true" if the calling object is a valid Octal (see above), and "false" otherwise.
     */
    public boolean isOctal()
    {
        for (int i = 0; i < this.octalString.length(); i++)
        {
            if ((this.octalString.charAt(i) < '0' || this.octalString.charAt(i) > '7')
                    && this.octalString.charAt(i) != '.' && this.octalString.charAt(i) != '-')
            {
                return false;
            }
        }

        return true;
    }



    /**
     * The add method adds two Octal objects, following the rules of base-eight addition
     * (@see https://www.math-only-math.com/addition-and-subtraction-of-octal-numbers.html).
     *
     * @param inOctal The addend that will be added to the calling object
     * @return answer The sum of the calling object and inOctal
     */
    public Octal add(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        Octal answer = new Octal();
        boolean negative = false;

        // Create local reference to Octals so they can be manipulated
        // without changing original objects
        Octal currentOctal = new Octal(this.octalString);
        Octal addend = new Octal(inOctal.octalString);

        if (isNegative(currentOctal.octalString) && isNegative(addend.octalString))
        {
            negative = true;

            currentOctal.octalString = removeNegativeSign(currentOctal.octalString);
            addend.octalString = removeNegativeSign(addend.octalString);
        }

        else if (isNegative(currentOctal.octalString) && !isNegative(addend.octalString))
        {
            currentOctal.octalString = removeNegativeSign(currentOctal.octalString);

            if (Double.parseDouble(currentOctal.octalString) > Double.parseDouble(addend.octalString))
            {
                answer = currentOctal.subtract(addend);
                answer.octalString = insertNegativeSign(answer.octalString);
            }

            else
            {
                answer = addend.subtract(currentOctal);
            }

            return answer;
        }

        else if (isNegative(addend.octalString) && !isNegative(currentOctal.octalString))
        {
            addend.octalString = removeNegativeSign(addend.octalString);

            if (Double.parseDouble(addend.octalString) > Double.parseDouble(currentOctal.octalString))
            {
                answer = addend.subtract(currentOctal);
                answer.octalString = insertNegativeSign(answer.octalString);
            }

            else
            {
                answer = currentOctal.subtract(addend);
            }

            return answer;
        }

        currentOctal.addPlaceholders(addend);

        int aDecimalPosition = getPointPosition(currentOctal.octalString);
        int bDecimalPosition = getPointPosition(addend.octalString);

        sb.append(currentOctal);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        sb.append(addend.octalString);

        // Remove decimal point from second Octal
        sb.reverse().deleteCharAt(bDecimalPosition);

        String b = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        int aLength = a.length() - 1;
        int bLength = b.length() - 1;

        // Find sum
        while (aLength >= 0 || bLength >= 0)
        {
            int sum = carry;

            if (aLength >= 0)
            {
                sum += a.charAt(aLength--) - '0';
            }

            if (bLength >= 0)
            {
                sum += b.charAt(bLength--) - '0';
            }

            sb.append(sum % 8);

            carry = sum / 8;
        }

        if (carry != 0)
        {
            sb.append(carry);
        }

        // Add decimal place to answer (aDecimalPosition = bDecimalPosition)
        sb.insert(aDecimalPosition, '.');

        answer.octalString = sb.reverse().toString();

        if (negative)
        {
            answer.octalString = insertNegativeSign(answer.octalString);
        }

        return answer;
    }



    /**
     * This method allows for the subtraction of two Octal objects, using the rules for base-eight subtraction
     * (@see https://mathforum.org/library/drmath/view/55943.html).
     *
     * @param inOctal The subtrahend, i.e. the value that is to be subtracted
     * @return difference The difference of the calling object and inOctal
     */
    public Octal subtract(Octal inOctal)
    {
        Octal minuend = new Octal(this.octalString);
        Octal subtrahend = new Octal(inOctal.octalString);

        Octal difference = new Octal();

        boolean negative = false;

        // If the minuend and subtrahend are the same, their difference is zero
        if (Double.parseDouble(minuend.octalString) == Double.parseDouble(subtrahend.octalString))
        {
            return new Octal("0.0");
        }

        // If both the minuend and subtrahend are negative
        if (isNegative(subtrahend.octalString) && isNegative(minuend.octalString))
        {
            subtrahend.octalString = removeNegativeSign(subtrahend.octalString);

            difference = minuend.add(subtrahend);

            return difference;
        }

        // If the minuend is negative and the subtrahend is positive,
        // their difference is -1 * (|minuend| + subtrahend)
        else if (isNegative(minuend.octalString) && !isNegative(subtrahend.octalString))
        {
            minuend.octalString = removeNegativeSign(minuend.octalString);

            difference = minuend.add(subtrahend);
            difference.octalString = insertNegativeSign(difference.octalString);
            difference.octalString = removeLeadingZeroes(difference.octalString);

            return difference;
        }

        // If the subtrahend is negative and the minuend is positive,
        // their difference is (minuend + |subtrahend|)
        else if (isNegative(subtrahend.octalString) && !isNegative(minuend.octalString))
        {
            subtrahend.octalString = removeNegativeSign(subtrahend.octalString);

            difference = minuend.add(subtrahend);

            return difference;
        }



        // Add placeholder zeroes if needed
        minuend.addPlaceholders(subtrahend);

        int pointPosition = 0;

        if (Double.parseDouble(subtrahend.octalString) > Double.parseDouble(minuend.octalString))
        {
            negative = true;
        }

        else
        {
            // Get final point position (the same value in both the minuend and subtrahend)
            pointPosition = getPointPosition(minuend.octalString);

            // Remove point to treat as whole number
            minuend.octalString = removePoint(minuend.octalString);
            subtrahend.octalString = removePoint(subtrahend.octalString);
        }

        subtrahend = subtrahend.eightsComplement();

        difference = minuend.add(subtrahend);

        StringBuilder sb = new StringBuilder();

        if (negative)
        {
            difference = difference.eightsComplement();
            sb.append('-').append(difference.octalString);
            difference.octalString = sb.toString();
        }

        else
        {
            sb.append(difference.octalString);
            int firstDigit = sb.charAt(0) - '0' - 1;

            sb.deleteCharAt(0);
            sb.reverse().append(firstDigit).reverse();

            difference.octalString = sb.toString();

            difference.octalString = insertPointFromRight(difference.octalString, pointPosition);
        }

        difference.octalString = removeLeadingZeroes(difference.octalString);

        return difference;
    }



    /**
     * The multiply method can multiply two Octal object, following the rules of base-eight multiplication
     * (@see http://numbersystemoperations.blogspot.com/2009/12/octal-multiplication.html).
     *
     * @param inOctal The Octal object that the calling object is to be multiplied by
     * @return product The product of the calling Octal object and inOctal
     */
    public Octal multiply(Octal inOctal)
    {
        Octal multiplicand = new Octal(this.octalString);
        Octal multiplier = new Octal(inOctal.octalString);

        boolean negative = false;

        // If both the multiplicand and multiplier are negative their product will
        // be positive, and we can remove the negative signs on both
        if (isNegative(multiplicand.octalString) && isNegative(multiplier.octalString))
        {
            multiplicand.octalString = removeNegativeSign(multiplicand.octalString);
            multiplier.octalString = removeNegativeSign(multiplier.octalString);
        }

        // If the multiplicand is negative and the multiplier is positive their product
        // will be negative, and we can remove the negative sign on the multiplicand
        else if (isNegative(multiplicand.octalString) && !isNegative(multiplier.octalString))
        {
            multiplicand.octalString = removeNegativeSign(multiplicand.octalString);
            negative = true;
        }

        // If the multiplier is negative and the multiplicand is positive their product
        // will be negative, and we can remove the negative sign on the multiplier
        else if (isNegative(multiplier.octalString) && !isNegative(multiplicand.octalString))
        {
            multiplier.octalString = removeNegativeSign(multiplier.octalString);
            negative = true;
        }



        // Add placeholder zeroes if needed
        multiplicand.addPlaceholders(multiplier);
        multiplier.addPlaceholders(multiplicand);

        int aDecimalPosition = getPointPosition(multiplicand.octalString);
        int bDecimalPosition = getPointPosition(inOctal.octalString);

        StringBuilder sb = new StringBuilder();

        sb.append(multiplicand.octalString);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.toString();

        // Reset sb
        sb.setLength(0);

        sb.append(multiplier.octalString);

        // Remove decimal point from second Octal
        sb.reverse().deleteCharAt(bDecimalPosition);

        String b = sb.toString();

        // Reset sb
        sb.setLength(0);

        int aLength = a.length();
        int bLength = b.length();

        // Add placeholder zeroes to smaller string so both octalString strings are
        // the same size as to avoid out of bounds error
        if (aLength > bLength)
        {
            sb.append(b).append("0".repeat(aLength - bLength));
            b = sb.toString();

            bLength = b.length();
        }

        else if (bLength > aLength)
        {
            sb.append(a).append("0".repeat(bLength - aLength));
            a = sb.toString();

            aLength = a.length();
        }

        // Reset sb
        sb.setLength(0);

        int arraySize = aLength + bLength;
        int currentIndex = arraySize - 1;
        int result = 0;
        int[] sumArray = new int[arraySize];

        // Do standard long multiplication in decimal (without carrying)
        for (int i = 0; i < aLength; i++)
        {
            // "Add" placeholder zeroes
            currentIndex -= i;

            for (int j = 0; j < bLength; j++)
            {
                result = (a.charAt(j) - '0') * (b.charAt(i) - '0');
                sumArray[currentIndex] += result;
                currentIndex--;
            }

            currentIndex = arraySize - 1;
        }


        // Reverse sumArray
        for (int i = 0; i < arraySize / 2; i++)
        {
            int temp = sumArray[i];
            sumArray[i] = sumArray[sumArray.length - i - 1];
            sumArray[sumArray.length - i - 1] = temp;
        }



        int[] closestMultipleOfEight = new int[arraySize];

        // Fill array with the largest multiple of eight that "fits"
        // into each individual element in sumArray, adding the power
        // of eight (n) associated with said multiple of eight to
        // the element to the right of the current index
        for (int i = 0; i < arraySize; i++)
        {
            int n = getClosestPowerOfEight(sumArray[i]);
            closestMultipleOfEight[i] = n * 8;

            if (i < arraySize - 1)
            {
                sumArray[i + 1] += n;
            }
        }

        // Find the difference of both arrays
        for (int i = 0; i < arraySize; i++)
        {
            sumArray[i] -= closestMultipleOfEight[i];
        }

        // Add sb to answer
        for (int i = 0; i < arraySize; i++)
        {
            // Add decimal point
            if (i == ((aDecimalPosition + bDecimalPosition)))
            {
                sb.append('.');
            }

            sb.append(sumArray[i]);
        }

        sb.reverse();

        Octal product = new Octal(sb.toString());

        product.octalString = removeLeadingZeroes(product.octalString);

        if (negative)
        {
            product.octalString = insertNegativeSign(product.octalString);
        }

        return product;
    }



    /**
     * The divide method allows for the division of two Octals, following the rules of base-eight division
     * (@see https://basicelectronicsguide.blogspot.com/2018/05/octal-division.html).
     *
     * @param divisor The Octal that will be dividing the calling object
     * @param scale The amount of digits after the radix point
     * @return quotient The result of the division of the calling Octal and divisor
     */
    public Octal divide(Octal divisor, int scale)
    {
        StringBuilder sb = new StringBuilder();

        boolean negative = false;

        Octal quotient = new Octal();
        Octal remainder = new Octal();
        Octal dividend = new Octal(this.octalString);
        Octal multiplier = new Octal();
        Octal product = new Octal();

        if (isNegative(divisor.octalString))
        {
            sb.append(divisor);
            sb.deleteCharAt(0);

            divisor.octalString = sb.toString();

            // Reset sb
            sb.setLength(0);

            negative = true;
        }

        if (Double.parseDouble(divisor.octalString) == 0)
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else if (Double.parseDouble(dividend.octalString) < 0)
        {
            throw new InvalidParameterException("Error: Cannot divide a negative Octal.");
        }

        // Add placeholder zeroes, if needed
        divisor.addPlaceholders(dividend);
        dividend.addPlaceholders(divisor);

        // Remove points from both the divisor and dividend to treat as whole numbers
        dividend.octalString = removePoint(dividend.octalString);
        divisor.octalString = removePoint(divisor.octalString);

        // Add point to end of dividend and divisor so in Octal format
        dividend.octalString = insertPointFromRight(dividend.octalString, 0);
        divisor.octalString = insertPointFromRight(divisor.octalString,0);

        // Add a zero to the end of dividend and divisor so in Octal format
        dividend.octalString = appendZero(dividend.octalString);
        divisor.octalString = appendZero(divisor.octalString);

        // Find the biggest base-eight integer that, when multiplied by the divisor,
        // is closest to the dividend
        multiplier = getLargestMultiplier(divisor, dividend);

        product = multiplier.multiply(divisor);

        remainder = dividend.subtract(product);

        // If the divisor divides into the dividend evenly (i.e. remainder is 0)
        if (Double.parseDouble(remainder.octalString) == 0)
        {
            quotient = multiplier;
        }

        else
        {
            // Place the multiplier in front of the point, removing any trailing zeroes
            sb.append(multiplier);
            quotient.octalString = sb.toString();
            quotient.octalString = removeTrailingZeroes(quotient.octalString);
            sb.setLength(0);

            int digitsAfterPoint = 0;
            // While there is still a remainder and digitsAfterPoint is less
            // than the desired scale of the final answer
            while (Double.parseDouble(remainder.octalString) != 0 &&
                    digitsAfterPoint < scale)
            {
                remainder.octalString = shiftPointRightByOne(remainder.octalString);

                multiplier = getLargestMultiplier(divisor, remainder);

                // If the divisor doesn't 'fit' into the remainder,
                // place a zero to the correct place in the quotient
                if (Double.parseDouble(multiplier.octalString) == 0)
                {
                    sb.append(quotient).append('0');
                    quotient.octalString = sb.toString();

                    // Reset sb
                    sb.setLength(0);
                }

                else
                {
                    product = multiplier.multiply(divisor);

                    remainder = remainder.subtract(product);

                    multiplier.octalString = removePoint(multiplier.octalString);
                    sb.append(quotient).append(multiplier);
                    quotient.octalString = sb.toString();
                    quotient.octalString = removeTrailingZeroes(quotient.octalString);

                    // Reset sb
                    sb.setLength(0);
                }

                digitsAfterPoint++;
            }
        }

        if (negative)
        {
            sb.append(quotient).insert(0, '-');

            quotient.octalString = sb.toString();
        }
        
        return quotient;
    }



    /**
     * This method converts the calling Octal (base-eight) into a Decimal (base-ten)
     * (@see https://www.schoolelectronic.com/2012/01/convert-octal-fraction-to-decimal.html).
     *
     * @return new Decimal(sum) The calling object that has been converted to base-ten
     */
    public Decimal octalToDecimal()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this);

        int n = getDigitsBeforePoint(this.octalString) - 1;

        boolean isNegative = false;
        if (sb.charAt(0) == '-')
        {
            isNegative = true;

            n--;

            sb.deleteCharAt(0);
        }

        String inOctal = sb.toString();

        double sum = 0;

        for (int i = 0; i < inOctal.length(); i++)
        {
            if (inOctal.charAt(i) == '.')
            {
                continue;
            }

            sum += (inOctal.charAt(i) - '0') * Math.pow(8, n);

            n--;
        }

        if (isNegative)
        {
            sum *= -1;
        }

        return new Decimal(sum);
    }



    /**
     * This method converts the calling Octal (base-eight) into a Binary (base-two)
     * (@see https://www.educative.io/edpresso/how-to-convert-octal-to-binary).
     *
     * @return new Binary(tempString) The calling object that has been converted to base-two
     */
    public Binary octalStringToBinary()
    {
        Octal currentOctal = new Octal(this.octalString);

        StringBuilder binaryStringBuilder = new StringBuilder();

        boolean negative = false;
        if (isNegative(currentOctal.octalString))
        {
            currentOctal.octalString = removeNegativeSign(currentOctal.octalString);

            negative = true;
        }

        for (int i = 0; i < currentOctal.octalString.length(); i++)
        {
            if (currentOctal.octalString.charAt(i) == '.')
            {
                binaryStringBuilder.append('.');
                continue;
            }

            int currentDigit = (currentOctal.octalString.charAt(i) - '0');

            if (currentDigit % 4 < currentDigit)
            {
                binaryStringBuilder.append(1);

                currentDigit -= 4;
            }

            else
            {
                binaryStringBuilder.append(0);
            }



            if (currentDigit % 2 < currentDigit)
            {
                binaryStringBuilder.append(1);

                currentDigit -= 2;
            }

            else
            {
                binaryStringBuilder.append(0);
            }



            if (currentDigit == 1)
            {
                binaryStringBuilder.append(1);
            }

            else
            {
                binaryStringBuilder.append(0);
            }
        }

        String tempString = binaryStringBuilder.toString();

        if (negative)
        {
            tempString = insertNegativeSign(tempString);
        }

        Binary answer = new Binary(tempString);

        return answer;
    }



    /**
     * This method converts the calling Octal (base-eight) into a Hexadecimal (base-sixteen).
     *
     * @return answer The calling object that has been converted to base-sixteen
     */
    public Hexadecimal octalStringToHexadecimal()
    {
        Binary binaryTemp = new Binary();

        binaryTemp = this.octalStringToBinary();

        Hexadecimal answer = binaryTemp.binaryToHexadecimal();

        return answer;
    }



    /**
     * This method finds the seven's complement of the calling Octal
     * (@see https://atozmath.com/example/NumberSubComp.aspx?he=e&q=7#)
     *
     * @return new Octal(sb.reverse().toString()) The seven's complement of the calling object
     */
    public Octal sevensComplement()
    {
        int decimalPosition = getPointPosition(this.octalString);

        StringBuilder sb = new StringBuilder();

        // Append number of 7s that are to the right of the decimal point in final number
        sb.append("7".repeat(decimalPosition));
        // Append number of 7s that are to the left of the decimal point in final number
        sb.append("7".repeat(this.octalString.length() - 1 - decimalPosition));

        String sevens = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        sb.append(this.octalString);
        sb.reverse().deleteCharAt(decimalPosition);

        String octalStringString = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        // Find difference of the digits of 'sevens' and 'octalStringString'
        for (int i = 0; i < octalStringString.length(); i++)
        {
            sb.append((sevens.charAt(i) - '0') - (octalStringString.charAt(i) - '0'));
        }

        sb.reverse().insert(decimalPosition, '.');

        return new Octal(sb.reverse().toString());
    }



    /**
     * This method finds the eight's complement of the calling Octal
     * (@see https://atozmath.com/example/NumberSubComp.aspx?he=e&q=8)
     *
     * @return new Octal(sb.toString()) The eight's complement of the calling object
     */
    public Octal eightsComplement()
    {
        StringBuilder sb = new StringBuilder();

        Octal sevensComplement = this.sevensComplement();
        Octal one = new Octal("1.0");

        sb.append(sevensComplement.add(one));

        return new Octal(sb.toString());
    }



    /**
     * addPlaceholders places zeroes in the calling Octal and/or inOctal such that they are the same length, while
     * maintaining the same values.
     *
     * @param inOctal The Octal that is to be compared with the calling object
     */
    public void addPlaceholders(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octalString);

        int aPointPosition = getPointPosition(currentOctal.octalString);
        int bPointPosition = getPointPosition(inOctal.octalString);

        int aDigitsBeforePoint = getDigitsBeforePoint(currentOctal.octalString);
        int bDigitsBeforePoint = getDigitsBeforePoint(inOctal.octalString);
        // If needed, add placeholder zeroes so both Octals
        // have same number of digits in front of point
        if (aDigitsBeforePoint > bDigitsBeforePoint)
        {
            sb.append("0".repeat(aDigitsBeforePoint - bDigitsBeforePoint));
            sb.append(inOctal);

            inOctal.octalString = sb.toString();

            sb.setLength(0);
        }

        else if (bDigitsBeforePoint > aDigitsBeforePoint) {
            sb.append("0".repeat(bDigitsBeforePoint - aDigitsBeforePoint));
            sb.append(currentOctal);

            currentOctal.octalString = sb.toString();

            sb.setLength(0);
        }



        // If needed, add placeholder zeroes so both Octals
        // have same number of digits behind point
        if (aPointPosition > bPointPosition)
        {
            sb.append(inOctal.octalString);

            sb.append("0".repeat(aPointPosition - bPointPosition));

            inOctal.octalString = sb.toString();
        }

        else
        {
            sb.append(this.octalString);

            sb.append("0".repeat(bPointPosition - aPointPosition));

            this.octalString = sb.toString();
        }
    }



    /**
     * This method finds the largest power of eight that fits into inInt.
     *
     * @param inInt The incoming integer
     * @return n The largest power of eight such that 8 * n is the closest to inInt
     */
    public int getClosestPowerOfEight(int inInt)
    {
        int n = 1;
        while (inInt % (8 * n) < inInt)
        {
            n++;
        }

        n--;

        return n;
    }



    /**
     * The getLargestMultiplier method finds the largest Octal that fits into both the divisor and dividend.
     *
     * @param divisor The number that is to divide the dividend
     * @param dividend The number that is to be divided by the divisor
     * @return multiplier The largest Octal that "fits" into both the divisor and dividend
     */
    public Octal getLargestMultiplier(Octal divisor, Octal dividend)
    {
        int n = 1;
        Octal multiplier = new Octal(Double.toString(n));

        while (Double.parseDouble((multiplier.multiply(divisor).octalString)) <=
                Double.parseDouble(dividend.octalString))
        {
            n++;
            multiplier.octalString = Double.toString(n);
        }

        n--;

        multiplier.octalString = Double.toString(n);

        return multiplier;
    }



    @Override
    public String toString()
    {
        return this.octalString;
    }
}