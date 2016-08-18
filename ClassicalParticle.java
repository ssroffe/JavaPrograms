/* Written by
   Simonseth Roffe
*/

/** Represents a classical particle
    @author Simonseth Roffe
*/

import java.lang.Math;

public class ClassicalParticle {

    private double mass;
    private double charge;
    private double[] position = new double[3];
    private double[] velocity = new double[3];
    private double g = 9.8;

    public ClassicalParticle(double m,double c, double x, double y, double z, double vx, double vy, double vz) {
	setMass(m);
	setCharge(c);
	setXPosition(x);
	setYPosition(y);
	setZPosition(z);
	setXVelocity(vx);
	setYVelocity(vy);
	setZVelocity(vz);
    }

    public ClassicalParticle(double m) {
	setMass(m);
	setXPosition(0);
	setYPosition(0);
	setZPosition(0);
	setXVelocity(0);
	setYVelocity(0);
	setZVelocity(0);
    }

    public ClassicalParticle(ClassicalParticle c) {
	setMass(c.getMass());
	setCharge(c.getCharge());
	setXPosition(c.getXPosition());
	setYPosition(c.getYPosition());
	setZPosition(c.getZPosition());
	setXVelocity(c.getXVelocity());
	setYVelocity(c.getYVelocity());
	setZVelocity(c.getZVelocity());
    }

    public ClassicalParticle() {
	setMass(0);
	setCharge(0);
	setXPosition(0);
	setYPosition(0);
	setZPosition(0);
	setXVelocity(0);
	setYVelocity(0);
	setZVelocity(0);
    }

    public void setMass(double m) {
	if (m<=0) {
	    IllegalArgumentException iae = new IllegalArgumentException("Mass must be greater than 0!");
	    throw iae;
	}
	mass = m;
    }

    public void setCharge(double c) {
	charge = c;
    }

    public void setXPosition(double x) {
	position[0] = x;
    }

    public void setYPosition(double y) {
	position[1] = y;
    }

    public void setZPosition(double z) {
	position[2] = z;
    }

    public void setXVelocity(double vx) {
	velocity[0] = vx;
    }

    public void setYVelocity(double vy) {
	velocity[1] = vy;
    }

    public void setZVelocity(double vz) {
	velocity[2] = vz;
    }

    public void setG(double grav) {
	g = grav;
    }

    public double getMass() {
	return mass;
    }

    public double getCharge() {
	return charge;
    }

    public double getXPosition() {
	return position[0];
    }

    public double getYPosition() {
	return position[1];
    }

    public double getZPosition() {
	return position[2];
    }

    public double getPosition() {
	double r = Math.sqrt((position[0]*position[0])+(position[1]*position[1])+(position[2]*position[2]));
	return r;
    }

    public double getXVelocity() {
	return velocity[0];
    }

    public double getYVelocity() {
	return velocity[1];
    }

    public double getZVelocity() {
	return velocity[2];
    }

    public double getVelocity() {
	double v = Math.sqrt((velocity[0]*velocity[0])+(velocity[1]*velocity[1])+(velocity[2]*velocity[2]));
	return v;
    }

    public double getGravityPE() {
	double pe = -mass*g*position[1];
	return pe;
    }

    public double getKE() {
	double ke = 0.5*mass*(getVelocity()*getVelocity());
	return ke;
    }

    public double getXMomentum() {
	double px = mass*velocity[0];
	return px;
    }

    public double getYMomentum() {
	double py = mass*velocity[1];
	return py;
    }

    public double getZMomentum() {
	double pz = mass*velocity[2];
	return pz;
    }

    public double getMomentum() {
	double p = Math.sqrt((getXMomentum()*getXMomentum())+(getYMomentum()*getYMomentum())+(getZMomentum()*getZMomentum()));
	return p;
    }

    public double getWeight() {
	double w = mass*g;
	return w;
    }

    public double getVoltage() {
	double V = 8.99*Math.pow(10,9)*(getCharge() / getPosition());
	    return V;
    }
}
