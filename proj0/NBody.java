public class NBody {
	public static double readRadius(String x){
		In in = new In(x);
		
		int number_of_planets = in.readInt();
		double radius_of_universe = in.readDouble();
		return radius_of_universe;
	}
	public static Planet[] readPlanets(String x) {
		In in = new In(x);
		int number_of_planets = in.readInt();
		double radius_of_universe = in.readDouble();
		int i =0;
		Planet a[] = new Planet[number_of_planets];
		for(;i<number_of_planets;i++) {
			double xxPos = in.readDouble();
	        double yyPos = in.readDouble();
	        double xxVel = in.readDouble();
	        double yyVel = in.readDouble();
	        double mass = in.readDouble();
	        String imgFileName = in.readString();
	        a[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
		}
		return a;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius_of_universe = readRadius(filename);
		Planet planets[] = readPlanets(filename);

		StdDraw.enableDoubleBuffering();
		String starfield = "images/starfield.jpg";
		StdDraw.setScale(-radius_of_universe,radius_of_universe);
		StdDraw.clear();
		StdDraw.picture(0,0,starfield);
		StdDraw.show();
		
		double time = 0;
		double xForce[] = new double[planets.length];
		double yForce[] = new double[planets.length];
		for(;time<T;) {
			for (int i = 0; i < planets.length; i++) {
				xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForce[i], yForce[i]);

            }
			StdDraw.picture(0,0,starfield);
			for (int j = 0; j < planets.length; j++) {
                planets[j].draw();
            }
			StdDraw.show();
			StdDraw.pause(10);
			time = time + dt;
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
		                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}