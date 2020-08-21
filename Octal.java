package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;

public class Octal extends NumberSystem<Octal>
{

    private String octalString = "0.0";



    public Octal()
    {
    }



    public Octal(String inString)
    {
        this.octalString = inString;
    }



    public String getOctal()
    {
        return this.octalString;
    }



    public void setOctal(String inString)
    {
        this.octalString = inString;
    }



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

        if (currentOctal.isNegative() && addend.isNegative())
        {
            negative = true;

            currentOctal = currentOctal.removeNegativeSign();
            addend = addend.removeNegativeSign();
        }

        else if (currentOctal.isNegative() && !addend.isNegative())
        {
            currentOctal = currentOctal.removeNegativeSign();

            if (Double.parseDouble(currentOctal.octalString) > Double.parseDouble(addend.octalString))
            {
                answer = currentOctal.subtract(addend);
                answer = answer.insertNegativeSign();
            }

            else
            {
                answer = addend.subtract(currentOctal);
            }

            return answer;
        }

        else if (addend.isNegative() && !currentOctal.isNegative())
        {
            addend = addend.removeNegativeSign();

            if (Double.parseDouble(addend.octalString) > Double.parseDouble(currentOctal.octalString))
            {
                answer = addend.subtract(currentOctal);
                answer = answer.insertNegativeSign();
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
            answer = answer.insertNegativeSign();
        }

        return answer;
    }



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

            difference = difference.insertPoint(pointPosition);
        }

        difference = difference.removeLeadingZeroes();

        return difference;
    }



    public Octal multiply(Octal inOctal)
    {
        Octal multiplicand = new Octal(this.octalString);
        Octal multiplier = new Octal(inOctal.octalString);

        boolean negative = false;

        // If both the multiplicand and multiplier are negative their product will
        // be positive, and we can remove the negative signs on both
        if (multiplicand.isNegative() && multiplier.isNegative())
        {
            multiplicand = multiplicand.removeNegativeSign();
            multiplier = multiplier.removeNegativeSign();
        }

        // If the multiplicand is negative and the multiplier is positive their product
        // will be negative, and we can remove the negative sign on the multiplicand
        else if (multiplicand.isNegative() && !multiplier.isNegative())
        {
            multiplicand = multiplicand.removeNegativeSign();
            negative = true;
        }

        // If the multiplier is negative and the multiplicand is positive their product
        // will be negative, and we can remove the negative sign on the multiplier
        else if (multiplier.isNegative() && !multiplicand.isNegative())
        {
            multiplier = multiplier.removeNegativeSign();
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

        product = product.removeLeadingZeroes();

        if (negative)
        {
            product = product.insertNegativeSign();
        }

        return product;
    }



    public Octal divide(Octal divisor, int scale)
    {
        StringBuilder sb = new StringBuilder();

        boolean negative = false;

        Octal quotient = new Octal();
        Octal remainder = new Octal();
        Octal dividend = new Octal(this.octalString);
        Octal multiplier = new Octal();
        Octal product = new Octal();

        if (divisor.isNegative())
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
        dividend = dividend.insertPoint(0);
        divisor = divisor.insertPoint(0);

        // Add a zero to the end of dividend and divisor so in Octal format
        dividend = dividend.appendZero();
        divisor = divisor.appendZero();

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
            quotient = quotient.removeTrailingZeroes();
            sb.setLength(0);

            int digitsAfterPoint = 0;
            // While there is still a remainder and digitsAfterPoint is less
            // than the desired scale of the final answer
            while (Double.parseDouble(remainder.octalString) != 0 &&
                    digitsAfterPoint < scale)
            {
                remainder = remainder.shiftPointRightByOne();

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

            quotient.octalString = sb.toString();
        }
        
        return quotient;
    }



    public Decimal octalStringToDecimal()
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



    public Binary octalStringToBinary()
    {
        Octal currentOctal = new Octal(this.octalString);

        StringBuilder binaryStringBuilder = new StringBuilder();

        boolean negative = false;
        if (currentOctal.isNegative())
        {
            currentOctal = currentOctal.removeNegativeSign();

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

        Binary answer = new Binary(binaryStringBuilder.toString());

        if (negative)
        {
            answer = answer.insertNegativeSign();
        }

        return answer;
    }



    public Hexadecimal octalStringToHexadecimal()
    {
        Binary binaryTemp = new Binary();

        binaryTemp = this.octalStringToBinary();

        Hexadecimal answer = binaryTemp.binaryToHexadecimal();

        return  answer;
    }



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



    public Octal eightsComplement()
    {
        StringBuilder sb = new StringBuilder();

        Octal sevensComplement = this.sevensComplement();
        Octal one = new Octal("1.0");

        sb.append(sevensComplement.add(one));

        return new Octal(sb.toString());
    }



    public Octal insertPoint(int pointPosition)
    {
        Octal currentOctal = this;

        StringBuilder sb = new StringBuilder();

        currentOctal.octalString = removePoint(currentOctal.octalString);

        sb.append(currentOctal);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        currentOctal.octalString = sb.toString();

        return currentOctal;
    }



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



    public Octal removeLeadingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octalString);

        sb.append(currentOctal);

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

            currentOctal.octalString = sb.toString();
        }

        return currentOctal;
    }



    public Octal removeTrailingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octalString);

        sb.append(currentOctal).reverse();

        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }
        }

        currentOctal.octalString = sb.reverse().toString();

        return currentOctal;
    }



    public Octal shiftPointRightByOne()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octalString);

        int pointPosition = getDigitsBeforePoint(currentOctal.octalString);
        if (pointPosition == currentOctal.octalString.length() - 2)
        {
            sb.append(currentOctal).append('0');

            currentOctal.octalString = sb.toString();
        }

        currentOctal.insertPoint(1);

        return currentOctal;
    }



    public Octal appendZero()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octalString);

        sb.append(currentOctal).append('0');

        currentOctal.octalString = sb.toString();

        return currentOctal;
    }



    public boolean isNegative()
    {
        Octal currentOctal = new Octal(this.octalString);

        if (currentOctal.octalString.charAt(0) == '-')
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    public Octal removeNegativeSign()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this).deleteCharAt(0);

        return new Octal(sb.toString());
    }



    public Octal insertNegativeSign()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this).insert(0, '-');

        return new Octal(sb.toString());
    }



    @Override
    public String toString()
    {
        return this.octalString;
    }
}