package Ehnes.Izzy.NumberSystems;

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




    @Override
    public String toString()
    {
        return binaryString;
    }

}
