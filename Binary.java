package Ehnes.Izzy.NumberSystems;

import java.lang.Math;

public class Binary
{
    private String binaryString = "";



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
            if (this.binaryString.charAt(i) != '0' && this.binaryString.charAt(i) != '1')
            {
                return false;
            }
        }

        return true;
    }



    public Binary addBinary(Binary inBinary)
    {
        Binary answer = new Binary();
        String reverseAnswer = "";
        StringBuilder sb = new StringBuilder();
        int aLength = this.binaryString.length() - 1;
        int bLength = inBinary.binaryString.length() - 1;
        int carry = 0;

        while (aLength >= 0 || bLength >= 0)
        {
            int sum = carry;

            if (aLength >= 0)
            {
                sum += this.binaryString.charAt(aLength--) - '0';
            }

            if (bLength >= 0)
            {
                sum += inBinary.binaryString.charAt(bLength--) - '0';
            }

            sb.append(sum % 2);

            carry = sum / 2;
        }

        if (carry != 0)
        {
            sb.append(carry);
        }

        answer.binaryString = sb.reverse().toString();

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

        if (b.length() > a.length())
        {
            String stringTemp = a;
            a = b;
            b = stringTemp;
        }

        int aLength = a.length();
        int bLength = b.length();

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



    @Override
    public String toString()
    {
        return binaryString;
    }

}
