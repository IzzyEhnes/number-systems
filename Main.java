package Ehnes.Izzy.NumberSystems;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("******************************************");
        addDecimalTest();
        System.out.println("******************************************");
        subtractDecimalTest();
        System.out.println("******************************************");
        multiplyDecimalTest();
        System.out.println("******************************************");
        divideDecimalTest();
        System.out.println("******************************************");
        decimalToBinaryTest();
        System.out.println("******************************************");
    }


    public static void addDecimalTest()
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(2.5);
        Decimal d3 = new Decimal(9.75);
        Decimal d4 = new Decimal(-128.38);

        System.out.println("Begin addDecimalTest():");

        System.out.println("\nd1.addDecimal(d2)");
        System.out.println("Expected: 2.5");
        System.out.print("Returned value: ");
        System.out.print(d1.addDecimal(d2));

        System.out.println("\n\nd3.addDecimal(d4)");
        System.out.println("Expected: -118.63");
        System.out.print("Returned value: ");
        System.out.print(d3.addDecimal(d4));

        System.out.println("\n\nd1.addDecimal(d3)");
        System.out.println("Expected: 9.75");
        System.out.print("Returned value: ");
        System.out.print(d1.addDecimal(d3));

        System.out.print("\n\nEnd addDecimalTest()\n");
    }



    public static void subtractDecimalTest()
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(86.19);
        Decimal d3 = new Decimal(12.463);
        Decimal d4 = new Decimal(5.1);

        System.out.println("Begin subtractDecimalTest():");

        System.out.println("\nd1.subtractDecimal(d2)");
        System.out.println("Expected: -86.19");
        System.out.print("Returned value: ");
        System.out.print(d1.subtractDecimal(d2));

        System.out.println("\n\nd3.subtractDecimal(d4)");
        System.out.println("Expected: 7.363");
        System.out.print("Returned value: ");
        System.out.print(d3.subtractDecimal(d4));

        System.out.println("\n\nd1.subtractDecimal(d3)");
        System.out.println("Expected: -12.463");
        System.out.print("Returned value: ");
        System.out.print(d1.subtractDecimal(d3));

        System.out.print("\n\nEnd subtractDecimalTest()\n");
    }



    public static void multiplyDecimalTest()
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(2.25);
        Decimal d3 = new Decimal(15.64);
        Decimal d4 = new Decimal(-5.1);

        System.out.println("Begin multiplyDecimalTest():");

        System.out.println("\nd1.multiplyDecimal(d2)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(d1.multiplyDecimal(d2));

        System.out.println("\n\nd2.multiplyDecimal(d3)");
        System.out.println("Expected: 35.19");
        System.out.print("Returned value: ");
        System.out.print(d2.multiplyDecimal(d3));

        System.out.println("\n\nd3.multiplyDecimal(d4)");
        System.out.println("Expected: -79.764");
        System.out.print("Returned value: ");
        System.out.print(d3.multiplyDecimal(d4));

        System.out.print("\n\nEnd multiplyDecimalTest()\n");
    }



    public static void divideDecimalTest()
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(1.25);
        Decimal d3 = new Decimal(63.25);
        Decimal d4 = new Decimal(1345.615);

        System.out.println("Begin divideDecimalTest():");

        System.out.println("\nd2.divideDecimal(d3)");
        System.out.println("Expected: 0.0197");
        System.out.print("Returned value: ");
        System.out.print(d2.divideDecimal(d3));

        System.out.println("\n\nd4.divideDecimal(d3)");
        System.out.println("Expected: 21.27");
        System.out.print("Returned value: ");
        System.out.print(d4.divideDecimal(d3));

        System.out.println("\n\nd3.divideDecimal(d4)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(d1.divideDecimal(d4));

        System.out.print("\n\nEnd divideDecimalTest()\n");
    }



    public static void decimalToBinaryTest()
    {
        Decimal d1 = new Decimal(10.0);
        Decimal d2 = new Decimal(753.0);
        Decimal d3 = new Decimal(92.00);

        System.out.println("Begin decimalToBinaryTest():");

        System.out.println("\nd2.decimalToBinary()");
        System.out.println("Expected: 1010");
        System.out.print("Returned value: ");
        System.out.print(d1.decimalToBinary());

        System.out.println("\n\nd4.decimalToBinary(d3)");
        System.out.println("Expected: 1011110001");
        System.out.print("Returned value: ");
        System.out.print(d2.decimalToBinary());

        System.out.println("\n\nd3.decimalToBinary(d4)");
        System.out.println("Expected: 1011100");
        System.out.print("Returned value: ");
        System.out.print(d3.decimalToBinary());

        System.out.print("\n\nEnd decimalToBinaryTest()\n");
    }




    /*
        System.out.println("Tests for Decimal class");

        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(2.5);
        Decimal d3 = new Decimal(9.5);
        Decimal d4 = new Decimal(50.0);
        Decimal d5 = new Decimal(64.0);
        Decimal d6 = new Decimal(140.7);
        Decimal d7 = new Decimal(261.98);

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d2.addDecimal(d3));
        System.out.println(d2.subtractDecimal(d3));
        System.out.println(d2.multiplyDecimal(d3));
        System.out.println(d2.divideDecimal(d3));
        // System.out.println(d2.divideDecimal(d1)); // should throw divide by zero error

        System.out.println("\ndecimalToBinary: ");
        System.out.println(d3.decimalToBinary());
        System.out.println(d4.decimalToBinary());
        System.out.println(d5.decimalToBinary());
        System.out.println(d6.decimalToBinary());
        System.out.println(d7.decimalToBinary());





        System.out.println("\nTests for Binary class");

        Binary b1 = new Binary();
        Binary b2 = new Binary("101");
        Binary b3 = new Binary("010");
        Binary b4 = new Binary("100101");
        Binary b5 = new Binary("10101");
        Binary b6 = new Binary("1111");
        Binary b7 = new Binary("1111");
        Binary b8 = new Binary("11100");
        Binary b9 = new Binary("0012001");

        System.out.println("\nisBinary: ");
        System.out.println(b8.isBinary());
        System.out.println(b9.isBinary());

        System.out.println("\naddBinary: ");
        System.out.println(b2.addBinary(b3));
        System.out.println(b5.addBinary(b4));
        System.out.println(b6.addBinary(b7));

        System.out.println("\nonesComplement: ");
        System.out.println(b4.onesComplement());
        System.out.println(b6.onesComplement());

        System.out.println("\ntwosComplement: ");
        System.out.println(b8.twosComplement());
        System.out.println(b2.twosComplement());
        System.out.println(b3.twosComplement());
        System.out.println(b6.twosComplement());

        System.out.println("\nsubtractBinary: ");
        System.out.println(b2.subtractBinary(b3));
        System.out.println(b4.subtractBinary(b5));

        System.out.println("\nmultiplyBinary: ");
        System.out.println(b2.multiplyBinary(b3));
        System.out.println(b3.multiplyBinary(b2));
        System.out.println(b2.multiplyBinary(b8));

        System.out.println("\nbinaryToDecimal: ");
        System.out.println(b2.binaryToDecimal());
        System.out.println(b4.binaryToDecimal());





        System.out.println("\nTests for Octal class");
        Octal o1 = new Octal();
        Octal o2 = new Octal("-40.0");
        Octal o3 = new Octal("-30.0");
        //Octal o2 = new Octal("23.14");
        //Octal o3 = new Octal("6.35");
        Octal o4 = new Octal("1.0");
        Octal o5 = new Octal("-13.62");
        Octal o6 = new Octal("-10.0");
        Octal o7 = new Octal("-345.0");
        Octal o8 = new Octal("146.0");

        o1.setOctal("-3.0");

        System.out.println("\nisNegative: ");
        System.out.println(o1.isNegative());
        System.out.println(o7.isNegative());
        System.out.println(o5.isNegative());
        System.out.println(o8.isNegative());

        System.out.println("\naddOctal: ");
        System.out.println(o1.addOctal(o2));
        System.out.println(o4.addOctal(o5));
        System.out.println(o6.addOctal(o7));

        System.out.println("\nisOctal: ");
        System.out.println(o1.isOctal());
        System.out.println(o2.isOctal());
        System.out.println(o3.isOctal());
        System.out.println(o8.isOctal());

        //System.out.println("\nsevensComplement: ");
        //System.out.println(o3);
        //System.out.println(o3.sevensComplement());
        //System.out.println(o5);
        //System.out.println(o5.sevensComplement());

        //System.out.println("\neightsComplement: ");
        //System.out.println(o3);
        //System.out.println(o3.eightsComplement());
        //System.out.println(o5);
        //System.out.println(o5.eightsComplement());

        System.out.println("\nsubtractOctal: ");
        System.out.println(o2.subtractOctal(o3));
        System.out.println(o4.subtractOctal(o5));
        System.out.println(o7.subtractOctal(o6));
        System.out.println(o7.subtractOctal(o8));

        System.out.println("\nmultiplyOctal: ");
        System.out.println(o2.multiplyOctal(o3));
        System.out.println(o4.multiplyOctal(o5));
        System.out.println(o7.multiplyOctal(o6));
        System.out.println(o7.multiplyOctal(o8));

        Octal o9 = new Octal("524.104");
        Octal o10 = new Octal("23.75");

        //System.out.println("\ndivideOctal: ");
        //System.out.println(o9.divideOctal(o10, 3));

    }

     */
}
