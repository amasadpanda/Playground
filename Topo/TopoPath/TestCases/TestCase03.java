// Sean Szumlanski
// COP 3503, Fall 2017

// =========================
// TopoPath: TestCase03.java
// =========================
// This corresponds to graph G3 from the assignment PDF.


import java.io.*;

public class TestCase03
{
	public static void main(String [] args) throws IOException
	{
		System.out.print("Test Case #3: ");
		System.out.println((TopoPath.hasTopoPath("g3.txt") == true) ? "PASS" : "fail whale :(");
	}
}
