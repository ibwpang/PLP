package cop5556sp17;

import java.util.ArrayList;

//import com.sun.xml.internal.ws.util.xml.XMLReaderComposite.State;

//import cop5556sp17.Scanner.IllegalNumberException;

public class Scanner {
	/**
	 * Kind enum
	 */
	
	public static enum Kind {
		IDENT(""), INT_LIT(""), KW_INTEGER("integer"), KW_BOOLEAN("boolean"), 
		KW_IMAGE("image"), KW_URL("url"), KW_FILE("file"), KW_FRAME("frame"), 
		KW_WHILE("while"), KW_IF("if"), KW_TRUE("true"), KW_FALSE("false"), 
		SEMI(";"), COMMA(","), LPAREN("("), RPAREN(")"), LBRACE("{"), 
		RBRACE("}"), ARROW("->"), BARARROW("|->"), OR("|"), AND("&"), 
		EQUAL("=="), NOTEQUAL("!="), LT("<"), GT(">"), LE("<="), GE(">="), 
		PLUS("+"), MINUS("-"), TIMES("*"), DIV("/"), MOD("%"), NOT("!"), 
		ASSIGN("<-"), OP_BLUR("blur"), OP_GRAY("gray"), OP_CONVOLVE("convolve"), 
		KW_SCREENHEIGHT("screenheight"), KW_SCREENWIDTH("screenwidth"), 
		OP_WIDTH("width"), OP_HEIGHT("height"), KW_XLOC("xloc"), KW_YLOC("yloc"), 
		KW_HIDE("hide"), KW_SHOW("show"), KW_MOVE("move"), OP_SLEEP("sleep"), 
		KW_SCALE("scale"), EOF("eof");

		Kind(String text) {
			this.text = text;
		}

		final String text;

		String getText() {
			return text;
		}
	}
	
	
	public static enum State{
		START, INT_LIT, AFTER_EQ, IN_DIGIT, IN_IDENT, AFTER_OR, AFTER_OR_MINUS, AFTER_NOT,
		AFTER_LT, AFTER_GT, AFTER_MINUS, AFTER_DIV, COMMENT, COMMENT_1, AFTER_RETURN;
	}
	
	
/**
 * Thrown by Scanner when an illegal character is encountered
 */
	@SuppressWarnings("serial")
	public static class IllegalCharException extends Exception {
		public IllegalCharException(String message) {
			super(message);
		}
	}
	
	/**
	 * Thrown by Scanner when an int literal is not a value that can be represented by an int.
	 */
	@SuppressWarnings("serial")
	public static class IllegalNumberException extends Exception {
	public IllegalNumberException(String message){
		super(message);
		}
	}
	

	/**
	 * Holds the line and position in the line of a token.
	 */
	static class LinePos {
		public final int line;
		public final int posInLine;
		
		public LinePos(int line, int posInLine) {
			super();
			this.line = line;
			this.posInLine = posInLine;
		}

		@Override
		public String toString() {
			return "LinePos [line=" + line + ", posInLine=" + posInLine + "]";
		}
	}
		

	

	public class Token {
		public final Kind kind;
		public final int pos;  //position in input array, overall pos
		public final int length;  
		
		
		

		Token(Kind kind, int pos, int length) {
			this.kind = kind;
			this.pos = pos;
			this.length = length;
			
		}

		//returns the text of this Token
		public String getText() {
			//TODO IMPLEMENT THIS
			return chars.substring(pos, pos+length);
		}
		
		//returns a LinePos object representing the line and column of this Token
		LinePos getLinePos(){
			//TODO IMPLEMENT THIS
			int line = 0;
			int posinline = 0;
			
			//calculate the line and posinline using arr and pos, inner class can call the variables arr of outerclass
			//using Binary Search?? BS may not work in this case.
			
			int index = 0;
			
			if(arr.get(arr.size()-1)<=pos){
				index = arr.size();
			}
			else{
				while(arr.get(index)<=pos){ 
				
					if(arr.get(index)==pos){
						index++;
						break;
					}
				
					else{
						index++;
					}
				}
			}
			line = index-1;
			posinline = pos - arr.get(index-1);
			LinePos LP = new LinePos(line, posinline);
			return LP;
		}

		/** 
		 * Precondition:  kind = Kind.INT_LIT,  the text can be represented with a Java int.
		 * Note that the validity of the input should have been checked when the Token was created.
		 * So the exception should never be thrown.
		 * 
		 * @return  int value of this token, which should represent an INT_LIT
		 * @throws NumberFormatException
		 */
		public int intVal() throws NumberFormatException{
			//TODO IMPLEMENT THIS
			int num = 0;
			
			try{
				num = Integer.parseInt(chars.substring(pos,pos+length));
			}
			catch(NumberFormatException e){
				//throw new IllegalNumberException("illegal number "+chars.substring(pos,pos+length)+" at pos "+pos);
			}
			
			return num;
		}
		
	}

	 
	final ArrayList<Token> tokens;
	final String chars;
	int tokenNum;
	
	final ArrayList<Integer> arr = new ArrayList<Integer>();

