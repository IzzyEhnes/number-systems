package Ehnes.Izzy.NumberSystems;

import java.lang.Math;

public class Binary
{
    private String binaryString = "0.0";



    public Binary()
    {
    }



    public Binary(String inString)
    {
        binaryString = inString;
    }



    public String getBinary()
    {
        return binaryString;
    }



    public void setBinaryString(String inString)
    {
        binaryString = inString;
    }



    public boolean isBinary()
    {
        for (int i = 0; i < this.binaryString.length(); i++)
        {
            if (this.binaryString.charAt(i) != '0'
                    && this.binaryString.charAt(i) != '1'
                        && this.binaryString.charAt(i) != '.')
            {
                return false;
            }
        }

        return true;
    }



    public Binary addBinary(Binary inBinary)
    {
        Binary answer = new Binary();
        StringBuilder sb = new StringBuilder();
        int carry = 0;

        Binary currentBinary = new Binary(this.binaryString);
        Binary addend = new Binary(inBinary.binaryString);

        addend.addPlaceholders(currentBinary);
        currentBinary.addPlaceholders(addend);

        int currentBinaryPointPosition = currentBinary.getPointPosition();
        int addendPointPosition = addend.getPointPosition();

        currentBinary = currentBinary.removePoint();
        addend = addend.removePoint();

        int aLength = currentBinary.binaryString.length() - 1;
        int bLength = addend.binaryString.length() - 1;

        while (aLength >= 0 || bLength >= 0)
        {
            int sum = carry;

            if (aLength >= 0)
            {
                sum += currentBinary.binaryString.charAt(aLength--) - '0';
            }

            if (bLength >= 0)
            {
                sum += addend.binaryString.charAt(bLength--) - '0';
            }

            sb.append(sum % 2);

            carry = sum / 2;
        }

        if (carry != 0)
        {
            sb.append(carry);
        }

        answer.binaryString = sb.reverse().toString();

        if (currentBinaryPointPosition > addendPointPosition)
        {
            answer = answer.insertPoint(currentBinaryPointPosition);
        }

        else
        {
            answer = answer.insertPoint(addendPointPosition);
        }

        return answer;
    }



    public Binary subtractBinary(Binary inBinary)
    {
        Binary answer = new Binary();

        StringBuilder sb = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        currentBinary.addPlaceholders(inBinary);
        inBinary.addPlaceholders(currentBinary);

        inBinary = inBinary.twosComplement();

        sb.append(currentBinary.addBinary(inBinary));

        // if there was overflow, remove leftmost bit
        if (sb.length() > answer.binaryString.length())
        {
            sb.deleteCharAt(0);
        }

        answer.binaryString = sb.toString();

        return answer;
    }



    public Binary multiplyBinary(Binary inBinary)
    {
        Binary answer = new Binary();
        Binary binaryTemp = new Binary();

        StringBuilder sb = new StringBuilder();

        sb.append(this.binaryString);
        sb.reverse();
        Binary multiplicand = new Binary(sb.toString());

        // Reset sb
        sb.setLength(0);

        sb.append(inBinary.binaryString);
        sb.reverse();
        Binary multiplier = new Binary(sb.toString());

        if (Double.parseDouble(multiplicand.binaryString) == 0 ||
                Double.parseDouble(multiplier.binaryString) == 0)
        {
            return new Binary("0.0");
        }

        multiplicand.addPlaceholders(multiplier);
        multiplier.addPlaceholders(multiplicand);

        int pointPosition = multiplicand.getDigitsBeforePoint();

        multiplicand = multiplicand.removePoint();
        multiplier = multiplier.removePoint();

        int aLength = multiplicand.binaryString.length();
        int bLength = multiplier.binaryString.length();

        // Reset sb
        sb.setLength(0);

        for (int i = 0; i < aLength; i++)
        {
            // Add placeholder zeroes
            sb.append("0".repeat(i));

            for (int j = 0; j < bLength; j++)
            {
                sb.append((multiplicand.binaryString.charAt(j) - '0')
                        * (multiplier.binaryString.charAt(i) - '0'));
            }

            binaryTemp.binaryString = sb.reverse().toString();

            answer = answer.addBinary(binaryTemp);
            sb.delete(0, bLength + i);
        }

        answer = answer.removePoint().insertPoint(pointPosition * 2).removeLeadingZeroes().removeTrailingZeroes();

        return answer;
    }



    public Binary divideBinary(Binary divisor)
    {
        StringBuilder sb = new StringBuilder();

        Binary quotient = new Binary();

        Binary dividend = new Binary(this.binaryString);

        divisor = divisor.removeTrailingZeroes();

        // Add placeholder zeroes behind radix point of dividend so
        // both divisor and dividend have same number of digits after point
        divisor.addPlaceholders(dividend);
        dividend = dividend.removeLeadingZeroes();

        int divisorPointPosition = divisor.getPointPosition();

        // Shift radix point of the divisor to the right until it is the last char
        int numShifts = 0;
        while (divisorPointPosition != 0)
        {
            divisor = divisor.shiftPointRightByOne();

            numShifts++;

            divisorPointPosition = divisor.getPointPosition();
        }

        // Append a zero to the divisor so in Binary form
        divisor = divisor.appendZero();

        // Shift the radix point of the dividend to the right numShift times
        for (int i = 0; i < numShifts; i++)
        {
            dividend = dividend.shiftPointRightByOne();
        }

        // If the dividend has no zero behind the radix point, append one
        if (dividend.getPointPosition() == 0)
        {
            dividend = dividend.appendZero();
        }



        System.out.println("dividend");
        System.out.println(dividend);

        return quotient;
    }



