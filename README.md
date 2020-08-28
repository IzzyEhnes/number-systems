# Number System Calculator and Converter
A Java program to perform mathematical calculations and conversions of the decimal, binary, octal, and hexadecimal number systems.

## Table of Contents
- [Project Description](#project-description)
- [Motivation](#motivation)
- [Technologies](#technologies)
- [The Number System Class](#number-system-class)
- [The Binary Class](#binary-class)
- [The Octal Class](#octal-class)
- [The Decimal Class](#decimal-class)
- [The Hexadecimal Class](#hexadecimal-class)

## Project Description
This project contains classes that represent the four major types of number systems: binary (base-2), octal (base-8), decimal (base-10), and hexadecimal (base-16). These classes allow for the addition, subtraction, multiplication, and division of Binary, Octal, Decimal, and Hexadecimal objects, as well as the conversion of objects to and from these number systems.

## Motivation
This project was intended to be a personal study of the various algorithms used to perform mathematical calculations and conversions of the four major number systems, as well as for me to practice converting somewhat complex mathematical procedures into code (and to keep myself busy during my free time of the summer of COVID-19 in the process!). 

For example, I know it'd probably be more efficient to multiply two Octals by first converting them into base-10, multiplying them, and then converting them back into base-8, rather than implementing the fairly convoluted Octal multiplication process step by step... but where's the fun and discovery in that? ;)

## Technologies
- Language: Java
- JDK: jdk-11.0.8
- Compiler: Javac

## The Number System Class
The NumberSystem class is an abstract class that is the parent of the Binary, Octal, Decimal, and Hexadecimal classes. Being abstract, it cannot be instantiated, and instead contains both abstract and non-abstract methods and HashMaps that provide the default implementations utilized by its child classes.

## The Binary Class
The Binary class represents the base-2 number system, and can perform addition, subtraction, multiplication, and division of Binary objects. Binary objects are made up of a String, binaryString, which is comprised of a radix point ('.') surrounded by at least one digit on each side; having a base of two, the digits of a Binary object can only be 0 or 1. Binary objects can also be converted to Octal, Decimal, and Hexadecimal objects using the conversion methods contained within the class.

## The Octal Class
The Octal class represents the base-8 number system. An Octal object is made up of a String, octalString, which contains a radix point ('.') surrounded by at least one digit (which can be the numbers 0-7) on each side. Octal objects can be added, subtracted, multiplied, and divided, as well as converted into the other three main number systems.

## The Decimal Class
This class represents the most common number system, the decimal system (base-10). A Decimal object is comprised of a Double, decimal, and the basic mathematical operations of addition, subtraction, multiplication, and division, may be performed. Decimal objects can also be converted into base-2, base-8, and base-16 number systems using the conversion methods in the class.

## The Hexadecimal Class
The Hexadecimal class represents the base-16 number system, and can perform addition, subtraction, multiplication, and division of Hexadecimal objects. A Hexadecimal object is comprised of a String, hexString, which is made up of a radix point ('.') surrounded by at least one symbol (the digits 0-9 and/or letters A-F) on either side of the radix point. Hexadecimal objects can also be converted to Binary, Octal, and Decimal objects.