	Scanner(String chars) {
		this.chars = chars;
		tokens = new ArrayList<Token>();

	}

	
	
	/**
	 * Initializes Scanner object by traversing chars and adding tokens to tokens list.
	 * 
	 * @return this scanner
	 * @throws IllegalCharException
	 * @throws IllegalNumberException
	 */
	public Scanner scan() throws IllegalCharException, IllegalNumberException {
		int pos = 0; 
		//TODO IMPLEMENT THIS!!!!
		int length = chars.length();
		State state = State.START;
		int startPos = 0;
		int ch;
		arr.add(pos);
		while(pos<=length){
			ch = pos<length?chars.charAt(pos):-1;
			switch(state){
				case START:{
					//pos = skipWhiteSpace(pos);
					//ch = pos<length?chars.charAt(pos):-1;
					startPos = pos;
					switch(ch){
						case -1:{
							tokens.add(new Token(Kind.EOF, pos, 0));
							pos++;
						}break;
						case ' ':{
							pos++;
						}break;
						case '\n':{//cannot change the order of pos++ and arr.add. \n just take one pos
							pos++;
							arr.add(pos);
						}break;
						case '\r':{
							pos++;
							state = State.AFTER_RETURN;
						}break;
						case '\t':{
							pos++;
						}break;
						case ';':{
							tokens.add(new Token(Kind.SEMI, startPos, 1));
							pos++;
						}break;
						case ',':{
							tokens.add(new Token(Kind.COMMA, startPos, 1));
							pos++;
						}break;
						case '(':{
							tokens.add(new Token(Kind.LPAREN, startPos, 1));
							pos++;
						}break;
						case ')':{
							tokens.add(new Token(Kind.RPAREN, startPos, 1));
							pos++;
						}break;
						case '{':{
							tokens.add(new Token(Kind.LBRACE, startPos, 1));
							pos++;
						}break;
						case '}':{
							tokens.add(new Token(Kind.RBRACE, startPos, 1));
							pos++;
						}break;
						case '&':{
							tokens.add(new Token(Kind.AND, startPos, 1));
							pos++;
						}break;
						case '%':{
							tokens.add(new Token(Kind.MOD, startPos, 1));
							pos++;
						}break;
						case '+':{
							tokens.add(new Token(Kind.PLUS, startPos, 1));
							pos++;
						}break;
						case '*':{
							tokens.add(new Token(Kind.TIMES, startPos, 1));
							pos++;
						}break;
						case '=':{
							state = State.AFTER_EQ;
							pos++;
						}break;
						case '0':{
							tokens.add(new Token(Kind.INT_LIT, startPos, 1));
							pos++;
						}break;
						case '|':{
							state = State.AFTER_OR;
							pos++;
						}break;
						case '!':{
							state = State.AFTER_NOT;
							pos++;
						}break;
						case '<':{
							state = State.AFTER_LT;
							pos++;
						}break;
						case '>':{
							state = State.AFTER_GT;
							pos++;
						}break;
						case '-':{
							state = State.AFTER_MINUS;
							pos++;
						}break;
						case '/':{
							state = State.AFTER_DIV;
							pos++;
						}break;
						
						default:{
							if(Character.isDigit(ch)){
								state = State.IN_DIGIT;
								pos++;
							}
							else if(Character.isJavaIdentifierStart(ch)){
								state = State.IN_IDENT;
								pos++;
							}
							else{
								throw new IllegalCharException("illegal char "+ch+" at pos "+pos);
							}
						}
					}
				}break;
				case AFTER_RETURN:{
					if(ch=='\n'){
						pos++;
						arr.add(pos);
						state = State.START;
					}
					else{
						//pos = arr.get(arr.size()-1);
						//state = State.START;
						//throw new IllegalCharException("illegal char "+ch+" at pos "+pos);
						state = State.START;
					}
				}break;
				case IN_DIGIT:{
					if(Character.isDigit(ch)){
						pos++;
					}
					else{
						
//						if(chars.substring(startPos,pos).length()>10){//2,147,483,647
//							throw new IllegalNumberException("illegal number:"+chars.substring(startPos, pos)+"it is too large");
//						}	
//						else if(Long.parseLong(chars.substring(startPos,pos))>2147483647){
//							throw new IllegalNumberException("illegal number:"+chars.substring(startPos, pos)+"it is too large");							
//						}
//						
//						else{
//							tokens.add(new Token(Kind.INT_LIT, startPos, pos-startPos));
//							state = State.START;
//						}
						try{
							Integer.parseInt(chars.substring(startPos,pos));
						}
						catch(NumberFormatException e){
							throw new IllegalNumberException("illegal number:"+chars.substring(startPos, pos)+"it is too large");
						}
						tokens.add(new Token(Kind.INT_LIT, startPos, pos-startPos));
						state = State.START;
					}
				}break;
				case IN_IDENT:{
					if(Character.isJavaIdentifierPart(ch)){
						pos++;
					}
					else{
						String str= chars.substring(startPos,pos);
						//System.out.println(str);
						if(str.equals("integer")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_INTEGER, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("boolean")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_BOOLEAN, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("image")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_IMAGE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("url")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_URL, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("file")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_FILE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("frame")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_FRAME, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("while")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_WHILE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("if")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_IF, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("sleep")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_SLEEP, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("screenheight")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_SCREENHEIGHT, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("screenwidth")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_SCREENWIDTH, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("gray")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_GRAY, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("convolve")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_CONVOLVE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("blur")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_BLUR, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("scale")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_SCALE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("width")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_WIDTH, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("height")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.OP_HEIGHT, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("xloc")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_XLOC, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("yloc")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_YLOC, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("hide")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_HIDE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("show")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_SHOW, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("move")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_MOVE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("true")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_TRUE, startPos, pos-startPos));
							state = State.START;
						}
						else if(str.equals("false")){
							//tokens.remove(tokens.get(tokens.size()-1));
							tokens.add(new Token(Kind.KW_FALSE, startPos, pos-startPos));
							state = State.START;
						}
						else{
							tokens.add(new Token(Kind.IDENT, startPos, pos-startPos));
							state = State.START;
						}
					}
				}break;
				case AFTER_EQ:{
					if(ch=='='){
						tokens.add(new Token(Kind.EQUAL, startPos, 2));
						pos++;
						state = State.START;
					}
					else{
						throw new IllegalCharException("illegal char "+ch+" at pos "+pos);
					}
				}break;
				case AFTER_OR:{
					if(ch=='-'){
						state = State.AFTER_OR_MINUS;
						pos++;
					}
					else{
						tokens.add(new Token(Kind.OR, startPos, 1));
						state = State.START;
					}
				}break;
				case AFTER_OR_MINUS:{
					if(ch=='>'){
						tokens.add(new Token(Kind.BARARROW, startPos, 3));
						pos++;
						state = State.START;
					}
					else{
						tokens.add(new Token(Kind.OR, startPos, 1));
						tokens.add(new Token(Kind.MINUS, startPos+1, 1));
						state = State.START;
						//throw new IllegalCharException("illegal char "+ch+" at pos "+pos);
					}
				}break;
				case AFTER_NOT:{
					if(ch=='='){
						tokens.add(new Token(Kind.NOTEQUAL, startPos, 2));
						pos++;
						state = State.START;
					}
					else{
						tokens.add(new Token(Kind.NOT, startPos, 1));
						state = State.START;
					}
				}break;
				case AFTER_LT:{
					if(ch=='='){
						tokens.add(new Token(Kind.LE, startPos, 2));
						pos++;
						state = State.START;
					}
					else if(ch=='-'){
						tokens.add(new Token(Kind.ASSIGN, startPos, 2));
						pos++;
						state = State.START;
					}
					else{
						tokens.add(new Token(Kind.LT, startPos, 1));
						state = State.START;
					}
				}break;
				case AFTER_GT:{
					if(ch=='='){
						tokens.add(new Token(Kind.GE, startPos, 2));
						pos++;
						state = State.START;
					}
					else{
						tokens.add(new Token(Kind.GT, startPos, 1));
						state = State.START;
					}
				}break;
				case AFTER_MINUS:{
					if(ch=='>'){
						tokens.add(new Token(Kind.ARROW, startPos, 2));
						pos++;
						state = State.START;
					}
					else{
						tokens.add(new Token(Kind.MINUS, startPos, 1));
						state = State.START;
					}
				}break;
				case AFTER_DIV:{
					if(ch=='*'){
						pos++;
						state = State.COMMENT;
					}
					else{
						tokens.add(new Token(Kind.DIV, startPos, 1));
						state = State.START;
					}
				}break;
				case COMMENT:{
					if(ch=='*'){
						state = State.COMMENT_1;
						pos++;
					}
					else if(ch=='\n'){
						pos++;
						arr.add(pos);
					}
					else if(ch==-1){
						tokens.add(new Token(Kind.EOF, pos, 0));
						pos++;
					}
					else{
						pos++;
					}
				}break;
				case COMMENT_1:{
					if(ch=='/'){
						state = State.START;
						pos++;
					}
					else if(ch==-1){
						tokens.add(new Token(Kind.EOF, pos, 0));
						pos++;
					}
					else if(ch=='\n'){
						state = State.COMMENT;
						pos++;
						arr.add(pos);
					}
					else if(ch=='*'){
						pos++;
					}
					else{
						state = State.COMMENT;
						pos++;
					}
				}break;
				
				default: assert false;
			}
		}
		
		//tokens.add(new Token(Kind.EOF,pos,0));
		//System.out.println(arr);
	    return this;  
	}
	
	
	
	/*
	 * Return the next token in the token list and update the state so that
	 * the next call will return the Token..  
	 */
	public Token nextToken() {
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum++);
	}
	
	/*
	 * Return the next token in the token list without updating the state.
	 * (So the following call to next will return the same token.)
	 */
	public Token peek(){
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum);		
	}

	

	/**
	 * Returns a LinePos object containing the line and position in line of the 
	 * given token.  
	 * 
	 * Line numbers start counting at 0
	 * 
	 * @param t
	 * @return
	 */
	public LinePos getLinePos(Token t) {
		//TODO IMPLEMENT THIS
		
		return t.getLinePos();
	}


}
