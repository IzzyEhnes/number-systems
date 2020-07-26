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



    public Octal subtractOctal(Octal inOctal)
    {
        StringBuilder sb = new StringBuilder();

        Octal eightsComplement = inOctal.eightsComplement();
        System.out.println("\neightsComplement: ");
        System.out.println(eightsComplement.octal);

        Octal answer = new Octal();

        //System.out.println(eightsComplement.octal);

        sb.append(eightsComplement.addOctal(this));
        //System.out.println(sb.toString());
        //answer.octal = sb.toString();

        //System.out.println("\nanswer: ");
        //System.out.println(answer.octal);
        //System.out.println("answer length: ");
        //System.out.println(answer.octal.length());

        System.out.println("inOctal: ");
        System.out.println(inOctal.octal);
        System.out.println("inOctal length: ");
        System.out.println(inOctal.octal.length());
        System.out.println("this: ");
        System.out.println(this.octal);
        System.out.println("this length: ");
        System.out.println(this.octal.length());
        System.out.println("END OF SUBTRACTOCTAL");

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



    public Octal multiplyOctal(Octal inOctal)
    {
        Octal currentOctal = new Octal();
        Octal answer = new Octal();
        currentOctal = this;

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

        int arraySize = aLength + bLength - 1;
        int currentIndex = arraySize - 1;
        int result = 0;
        int[] sumArray = new int[arraySize];

        // Do standard long multiplication (without carrying)
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

        int[] closestMultipleOfEight = new int[arraySize];

        // Fill array with the largest multiple of eight that "fits"
        // into each individual element in sumArray, adding the power
        // of eight (n) associated with said multiple of eight to
        // the element to the left of the current index
        for (int i = arraySize - 1; i > 0; i--)
        {
            int n = getClosestPowerOfEight(sumArray[i]);
            closestMultipleOfEight[i] = n * 8;

            sumArray[i-1] += n;
        }

        // Find the difference of both arrays
        for (int i = 0; i < arraySize; i++)
        {
            sumArray[i] -= closestMultipleOfEight[i];
        }

        // Add answer to sb
        for (int i = 0; i < arraySize; i++)
        {
            // Add decimal point
            if (i == (arraySize - (aDecimalPosition + bDecimalPosition)))
            {
                sb.append('.');
            }

            sb.append(sumArray[i]);
        }

        return new Octal(sb.toString());
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



    @Override
    public String toString()
    {
        return octal;
    }
}
