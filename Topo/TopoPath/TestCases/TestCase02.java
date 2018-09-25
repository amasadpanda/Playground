// Sean Szumlanski
// COP 3503, Fall 2017

// =========================
// TopoPath: TestCase02.java
// =========================
// This corresponds to graph G2 from the assignment PDF.


import java.io.*;

public class TestCase02
{
	public static void main(String [] args) throws IOException
	{
		System.out.print("Test Case #2: ");
		System.out.println((TopoPath.hasTopoPath("g2.txt") == false) ? "PASS" : "fail whale :(");
	}
}
