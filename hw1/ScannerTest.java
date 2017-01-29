package cop5556sp17;

import static cop5556sp17.Scanner.Kind.SEMI;
import static cop5556sp17.Scanner.Kind.EQUAL;
import static cop5556sp17.Scanner.Kind.*;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp17.Scanner.IllegalCharException;
import cop5556sp17.Scanner.IllegalNumberException;

public class ScannerTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();


	
	@Test
	public void testEmpty() throws IllegalCharException, IllegalNumberException {
		String input = "";
		Scanner scanner = new Scanner(input);
		scanner.scan();
	}

	@Test
	public void testSemiConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = ";;;";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(SEMI, token.kind);
		assertEquals(0, token.pos);
		String text = SEMI.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(SEMI, token1.kind);
		assertEquals(1, token1.pos);
		assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(SEMI, token2.kind);
		assertEquals(2, token2.pos);
		assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token3.kind);
	}
	
	
	/**
	 * This test illustrates how to check that the Scanner detects errors properly. 
	 * In this test, the input contains an int literal with a value that exceeds the range of an int.
	 * The scanner should detect this and throw and IllegalNumberException.
	 * 
	 * @throws IllegalCharException
	 * @throws IllegalNumberException
	 */
	@Test
	public void testIntOverflowError() throws IllegalCharException, IllegalNumberException, NumberFormatException{
		String input = "99999999999999999";
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalNumberException.class);
		//thrown.expect(NumberFormatException.class);
		scanner.scan();		
	}

