package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;

public class Octal
{

    private String octal = "0.0";



    public Octal()
    {
    }



    public Octal(String inString)
    {
        this.octal = inString;
    }



    public String getOctal()
    {
        return this.octal;
    }



    public void setOctal(String inString)
    {
        this.octal = inString;
    }



    public boolean isOctal()
    {
        for (int i = 0; i < this.octal.length(); i++)
        {
            if ((this.octal.charAt(i) < '0' || this.octal.charAt(i) > '7')
                    && this.octal.charAt(i) != '.')
            {
                return false;
            }
        }

        return true;
    }



    public Octal addOctal(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();
        int carry = 0;

        // Create local reference to Octals so they can be manipulated
        // without changing original objects
        Octal currentOctal = new Octal(this.octal);
        Octal addend = new Octal(inOctal.octal);

        int aDecimalPosition = currentOctal.getPointPosition();
        int bDecimalPosition = addend.getPointPosition();

        currentOctal.addPlaceholders(addend);

        aDecimalPosition = currentOctal.getPointPosition();
        bDecimalPosition = addend.getPointPosition();

        sb.append(currentOctal);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        sb.append(addend.octal);

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

        return new Octal(sb.reverse().toString());
    }



    public Octal subtractOctal(Octal inOctal)
    {
        Octal minuend = new Octal(this.octal);
        Octal subtrahend = new Octal(inOctal.octal);

        Octal difference = new Octal();

        boolean negative = false;

        // Add placeholder zeroes if needed
        minuend.addPlaceholders(subtrahend);

        int pointPosition = 0;
        if (Double.parseDouble(subtrahend.octal) > Double.parseDouble(minuend.octal))
        {
            negative = true;
        }

        else
        {
            // Get final point position (the same value in both the minuend and subtrahend)
            pointPosition = minuend.getPointPosition();

            // Remove point to treat as whole number
            minuend = minuend.removePoint();
            subtrahend = subtrahend.removePoint();
        }

        subtrahend = subtrahend.eightsComplement();

        difference = minuend.addOctal(subtrahend);

        StringBuilder sb = new StringBuilder();

        if (negative)
        {
            difference = difference.eightsComplement();
            sb.append('-').append(difference.octal);
            difference.octal = sb.toString();
        }

        else
        {
            sb.append(difference.octal);
            int firstDigit = sb.charAt(0) - '0' - 1;

            sb.deleteCharAt(0);
            sb.reverse().append(firstDigit).reverse();

            difference.octal = sb.toString();

            difference = difference.insertPoint(pointPosition);
        }

        difference = difference.removeLeadingZeroes();

        return difference;
    }



    public Octal multiplyOctal(Octal inOctal)
    {
        Octal multiplicand = new Octal(this.octal);
        Octal multiplier = new Octal(inOctal.octal);

        int aDecimalPosition = multiplicand.getPointPosition();
        int bDecimalPosition = inOctal.getPointPosition();

        // Add placeholder zeroes if needed
        multiplicand.addPlaceholders(multiplier);
        multiplier.addPlaceholders(multiplicand);

        StringBuilder sb = new StringBuilder();

        sb.append(multiplicand.octal);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.toString();

        // Reset sb
        sb.setLength(0);

        sb.append(multiplier.octal);

        // Remove decimal point from second Octal
        sb.reverse().deleteCharAt(bDecimalPosition);

        String b = sb.toString();

        // Reset sb
        sb.setLength(0);

        int aLength = a.length();
        int bLength = b.length();

        // Add placeholder zeroes to smaller string so both octal strings are
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

        product.removeLeadingZeroes();

        return product;
    }



