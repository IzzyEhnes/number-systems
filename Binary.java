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

        inBinary = inBinary.twosComplement();

        sb.append(this.addBinary(inBinary));

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
        String a = sb.toString();

        // Reset sb
        sb.setLength(0);

        sb.append(inBinary.binaryString);
        sb.reverse();
        String b = sb.toString();

        int aLength = a.length();
        int bLength = b.length();

        sb.setLength(0);



        // Add placeholder zeroes to smaller string so both binary strings are
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

        for (int i = 0; i < aLength; i++)
        {
            // Add placeholder zeroes
            sb.append("0".repeat(i));

            for (int j = 0; j < bLength; j++)
            {
                sb.append((a.charAt(j) - '0') * (b.charAt(i) - '0'));
            }

            binaryTemp.binaryString = sb.reverse().toString();

            answer = answer.addBinary(binaryTemp);
            sb.delete(0, bLength + i);
        }

        return answer;
    }



    public Binary divideBinary(Binary inBinary)
    {
        StringBuilder sb = new StringBuilder();

        Binary quotient = new Binary();

        sb.append(this);
        Binary dividend = new Binary(sb.toString());

        sb.setLength(0);

        sb.append(inBinary);

        Binary divisor = new Binary(sb.toString());

        sb.setLength(0);

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
        }

        return new Binary(sb.toString());
    }



    public Binary twosComplement()
    {
        Binary answer;
        Binary one = new Binary ("1");
        answer = this.onesComplement();
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



    public void addPlaceholders(Binary inBinary)
    {
        Binary currentBinary = new Binary(this.binaryString);

        StringBuilder sb = new StringBuilder();

        int currentBinaryPointPosition = currentBinary.getPointPosition();
        int inBinaryPointPosition = inBinary.getPointPosition();

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



    @Override
    public String toString()
    {
        return binaryString;
    }

}
