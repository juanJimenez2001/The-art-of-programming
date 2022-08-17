package aop.solution;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class Partidos implements Comparable<Partidos>{
	String local;
	String visitante;
	String inicio;
	String fin;

	public Partidos(String local, String visitante, String inicio, String fin) {
		this.local=local;
		this.visitante=visitante;
		this.inicio=inicio;
		this.fin=fin;
	}

	@Override
	public int compareTo(Partidos o) {
		return Integer.parseInt(fin)-Integer.parseInt(o.fin);
	}
	
}

public class PartidosFutbol {

	public static List<Partidos> horario (List<Partidos> horarios){
		Collections.sort(horarios);
		List<Partidos> aux=new LinkedList<Partidos>();
		for(Partidos partido: horarios) {
			if(aux.isEmpty()) {
				aux.add(partido);
			}
			else {
				if(Integer.parseInt(partido.inicio)>=Integer.parseInt(aux.get(aux.size()-1).fin)){
					aux.add(partido);
				}
			}		
		}
		return aux;
	}


	public static void toString(List<Partidos> horarios, String dia) {
		List<Partidos> aux=horario(horarios);
		System.out.println(dia+" "+aux.size());
		for(Partidos partido: aux) {
			System.out.println(partido.local+" "+partido.visitante+" "+partido.inicio+" "+partido.fin);
		}		
	}

	public static void main(String [] argcs) {
		Scanner sc=new Scanner(System.in);
		String dia=sc.next();
		while(!dia.equals("000000")){
			int partidos=sc.nextInt();
			int n=0;
			List <Partidos> horarios=new LinkedList<Partidos>();
			while(n<partidos) {
				String local=sc.next();
				String visitante=sc.next();
				String inicio=sc.next();
				String fin=sc.next();
				Partidos partido=new Partidos(local, visitante, inicio, fin);
				horarios.add(partido);
				n++;
			}
			toString(horarios, dia);
			dia=sc.next();
		}
		System.out.println("000000 0");
		sc.close();
	}
}


