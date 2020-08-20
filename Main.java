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
        binaryPointTest();
        System.out.println("******************************************");
        addBinaryTest();
        System.out.println("******************************************");
        subtractBinaryTest();
        System.out.println("******************************************");
        multiplyBinaryTest();
        System.out.println("******************************************");
        divideBinaryTest();
        System.out.println("******************************************");
        lessThanBinaryTest();
        System.out.println("******************************************");
        binaryToDecimalTest();
        System.out.println("******************************************");
        decimalToOctalTest();
        System.out.println("******************************************");
        binaryToOctalTest();
        System.out.println("******************************************");
        octalToDecimalTest();
        System.out.println("******************************************");
        octalToBinaryTest();
        System.out.println("******************************************");
        isHexadecimalTest();
        System.out.println("******************************************");
        hexadecimalPointTest();
        System.out.println("******************************************");
        addHexadecimalTest();
        System.out.println("******************************************");
        lessThanHexadecimalTest();
        System.out.println("******************************************");
        subtractHexadecimalTest();
        System.out.println("******************************************");
        decimalToHexadecimalTest();
        System.out.println("******************************************");
        multiplyHexadecimalTest();
        System.out.println("******************************************");
        getLargestMultiplier();
        System.out.println("******************************************");
        hexadecimalToDecimalTest();
        System.out.println("******************************************");
        divideHexadecimalTest();
        System.out.println("******************************************");
        hexadecimalToBinaryTest();
        System.out.println("******************************************");
        //binaryToHexadecimalTest();
        System.out.println("******************************************");
        makeGroupsOfNibblesTest();
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
        System.out.print(d1.add(d2));

        System.out.println("\n\nd3.addDecimal(d4)");
        System.out.println("Expected: -118.63");
        System.out.print("Returned value: ");
        System.out.print(d3.add(d4));

        System.out.println("\n\nd1.addDecimal(d3)");
        System.out.println("Expected: 9.75");
        System.out.print("Returned value: ");
        System.out.print(d1.add(d3));

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
        System.out.print(d1.subtract(d2));

        System.out.println("\n\nd3.subtractDecimal(d4)");
        System.out.println("Expected: 7.363");
        System.out.print("Returned value: ");
        System.out.print(d3.subtract(d4));

        System.out.println("\n\nd1.subtractDecimal(d3)");
        System.out.println("Expected: -12.463");
        System.out.print("Returned value: ");
        System.out.print(d1.subtract(d3));

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
        System.out.print(d1.multiply(d2));

        System.out.println("\n\nd2.multiplyDecimal(d3)");
        System.out.println("Expected: 35.19");
        System.out.print("Returned value: ");
        System.out.print(d2.multiply(d3));

        System.out.println("\n\nd3.multiplyDecimal(d4)");
        System.out.println("Expected: -79.764");
        System.out.print("Returned value: ");
        System.out.print(d3.multiply(d4));

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
        System.out.print(d2.divide(d3, 3));

        System.out.println("\n\nd4.divideDecimal(d3)");
        System.out.println("Expected: 21.27");
        System.out.print("Returned value: ");
        System.out.print(d4.divide(d3, 3));

        System.out.println("\n\nd3.divideDecimal(d4)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(d1.divide(d4, 3));

        System.out.print("\n\nEnd divideDecimalTest()\n");
    }



    public static void decimalToBinaryTest()
    {
        Decimal d1 = new Decimal(10.375);
        Decimal d2 = new Decimal(753.0);
        Decimal d3 = new Decimal(92.55);

        System.out.println("Begin decimalToBinaryTest():");

        System.out.println("\nd2.decimalToBinary()");
        System.out.println("Expected: 1010.011");
        System.out.print("Returned value: ");
        System.out.print(d1.decimalToBinary(5));

        System.out.println("\n\nd2.decimalToBinary()");
        System.out.println("Expected: 1011110001.0");
        System.out.print("Returned value: ");
        System.out.print(d2.decimalToBinary(5));

        System.out.println("\n\nd3.decimalToBinary(d4)");
        System.out.println("Expected: 1011100.10001100110011001101");
        System.out.print("Returned value: ");
        System.out.print(d3.decimalToBinary(20));

        System.out.print("\n\nEnd decimalToBinaryTest()\n");
    }



    public static void decimalToHexadecimalTest()
    {
        Decimal d1 = new Decimal(7803.0);
        Decimal d2 = new Decimal(36.89);
        Decimal d3 = new Decimal(4018.65312);
        Decimal d4 = new Decimal();
        Decimal d5 = new Decimal(16.0);
        Decimal d6 = new Decimal(48.0);
        Decimal d7 = new Decimal(171.0);

        System.out.println("Begin decimalToHexadecimalTest():");

        System.out.println("\nd1.decimalToHexadecimal()");
        System.out.println("Expected: 1E7B.0");
        System.out.print("Returned value: ");
        System.out.print(d1.decimalToHexadecimal(10));

        System.out.println("\n\nd3.decimalToHexadecimal()");
        System.out.println("Expected: 24.E3D70A");
        System.out.print("Returned value: ");
        System.out.print(d2.decimalToHexadecimal(6));

        System.out.println("\n\nd1.decimalToHexadecimal()");
        System.out.println("Expected: 0FB2.A732DF505D");
        System.out.print("Returned value: ");
        System.out.print(d3.decimalToHexadecimal(10));

        System.out.println("\n\nd4.decimalToHexadecimal()");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(d4.decimalToHexadecimal(1));

        System.out.println("\n\nd5.decimalToHexadecimal()");
        System.out.println("Expected: 10.0");
        System.out.print("Returned value: ");
        System.out.print(d5.decimalToHexadecimal(1));

        System.out.println("\n\nd6.decimalToHexadecimal()");
        System.out.println("Expected: C.0");
        System.out.print("Returned value: ");
        System.out.print(d6.decimalToHexadecimal(1));

        System.out.println("\n\nd7.decimalToHexadecimal()");
        System.out.println("Expected: AB.0");
        System.out.print("Returned value: ");
        System.out.print(d7.decimalToHexadecimal(1));

        System.out.print("\n\nEnd decimalToHexadecimalTest()\n");
    }



    public static void binaryPointTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("101.101");
        Binary b3 = new Binary("110.010101");
        Binary b4 = new Binary("1001011.0");
        Binary b5 = new Binary("0.001");


        System.out.println("Begin binaryPointTest():");

        //System.out.println("\n\nb4.getDigitsBeforePoint()");
        //System.out.println("Expected: 7");
        //System.out.print("Returned value: ");
        //System.out.print(b4.getDigitsBeforePoint());

        System.out.println("\n\nb2.AddPlaceholders(b4)");
        System.out.println("Expected: 101.101000");
        System.out.print("Returned value: ");
        b3.addPlaceholders(b2);
        System.out.print(b2);

        System.out.println("\n\nb1.AddPlaceholders(b3)");
        System.out.println("Expected: 0.000000");
        System.out.print("Returned value: ");
        b3.addPlaceholders(b1);
        System.out.print(b1);

        System.out.println("\n\nb3.AddPlaceholders(b4)");
        System.out.println("Expected: 0000110.010101");
        System.out.print("Returned value: ");
        b4.addPlaceholders(b3);
        System.out.print(b3);

        System.out.println("\n\nb5.shiftPointRightByOne()");
        System.out.println("Expected: 00.01");
        System.out.print("Returned value: ");
        System.out.println(b5.shiftPointRightByOne());

        System.out.print("\n\nEnd binaryPointTest()\n");
    }



    public static void addBinaryTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("101.1");
        Binary b3 = new Binary("010.0");
        Binary b4 = new Binary("1100.101");

        System.out.println("Begin addBinaryTest():");

        System.out.println("\nd2.add()");
        System.out.println("Expected: 111");
        System.out.print("Returned value: ");
        System.out.print(b2.add(b3));

        System.out.println("\n\nd4.add(d3)");
        System.out.println("Expected: 10010.001");
        System.out.print("Returned value: ");
        System.out.print(b2.add(b4));

        System.out.println("\n\nb1.add(b4)");
        System.out.println("Expected: 1100.101");
        System.out.print("Returned value: ");
        System.out.print(b1.add(b4));

        System.out.print("\n\nEnd addBinaryTest()\n");
    }



    public static void subtractBinaryTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("101.1");
        Binary b3 = new Binary("010.0");
        Binary b4 = new Binary("1100.101");

        System.out.println("Begin subtractBinaryTest():");

        System.out.println("\nd2.subtractBinary(b3)");
        System.out.println("Expected: 11.1");
        System.out.print("Returned value: ");
        System.out.print(b2.subtract(b3));

        System.out.println("\n\nb4.subtractBinary(b2)");
        System.out.println("Expected: 111.001");
        System.out.print("Returned value: ");
        System.out.print(b4.subtract(b2));

        System.out.println("\n\nb3.subtractBinary(b4)");
        System.out.println("Expected: 101.011");
        System.out.print("Returned value: ");
        System.out.print(b3.subtract(b4));

        System.out.print("\n\nEnd subtractBinaryTest()\n");
    }



    public static void multiplyBinaryTest()
    {
        Binary b1 = new Binary("0");
        Binary b2 = new Binary("101.0");
        Binary b3 = new Binary("010.0");
        Binary b4 = new Binary("1100.101");
        Binary b5 = new Binary("1111.11");
        Binary b6 = new Binary("10001.1");
        Binary b7 = new Binary("1101001.110011");

        System.out.println("Begin multiplyBinaryTest():");

        System.out.println("\nb2.multiplyBinary(b3)");
        System.out.println("Expected: 1010.00");
        System.out.print("Returned value: ");
        System.out.print(b2.multiply(b3));

        System.out.println("\n\nb4.multiplyBinary(b2)");
        System.out.println("Expected: 111111.001");
        System.out.print("Returned value: ");
        System.out.print(b4.multiply(b2));

        System.out.println("\n\nb2.multiplyBinary(b5)");
        System.out.println("Expected: 1001110.11");
        System.out.print("Returned value: ");
        System.out.print(b2.multiply(b5));

        System.out.println("\n\nb6.multiplyBinary(b7)");
        System.out.println("Expected: 11100111011.0111001");
        System.out.print("Returned value: ");
        System.out.print(b6.multiply(b7));

        System.out.println("\n\nb1.multiplyBinary(b4)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(b1.multiply(b4));

        System.out.print("\n\nEnd subtractBinaryTest()\n");
    }



    public static void divideBinaryTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("11.1");
        Binary b3 = new Binary("10010.011");
        Binary b4 = new Binary("1100.01");
        Binary b5 = new Binary("100.00");
        Binary b6 = new Binary("1.10");
        Binary b7 = new Binary("0.01");
        Binary b8 = new Binary("1010.10");

        System.out.println("Begin divideBinaryTest():");

        System.out.println("\nb3.divide(b2)");
        System.out.println("Expected: 101.01");
        System.out.print("Returned value: ");
        System.out.print(b3.divide(b2, 3));

        System.out.println("\n\nb4.divide(b2)");
        System.out.println("Expected: 11.1");
        System.out.print("Returned value: ");
        System.out.print(b4.divide(b2, 3));

        System.out.println("\n\nb5.divide(b6)");
        System.out.println("Expected: 10.10101");
        System.out.print("Returned value: ");
        System.out.print(b5.divide(b6, 6));

        System.out.println("\n\nb7.divide(b8)");
        System.out.println("Expected: 0.0000011");
        System.out.print("Returned value: ");
        System.out.print(b7.divide(b8, 8));

        System.out.print("\n\nEnd divideBinaryTest()\n");
    }



    public static void lessThanBinaryTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("11.1");
        Binary b3 = new Binary("10010.001");
        Binary b4 = new Binary("10010.001");
        Binary b5 = new Binary("10010.011");
        Binary b6 = new Binary("1001.0");
        Binary b7 = new Binary("111.0");

        System.out.println("Begin lessThanBinaryTest():");

        System.out.println("\nb1.lessThanBinary(b2)");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(b1.lessThanBinary(b2));

        System.out.println("\n\nb3.lessThanBinary(b2)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(b3.lessThanBinary(b2));

        System.out.println("\n\nb3.lessThanBinary(b4)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(b3.lessThanBinary(b4));

        System.out.println("\n\nb4.lessThanBinary(b5)");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(b4.lessThanBinary(b5));

        System.out.println("\n\nb5.lessThanBinary(b4)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(b5.lessThanBinary(b4));

        System.out.println("\n\nb5.lessThanBinary(b4)");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(b7.lessThanBinary(b6));

        System.out.print("\n\nEnd lessThanBinaryTest()\n");
    }



    public static void binaryToDecimalTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("10110.11");
        Binary b3 = new Binary("1101001011.1011");
        Binary b4 = new Binary("101.0");

        System.out.println("Begin binaryToDecimalTest():");

        System.out.println("\nb1.binaryToDecimal()");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(b1.binaryToDecimal());

        System.out.println("\n\nb2.binaryToDecimal()");
        System.out.println("Expected: 22.75");
        System.out.print("Returned value: ");
        System.out.print(b2.binaryToDecimal());

        System.out.println("\n\nb3.binaryToDecimal()");
        System.out.println("Expected: 843.6875");
        System.out.print("Returned value: ");
        System.out.print(b3.binaryToDecimal());

        System.out.println("\n\nb4.binaryToDecimal()");
        System.out.println("Expected: 5.0");
        System.out.print("Returned value: ");
        System.out.print(b4.binaryToDecimal());

        System.out.print("\n\nEnd lessThanBinaryTest()\n");
    }



    public static void decimalToOctalTest()
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(670.1640625);
        Decimal d3 = new Decimal(-23.0);

        System.out.println("Begin decimalToOctalTest():");

        System.out.println("\nd1.decimalToOctal(d2)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(d1.decimalToOctal(3));

        System.out.println("\n\nd2.decimalToOctal()");
        System.out.println("Expected: 1236.124");
        System.out.print("Returned value: ");
        System.out.print(d2.decimalToOctal(3));

        System.out.println("\n\nd3.decimalToOctal()");
        System.out.println("Expected: -27.0");
        System.out.print("Returned value: ");
        System.out.print(d3.decimalToOctal(3));

        System.out.print("\n\nEnd decimalToOctalTest()\n");
    }



    public static void binaryToOctalTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("10011011.00");
        Binary b3 = new Binary("101001101110.001011");
        Binary b4 = new Binary("11.1");

        System.out.println("Begin binaryToOctalTest():");

        System.out.println("\nd2.binaryToOctal()");
        System.out.println("Expected: 233.0");
        System.out.print("Returned value: ");
        System.out.print(b2.binaryToOctal());

        System.out.println("\n\nd4.binaryToOctal()");
        System.out.println("Expected: 5156.13");
        System.out.print("Returned value: ");
        System.out.print(b3.binaryToOctal());

        System.out.println("\n\nb1.binaryToOctal()");
        System.out.println("Expected: 3.4");
        System.out.print("Returned value: ");
        System.out.print(b4.binaryToOctal());

        System.out.print("\n\nEnd binaryToOctalTest()\n");
    }


