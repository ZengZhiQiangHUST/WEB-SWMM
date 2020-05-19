package edu.hust.webswmm.framework.mathmodel.test;
import edu.hust.webswmm.framework.mathmodel.engine.dll.hydrologiclModeling.SWMMEngine;
public class TestSWHHM {
    public static void main(String[] args) {
        String rptfile = "tmp.rpt";
        String outfile = "tmp.out";
        SWMMEngine coi = SWMMEngine.swhhmtool;
        coi.RunSwmmDll("swmm5Example.inp", rptfile, outfile);
        coi.OpenSwmmOutFile(outfile);
    }
}
