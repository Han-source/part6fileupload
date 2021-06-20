package www.dream.com.framework.util;

public class Quad<A extends Comparable, B, C, D> implements Comparable<Quad<A , B, C, D>> {

	private A a;
	private B b;
	private C c;
	private D d;
	
	public Quad(A a, B b, C c, D d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public C getC() {
		return c;
	}

	public D getD() {
		return d;
	}

	@Override
	public int compareTo(Quad<A, B, C, D> o) {
		return this.a.compareTo(o.a);
	}

	@Override
	public String toString() {
		return "a=" + a + ", b=" + b + ", c=" + c + ", d=" + d ;
	}

}
