package Ehnes.Izzy.NumberSystems;

import java.lang.Math;
import java.security.InvalidParameterException;
import java.util.HashMap;

public class Binary extends NumberSystem<Binary>
{
    private String binaryString = "0.0";

    HashMap<String, Character> binaryToHexMap = new HashMap<>();



    public Binary()
    {
        binaryToHexMap.put("-", '-');
        binaryToHexMap.put(".", '.');
        binaryToHexMap.put("0000", '0');
        binaryToHexMap.put("0001", '1');
        binaryToHexMap.put("0010", '2');
        binaryToHexMap.put("0011", '3');
        binaryToHexMap.put("0100", '4');
        binaryToHexMap.put("0101", '5');
        binaryToHexMap.put("0110", '6');
        binaryToHexMap.put("0111", '7');
        binaryToHexMap.put("1000", '8');
        binaryToHexMap.put("1001", '9');
        binaryToHexMap.put("1010", 'A');
        binaryToHexMap.put("1011", 'B');
        binaryToHexMap.put("1100", 'C');
        binaryToHexMap.put("1101", 'D');
        binaryToHexMap.put("1110", 'E');
        binaryToHexMap.put("1111", 'F');
    }



    public Binary(String inString)
    {
        binaryString = inString;

        binaryToHexMap.put("-", '-');
        binaryToHexMap.put(".", '.');
        binaryToHexMap.put("0000", '0');
        binaryToHexMap.put("0001", '1');
        binaryToHexMap.put("0010", '2');
        binaryToHexMap.put("0011", '3');
        binaryToHexMap.put("0100", '4');
        binaryToHexMap.put("0101", '5');
        binaryToHexMap.put("0110", '6');
        binaryToHexMap.put("0111", '7');
        binaryToHexMap.put("1000", '8');
        binaryToHexMap.put("1001", '9');
        binaryToHexMap.put("1010", 'A');
        binaryToHexMap.put("1011", 'B');
        binaryToHexMap.put("1100", 'C');
        binaryToHexMap.put("1101", 'D');
        binaryToHexMap.put("1110", 'E');
        binaryToHexMap.put("1111", 'F');
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



    public Binary add(Binary inBinary)
    {
        Binary answer = new Binary();
        StringBuilder sb = new StringBuilder();
        int carry = 0;

        Binary currentBinary = new Binary(this.binaryString);
        Binary addend = new Binary(inBinary.binaryString);

        addend.addPlaceholders(currentBinary);
        currentBinary.addPlaceholders(addend);

        int currentBinaryPointPosition = getPointPosition(currentBinary.binaryString);
        int addendPointPosition = getPointPosition(addend.binaryString);

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
            answer = answer.insertPointFromRight(currentBinaryPointPosition);
        }

        else
        {
            answer = answer.insertPointFromRight(addendPointPosition);
        }

        return answer;
    }


    public Binary subtract(Binary inBinary)
    {
        Binary answer = new Binary();

        StringBuilder sb = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        currentBinary.addPlaceholders(inBinary);
        inBinary.addPlaceholders(currentBinary);

        inBinary = inBinary.twosComplement();

        sb.append(currentBinary.add(inBinary));

        // if there was overflow, remove leftmost bit
        if (sb.length() > answer.binaryString.length())
        {
            sb.deleteCharAt(0);
        }

        answer.binaryString = sb.toString();

        answer = answer.removeLeadingZeroes();

        return answer;
    }


    public Binary multiply(Binary inBinary)
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

        int pointPosition = getDigitsBeforePoint(multiplicand.binaryString);

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

