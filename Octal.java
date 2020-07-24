package Ehnes.Izzy.NumberSystems;

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

        int aDecimalPosition = currentOctal.getDecimalPosition();
        int bDecimalPosition = inOctal.getDecimalPosition();

        // Add placeholder zeroes so the Octals "line up"
        if (aDecimalPosition > bDecimalPosition)
        {
            inOctal = currentOctal.addPlaceholders(inOctal);

            // After addPlaceholders method call, aDecimalPosition = bDecimalPosition
            bDecimalPosition = inOctal.getDecimalPosition();
        }

        else if (bDecimalPosition > aDecimalPosition)
        {
            currentOctal = inOctal.addPlaceholders(currentOctal);

            // After addPlaceholders method call, aDecimalPosition = bDecimalPosition
            aDecimalPosition = currentOctal.getDecimalPosition();
        }

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



    public Octal sevensComplement()
    {
        int decimalPosition = this.getDecimalPosition();

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



    public int getDecimalPosition()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(this.octal);

        String a = sb.reverse().toString();

        // Find decimal position with respect to rightmost digit
        int decimalPosition = 0;
        for (int i = 0; i < a.length(); i++)
        {
            if (a.charAt(i) == '.')
            {
                decimalPosition = i;
            }
        }

        return decimalPosition;
    }



    public Octal addPlaceholders(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        int aDecimalPosition = this.getDecimalPosition();
        int bDecimalPosition = inOctal.getDecimalPosition();

        if (aDecimalPosition > bDecimalPosition)
        {
            sb.append(inOctal.octal);

            sb.append("0".repeat(aDecimalPosition - bDecimalPosition));

            inOctal.octal = sb.toString();

            return inOctal;
        }

        else
        {
            sb.append(this.octal);

            sb.append("0".repeat(bDecimalPosition - aDecimalPosition));

            this.octal = sb.toString();

            return this;
        }
    }



    @Override
    public String toString()
    {
        return octal;
    }
}
