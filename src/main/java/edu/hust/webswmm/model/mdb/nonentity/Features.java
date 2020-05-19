package edu.hust.webswmm.model.mdb.nonentity;
import edu.hust.webswmm.model.mdb.nonentity.resList.*;

import java.util.List;
/**
 * 所有要素类
 */
public class Features extends AllFeatures {
        private List<Subcatchment> subcatchments;// 子汇水区结果集合
        private List<Outfall> outfalls;// 出口结果集合
        private List<Junction> junctions;// 节点结果集合
        private List<Section> sections;// 断面结果集合
        private List<Link> links;// 管段结果集合
    /**
     *
     * @param subcatchments
     * @param outfalls
     * @param junctions
     * @param sections
     * @param links
     */
    public Features(List<Subcatchment> subcatchments, List<Outfall> outfalls, List<Junction> junctions, List<Section> sections, List<Link> links) {
        this.subcatchments=subcatchments;
        this.outfalls = outfalls;
        this.junctions = junctions;
        this.sections = sections;
        this.links = links;
    }

    public List<Subcatchment> getSubcatchments() {
        return subcatchments;
    }

    public void setSubcatchments(List<Subcatchment> subcatchments) {
        this.subcatchments = subcatchments;
    }

    public List<Outfall> getOutfalls() {
        return outfalls;
    }

    public void setOutfalls(List<Outfall> outfalls) {
        this.outfalls = outfalls;
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<Junction> junctions) {
        this.junctions = junctions;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