/*
    public static void binaryToHexadecimalTest()
    {
        Binary b1 = new Binary();
        Binary b2 = new Binary("11.1");
        Binary b3 = new Binary("10010.001");

        System.out.println("Begin binaryToHexadecimalTest():");

        System.out.println("\nb2.binaryToHexadecimal()");
        System.out.println("Expected: 0011.1000");
        System.out.print("Returned value: ");
        System.out.print(b1.binaryToHexadecimal());

        /*
        System.out.println("\n\nb3.lessThanBinary(b2)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(b3.lessThanBinary(b2));
         */
       // System.out.print("\n\nEnd lessThanBinaryTest()\n");
   // }




    public static void octalToDecimalTest()
    {
        Octal b1 = new Octal();
        Octal b2 = new Octal("345.67");
        Octal b3 = new Octal("-12.1");
        Octal b4 = new Octal("61317.42631");

        System.out.println("Begin octalToDecimalTest():");

        System.out.println("\nd2.octalToDecimal()");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(b1.octalToDecimal());

        System.out.println("\n\nd2.octalToDecimal()");
        System.out.println("Expected: 229.859375");
        System.out.print("Returned value: ");
        System.out.print(b2.octalToDecimal());

        System.out.println("\n\nd4.octalToDecimal()");
        System.out.println("Expected: -10.125");
        System.out.print("Returned value: ");
        System.out.print(b3.octalToDecimal());

        System.out.println("\n\nb1.octalToDecimal()");
        System.out.println("Expected: 25295.543731689453125");
        System.out.print("Returned value: ");
        System.out.print(b4.octalToDecimal());

        System.out.print("\n\nEnd octalToDecimalTest()\n");
    }



    public static void octalToBinaryTest()
    {
        Octal b1 = new Octal();
        Octal b2 = new Octal("305.4");
        Octal b3 = new Octal("12.1");
        Octal b4 = new Octal("61317.42631");

        System.out.println("Begin octalToBinaryTest():");

        System.out.println("\nd2.octalToBinary()");
        System.out.println("Expected: 000.000");
        System.out.print("Returned value: ");
        System.out.print(b1.octalToBinary());

        System.out.println("\n\nd2.octalToBinary()");
        System.out.println("Expected: 011000101.100");
        System.out.print("Returned value: ");
        System.out.print(b2.octalToBinary());

        System.out.println("\n\nd4.octalToBinary()");
        System.out.println("Expected: 001010.001");
        System.out.print("Returned value: ");
        System.out.print(b3.octalToBinary());

        System.out.println("\n\nb1.octalToBinary()");
        System.out.println("Expected: 110001011001111.100010110011001");
        System.out.print("Returned value: ");
        System.out.print(b4.octalToBinary());

        System.out.print("\n\nEnd octalToBinaryTest()\n");
    }



    public static void isHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("A9.F");
        Hexadecimal h3 = new Hexadecimal("8H6.EBC");
        Hexadecimal h4 = new Hexadecimal("D3F1.A02");
        Hexadecimal h5 = new Hexadecimal("LP.QW");

        System.out.println("Begin isHexadecimalTest():");

        System.out.println("\nh1.isHexadecimal()");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(h1.isHexadecimal());

        System.out.println("\n\nh2.isHexadecimal()");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(h2.isHexadecimal());

        System.out.println("\n\nh3.isHexadecimal()");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(h3.isHexadecimal());

        System.out.println("\n\nh4.isHexadecimal()");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(h4.isHexadecimal());

        System.out.println("\n\nh5.isHexadecimal()");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(h5.isHexadecimal());

        System.out.print("\n\nEnd isHexadecimalTest()\n");
    }



    public static void hexadecimalPointTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("F52B.9E");
        Hexadecimal h3 = new Hexadecimal("A8.04");
        Hexadecimal h4 = new Hexadecimal("24C7DFB.3");
        Hexadecimal h5 = new Hexadecimal("B.90FC5C");


        System.out.println("Begin hexadecimalPointTest():");

        //System.out.println("\n\nh4.getDigitsBeforePoint()");
        //System.out.println("Expected: 7");
        //System.out.print("Returned value: ");
        //System.out.print(h4.getDigitsBeforePoint());

        System.out.println("\n\nh3.removePoint()");
        System.out.println("Expected: A804");
        System.out.print("Returned value: ");
        System.out.print(h3.removePoint());

        System.out.println("\n\nh4.AddPlaceholders(h5)");
        System.out.println("Expected: 000000B.90FC5C");
        System.out.print("Returned value: ");
        h4.addPlaceholders(h5);
        System.out.print(h5);

        System.out.println("\n\nh5.AddPlaceholders(h4)");
        System.out.println("Expected: 24C7DFB.300000");
        System.out.print("Returned value: ");
        h5.addPlaceholders(h4);
        System.out.print(h4);

        System.out.println("\n\nh2.AddPlaceholders(h1)");
        System.out.println("Expected: 0000.00");
        System.out.print("Returned value: ");
        h2.addPlaceholders(h1);
        System.out.print(h1);

        System.out.print("\n\nEnd hexadecimalPointTest()\n");
    }



    public static void addHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("4AD.9C2");
        Hexadecimal h3 = new Hexadecimal("F5.B");
        Hexadecimal h4 = new Hexadecimal("B.E");
        Hexadecimal h5 = new Hexadecimal("A.F");
        Hexadecimal h6 = new Hexadecimal("1.0");
        Hexadecimal h7 = new Hexadecimal("A.0");

        System.out.println("Begin addHexadecimalTest():");

        System.out.println("\nh2.addHexadecimal(h3)");
        System.out.println("Expected: 5A3.4C2");
        System.out.print("Returned value: ");
        System.out.print(h2.add(h3));


        System.out.println("\n\nh4.addHexadecimal(h5)");
        System.out.println("Expected: 16.D");
        System.out.print("Returned value: ");
        System.out.print(h4.add(h5));

        System.out.println("\n\nh2.addHexadecimal(h5)");
        System.out.println("Expected: 4B8.8C2");
        System.out.print("Returned value: ");
        System.out.print(h2.add(h5));

        System.out.println("\n\nh1.addHexadecimal(h3)");
        System.out.println("Expected: F5.B");
        System.out.print("Returned value: ");
        System.out.print(h1.add(h3));

        System.out.println("\n\nh6.addHexadecimal(h7)");
        System.out.println("Expected: B.0");
        System.out.print("Returned value: ");
        System.out.print(h6.add(h7));

        System.out.print("\n\nEnd isHexadecimalTest()\n");
    }



    public static void lessThanHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("4AD.9C2");
        Hexadecimal h3 = new Hexadecimal("4AD.9C2");
        Hexadecimal h4 = new Hexadecimal("B.E");
        Hexadecimal h5 = new Hexadecimal("A.F");
        Hexadecimal h6 = new Hexadecimal("4AD.9C1");
        Hexadecimal h7 = new Hexadecimal("FC.0");
        Hexadecimal h8 = new Hexadecimal("EC.0");

        System.out.println("Begin lessThanHexadecimal():");

        System.out.println("\nh1.lessThanHexadecimal(h2)");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(h1.lessThanHexadecimal(h2));

        System.out.println("\n\nh2.lessThanHexadecimal(h3)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(h2.lessThanHexadecimal(h3));

        System.out.println("\n\nh5.lessThanHexadecimal(h4)");
        System.out.println("Expected: true");
        System.out.print("Returned value: ");
        System.out.print(h5.lessThanHexadecimal(h4));

        System.out.println("\n\nh3.lessThanHexadecimal(h6)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(h3.lessThanHexadecimal(h6));

        System.out.println("\n\nh7.lessThanHexadecimal(h8)");
        System.out.println("Expected: false");
        System.out.print("Returned value: ");
        System.out.print(h7.lessThanHexadecimal(h8));

        System.out.print("\n\nEnd lessThanHexadecimalTest()\n");
    }



    public static void subtractHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("4AD.9C2");
        Hexadecimal h3 = new Hexadecimal("F5.B");
        Hexadecimal h4 = new Hexadecimal("7E5AF.13");
        Hexadecimal h5 = new Hexadecimal("92BE.0DFF");

        System.out.println("Begin subtractHexadecimalTest():");

        System.out.println("\nh2.subtractHexadecimal(h3)");
        System.out.println("Expected: 3B7.EC2");
        System.out.print("Returned value: ");
        System.out.print(h2.subtract(h3));

        System.out.println("\n\nh4.subtractHexadecimal(h5)");
        System.out.println("Expected: 752F1.0501");
        System.out.print("Returned value: ");
        System.out.print(h4.subtract(h5));

        System.out.println("\n\nh3.subtractHexadecimal(h4)");
        System.out.println("Expected: -7E4B9.63");
        System.out.print("Returned value: ");
        System.out.print(h3.subtract(h4));

        System.out.println("\n\nh1.subtractHexadecimal(h5)");
        System.out.println("Expected: -92BE.0DFF");
        System.out.print("Returned value: ");
        System.out.print(h1.subtract(h5));

        System.out.print("\n\nEnd isHexadecimalTest()\n");
    }



    public static void multiplyHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("2F.0");
        Hexadecimal h3 = new Hexadecimal("A5.1C");
        Hexadecimal h4 = new Hexadecimal("4A.1C");
        Hexadecimal h5 = new Hexadecimal("15.3");
        Hexadecimal h6 = new Hexadecimal("9D4E053.FA5");
        Hexadecimal h7 = new Hexadecimal("BC5A.082D1FE67");
        Hexadecimal h8 = new Hexadecimal("4E053.FA5");
        Hexadecimal h9 = new Hexadecimal("BC5A.082D1FE");
        Hexadecimal h10 = new Hexadecimal("D2.0");
        Hexadecimal h11 = new Hexadecimal("1200.0");
        Hexadecimal h12 = new Hexadecimal("12.0");
        Hexadecimal h13 = new Hexadecimal("59.0");

        System.out.println("Begin multiplyHexadecimalTest():");

        System.out.println("\nh2.multiplyHexadecimal(h3)");
        System.out.println("Expected: 1E50.240");
        System.out.print("Returned value: ");
        System.out.print(h3.multiply(h2));

        System.out.println("\n\nh4.multiplyHexadecimal(h5)");
        System.out.println("Expected: 622.314");
        System.out.print("Returned value: ");
        System.out.print(h4.multiply(h5));

        System.out.println("\n\nh6.multiplyHexadecimal(h7)");
        System.out.println("Expected: 73BC9E4EC00.6F6E58F75163");
        System.out.print("Returned value: ");
        System.out.print(h6.multiply(h7));

        System.out.println("\n\nh1.multiplyHexadecimal(h5)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(h1.multiply(h5));

        System.out.println("\n\nh8.multiplyHexadecimal(h9)");
        System.out.println("Expected: 39674B137.6A23F4DAB6");
        System.out.print("Returned value: ");
        System.out.print(h8.multiply(h9));

        System.out.println("\n\nh10.multiplyHexadecimal(h11)");
        System.out.println("Expected: EC400.0");
        System.out.print("Returned value: ");
        System.out.print(h10.multiply(h11));

        System.out.println("\n\nh10.multiplyHexadecimal(h11)");
        System.out.println("Expected: 642.0");
        System.out.print("Returned value: ");
        System.out.print(h13.multiply(h12));

        System.out.print("\n\nEnd isHexadecimalTest()\n");
    }



    public static void getLargestMultiplier()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("2.0");
        Hexadecimal h3 = new Hexadecimal("E.0");
        Hexadecimal h4 = new Hexadecimal("15B.0");
        Hexadecimal h5 = new Hexadecimal("F.0");
        Hexadecimal h6 = new Hexadecimal("1043.0");
        Hexadecimal h7 = new Hexadecimal("59.0");
        Hexadecimal h8 = new Hexadecimal("1043.0");
        Hexadecimal h9 = new Hexadecimal("59.0");

        System.out.println("Begin getLargestMultiplierTest():");

        System.out.println("\nh2.getLargestMultiplier(h3)");
        System.out.println("Expected: 7.0");
        System.out.print("Returned value: ");
        System.out.print(h2.getLargestMultiplier(h2, h3));


        System.out.println("\n\nh4.getLargestMultiplier(h5)");
        System.out.println("Expected: 17.0");
        System.out.print("Returned value: ");
        System.out.print(h4.getLargestMultiplier(h5, h4));

        System.out.println("\n\nh6.getLargestMultiplier(h7)");
        System.out.println("Expected: 2E.0");
        System.out.print("Returned value: ");
        System.out.print(h6.getLargestMultiplier(h7, h6));

        System.out.println("\n\nh6.getLargestMultiplier(h7)");
        System.out.println("Expected: 0.0");
        System.out.print("Returned value: ");
        System.out.print(h8.getLargestMultiplier(h8, h9));

        System.out.print("\n\nEnd getLargestMultiplierTest()\n");
    }



    public static void hexadecimalToDecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("ECF.0");
        Hexadecimal h3 = new Hexadecimal("7D0A.9B");

        System.out.println("Begin hexadecimalToDecimalTest():");

        System.out.println("\nh2.hexadecimalToDecimal()");
        System.out.println("Expected: 3791.0");
        System.out.print("Returned value: ");
        System.out.print(h2.hexadecimalToDecimal());

        System.out.println("\n\nh3.hexadecimalToDecimal()");
        System.out.println("Expected: 32010.60546875");
        System.out.print("Returned value: ");
        System.out.print(h3.hexadecimalToDecimal());

        System.out.print("\n\nEnd hexadecimalToDecimalTest()\n");
    }



    public static void divideHexadecimalTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("EA.0");
        Hexadecimal h3 = new Hexadecimal("4.0");
        Hexadecimal h4 = new Hexadecimal("F9.0");
        Hexadecimal h5 = new Hexadecimal("35.0");
        Hexadecimal h6 = new Hexadecimal("E.570");
        Hexadecimal h7 = new Hexadecimal("B.A");
        Hexadecimal h8 = new Hexadecimal("0.2B");
        Hexadecimal h9 = new Hexadecimal("35FD.916A");

        System.out.println("Begin divideHexadecimalTest():");

        System.out.println("\nh2.divideHexadecimal(h3)");
        System.out.println("Expected: 3A.8");
        System.out.print("Returned value: ");
        System.out.print(h2.divide(h3, 3));

        System.out.println("\n\nh2.divideHexadecimal(h3)");
        System.out.println("Expected: 4.B2B78C13521");
        System.out.print("Returned value: ");
        System.out.print(h4.divide(h5, 10));

        System.out.println("\n\nh4.divideHexadecimal(h5)");
        System.out.println("Expected: 1.3BC8F23C8F23");
        System.out.print("Returned value: ");
        System.out.print(h6.divide(h7, 12));

        System.out.println("\n\nh3.divideHexadecimal(h4)");
        System.out.println("Expected: 0.0000CBE341");
        System.out.print("Returned value: ");
        System.out.print(h8.divide(h9, 10));

        System.out.print("\n\nEnd divideHexadecimalTest()\n");
    }



    public static void hexadecimalToBinaryTest()
    {
        Hexadecimal h1 = new Hexadecimal();
        Hexadecimal h2 = new Hexadecimal("EA.53");
        Hexadecimal h3 = new Hexadecimal("6D12F.A04C");
        Hexadecimal h4 = new Hexadecimal("-B25.80E");
        Hexadecimal h5 = new Hexadecimal();

        System.out.println("Begin hexadecimalToBinaryTest():");

        System.out.println("\nh2.hexadecimalToBinary()");
        System.out.println("Expected: 11101010.01010011");
        System.out.print("Returned value: ");
        System.out.print(h2.hexadecimalToBinary());

        System.out.println("\n\nh2.hexadecimalToBinary()");
        System.out.println("Expected: 1101101000100101111.1010000001001100");
        System.out.print("Returned value: ");
        System.out.print(h3.hexadecimalToBinary());

        System.out.println("\n\nh4.hexadecimalToBinary()");
        System.out.println("Expected: -101100100101.1000000011100");
        System.out.print("Returned value: ");
        System.out.print(h4.hexadecimalToBinary());

        System.out.println("\n\nh3.hexadecimalToBinary(h4)");
        System.out.println("Expected: 0000.0000");
        System.out.print("Returned value: ");
        System.out.print(h5.hexadecimalToBinary());

        System.out.print("\n\nEnd divideHexadecimalTest()\n");
    }



    public static void makeGroupsOfNibblesTest()
    {
        Binary h1 = new Binary();
        Binary h2 = new Binary("11.1");
        Binary h3 = new Binary("1011.0110");
        Binary h4 = new Binary("100010110.1011100011");

        System.out.println("Begin makeGroupsOfNibblesTest():");

        System.out.println("\nh2.makeGroupsOfNibbles()");
        System.out.println("Expected: 0011.1000");
        System.out.print("Returned value: ");
        System.out.print(h2.makeGroupsOfNibbles());

        System.out.println("\n\nh3.makeGroupsOfNibbles()");
        System.out.println("Expected: 1011.0110");
        System.out.print("Returned value: ");
        System.out.print(h3.makeGroupsOfNibbles());

        System.out.println("\n\nh4.makeGroupsOfNibbles()");
        System.out.println("Expected: 000100010110.101110001100");
        System.out.print("Returned value: ");
        System.out.print(h4.makeGroupsOfNibbles());

        System.out.print("\n\nEnd divideHexadecimalTest()\n");
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
