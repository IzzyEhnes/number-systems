package Ehnes.Izzy.NumberSystems;

import java.lang.Math;

public class Octal
{

    private String octal = "0.0";



    public Octal()
    {
    }



    public Octal(String inString)
    {
        octal = inString;
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

        // Create local reference to 'this' so it can be manipulated
        Octal currentOctal = this;

        int aDecimalPosition = currentOctal.getPointPosition();
        int bDecimalPosition = inOctal.getPointPosition();

        currentOctal.addPlaceholders(inOctal);

        aDecimalPosition = currentOctal.getPointPosition();
        bDecimalPosition = inOctal.getPointPosition();

        sb.append(currentOctal);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.reverse().toString();

        // Reset sb
        sb.setLength(0);

        sb.append(inOctal.octal);

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



    public Octal subtractOctal(Octal subtrahend)
    {
        Octal minuend = this;

        Octal difference = new Octal();

        boolean negative = false;

        System.out.println("minuend");
        System.out.println(minuend);
        System.out.println("subtrahend");
        System.out.println(subtrahend);

        // Add placeholder zeroes if needed
        minuend.addPlaceholders(subtrahend);
        //minuend.addPlaceHoldersBeforePoint(subtrahend);

        System.out.println("NEW minuend");
        System.out.println(minuend);
        System.out.println("NEW subtrahend");
        System.out.println(subtrahend);

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

        System.out.println("negative");
        System.out.println(negative);

        subtrahend = subtrahend.eightsComplement();

        System.out.println("8s subtrahend");
        System.out.println(subtrahend);

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

        System.out.println("difference");

        difference = difference.removeLeadingZeroes();

        return difference;
    }


    /*
    public Octal subtractOctal(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        Octal eightsComplement = inOctal.eightsComplement();
        System.out.println("\neightsComplement: ");
        System.out.println(eightsComplement.octal);

        Octal answer = new Octal();

        sb.append(eightsComplement.addOctal(this));

        System.out.println();

        if (Double.parseDouble(inOctal.octal) > Double.parseDouble(this.octal))
        {
            answer.octal = sb.toString();

            // Reset sb
            sb.setLength(0);

            answer = answer.eightsComplement();
            sb.append('-').append(answer.octal);
            answer.octal = sb.toString();
        }

        else
        {
            if (sb.toString().length() > eightsComplement.octal.length() &&
                sb.toString().length() > this.octal.length())
            {
                sb.deleteCharAt(0);
            }

            answer.octal = sb.toString();
        }

        // Remove any leading zeroes
        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }

            if (sb.toString().charAt(0) == '-')
            {
                System.out.println("IN - IF");

                System.out.println(sb.toString().charAt(1));

                while (sb.toString().charAt(1) == '0')
                {
                    sb.deleteCharAt(1);
                }
            }

            answer.octal = sb.toString();
        }

        return answer;
    }
*/


    public Octal multiplyOctal(Octal inOctal)
    {
        Octal answer = new Octal();

        Octal currentOctal = new Octal();
        currentOctal = this;

        int aDecimalPosition = currentOctal.getPointPosition();
        int bDecimalPosition = inOctal.getPointPosition();

        // Add placeholder zeroes if needed
        currentOctal.addPlaceholders(inOctal);
        inOctal.addPlaceholders(currentOctal);

        StringBuilder sb = new StringBuilder();

        sb.append(currentOctal.octal);

        // Remove decimal point from first Octal
        sb.reverse().deleteCharAt(aDecimalPosition);

        String a = sb.toString();

        // Reset sb
        sb.setLength(0);

        sb.append(inOctal.octal);

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

        int arraySize = aLength + bLength - 1;
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

        // Remove any leading zeroes
        while (sb.toString().charAt(0) == '0')
        {
            sb.deleteCharAt(0);
        }

        return new Octal(sb.toString());
    }



    public Octal divideOctal(Octal divisor)
    {
        StringBuilder sb = new StringBuilder();

        Octal quotient = new Octal();

        Octal dividend = new Octal();
        dividend = this;

        System.out.println("THIS");
        System.out.println(this);

        Octal multiplier = getLargestMultiplier(divisor, dividend);

        quotient = multiplier.multiplyOctal(divisor);

        // Add whole number and decimal point to answer, remove trailing zeroes
        sb.append(quotient.octal);
        sb.reverse().delete(0, 2).reverse();

        System.out.println("QUOTIENT");
        System.out.println(quotient);

        System.out.println("DIVIDEND");
        System.out.println(dividend);

        Octal remainder = new Octal();

        remainder = dividend.subtractOctal(quotient);

        System.out.println("remainder");
        System.out.println(remainder);

        return new Octal();
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

        System.out.println("pointPosition");
        System.out.println(pointPosition);

        sb.append(currentOctal);

        // Remove existing default point
        sb.reverse().deleteCharAt(0);

        // Insert point at pointPosition
        sb.insert(pointPosition, '.').reverse();

        currentOctal.octal = sb.toString();

        return currentOctal;
    }



    public void addPlaceholders(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = new Octal(this.octal);

        int aPointPosition = currentOctal.getPointPosition();
        int bPointPosition = inOctal.getPointPosition();

        // If currentOctal.octal has only one digit in front of the octal point,
        // add a placeholder zero in front of digit
        if ((aPointPosition + 2) == currentOctal.octal.length())
        {
            sb.append('0').append(currentOctal.octal);
            currentOctal.octal = sb.toString();
            sb.setLength(0);
        }

        // If inOctal.octal has only one digit in front of the octal point,
        // add a placeholder zero in front of digit
        if ((bPointPosition + 2) == inOctal.octal.length())
        {
            sb.append('0').append(inOctal.octal);
            inOctal.octal = sb.toString();
            sb.setLength(0);
        }



        int aDigitsBeforePoint = getDigitsBeforePoint(currentOctal);
        int bDigitsBeforePoint = getDigitsBeforePoint(inOctal);
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



    public int getDigitsBeforePoint(Octal inOctal)
    {
        int numDigits = 0;

        System.out.println("inOctal.octal.length()");
        System.out.println(inOctal.octal.length());

        while (numDigits < inOctal.octal.length() && inOctal.octal.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



    public Octal removeLeadingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Octal currentOctal = this;

        sb.append(currentOctal);

        if (sb.toString().charAt(0) == '0' || sb.toString().charAt(0) == '-')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }

            if (sb.toString().charAt(0) == '-')
            {
                System.out.println("IN - IF");

                System.out.println(sb.toString().charAt(1));

                while (sb.toString().charAt(1) == '0')
                {
                    sb.deleteCharAt(1);
                }
            }

            currentOctal.octal = sb.toString();
        }

        return currentOctal;
    }


    @Override
    public String toString()
    {
        return octal;
    }
}
