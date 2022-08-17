import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


class Punto{
	double x;
	double y;
	public Punto(double x, double y){
		this.x=x;
		this.y=y;
	}
	public String toString(){
		return "("+x+", "+y+")";
	}
	public double distancia(Punto p){
		double cateto1=x-p.x;
		double cateto2=y-p.y;
		double hipotenusa=Math.sqrt(cateto1*cateto1 + cateto2*cateto2);
		return hipotenusa;
	}
}

class Circulo{
	Punto centro;
	double radio;
	public Circulo(Punto p, double radio){
		this.centro=p;
		this.radio=radio;
	}
	public boolean contiene(Punto punto){
		if(Math.sqrt(Math.pow(centro.x-punto.x,2)+Math.pow(centro.y-punto.y,2))<=radio)
			return true;
		else
			return false;
	}
	public String toString(){
		radio=Math.round(radio*100);
		radio=radio/100;
		return String.format("%.2f %.2f %.2f", centro.x,centro.y,radio);
	}
}

public class RandomizedAlgorithmsCoronavirus{

	public static Circulo constCirculo(List<Punto> puntos){
		Circulo circulo=null;
		int i=0;
		for (Punto punto: puntos) {
			if (i==0 || !circulo.contiene(punto))
				circulo=circuloUnPunto(puntos.subList(0, i + 1), punto);
			i++;
		}
		return circulo;
	}

	public static Circulo circuloUnPunto(List<Punto> subListaPuntos, Punto punto){
		Circulo circulo=new Circulo(punto, 0);
		int i=0;
		for (Punto aux: subListaPuntos) {
			if (i==0) {
				double cenX=(punto.x+aux.x)/2;
				double cenY=(punto.y+aux.y)/2;
				Punto centro=new Punto(cenX, cenY);
				circulo=new Circulo(centro, centro.distancia(aux));
			}
			else if (!circulo.contiene(aux)) {
				double cenX=(punto.x+aux.x)/2;
				double cenY=(punto.y+aux.y)/2;
				Punto centro=new Punto(cenX, cenY);
				circulo=circuloDosPuntos(subListaPuntos.subList(0, i+1), punto, aux, centro);
			}
			i++;
		}
		return circulo;
	}

	public static Circulo circuloDosPuntos(List<Punto> subList, Punto punto, Punto aux, Punto centro){
		Circulo circulo=new Circulo(centro, centro.distancia(punto));
		Circulo izq=null;
		Circulo dcha=null;
		Punto pt=new Punto(aux.x-punto.x, aux.y-punto.y);
		for (Punto punt: subList) {
			if (circulo.contiene(punt))
				continue;
			double val=entrelazar(pt, new Punto(punt.x-punto.x, punt.y-punto.y));
			Circulo cir=circuloTresPuntos(punto, aux, punt);
			if (cir==null)
				continue;
			else if (val>0 && (izq==null || 
					entrelazar(pt, new Punto(cir.centro.x-punto.x, cir.centro.y-punto.y))>entrelazar(pt, new Punto(izq.centro.x-punto.x, izq.centro.y-punto.y))))
				izq=cir;
			else if (val<0 && (dcha==null || 
					entrelazar(pt, new Punto(cir.centro.x-punto.x, cir.centro.y-punto.y))<entrelazar(pt, new Punto(dcha.centro.x-punto.x, dcha.centro.y-punto.y))))
				dcha=cir;
		}
		if (izq==null && dcha==null)
			return circulo;
		else if (izq==null)
			return dcha;
		else if (dcha==null)
			return izq;
		else{
			if(izq.radio <= dcha.radio)
				return izq;
			else 
				return dcha;
		}
	}

	public static double entrelazar(Punto puntoA, Punto puntoB) {
		return puntoA.x*puntoB.y-puntoA.y*puntoB.x;
	}

	public static Circulo circuloTresPuntos(Punto puntoA, Punto puntoB, Punto puntoC){
		double centrox=(Math.min(Math.min(puntoA.x, puntoB.x), puntoC.x)+Math.max(Math.max(puntoA.x, puntoB.x), puntoC.x))/2;
		double centroy=(Math.min(Math.min(puntoA.y, puntoB.y), puntoC.y)+Math.max(Math.max(puntoA.y, puntoB.y), puntoC.y))/2;
		double puntoAx=puntoA.x-centrox; 
		double puntoAy=puntoA.y-centroy;
		double puntoBx=puntoB.x-centrox; 
		double puntoBy=puntoB.y-centroy;
		double puntoCx=puntoC.x-centrox; 
		double puntoCy=puntoC.y-centroy;
		double distancia=(puntoAx*(puntoBy-puntoCy)+puntoBx*(puntoCy-puntoAy)+puntoCx*(puntoAy-puntoBy))*2;
		if (distancia==0)
			return null;
		else {
			double x=((Math.pow(puntoAx, 2)+Math.pow(puntoAy, 2))*(puntoBy-puntoCy)+(Math.pow(puntoBx, 2)+Math.pow(puntoBy, 2))*(puntoCy-puntoAy)+(Math.pow(puntoCx, 2)+Math.pow(puntoCy, 2))*(puntoAy-puntoBy))/distancia;
			double y=((Math.pow(puntoAx, 2)+Math.pow(puntoAy, 2))*(puntoCx-puntoBx)+(Math.pow(puntoBx, 2)+Math.pow(puntoBy, 2))*(puntoAx-puntoCx)+(Math.pow(puntoCx, 2)+Math.pow(puntoCy, 2))*(puntoBx-puntoAx))/distancia;
			Punto centro=new Punto(centrox+x, centroy+y);
			double radio=Math.max(Math.max(centro.distancia(puntoA), centro.distancia(puntoB)), centro.distancia(puntoC));
			return new Circulo(centro, radio);
		}
	}


	public static void main(String [] argcs) {
		Scanner sc=new Scanner(System.in);
		double n=sc.nextDouble();
		int i=0;
		double x=0;
		double y=0;
		List<Punto> list = new ArrayList<Punto>();
		while(i<n) {
			x=Double.parseDouble(sc.next());
			y=Double.parseDouble(sc.next());
			Punto p=new Punto(x,y);
			list.add(p);
			i++;
		}
		System.out.println(constCirculo(list));
		sc.close();
	}
}
