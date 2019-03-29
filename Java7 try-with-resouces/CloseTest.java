package com.snowcattle.game.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CloseTest {

	public static void try1() throws Exception {
		Close1 c1 = new Close1();
		try {
			c1.doIt();
		} finally {
			c1.close();
		}
	}
	
	public static void try1_with() throws Exception {
		try (Close1 c1 = new Close1()) {
			c1.doIt();
		}
	}
	
	public static void try1_2() throws Exception {
		Close1_2 c1 = new Close1_2();
		try {
			c1.doIt();
		} finally {
			c1.close();
		}
	}
	
	public static void try1_2_with() throws Exception {
		try (Close1_2 c1 = new Close1_2()) {
			c1.doIt();
		}
	}
	
	public static void try1_2_with_suppress() {
		try (Close1_2 c1 = new Close1_2()) {
			c1.doIt();
		} catch (Exception e) {
			System.out.println("[Catch]" + e.getMessage());
			Throwable[] ths = e.getSuppressed();
			for(Throwable th : ths) {
				System.out.println("[Catch]" + th.getMessage());
			}
		}
	}
	
	public static void try2() throws Exception {
		Close1 c1 = new Close1();
		try {
			Close2 c2 = new Close2();
			try {
				c1.doIt();
				c2.doIt();
			} finally {
				c2.close();
			}
		} finally {
			c1.close();
		}
	}
	
	public static void try2_with() throws Exception {
		try (Close1 c1 = new Close1(); Close2 c2 = new Close2()) {
			c1.doIt();
			c2.doIt();
		}
	}
	
	public static void try2_2() throws Exception {
		Close1_2 c1 = new Close1_2();
		try {
			Close2_2 c2 = new Close2_2();
			try {
				c1.doIt();
				c2.doIt();
			} finally {
				c2.close();
			}
		} finally {
			c1.close();
		}
	}
	
	public static void try2_2_with() throws Exception {
		try (Close1_2 c1 = new Close1_2(); Close2_2 c2 = new Close2_2()) {
			c1.doIt();
			c2.doIt();
		}
	}
	
	public static void try2_2_with_suppress() {
		try (Close1_2 c1 = new Close1_2(); Close2_2 c2 = new Close2_2()) {
			c1.doIt();
			c2.doIt();
		} catch (Exception e) {
			System.out.println("[Catch]" + e.getMessage());
			Throwable[] ths = e.getSuppressed();
			for(Throwable th : ths) {
				System.out.println("[Catch]" + th.getMessage());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		try1();
//		try1_with();
//		try1_2();
//		try1_2_with();
//		try1_2_with_suppress();
//		try2();
//		try2_2();
//		try2_2_with();
		try2_2_with_suppress();
		File f = new File("");
		InputStream is = new FileInputStream(f);
		is.close();
				
	}
}
