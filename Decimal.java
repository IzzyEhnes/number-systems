package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;
import java.lang.Math;



public class Decimal
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
        return new Decimal(decimal + inDecimal.decimal);
    }



    public Decimal subtract(Decimal inDecimal)
    {
        return new Decimal(decimal - inDecimal.decimal);
    }



    public Decimal multiply(Decimal inDecimal)
    {
        return new Decimal(decimal * inDecimal.decimal);
    }



    public Decimal divide(Decimal inDecimal)
    {
        Decimal zero = new Decimal(0.0);

        if ((inDecimal.decimal).equals(0.0))
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else
        {
            return new Decimal(decimal / inDecimal.decimal);
        }
    }



    public Decimal addDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.add(inDecimal);

        return answer;
    }



    public Decimal subtractDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.subtract(inDecimal);

        return answer;
    }



    public Decimal multiplyDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.multiply(inDecimal);

        return answer;
    }



    public Decimal divideDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        try
        {
            answer = this.divide(inDecimal);
        }

        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
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