    public Octal divideOctal(Octal divisor, int scale)
    {
        StringBuilder sb = new StringBuilder();

        boolean negative = false;

        Octal quotient = new Octal();
        Octal remainder = new Octal();
        Octal dividend = new Octal(this.octal);
        Octal multiplier = new Octal();
        Octal product = new Octal();

        if (divisor.octal.charAt(0) == '-')
        {
            sb.append(divisor);
            sb.deleteCharAt(0);

            divisor.octal = sb.toString();

            // Reset sb
            sb.setLength(0);

            negative = true;
        }

        if (Double.parseDouble(divisor.octal) == 0)
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else if (Double.parseDouble(dividend.octal) < 0)
        {
            throw new InvalidParameterException("Error: Cannot divide a negative Octal.");
        }

        // Add placeholder zeroes, if needed
        divisor.addPlaceholders(dividend);
        dividend.addPlaceholders(divisor);

        // Remove points from both the divisor and dividend to treat as whole numbers
        dividend = dividend.removePoint();
        divisor = divisor.removePoint();

        // Add point to end of dividend and divisor so in Octal format
        dividend = dividend.insertPoint(0);
        divisor = divisor.insertPoint(0);

        // Add a zero to the end of dividend and divisor so in Octal format
        dividend = dividend.appendZero();
        divisor = divisor.appendZero();

        // Find the biggest base-eight integer that, when multiplied by the divisor,
        // is closest to the dividend
        multiplier = getLargestMultiplier(divisor, dividend);

        product = multiplier.multiplyOctal(divisor);

        remainder = dividend.subtractOctal(product);

        // If the divisor divides into the dividend evenly (i.e. remainder is 0)
        if (Double.parseDouble(remainder.octal) == 0)
        {
            quotient = multiplier;
        }

        else
        {
            // Place the multiplier in front of the point, removing any trailing zeroes
            sb.append(multiplier);
            quotient.octal = sb.toString();
            quotient = quotient.removeTrailingZeroes();
            sb.setLength(0);

            int digitsAfterPoint = 0;
            // While there is still a remainder and digitsAfterPoint is less
            // than the desired scale of the final answer
            while (Double.parseDouble(remainder.octal) != 0 &&
                    digitsAfterPoint < scale)
            {
                remainder = remainder.shiftPointRightByOne();

                multiplier = getLargestMultiplier(divisor, remainder);

                // If the divisor doesn't 'fit' into the remainder,
                // place a zero to the correct place in the quotient
                if (Double.parseDouble(multiplier.octal) == 0)
                {
                    sb.append(quotient).append('0');
                    quotient.octal = sb.toString();

                    // Reset sb
                    sb.setLength(0);
                }

                else
                {
                    product = multiplier.multiplyOctal(divisor);

                    remainder = remainder.subtractOctal(product);

                    multiplier = multiplier.removePoint();
                    sb.append(quotient).append(multiplier);
                    quotient.octal = sb.toString();
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

            quotient.octal = sb.toString();
        }
        
        return quotient;
    }



    public Octal sevensComplement()
    {
        int decimalPosition = this.getPointPosition();

        StringBuilder sb = new StringBuilder();

        // Append number of 7s that are to the right of the decimal point in final number
        sb.append("7".repeat(decimalPosition));
        // Append number of 7s that are to the left of the decimal point in final number
        sb.append("7".repeat(this.octal.length() - 1 - decimalPosition));

        String sevens = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        sb.append(this.octal);
        sb.reverse().deleteCharAt(decimalPosition);

        String octalString = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        // Find difference of the digits of 'sevens' and 'octalString'
        for (int i = 0; i < octalString.length(); i++)
        {
            sb.append((sevens.charAt(i) - '0') - (octalString.charAt(i) - '0'));
        }

        sb.reverse().insert(decimalPosition, '.');

        return new Octal(sb.reverse().toString());
    }



    public Octal eightsComplement()
    {
        StringBuilder sb = new StringBuilder();

        Octal sevensComplement = this.sevensComplement();
        Octal one = new Octal("1.0");

        sb.append(sevensComplement.addOctal(one));

        return new Octal(sb.toString());
    }



    public int getPointPosition()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.octal);

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



    public Octal removePoint()
    {
        Octal currentOctal = this;

        int pointPosition = currentOctal.getPointPosition();

        StringBuilder sb = new StringBuilder();

        sb.append(currentOctal);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        currentOctal.octal = sb.toString();

        return currentOctal;
    }



    public Octal insertPoint(int pointPosition)
    {
        Octal currentOctal = this;

        StringBuilder sb = new StringBuilder();

        currentOctal.removePoint();

        sb.append(currentOctal);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        currentOctal.octal = sb.toString();

        return currentOctal;
    }



    public void addPlaceholders(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

        int aPointPosition = currentOctal.getPointPosition();
        int bPointPosition = inOctal.getPointPosition();

        int aDigitsBeforePoint = currentOctal.getDigitsBeforePoint();
        int bDigitsBeforePoint = inOctal.getDigitsBeforePoint();
        // If needed, add placeholder zeroes so both Octals
        // have same number of digits in front of point
        if (aDigitsBeforePoint > bDigitsBeforePoint)
        {
            sb.append("0".repeat(aDigitsBeforePoint - bDigitsBeforePoint));
            sb.append(inOctal);

            inOctal.octal = sb.toString();

            sb.setLength(0);
        }

        else if (bDigitsBeforePoint > aDigitsBeforePoint) {
            sb.append("0".repeat(bDigitsBeforePoint - aDigitsBeforePoint));
            sb.append(currentOctal);

            currentOctal.octal = sb.toString();

            sb.setLength(0);
        }



        // If needed, add placeholder zeroes so both Octals
        // have same number of digits behind point
        if (aPointPosition > bPointPosition)
        {
            sb.append(inOctal.octal);

            sb.append("0".repeat(aPointPosition - bPointPosition));

            inOctal.octal = sb.toString();
        }

        else
        {
            sb.append(this.octal);

            sb.append("0".repeat(bPointPosition - aPointPosition));

            this.octal = sb.toString();
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

        while (Double.parseDouble((multiplier.multiplyOctal(divisor).octal)) <=
                Double.parseDouble(dividend.octal))
        {
            n++;
            multiplier.octal = Double.toString(n);
        }

        n--;

        multiplier.octal = Double.toString(n);

        return multiplier;
    }



    public int getDigitsBeforePoint()
    {
        int numDigits = 0;

        Octal currentOctal = new Octal(this.octal);

        while (numDigits < currentOctal.octal.length() && currentOctal.octal.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



    public Octal removeLeadingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

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

            currentOctal.octal = sb.toString();
        }

        return currentOctal;
    }



    public Octal removeTrailingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

        sb.append(currentOctal).reverse();

        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }
        }

        currentOctal.octal = sb.reverse().toString();

        return currentOctal;
    }



    public Octal shiftPointRightByOne()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

        int pointPosition = currentOctal.getDigitsBeforePoint();
        if (pointPosition == currentOctal.octal.length() - 2)
        {
            sb.append(currentOctal).append('0');

            currentOctal.octal = sb.toString();
        }

        currentOctal.insertPoint(1);

        return currentOctal;
    }



    public Octal appendZero()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

        sb.append(currentOctal).append('0');

        currentOctal.octal = sb.toString();

        return currentOctal;
    }



    @Override
    public String toString()
    {
        return this.octal;
    }
}