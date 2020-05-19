package edu.hust.webswmm.framework.mathmodel.engine.dll.hydrologiclModeling;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * The calculation engine of SWHHM
 */
public interface SWMMEngine extends Library {
    SWMMEngine swhhmtool = Native.loadLibrary("swmmifaceDLL64", SWMMEngine.class);
    int RunSwmmDll(String inpFile, String rptFile, String outFile);
    int OpenSwmmOutFile(String outFile);
    float GetSwmmResult(int iType, int iIndex, int vIndex, int period);
    int getSWMM_Nperiods();
    int getSWMM_Nsubcatch();
    int getSWMM_Nnodes();
    int getSWMM_Nlinks();
}
