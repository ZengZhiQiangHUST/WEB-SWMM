package edu.hust.webswmm.framework.mathmodel.test;
import edu.hust.webswmm.framework.mathmodel.engine.dll.hydraulicModeling.hd1d.Hd_fortran;
import com.sun.jna.ptr.IntByReference;

public class TestHD {
    public static void main(String[] args) {
        Hd_fortran hdfortran = Hd_fortran.GHH;
        IntByReference a = new IntByReference(36);
        hdfortran.HDWQCLAC(a);
    }
}
