import java.util.Scanner;
import java.util.PriorityQueue;

class Board{
	Cell [][] tablero;

	public Board() {
		tablero=new Cell[9][9];
	}

	public void setCell(int i, int j, Cell celda) {
		tablero[i][j]=celda;
	}

	public void imprimir() {
		String cadena="";
		for(int i=0; i<9; i++) {
			cadena="";
			for(int j=0; j<9; j++)
				cadena=cadena+tablero[i][j].valor;
			System.out.println(cadena);
		}
	}
}

class Cell{
	char valor;
	int fila;
	int columna;
	PriorityQueue<Integer> posiblesValores;

	public Cell(char valor, int fila, int columna) {
		this.valor=valor;
		this.fila=fila;
		this.columna=columna;
		this.posiblesValores=new PriorityQueue <>();
	}

	public int freedom(Board tablero){
		for (int val = 1; val <= 9; val++){
			boolean mismoValor=false;
			for (int i = 0; i < 9; i++){
				if (Character.getNumericValue(tablero.tablero[this.fila][i].valor)==val || Character.getNumericValue(tablero.tablero[i][this.columna].valor)==val
						|| cuadrado(val, tablero)){
					mismoValor = true;
					break;
				}
			}
			if (!mismoValor){
				this.posiblesValores.add(val);
			}
		}
		return this.posiblesValores.size();
	}

	public boolean cuadrado(int val, Board tablero) {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(Character.getNumericValue(tablero.tablero[(this.fila/3)*3+i][(this.columna/3)*3+j].valor)==val) {
					return true;
				}
			}
		}
		return false;
	}
}

public class Sudoku {

	public static boolean resolverSudoku(Board tablero){
		for (int i=0; i<9; i++){
			for (int j=0; j<9; j++){
				if (tablero.tablero[i][j].valor=='-'){
					int n=tablero.tablero[i][j].freedom(tablero);
					for (int k=1; k<=n; k++){
						tablero.tablero[i][j].valor=Character.forDigit(tablero.tablero[i][j].posiblesValores.poll(), 10);
						if (resolverSudoku(tablero)){
							return true;
						}
						tablero.tablero[i][j].valor='-';
					}
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String [] argcs) {
		Scanner sc=new Scanner(System.in);
		Board board=new Board();
		String cadena="";
		Cell celda;
		while(sc.hasNextLine()) {
			for(int i=0; i<9; i++) {
				cadena=sc.next();
				for(int j=0; j<9; j++) {
					celda=new Cell(cadena.charAt(j), i, j);
					board.setCell(i, j, celda);
				}				
			}
			if(resolverSudoku(board))
				board.imprimir();
			if(sc.hasNextLine()) {
				sc.nextLine();
				System.out.println();
			}	
		}
		sc.close();
	}
}
