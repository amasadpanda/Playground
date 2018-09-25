// Sean Szumlanski
// COP 3503, Fall 2017

// =========================
// TopoPath: TestCase04.java
// =========================
// A brief test case to help ensure you've implemented the difficultyRating()
// and hoursSpent() methods correctly.


import java.io.*;
import java.util.*;

public class TestCase04
{
	private static void failwhale()
	{
		System.out.println("Test Case #4: fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args)
	{
		double difficulty = TopoPath.difficultyRating();
		if (difficulty < 1.0 || difficulty > 5.0) failwhale();

		double hours = TopoPath.hoursSpent();
		if (hours <= 0.0) failwhale();

		System.out.println("Test Case #4: PASS");
	}
}
