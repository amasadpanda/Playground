// Sean Szumlanski
// COP 3503, Fall 2017

// =========================
// TopoPath: TestCase01.java
// =========================
// This corresponds to graph G1 from the assignment PDF.


import java.io.*;

public class TestCase01
{
	public static void main(String [] args) throws IOException
	{
		System.out.print("Test Case #1: ");
		System.out.println((TopoPath.hasTopoPath("g1.txt") == false) ? "PASS" : "fail whale :(");
	}
}
