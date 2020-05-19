package edu.hust.webswmm.framework.mathmodel.engine.dll.hydraulicModeling.hd1d;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.ByReference;
/**
 * Call HD.dll
 */
public interface Hd_fortran extends Library {
    Hd_fortran GHH=(Hd_fortran)Native.loadLibrary("SuQianProjectDLL", Hd_fortran.class);
    Hd_fortran GHH2=Native.loadLibrary("QSHDLL", Hd_fortran.class);
    /**
     * Main function
     */
     void HDWQCLAC(ByReference NT);
}