    public Binary onesComplement()
    {
        int binaryLength = this.binaryString.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryLength; i++)
        {
            if (this.binaryString.charAt(i) == '1')
            {
                sb.append('0');
            }

            else if (this.binaryString.charAt(i) == '0')
            {
                sb.append('1');
            }

            else if (this.binaryString.charAt(i) == '.')
            {
                sb.append('.');
            }
        }

        return new Binary(sb.toString());
    }



    public Binary twosComplement()
    {
        StringBuilder sb = new StringBuilder();
        Binary answer;
        Binary one = new Binary ("0.0");

        answer = this.onesComplement();

        answer.addPlaceholders(one);

        sb.append(one).reverse().deleteCharAt(0).insert(0, '1').reverse();

        one.binaryString = sb.toString();

        answer = answer.addBinary(one);

        return answer;
    }



    public Decimal binaryToDecimal()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.binaryString);

        double sum = 0;

        String reversedBinaryString = sb.reverse().toString();

        for (int i = 0; i < reversedBinaryString.length(); i++)
        {
            if (reversedBinaryString.charAt(i) == '1')
            {
                sum += Math.pow(2, i);
            }
        }

        return new Decimal(sum);
    }



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



    public Binary insertPoint(int pointPosition)
    {
        Binary currentBinary = new Binary(this.binaryString);

        StringBuilder sb = new StringBuilder();

        currentBinary.removePoint();

        sb.append(currentBinary);

        // Insert point at pointPosition
        sb.reverse().insert(pointPosition, '.').reverse();

        currentBinary.binaryString = sb.toString();

        return currentBinary;
    }



    public int getDigitsBeforePoint()
    {
        int numDigits = 0;

        Binary currentBinary = new Binary(this.binaryString);

        while (numDigits < currentBinary.binaryString.length()
                && currentBinary.binaryString.charAt(numDigits) != '.')
        {
            numDigits++;
        }

        return numDigits;
    }



    public void addPlaceholders(Binary inBinary)
    {
        Binary currentBinary = new Binary(this.binaryString);

        StringBuilder sb = new StringBuilder();

        int currentBinaryPointPosition = currentBinary.getPointPosition();
        int inBinaryPointPosition = inBinary.getPointPosition();

        int aDigitsBeforePoint = currentBinary.getDigitsBeforePoint();
        int bDigitsBeforePoint = inBinary.getDigitsBeforePoint();
        // If needed, add placeholder zeroes so both Octals
        // have same number of digits in front of point
        if (aDigitsBeforePoint > bDigitsBeforePoint)
        {
            sb.append("0".repeat(aDigitsBeforePoint - bDigitsBeforePoint));
            sb.append(inBinary);

            inBinary.binaryString = sb.toString();

            sb.setLength(0);
        }

        else if (bDigitsBeforePoint > aDigitsBeforePoint)
        {
            sb.append("0".repeat(bDigitsBeforePoint - aDigitsBeforePoint));
            sb.append(currentBinary);

            currentBinary.binaryString = sb.toString();

            sb.setLength(0);
        }



        // If needed, add placeholder zeroes so both Binary strings
        // have same number of digits behind point
        if (currentBinaryPointPosition > inBinaryPointPosition)
        {
            sb.append(inBinary);

            sb.append("0".repeat(currentBinaryPointPosition - inBinaryPointPosition));

            inBinary.binaryString = sb.toString();
        }

        else
        {
            sb.append(currentBinary);

            sb.append("0".repeat(inBinaryPointPosition - currentBinaryPointPosition));

            currentBinary.binaryString = sb.toString();
        }
    }



    public Binary removeLeadingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        sb.append(currentBinary);

        if (sb.toString().charAt(0) == '0')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }

            currentBinary.binaryString = sb.toString();
        }

        return currentBinary;
    }



    public Binary removeTrailingZeroes()
    {
        StringBuilder sb = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        sb.append(currentBinary).reverse();

        if (sb.toString().charAt(0) == '0' && sb.toString().charAt(2) != '.')
        {
            while (sb.toString().charAt(0) == '0')
            {
                sb.deleteCharAt(0);
            }
        }

        currentBinary.binaryString = sb.reverse().toString();

        return currentBinary;
    }



    public Binary appendZero()
    {
        StringBuilder sb = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        sb.append(currentBinary).append('0');

        currentBinary.binaryString = sb.toString();

        return currentBinary;
    }



    public Binary shiftPointRightByOne()
    {
        Binary currentBinary = new Binary(this.binaryString);

        int pointPosition = currentBinary.getPointPosition();

        currentBinary = currentBinary.removePoint().insertPoint(pointPosition - 1);

        return currentBinary;
    }



    public boolean lessThanBinary(Binary right)
    {
        Binary left = new Binary(this.binaryString);

        left = left.removeLeadingZeroes();
        right = right.removeLeadingZeroes();

        if (left.binaryString.length() < right.binaryString.length())
        {
            return true;
        }

        else if (left.binaryString.length() > right.binaryString.length())
        {
            return false;
        }

        else if (left.binaryString.equals(right.binaryString))
        {
            return false;
        }

        else
        {
            for (int i = 0; i < left.binaryString.length(); i++)
            {
                if (left.binaryString.charAt(i) == '1' &&
                        right.binaryString.charAt(i) == '0')
                {
                    return false;
                }
            }

            return true;
        }
    }



    @Override
    public String toString()
    {
        return binaryString;
    }

}
