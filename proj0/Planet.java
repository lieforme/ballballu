import static java.lang.Math.sqrt;

public class Planet {
	private double G = 6.67e-11;
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	
	public Planet(double xP, double yP, double xV,double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet P) {
		xxPos = P.xxPos;
		yyPos = P.yyPos;
		xxVel = P.xxVel;
		yyVel = P.yyVel;
		mass = P.mass;
		imgFileName = P.imgFileName;
	}
	public double calcDistance(Planet x) {
		return sqrt((x.xxPos - xxPos) * (x.xxPos - xxPos) + (x.yyPos - yyPos) * (x.yyPos - yyPos));
	}
	public double calcForceExertedBy(Planet x) {
	       return (G * mass * x.mass)/((x.xxPos - xxPos) * (x.xxPos - xxPos) + (x.yyPos - yyPos) * (x.yyPos - yyPos));
	}
	
    public double calcForceExertedByX(Planet x) {
        double F = (G * mass * x.mass)/((x.xxPos - xxPos) * (x.xxPos - xxPos) + (x.yyPos - yyPos) * (x.yyPos - yyPos));
        return (F * (x.xxPos - xxPos))/calcDistance(x);
    }


    public double calcForceExertedByY(Planet x) {
        double F = (G * mass * x.mass)/((x.xxPos - xxPos) * (x.xxPos - xxPos) + (x.yyPos - yyPos) * (x.yyPos - yyPos));
        return (F * (x.yyPos - yyPos))/calcDistance(x);
    }
    public double calcNetForceExertedByX(Planet[] x) {
        double forceX = 0;
        for (int i = 0; i < x.length; i++ ) {
            if (!this.equals(x[i])) {
                forceX = forceX + calcForceExertedByX(x[i]);
            }
        }
        return forceX;
    }
    public double calcNetForceExertedByY(Planet[] x) {
        double forceY = 0;
        for (int i = 0; i < x.length; i++ ) {
            if (!this.equals(x[i])) {
                forceY = forceY + calcForceExertedByY(x[i]);
            }
        }
        return forceY;
    }
    public void update(double t, double fx, double fy) {
        double avx = fx / mass;
        double avy = fy / mass;
        xxVel = xxVel + avx * t;
        yyVel = yyVel + avy * t;
        xxPos = xxPos + xxVel * t;
        yyPos = yyPos + yyVel * t;

    }
    
    public void draw() {
    	StdDraw.picture(xxPos,yyPos,imgFileName);
    }
}