            answer = answer.add(binaryTemp);
            sb.delete(0, bLength + i);
        }

        answer = answer.removePoint().insertPointFromRight(pointPosition * 2).removeLeadingZeroes().removeTrailingZeroes();

        return answer;
    }



    public Binary divide(Binary divisor, int scale)
    {
        Binary quotient = new Binary();

        Binary dividend = new Binary(this.binaryString);

        if (Double.parseDouble(divisor.binaryString) == 0)
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        if (Double.parseDouble(divisor.binaryString) == 1)
        {
            return dividend;
        }

        // Add placeholder zeroes behind radix point of dividend so
        // both divisor and dividend have same number of digits after point
        divisor.addPlaceholders(dividend);
        dividend = dividend.removeLeadingZeroes();

        divisor = divisor.removeTrailingZeroes();

        int divisorPointPosition = getPointPosition(divisor.binaryString);

        // Shift radix point of the divisor to the right until it is the last char
        int numShifts = 0;
        while (divisorPointPosition != 0)
        {
            divisor = divisor.shiftPointRightByOne();

            numShifts++;

            divisorPointPosition = getPointPosition(divisor.binaryString);
        }

        // Append a zero to the divisor so in Binary form
        divisor = divisor.appendZero();

        // Shift the radix point of the dividend to the right numShift times
        for (int i = 0; i < numShifts; i++)
        {
            dividend = dividend.shiftPointRightByOne();
        }

        // How many digits will be in front of the radix point in the final quotient (including placeholder zeroes)
        int dividendDigitsBeforePoint = getDigitsBeforePoint(dividend.binaryString);

        dividend = dividend.removePoint().insertPointFromRight(0).appendZero();

        StringBuilder quotientBuilder = new StringBuilder();
        quotient = quotient.removeLeadingZeroes();

        Binary one = new Binary("1.0");
        Binary zero = new Binary("0.0");

        Binary product = new Binary();

        // Append first digit of dividend to currentDividend
        Binary currentDividend = new Binary();
        currentDividend = currentDividend.removeLeadingZeroes().removeTrailingZeroes();

        StringBuilder dividendBuilder = new StringBuilder();
        dividendBuilder.append(currentDividend).insert(0, dividend.binaryString.charAt(0));
        currentDividend.binaryString = dividendBuilder.toString();

        // Reset dividendBuilder
        dividendBuilder.setLength(0);

        if (divisor.lessThanBinary(currentDividend))
        {
            quotientBuilder.append(quotient).insert(0, '1');
            quotient.binaryString = quotientBuilder.toString();

            // Reset quotientBuilder
            quotientBuilder.setLength(0);

            currentDividend = currentDividend.subtract(product);
            currentDividend = currentDividend.removeLeadingZeroes();
        }

        else
        {
            quotientBuilder.append(quotient).insert(0, '0');
            quotient.binaryString = quotientBuilder.toString();

            // Reset quotientBuilder
            quotientBuilder.setLength(0);

            currentDividend = currentDividend.subtract(product);
            currentDividend = currentDividend.removeLeadingZeroes();
        }

        int currentDividendDigitsBeforePoint = 0;

        int currentIndex = 1;
        while (Double.parseDouble(currentDividend.binaryString) != Double.parseDouble(divisor.binaryString) &&
                currentIndex < (dividendDigitsBeforePoint + scale))
        {
            dividend = dividend.appendZero();

            if (dividend.binaryString.charAt(currentIndex) == '.')
            {
                currentIndex++;
            }

            // "Bring down" the next digit of the dividend to add to currentDividend, placing it to the left of the radix point
            currentDividendDigitsBeforePoint = getDigitsBeforePoint(currentDividend.binaryString);
            dividendBuilder.append(currentDividend);
            dividendBuilder.insert(currentDividendDigitsBeforePoint, dividend.binaryString.charAt(currentIndex));
            currentDividend.binaryString = dividendBuilder.toString();

            // Reset dividendBuilder
            dividendBuilder.setLength(0);

            // If the divisor "fits" into currentDividend, but divisor != currentDividend
            if (divisor.lessThanBinary(currentDividend))
            {
                // Place a zero in the correct place in the quotient
                quotientBuilder.append(quotient);
                quotientBuilder.reverse().insert(2, '1').reverse();
                quotient.binaryString = quotientBuilder.toString();

                // Reset quotientBuilder
                quotientBuilder.setLength(0);

                product = divisor.multiply(one);

                currentDividend = currentDividend.subtract(product);
            }

            // When divisor == currentDividend, the division has been completed (no remainder)
            else if (Double.parseDouble(currentDividend.binaryString) == Double.parseDouble(divisor.binaryString))
            {
                // Place a one in the correct place in the quotient
                quotientBuilder.append(quotient);
                quotientBuilder.reverse().insert(2, '1').reverse();
                quotient.binaryString = quotientBuilder.toString();

                // Reset quotientBuilder
                quotientBuilder.setLength(0);
            }

            // If the divisor doesn't 'fit' into currendDividend
            else
            {
                // Place a zero in the correct place in the quotient
                quotientBuilder.append(quotient);
                quotientBuilder.reverse().insert(2, '0').reverse();
                quotient.binaryString = quotientBuilder.toString();

                // Reset quotientBuilder
                quotientBuilder.setLength(0);

                product = divisor.multiply(zero);

                currentDividend = currentDividend.subtract(product);
            }

            currentIndex++;
        }

        quotient = quotient.removePoint().insertPointFromLeft(dividendDigitsBeforePoint).removeTrailingZeroes().removeLeadingZeroes();

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

        answer = answer.add(one);

        return answer;
    }



    public Decimal binaryToDecimal()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this);

        String inString = sb.toString();

        sb.setLength(0);

        StringBuilder integerBuilder = new StringBuilder();
        StringBuilder decimalBuilder = new StringBuilder();

        boolean decimalFlag = false;
        for (int i = 0; i < inString.length(); i++)
        {
            if (inString.charAt(i) == '.')
            {
                decimalFlag = true;
                decimalBuilder.append("0.");
                continue;
            }

            if (!decimalFlag)
            {
                integerBuilder.append(inString.charAt(i));
            }

            else
            {
                decimalBuilder.append(inString.charAt(i));
            }
        }

        String integerString = integerBuilder.toString();
        String fractionString = decimalBuilder.toString();

        double integerSum = 0;
        String reversedIntegerString = integerBuilder.reverse().toString();
        for (int i = 0; i < reversedIntegerString.length(); i++)
        {
            if (reversedIntegerString.charAt(i) == '1')
            {
                integerSum += Math.pow(2, i);
            }
        }

        // If the Binary is a whole number
        if (Double.parseDouble(fractionString) == 0)
        {
            return new Decimal(integerSum);
        }

        // If the Binary has digits after the radix point
        else
        {
            sb.append(fractionString).delete(0, 2).append(".0").reverse();

            fractionString = sb.toString();

            double fractionSum = 0;
            double temp = 0;
            for (int i = 2; i < fractionString.length(); i++)
            {
                fractionSum = 0.5 * (fractionSum + (fractionString.charAt(i) - '0'));
            }

            integerSum += fractionSum;
        }

        return new Decimal(integerSum);
    }



    public Octal binaryToOctal()
    {
        String inString = this.binaryString;

        StringBuilder wholeBuilder = new StringBuilder();
        StringBuilder fractionalBuilder = new StringBuilder();

        boolean decimalFlag = false;
        for (int i = 0; i < inString.length(); i++)
        {
            if (inString.charAt(i) == '.')
            {
                decimalFlag = true;
                continue;
            }

            if (!decimalFlag)
            {
                wholeBuilder.append(inString.charAt(i));
            }

            else
            {
                fractionalBuilder.append(inString.charAt(i));
            }
        }

        String wholeNum = wholeBuilder.toString();
        String fractional = fractionalBuilder.toString();

        while (wholeNum.length() % 3 != 0)
        {
            wholeBuilder.insert(0,'0');

            wholeNum = wholeBuilder.toString();
        }

        int radixPosition = wholeNum.length() / 3;

        while (fractional.length() % 3 != 0)
        {
            fractionalBuilder.insert(fractional.length(),'0');

            fractional = fractionalBuilder.toString();
        }

        StringBuilder binaryStringBuilder = new StringBuilder();

        binaryStringBuilder.append(wholeNum).append(fractional);
        String binaryString = binaryStringBuilder.toString();

        StringBuilder answerBuilder = new StringBuilder();

        int groupSum = 0;
        int bitCount = 0;
        for (int i = 0; i < binaryString.length(); i++)
        {
            if (bitCount == 0)
            {
                if (binaryStringBuilder.toString().charAt(0) == '1')
                {
                    groupSum += 4;
                }
            }

            if (bitCount == 1)
            {
                if (binaryStringBuilder.toString().charAt(1) == '1')
                {
                    groupSum += 2;
                }
            }

            if (bitCount == 2)
            {
                if (binaryStringBuilder.toString().charAt(2) == '1')
                {
                    groupSum += 1;
                }
            }

            bitCount++;

            if (bitCount == 3)
            {
                answerBuilder.append(groupSum);

                binaryStringBuilder.delete(0, 3);

                groupSum = 0;
                bitCount = 0;
            }
        }

        answerBuilder.insert(radixPosition, '.');

        return new Octal(answerBuilder.toString());
    }



    public Hexadecimal binaryToHexadecimal()
    {
        StringBuilder nibbleBuilder = new StringBuilder();
        StringBuilder answerBuilder = new StringBuilder();

        Binary currentBinary = new Binary(this.binaryString);

        boolean negative = false;
        if (currentBinary.isNegative())
        {
            negative = true;

            StringBuilder tempSB = new StringBuilder();

            tempSB.append(currentBinary);
            tempSB.deleteCharAt(0);

            currentBinary.binaryString = tempSB.toString();
        }

        currentBinary = currentBinary.makeGroupsOfNibbles();

        int pointPosition = getDigitsBeforePoint(currentBinary.binaryString) / 4;

        currentBinary = currentBinary.removePoint();

        String nibble = "";

        int bitCount = 0;
        for (int i = 0; i < currentBinary.binaryString.length(); i++)
        {
            nibbleBuilder.append(currentBinary.binaryString.charAt(i));

            bitCount++;

            if (bitCount == 4)
            {
                nibble = nibbleBuilder.toString();

                answerBuilder.append(binaryToHexMap.get(nibble));

                nibbleBuilder.setLength(0);

                bitCount = 0;
            }
        }

        Hexadecimal answer = new Hexadecimal(answerBuilder.toString());

        answer = answer.insertPointFromLeft(pointPosition);

        if (negative)
        {
            answer = answer.insertNegativeSign();
        }

        return answer;
    }



    public Binary removePoint()
    {
        Binary currentBinary = new Binary(this.binaryString);

        int pointPosition = getPointPosition(currentBinary.binaryString);

        StringBuilder sb = new StringBuilder();

        sb.append(currentBinary);
        sb.reverse().deleteCharAt(pointPosition).reverse();

        currentBinary.binaryString = sb.toString();

        return currentBinary;
    }



    public Binary insertPointFromRight(int pointPosition)
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



    public Binary insertPointFromLeft(int pointPosition)
    {
        Binary currentBinary = new Binary(this.binaryString);

        StringBuilder sb = new StringBuilder();

        currentBinary.removePoint();

        sb.append(currentBinary);

        // Insert point at pointPosition
        sb.insert(pointPosition, '.');

        currentBinary.binaryString = sb.toString();

        return currentBinary;
    }



    public void addPlaceholders(Binary inBinary)
    {
        Binary currentBinary = new Binary(this.binaryString);

        StringBuilder sb = new StringBuilder();

        int currentBinaryPointPosition = getPointPosition(currentBinary.binaryString);
        int inBinaryPointPosition = getPointPosition(inBinary.binaryString);

        int aDigitsBeforePoint = getDigitsBeforePoint(currentBinary.binaryString);
        int bDigitsBeforePoint = getDigitsBeforePoint(inBinary.binaryString);
        // If needed, add placeholder zeroes so both Binarys
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

        if (sb.toString().charAt(0) == '0')
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



    public boolean isNegative()
    {
        if (this.binaryString.charAt(0) == '-')
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    public Binary shiftPointRightByOne()
    {
        Binary currentBinary = new Binary(this.binaryString);

        int pointPosition = getPointPosition(currentBinary.binaryString);

        currentBinary = currentBinary.removePoint().insertPointFromRight(pointPosition - 1);

        return currentBinary;
    }



    public boolean lessThanBinary(Binary right)
    {
        Binary left = new Binary(this.binaryString);

        left = left.removeLeadingZeroes();
        right = right.removeLeadingZeroes();

        left.addPlaceholders(right);
        right.addPlaceholders(left);

        if (left.binaryString.equals(right.binaryString))
        {
            return false;
        }

        else
        {
            for (int i = 0; i < left.binaryString.length(); i++)
            {
                if (left.binaryString.charAt(i) == '1' && right.binaryString.charAt(i) == '0')
                {
                    return false;
                }

                else if (right.binaryString.charAt(i) == '1' && left.binaryString.charAt(i) == '0')
                {
                    return true;
                }
            }

            return true;
        }
    }



    public Binary makeGroupsOfNibbles()
    {
        StringBuilder answerBuilder = new StringBuilder();

        StringBuilder integerBuilder = new StringBuilder();
        StringBuilder fractionalBuilder = new StringBuilder();

        boolean decimalFlag = false;
        for (int i = 0; i < this.binaryString.length(); i++)
        {
            if (this.binaryString.charAt(i) == '.')
            {
                decimalFlag = true;
                continue;
            }

            if (!decimalFlag)
            {
                integerBuilder.append(this.binaryString.charAt(i));
            }

            else
            {
                fractionalBuilder.append(this.binaryString.charAt(i));
            }
        }

        String integerString = integerBuilder.toString();
        String fractionString = fractionalBuilder.toString();

        // If the integer part is comprised of already defined groups of nibbles
        if (integerString.length() % 4 == 0)
        {
            answerBuilder.append(integerString);
        }

        else
        {
            // While the integer part of the Binary string cannot be broken up
            // into groups of nibbles, add a placeholder zero(es) to the front
            while (integerBuilder.toString().length() % 4 != 0)
            {
                integerBuilder.insert(0, '0');
            }

            answerBuilder.append(integerBuilder);
        }

        answerBuilder.append('.');

        if (fractionString.length() % 4 == 0)
        {
            answerBuilder.append(fractionString);
        }

        else
        {
            // While the integer part of the Binary string cannot be broken up
            // into groups of nibbles, add a placeholder zero(es) to the front
            while (fractionalBuilder.toString().length() % 4 != 0)
            {
                fractionalBuilder.append('0');
            }

            answerBuilder.append(fractionalBuilder);
        }

        return new Binary(answerBuilder.toString());
    }



    @Override
    public String toString()
    {
        return binaryString;
    }

}