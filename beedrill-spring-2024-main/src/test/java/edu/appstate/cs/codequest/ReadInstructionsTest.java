package edu.appstate.cs.codequest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadInstructionsTest {

	@Test
	public void testResourcePath() {
		assertEquals(Resource.resourcePath + "instructions/", ReadInstructions.filePathStr);
	}

	@Test
	public void testReadInstructionZero() {
		String[] expected = {
				"Hello and Welcome to Code Quest!",
				"Code Quest is an interactive learning platform",
				"that teaches basic coding logic in a game format." };

		String output = ReadInstructions.readFile("0");

		String[] lines = output.split("\n");

		assertEquals(expected[0], lines[0], expected[0] + " recieved " + lines[0]);
		assertEquals(expected[1], lines[1], expected[1] + " recieved " + lines[1]);
		assertEquals(expected[2], lines[2], expected[2] + " recieved " + lines[2]);
	}

	@Test
	public void testReadInstructionOne() {
		String[] expected = {
				"Level 1",
				"",
				"When you program, you are essentially giving" };

		String output = ReadInstructions.readFile("1");

		String[] lines = output.split("\n");

		assertEquals(expected[0], lines[0], expected[0] + " recieved " + lines[0]);
		assertEquals(expected[1], lines[1], expected[1] + " recieved " + lines[1]);
		assertEquals(expected[2], lines[2], expected[2] + " recieved " + lines[2]);
	}
}