//TODO  more tests
	
	@Test
	public void testEqualConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "====";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(EQUAL, token.kind);
		assertEquals(0, token.pos);
		String text = EQUAL.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(EQUAL, token1.kind);
		assertEquals(2, token1.pos);
		assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token3.kind);
	}
	
	@Test
	public void testSingleEqualConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "===";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(EQUAL, token.kind);
		assertEquals(0, token.pos);
		String text = EQUAL.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	
	@Test
	public void testDigitConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "98 070";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(INT_LIT, token.kind);
		assertEquals(0, token.pos);
		String text = INT_LIT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(INT_LIT, token1.kind);
		assertEquals(3, token1.pos);
		//assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(INT_LIT, token2.kind);
		assertEquals(4, token2.pos);
		//assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token3.kind);
	}
	
	@Test
	public void testIdentConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "abc D$_1 $12 ! {";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		//thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		String text1 = NOT.getText();
		String text2 = LBRACE.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(IDENT, token1.kind);
		assertEquals(4, token1.pos);
		//assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(IDENT, token2.kind);
		assertEquals(9, token2.pos);
		//assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(NOT, token3.kind);
		assertEquals(13, token3.pos);
		//assertEquals(text.length(), token3.length);
		assertEquals(text1, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(LBRACE, token4.kind);
		assertEquals(15, token4.pos);
		//assertEquals(text.length(), token4.length);
		assertEquals(text2, token4.getText());
		
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token5 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token5.kind);
	}
	
	
	
	@Test
	public void testEscapeStringConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "a\n Dfgh $12\txyz \r\nufo ishere";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		//thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(IDENT, token1.kind);
		assertEquals(3, token1.pos);
		//assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(IDENT, token2.kind);
		assertEquals(8, token2.pos);
		//assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(IDENT, token3.kind);
		assertEquals(12, token3.pos);
		//assertEquals(text.length(), token3.length);
		assertEquals(text, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(IDENT, token4.kind);
		assertEquals(18, token4.pos);
		//assertEquals(text.length(), token4.length);
		assertEquals(text, token4.getText());
		
		Scanner.Token token5 = scanner.nextToken();
		assertEquals(IDENT, token5.kind);
		assertEquals(22, token5.pos);
		//assertEquals(text.length(), token5.length);
		assertEquals(text, token5.getText());
		
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token6 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token6.kind);
	}
	
	
	
	@Test
	public void testEscapeString2Concat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "a\n Dfgh $12\txyz \r\nufo\rishere";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(IDENT, token1.kind);
		assertEquals(3, token1.pos);
		//assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(IDENT, token2.kind);
		assertEquals(8, token2.pos);
		//assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(IDENT, token3.kind);
		assertEquals(12, token3.pos);
		//assertEquals(text.length(), token3.length);
		assertEquals(text, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(IDENT, token4.kind);
		assertEquals(18, token4.pos);
		//assertEquals(text.length(), token4.length);
		assertEquals(text, token4.getText());
		
		Scanner.Token token5 = scanner.nextToken();
		assertEquals(IDENT, token5.kind);
		assertEquals(22, token5.pos);
		//assertEquals(text.length(), token5.length);
		assertEquals(text, token5.getText());
		
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token6 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token6.kind);
	}
	
	
	
	@Test
	public void testKeyWordConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "intergerrrr integer widtH height";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		assertEquals(11, token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(KW_INTEGER, token1.kind);
		assertEquals(12, token1.pos);
		String text1 = KW_INTEGER.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(IDENT, token2.kind);
		assertEquals(20, token2.pos);
		String text2 = IDENT.getText();
		assertEquals(5, token2.length);
		assertEquals(text2, token2.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(OP_HEIGHT, token3.kind);
		assertEquals(26, token3.pos);
		String text3 = OP_HEIGHT.getText();
		assertEquals(text3.length(), token3.length);
		assertEquals(text3, token3.getText());
		
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token4.kind);
	}
	
	
	
	@Test
	public void testOther() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = " ~";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalCharException.class);
		scanner.scan();
	}
	
	@Test
	public void testBararrowOrConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "|-> ||-";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(BARARROW, token.kind);
		assertEquals(0, token.pos);
		String text = BARARROW.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(OR, token1.kind);
		assertEquals(4, token1.pos);
		String text1 = OR.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
	}
	
	@Test
	public void testNotEqualConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "!= !+";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(NOTEQUAL, token.kind);
		assertEquals(0, token.pos);
		String text = NOTEQUAL.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(NOT, token1.kind);
		assertEquals(3, token1.pos);
		String text1 = NOT.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(PLUS, token2.kind);
		assertEquals(4, token2.pos);
		String text2 = PLUS.getText();
		assertEquals(text2.length(), token2.length);
		assertEquals(text2, token2.getText());
		
	}
	
	@Test
	public void testLeLtAssignConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "<= 08<10 a$<-b_";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(LE, token.kind);
		assertEquals(0, token.pos);
		String text = LE.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(INT_LIT, token1.kind);
		assertEquals(3, token1.pos);
		String text1 = INT_LIT.getText();
		//assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(INT_LIT, token2.kind);
		assertEquals(4, token2.pos);
		String text2 = INT_LIT.getText();
		//assertEquals(text2.length(), token2.length);
		assertEquals(text2, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(LT, token3.kind);
		assertEquals(5, token3.pos);
		String text3 = LT.getText();
		assertEquals(text3.length(), token3.length);
		assertEquals(text3, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(INT_LIT, token4.kind);
		assertEquals(6, token4.pos);
		String text4 = INT_LIT.getText();
		//assertEquals(text4.length(), token4.length);
		assertEquals(text4, token4.getText());
		
		Scanner.Token token5 = scanner.nextToken();
		assertEquals(IDENT, token5.kind);
		assertEquals(9, token5.pos);
		String text5 = IDENT.getText();
		//assertEquals(text5.length(), token5.length);
		assertEquals(text5, token5.getText());
		
		Scanner.Token token6 = scanner.nextToken();
		assertEquals(ASSIGN, token6.kind);
		assertEquals(11, token6.pos);
		String text6 = ASSIGN.getText();
		assertEquals(text6.length(), token6.length);
		assertEquals(text6, token6.getText());
		
		Scanner.Token token7 = scanner.nextToken();
		assertEquals(IDENT, token7.kind);
		assertEquals(13, token7.pos);
		String text7 = IDENT.getText();
		//assertEquals(text7.length(), token7.length);
		assertEquals(text7, token7.getText());
		
	}
	

	@Test
	public void testGeGtConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "> >= ";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(GT, token.kind);
		assertEquals(0, token.pos);
		String text = GT.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(GE, token1.kind);
		assertEquals(2, token1.pos);
		String text1 = GE.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
	}
	
	@Test
	public void testMinusArrowConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "->- >";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(ARROW, token.kind);
		assertEquals(0, token.pos);
		String text = ARROW.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(MINUS, token1.kind);
		assertEquals(2, token1.pos);
		String text1 = MINUS.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(GT, token2.kind);
		assertEquals(4, token2.pos);
		String text2 = GT.getText();
		assertEquals(text2.length(), token2.length);
		assertEquals(text2, token2.getText());
	}
	
	@Test
	public void testCommentConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "a *//***456***///*11";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(TIMES, token1.kind);
		assertEquals(2, token1.pos);
		String text1 = TIMES.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(DIV, token2.kind);
		assertEquals(3, token2.pos);
		String text2 = DIV.getText();
		assertEquals(text2.length(), token2.length);
		assertEquals(text2, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(DIV, token3.kind);
		assertEquals(15, token3.pos);
		String text3 = DIV.getText();
		assertEquals(text3.length(), token3.length);
		assertEquals(text3, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token4.kind);
		String text4 = EOF.getText();
		//assertEquals(text4.length(), token4.length);
		assertEquals(text4, token4.getText());
	}
	
	
	@Test
	public void testCommentEscapeConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "abc/*de\ngh*/ xyz";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(IDENT, token1.kind);
		assertEquals(13, token1.pos);
		String text1 = IDENT.getText();
		//assertEquals(text.length(), token1.length);
		assertEquals(text1, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token2.kind);
		
		assertEquals(scanner.getLinePos(token1).line, 1);
		assertEquals(scanner.getLinePos(token1).posInLine, 5);
	}
	
	
	
	@Test
	public void testgetLinePos() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "a\n Dfgh $12\txyz \r\nufo ishere";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		//thrown.expect(IllegalCharException.class);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(IDENT, token.kind);
		assertEquals(0, token.pos);
		String text = IDENT.getText();
		//assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(IDENT, token1.kind);
		assertEquals(3, token1.pos);
		//assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(IDENT, token2.kind);
		assertEquals(8, token2.pos);
		//assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(IDENT, token3.kind);
		assertEquals(12, token3.pos);
		//assertEquals(text.length(), token3.length);
		assertEquals(text, token3.getText());
		
		Scanner.Token token4 = scanner.nextToken();
		assertEquals(IDENT, token4.kind);
		assertEquals(18, token4.pos);
		//assertEquals(text.length(), token4.length);
		assertEquals(text, token4.getText());
		
		Scanner.Token token5 = scanner.nextToken();
		assertEquals(IDENT, token5.kind);
		assertEquals(22, token5.pos);
		//assertEquals(text.length(), token5.length);
		assertEquals(text, token5.getText());
		
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token6 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token6.kind);
		
		assertEquals(scanner.getLinePos(token1).line, 1);
		assertEquals(scanner.getLinePos(token1).posInLine, 1);
		
		assertEquals(scanner.getLinePos(token2).line, 1);
		assertEquals(scanner.getLinePos(token2).posInLine, 6);
		
		assertEquals(scanner.getLinePos(token3).line, 1);
		assertEquals(scanner.getLinePos(token3).posInLine, 10);
		
		assertEquals(scanner.getLinePos(token4).line, 2);
		assertEquals(scanner.getLinePos(token4).posInLine, 0);
	
	}
	
	
	
}
