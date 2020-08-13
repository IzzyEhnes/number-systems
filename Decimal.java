package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;
import java.lang.Math;



public class Decimal extends NumberSystem<Decimal>
{
    private Double decimal = 0.0;



    public Decimal()
    {
    }



    public Decimal(Double inDecimal)
    {
        decimal = inDecimal;
    }



    public double getDecimal()
    {
        return decimal;
    }



    public void setDecimal(Double inDouble)
    {
        this.decimal = inDouble;
    }



    public Decimal add(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer.decimal = inDecimal.decimal + this.decimal;

        return answer;
    }



    public Decimal subtract(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer.decimal = this.decimal - inDecimal.decimal;

        return answer;
    }



    public Decimal multiply(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer.decimal = this.decimal * inDecimal.decimal;

        return answer;
    }



    public Decimal divide(Decimal inDecimal, int scale)
    {
        Decimal answer = new Decimal();

        if ((inDecimal.decimal).equals(0.0))
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else if (this.decimal < 0)
        {
            throw new InvalidParameterException("Error: Cannot divide a negative Decimal.");
        }

        else
        {
            answer.decimal = this.decimal / inDecimal.decimal;
        }

        return answer;
    }



    public Binary decimalToBinary(int scale)
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
        String decimalString = decimalBuilder.toString();

        int integerValue = Integer.parseInt(integerString);

        // Find the maximum power of two that "fits" into the given integer
        int n = 1;
        while (integerValue % Math.pow(2, n) < integerValue)
        {
            n++;
        }

        n--;

        while (integerValue % Math.pow(2, n) >= 0 && n >= 0)
        {
            if (integerValue % Math.pow(2, n) < integerValue)
            {
                sb.append("1");
                integerValue %= Math.pow(2, n);
            }

            else
            {
                sb.append("0");
            }

            n--;
        }

        // If the Decimal is a whole number
        if (Double.parseDouble(decimalString) == 0)
        {
            sb.append(".0");
        }

        // If the Decimal has digits after the radix point
        else
        {
            // Convert decimal fraction part to binary
            String answer = sb.append('.').toString();

            sb.setLength(0);

            sb.append("0.").append(decimalString);

            decimalString = sb.toString();

            sb.setLength(0);

            double temp = Double.parseDouble(decimalString);

            int count = 0;
            while (temp != 0 && count < scale)
            {
                temp *= 2;

                sb.append(String.valueOf(temp).charAt(0));

                if (temp >= 1)
                {
                    temp -= 1;
                }

                count++;
            }

            decimalString = sb.toString();

            sb.setLength(0);

            sb.append(answer).append(decimalString);
        }

        return new Binary(sb.toString());
    }



    public Octal decimalToOctal(int scale)
    {
        StringBuilder sb = new StringBuilder();

        String inString = String.valueOf(this.decimal);

        StringBuilder integerBuilder = new StringBuilder();
        StringBuilder fractionBuilder = new StringBuilder();

        boolean negativeFlag = false;
        if (inString.charAt(0) == '-')
        {
            negativeFlag = true;

            sb.append(inString).deleteCharAt(0);

            inString = sb.toString();

            sb.setLength(0);
        }

        boolean radixFlag = false;
        for (int i = 0; i < inString.length(); i++)
        {
            if (inString.charAt(i) == '.')
            {
                radixFlag = true;
                fractionBuilder.append("0.");
                continue;
            }

            if (!radixFlag)
            {
                integerBuilder.append(inString.charAt(i));
            }

            else
            {
                fractionBuilder.append(inString.charAt(i));
            }
        }

        String integerString = integerBuilder.toString();
        String fractionString = fractionBuilder.toString();

        int integerValue = Integer.parseInt(integerString);
        double fractionValue = Double.parseDouble(fractionString);

        StringBuilder answerBuilder = new StringBuilder();

        // Convert the number in front of the radix point into Octal
        int multiple = integerValue / 8;
        int remainder = integerValue % 8;
        answerBuilder.append(remainder);

        while (multiple != 0)
        {
            remainder = multiple % 8;

            multiple /= 8;

            answerBuilder.append(remainder);
        }

        String answer = answerBuilder.reverse().toString();

        // If the Decimal is a whole number
        if (Double.parseDouble(fractionString) == 0)
        {
            answerBuilder.append(".0");

            answer = answerBuilder.toString();
        }

        // If the Decimal has digits after the radix point
        else
        {
            answerBuilder.append('.');

            double temp = Double.parseDouble(fractionString);

            int count = 0;
            while (temp != 0 && count < scale)
            {
                temp *= 8;

                answerBuilder.append(String.valueOf(temp).charAt(0));

                if (temp >= 1)
                {
                    temp -= String.valueOf(temp).charAt(0) - '0';
                }

                count++;
            }

            answer = answerBuilder.toString();
        }

        if (negativeFlag)
        {
            answerBuilder.insert(0, '-');

            answer = answerBuilder.toString();
        }

        return new Octal(answer);
    }



    public Hexadecimal decimalToHexadecimal(int scale)
    {
        StringBuilder answerBuilder = new StringBuilder();

        Hexadecimal temp = new Hexadecimal(String.valueOf(this.decimal));

        double tempDecimal = this.decimal;

        int intPart = (int) tempDecimal;

        double fractionPart = tempDecimal - intPart;

        boolean appendZero = false;
        if (fractionPart == 0)
        {
            appendZero = true;
        }

        double thisDouble = tempDecimal - fractionPart;

        int pointPosition = temp.getDigitsBeforePoint();

        int wholeNum = 0;
        double remainder = 0.0;

        while (thisDouble > 15)
        {
            thisDouble /= 16;
        }

        // Get the number in front of the radix point and append it to answerBuilder
        wholeNum = (int) thisDouble;

        answerBuilder.append(wholeNum);

        // Get the number behind the radix point
        remainder = thisDouble - wholeNum;

        int currentNum = 0;
        int digitsAfterPoint = 0;
        while (remainder != 0 && digitsAfterPoint < scale)
        {
            remainder *= 16;

            currentNum = (int) remainder;

            remainder -= currentNum;

            answerBuilder.append(temp.getKeyFromValue(hexMap, currentNum));

            digitsAfterPoint++;
        }

        digitsAfterPoint = 0;
        while (fractionPart != 0 && digitsAfterPoint < scale)
        {
            fractionPart *= 16;

            currentNum = (int) fractionPart;

            fractionPart -= currentNum;

            answerBuilder.append(temp.getKeyFromValue(hexMap, currentNum));

            digitsAfterPoint++;
        }

        Hexadecimal answer = new Hexadecimal(answerBuilder.toString());

        answer = answer.insertPointFromRight(digitsAfterPoint);

        if (appendZero)
        {
            answer = answer.appendZero();
        }

        return answer;
    }



    @Override
    public String toString()
    {
        return Double.toString(decimal);
    }



    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        Decimal myDecimal = (Decimal) obj;

        if (decimal == myDecimal.decimal)
        {
            System.out.println("WHY NOT???");
        }

        return (decimal == myDecimal.decimal);
    }
}