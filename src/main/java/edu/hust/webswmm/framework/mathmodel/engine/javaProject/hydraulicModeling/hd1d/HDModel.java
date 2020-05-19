package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;

import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDData.*;

import java.util.ArrayList;
import java.util.Hashtable;


public class HDModel {
    HDData data;

    public HDData getData() {
        return data;
    }

    public void setData(HDData data) {
        this.data = data;
    }

    public HDModel(HDData data) {
        this.data = data;
    }

    /**
     * 水动力模型计算
     *
     * @param initialType
     * @param dt
     * @param len
     * @param modelType
     */
    public void calc(String initialType, int dt, int len, String modelType) {
        double theta = 0.8;
        double alpha = 1.0;
        Hashtable<String, HDTopo> hatopo = getRiverTopo(data.getRiverTopos());
        String[] riverIDOrder = getComputeOrder(hatopo, getRivers(data.getRiverTopos()));
        Hashtable<String, ArrayList<HDSection>> hdSections = getRiverSections(riverIDOrder, data.hdSections);
        Hashtable<String, HDBoundary> boundaryBeanHT;
        Hashtable<String, HDInitCondition> initConditionBeanHT = new Hashtable<>();

        //预热计算的边界条件给定（给定正常计算边界条件的第一个值作为恒定边界条件）
        if (data.isPreHeat == true) {
            boundaryBeanHT = createBoundaryForPreHeat(data.byDatas);
        }

        //正常计算的边界条件给定
        else {
            boundaryBeanHT = createBoundaryBeanHT(data.byDatas);
        }

        /**
         *  该部分功能为：为预热计算的模型给定初始的水位和流量
         *  给定方式为：0:水位;1:流量;2:水位流量关系曲线;-1:内结点
         * （1）当上下边界分别为水位和流量
         * （2）当上下边界分别为流量和水位
         * （3）当上下边界都为流量
         * （4）当上下边界都为水位
         *  目前仅针对于直线型河网，树状河网有待进一步开发
         */

        //自动给定初始条件&&直线型河网
        if (initialType.equals("computer") && modelType.equals("linear")) {
            for (int i = 0; i < riverIDOrder.length; i++) {//河段的循环
                HDInitCondition hdInitCondition = null;
                //河段上所有断面的初始流量
                ArrayList<Double> QAL = new ArrayList<>();
                //河段上断面的初始水位
                ArrayList<Double> ZAL = new ArrayList<>();
                //如果上边界为流量下边界为水位
                if (boundaryBeanHT.get(riverIDOrder[i]).getBY1() == 1 && boundaryBeanHT.get(riverIDOrder[i]).getBY2() == 0) {
                    //下边界断面的水深
                    double HDownboundary = boundaryBeanHT.get(riverIDOrder[i]).getDownBoundary().get(0) - hdSections.get(riverIDOrder[i]).get(hdSections.get(riverIDOrder[i]).size() - 1).getMinz();
                    ZAL.add(boundaryBeanHT.get(riverIDOrder[i]).getDownBoundary().get(0));
                    for (int j = hdSections.get(riverIDOrder[i]).size() - 2; j >= 0; j--) {//河段断面的循环,从倒数第二个断面开始
                        //断面上点的最小高程
                        double ZMin = hdSections.get(riverIDOrder[i]).get(j).getMinz();
                        double ZMinDown = hdSections.get(riverIDOrder[i]).get(j + 1).getMinz();
                        //如果上断面的最低高程大于下端面的最低高程
                        if (ZMin >= ZMinDown) {
                            ZAL.add(ZMin + HDownboundary);//断面水位等于断面最低高程加上下边界断面的水深
                        } else {//如果上断面的最低高程小于下端面的最低高程
                            ZAL.add(ZMinDown + HDownboundary);//断面水位等于下断面最低高程加上下边界断面的水深
                        }
                    }
                    ArrayList<Double> ZALNew = new ArrayList<>();
                    //转换顺序
                    for (int j = ZAL.size() - 1; j >= 0; j--) {
                        ZALNew.add(ZAL.get(j));
                    }
                    //流量赋值为上边界值的第一个值
                    for (int j = 0; j < ZAL.size(); j++) {
                        QAL.add(boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0));
                    }
                    hdInitCondition = new HDInitCondition(riverIDOrder[i], QAL, ZALNew);
                }

                //如果上边界为水位下边界为流量
                if (boundaryBeanHT.get(riverIDOrder[i]).getBY1() == 0 && boundaryBeanHT.get(riverIDOrder[i]).getBY2() == 1) {
                    //上边界断面的水深
                    double HUpboundary = boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0) - hdSections.get(riverIDOrder[i]).get(0).getMinz();
                    //获取上边界的水深值添加到初始条件中去
                    ZAL.add(boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0));
                    //河段断面的循环,从下游断面开始
                    setHDInitial(hdSections, riverIDOrder[i], HUpboundary);
                    //流量赋值为下边界值的第一个值
                    for (int j = 0; j < ZAL.size(); j++) {
                        QAL.add(boundaryBeanHT.get(riverIDOrder[i]).getDownBoundary().get(0));
                    }
                    hdInitCondition = new HDInitCondition(riverIDOrder[i], QAL, ZAL);
                }

                //上下边界都为流量
                if (boundaryBeanHT.get(riverIDOrder[i]).getBY1() == 1 && boundaryBeanHT.get(riverIDOrder[i]).getBY2() == 1) {
                    //假定初始水深为2m
                    double HUpboundary = 2;
                    //获取上边界的第一个流量值
                    double QUp = boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0);
                    //获取下边界的第一个流量值
                    double QDown = boundaryBeanHT.get(riverIDOrder[i]).getDownBoundary().get(0);
                    //获取上边界的水深值添加到初始条件中去
                    ZAL.add(hdSections.get(riverIDOrder[i]).get(0).getMinz() + HUpboundary);
                    //河段断面的循环,从下游断面开始
                    setHDInitial(hdSections, riverIDOrder[i], HUpboundary);
                    //将流量值都赋值
                    for (int j = 0; j < ZAL.size(); j++) {
                        QAL.add((QDown + QUp) / 2.0);
                    }
                    hdInitCondition = new HDInitCondition(riverIDOrder[i], QAL, ZAL);
                }

                //上下边界都为水位
                if (boundaryBeanHT.get(riverIDOrder[i]).getBY1() == 0 && boundaryBeanHT.get(riverIDOrder[i]).getBY2() == 0) {
                    //上边界断面的水深
                    double HUpboundary = boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0) - hdSections.get(riverIDOrder[i]).get(0).getMinz();
                    //获取下端面的水深
                    double HDownboundary = boundaryBeanHT.get(riverIDOrder[i]).getDownBoundary().get(0) - hdSections.get(riverIDOrder[i]).get(0).getMinz();
                    //获取上边界的水深值添加到初始条件中去
                    ZAL.add(boundaryBeanHT.get(riverIDOrder[i]).getUpBoundary().get(0));
                    //断面的个数
                    int numOfSection = hdSections.get(riverIDOrder[i]).size();
                    //河段断面的循环,从下游断面开始
                    for (int j = 1; j < hdSections.get(riverIDOrder[i]).size(); j++) {
                        //断面上点的最小高程
                        double ZMin = hdSections.get(riverIDOrder[i]).get(j).getMinz();
                        //相邻上断面的最小高程
                        double ZMinUp = hdSections.get(riverIDOrder[i]).get(j - 1).getMinz();
                        //如果下断面的最低高程小于下断面的最低高程
                        if (ZMin <= ZMinUp) {
                            ZAL.add(ZMin + HUpboundary + (HDownboundary - HUpboundary) * j / (numOfSection - 1));//断面水位等于断面最低高程加上下边界断面的水深
                        } else {//如果下断面的最低高程大于上断面的最低高程
                            ZAL.add(ZMinUp + HUpboundary + (HDownboundary - HUpboundary) * j / (numOfSection - 1));//断面水位等于下断面最低高程加上下边界断面的水深
                        }
                    }
                    //将流量值都赋值为0
                    for (int j = 0; j < ZAL.size(); j++) {
                        QAL.add(0.0);
                    }
                    hdInitCondition = new HDInitCondition(riverIDOrder[i], QAL, ZAL);
                }
                //将河段的初始条件加入到map中
                initConditionBeanHT.put(riverIDOrder[i], hdInitCondition);
            }
        }

        //人工给定初始条件
        if (initialType.equals("people")) {
            initConditionBeanHT = createInitConditionBeanHT(hdSections, data.initiDatas, riverIDOrder);
        }
        //组装
        Hashtable<String, HDRiver> riverHT = createRiverHT(riverIDOrder, boundaryBeanHT, initConditionBeanHT, hdSections, theta, alpha, dt, hatopo);

        if (data.isPreHeat == true) {
            //预热计算
            Hashtable<String, ArrayList<outputData>> preHeatOutputData = preHeatCompute(hatopo, riverIDOrder, boundaryBeanHT, riverHT);
            //将预热计算得到的稳定值作为新的初始条件
            Hashtable<String, HDInitCondition> initConditionBeanHT2 = createInitConditionBeanHT2(preHeatOutputData, hdSections, data.initiDatas, riverIDOrder);
            //恢复原始的边界条件,并更新riverHT/hdSections
            Hashtable<String, HDBoundary> boundaryBeanHT2 = createBoundaryBeanHT(data.byDatas);
            Hashtable<String, ArrayList<HDSection>> hdSections2 = getRiverSections(riverIDOrder, data.hdSections);
            Hashtable<String, HDRiver> riverHT2 = createRiverHT(riverIDOrder, boundaryBeanHT2, initConditionBeanHT2, hdSections2, theta, alpha, dt, hatopo);
            //正常计算
            Hashtable<String, ArrayList<outputData>> nomalOutputDatas = normalCompute(len, hatopo, riverIDOrder, boundaryBeanHT2, riverHT2);
            data.setOutputDatas(nomalOutputDatas);
        }
        //不预热，直接进入正常计算
        else {
            Hashtable<String, ArrayList<outputData>> outputDatas = normalCompute(len, hatopo, riverIDOrder, boundaryBeanHT, riverHT);
            data.setOutputDatas(outputDatas);
        }
    }

    /**
     * 取预热计算得到的稳定值作为断面的初始计算值
     *
     * @param preHeatOutputData
     * @param hdSections
     * @param initiDatas
     * @param riverID
     * @return
     */
    private Hashtable<String, HDInitCondition> createInitConditionBeanHT2(
            Hashtable<String, ArrayList<outputData>> preHeatOutputData,
            Hashtable<String, ArrayList<HDSection>> hdSections, Hashtable<String, ArrayList<InitialData>> initiDatas,
            String[] riverID) {
        Hashtable<String, HDInitCondition> hdInitCondition = new Hashtable<String, HDInitCondition>();
        // 河段的循环
        for (int i = 0; i < riverID.length; i++) {
            ArrayList<Double> initQ = new ArrayList<Double>();
            ArrayList<Double> initZ = new ArrayList<Double>();
            // 断面的循环
            for (int j = 0; j < preHeatOutputData.get(riverID[i]).size(); j++) {
                initQ.add(preHeatOutputData.get(riverID[i]).get(j).Q[preHeatOutputData.get(riverID[i]).get(j).Q.length
                        - 1]);
                initZ.add(preHeatOutputData.get(riverID[i]).get(j).Z[preHeatOutputData.get(riverID[i]).get(j).Q.length
                        - 1]);
            }
            HDInitCondition hdInitCondition1 = new HDInitCondition(riverID[i], initQ, initZ);
            hdInitCondition.put(riverID[i], hdInitCondition1);
        }
        return hdInitCondition;
    }

    /**
     * 正常计算
     *
     * @param len
     * @param hatopo
     * @param riverIDOrder
     * @param boundaryBeanHT
     * @param riverHT
     * @return
     */
    private Hashtable<String, ArrayList<outputData>> normalCompute(int len, Hashtable<String, HDTopo> hatopo, String[] riverIDOrder, Hashtable<String, HDBoundary> boundaryBeanHT, Hashtable<String, HDRiver> riverHT) {
        Hashtable<String, ArrayList<outputData>> outputDatas;
        for (int i = 0; i < len; i++) {
            String[] forwardL = riverIDOrder;
            //求追赶系数的过程
            for (int j = 0; j < forwardL.length; j++) {
                String riverID = forwardL[j];
                HDBoundary hdBoundary = boundaryBeanHT.get(riverID);
                //河段上游流量边界条件
                if (hdBoundary.getBY1() == 1) {
                    Double p = hdBoundary.getUpBoundary().get(i);
                    riverHT.get(riverID).initPPVV(p, 0.0);
                }
                //河段上游边界为内节点
                else if (hdBoundary.getBY1() == -1) {
                    double[] pv = getPVOfJunction(hatopo, riverID, riverHT, boundaryBeanHT);
                    riverHT.get(riverID).initPPVV(pv[0], pv[1]);
                }
                //上边界为水位的情况
                else if (hdBoundary.getBY1() == 0) {
                    Double p = hdBoundary.getUpBoundary().get(i);
                    riverHT.get(riverID).initPPVV(p, 0.0);
                }
                riverHT.get(riverID).computeForward(riverID);
            }
            //赶求水位和流量的过程
            String[] backwardL = new String[riverIDOrder.length];
            int k = 0;
            for (int j = riverIDOrder.length - 1; j >= 0; j--) {
                backwardL[k] = riverIDOrder[j];
                k++;
            }
            for (int j = 0; j < backwardL.length; j++) {
                String riverID = backwardL[j];
                HDBoundary hdBoundary = boundaryBeanHT.get(riverID);
                //下边界水位条件
                if (hdBoundary.getBY2() == 0) {
                    riverHT.get(riverID).setBoundaryZ(hdBoundary.getDownBoundary().get(i), riverID);
                }
                //边界流量条件
                else if (hdBoundary.getBY2() == 1) {
                    riverHT.get(riverID).setBoundaryQ(hdBoundary.getDownBoundary().get(i), riverID);
                }
                //下边界水位流量条件
                else if (hdBoundary.getBY2() == 2) {
                }
                //计算Z和Q
                riverHT.get(riverID).computeBackward(riverID, i);
            }
        }
        outputDatas=getHDOutputData(riverHT,len,riverIDOrder);
        return outputDatas;
    }

    /**
     * 预热计算
     *
     * @param hatopo
     * @param riverIDOrder
     * @param boundaryBeanHT
     * @param riverHT
     * @return
     */
    private Hashtable<String, ArrayList<outputData>> preHeatCompute(Hashtable<String, HDTopo> hatopo, String[] riverIDOrder, Hashtable<String, HDBoundary> boundaryBeanHT, Hashtable<String, HDRiver> riverHT) {
        Hashtable<String, ArrayList<outputData>> outputDatas;
        int i = 0;
        double maxZ;
        double maxQ;
        do {
            maxZ = 0;//更新
            maxQ = 0;//更新
            String[] forwardL = riverIDOrder;

            //求追赶系数的过程
            for (int j = 0; j < forwardL.length; j++) {//河段的循环
                String riverID = forwardL[j];
                HDBoundary hdBoundary = boundaryBeanHT.get(riverID);
                //河段上游流量边界条件
                if (hdBoundary.getBY1() == 1) {
                    Double p = hdBoundary.getUpBoundary().get(0);
                    riverHT.get(riverID).initPPVV(p, 0.0);
                }
                //河段上游边界为内节点
                else if (hdBoundary.getBY1() == -1) {
                    double[] pv = getPVOfJunction(hatopo, riverID, riverHT, boundaryBeanHT);
                    riverHT.get(riverID).initPPVV(pv[0], pv[1]);
                }

                //上边界为水位的情况
                else if (hdBoundary.getBY1() == 0) {
                    Double p = hdBoundary.getUpBoundary().get(0);
                    riverHT.get(riverID).initPPVV(p, 0.0);
                }
                riverHT.get(riverID).computeForward(riverID);
            }

            //赶求水位和流量的过程
            String[] backwardL = new String[riverIDOrder.length];
            int k = 0;
            for (int j = riverIDOrder.length - 1; j >= 0; j--) {
                backwardL[k] = riverIDOrder[j];
                k++;
            }
            for (int j = 0; j < backwardL.length; j++) {
                String riverID = backwardL[j];
                HDBoundary hdBoundary = boundaryBeanHT.get(riverID);
                //下边界水位条件
                if (hdBoundary.getBY2() == 0) {
                    riverHT.get(riverID).setBoundaryZ(hdBoundary.getDownBoundary().get(0), riverID);
                }
                //下边界流量条件
                else if (hdBoundary.getBY2() == 1) {
                    riverHT.get(riverID).setBoundaryQ(hdBoundary.getDownBoundary().get(i), riverID);
                }
                //下边界水位流量条件
                else if (hdBoundary.getBY2() == 2) {
                }
                //计算Z和Q
                riverHT.get(riverID).computeBackward(riverID, i);
            }
            for (int k1 = 0; k1 < riverHT.size(); k1++) {//河道的循环
                for (int j = 0; j < riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).size(); j++) {//断面的循环
                    int index = riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).get(j).getZ().size();
                    double Z1 = riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).get(j).getZ().get(index - 1);
                    double Z2 = riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).get(j).getZ().get(index - 2);
                    double deltaZ = Z1 - Z2;
                    if (Math.abs(deltaZ) > maxZ)
                        maxZ = Math.abs(deltaZ);
                    double Q1 = riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).get(j).getQ().get(index - 1);
                    double Q2 = riverHT.get(riverIDOrder[k1]).getSectionAL().get(riverIDOrder[k1]).get(j).getQ().get(index - 2);
                    double deltQ = Q1 - Q2;
                    if (Math.abs(deltQ) > maxQ)
                        maxQ = Math.abs(deltQ);
                }
            }
            if ((maxZ > 0.01) || (maxQ > 1))//如果有继续计算的机会，则时间加1
                i++;
        } while (((maxZ > 0.01) || (maxQ > 1))&&i<20000);//预热计算结束的判定,所有断面中前后两个时间点的断面水位和流量差值最大的差值小于0.0001
        System.out.println("一维水动力模型迭代了 "+i+" 次");
        outputDatas=getHDOutputData(riverHT,i,riverIDOrder);
        return outputDatas;
    }

    /**
     * 预热计算的初始边界条件给定
     *
     * @param byDatas
     * @return
     */
    private Hashtable<String, HDBoundary> createBoundaryForPreHeat(ArrayList<BYData> byDatas) {
        // TODO Auto-generated method stub
        Hashtable<String, HDBoundary> hdBoundary = new Hashtable<>();
        for (int i = 0; i < byDatas.size(); i++) {
            String riverID = byDatas.get(i).getRiverID();
            int BY1 = byDatas.get(i).getBY1();
            int BY2 = byDatas.get(i).getBY2();
            String upSectionID = byDatas.get(i).getUpSectionID();
            String downSectionID = byDatas.get(i).getDownSectionID();

            ArrayList<Double> upBoundary = new ArrayList<>();
            ArrayList<Double> downBoundary = new ArrayList<>();
            if (BY1 == 0 || BY1 == 1)//当上边界为水位或者流量时才赋值
                for (int j = 0; j < byDatas.get(i).getUpBoundary().size(); j++) {
                    upBoundary.add(byDatas.get(i).getUpBoundary().get(0));//取第一个值作为恒定的预热上边界
                }
            if (BY2 == 0 || BY2 == 1)//当下边界为水位或者流量时才赋值
                for (int j = 0; j < byDatas.get(i).getDownBoundary().size(); j++) {
                    downBoundary.add(byDatas.get(i).getDownBoundary().get(0));//取第一个值作为恒定的预热上边界
                }
            double[][] xy = null;
            if (BY2 == 2)//当下边界为水位流量关系时的赋值
                xy = byDatas.get(i).getXy();

            HDBoundary hdBoundaryBean = new HDBoundary(riverID, BY1, BY2, upSectionID, downSectionID, upBoundary,
                    downBoundary, xy);
            hdBoundary.put(riverID, hdBoundaryBean);
        }
        return hdBoundary;
    }

    /**
     * @param riversID
     * @param hdSections
     * @return
     */
    private Hashtable<String, ArrayList<HDSection>> getRiverSections(String[] riversID,
                                                                     Hashtable<String, ArrayList<HdSection>> hdSections) {
        Hashtable<String, ArrayList<HDSection>> hdSections1 = new Hashtable<>();
        String SectionID;
        String SectionName;
        String lat;
        String lon;
        double nclLeft;
        double nclRiver;
        double nclRight;
        double dx;
        double min;
        double max;
        for (int i = 0; i < riversID.length; i++) {
            ArrayList<HDSection> hdSections2 = new ArrayList<>();
            for (int j = 0; j < hdSections.get(riversID[i]).size(); j++) {
                SectionID = hdSections.get(riversID[i]).get(j).getSectionID();
                SectionName = hdSections.get(riversID[i]).get(j).getSectionName();
                ArrayList<HDpoint> hDpoints = new ArrayList<>();
                /**
                 * 为了防止洪水超过河提，现在断面的左右点各加一个点
                 */
                HDpoint hDpoint = new HDpoint(
                        hdSections.get(riversID[i]).get(j).getHdPoints().get(0).getStartDistance(), 2400, 0);
                hDpoints.add(hDpoint);
                for (int k = 0; k < hdSections.get(riversID[i]).get(j).getHdPoints().size(); k++) {
                    double x = hdSections.get(riversID[i]).get(j).getHdPoints().get(k).getStartDistance();
                    double y = hdSections.get(riversID[i]).get(j).getHdPoints().get(k).getElevation();
                    int mark = hdSections.get(riversID[i]).get(j).getHdPoints().get(k).getMark();
                    HDpoint hDpoint1 = new HDpoint(x, y, mark);
                    hDpoints.add(hDpoint1);
                }
                HDpoint hDpoint2 = new HDpoint(
                        hdSections.get(riversID[i]).get(j).getHdPoints()
                                .get(hdSections.get(riversID[i]).get(j).getHdPoints().size() - 1).getStartDistance(),
                        2400, 0);
                hDpoints.add(hDpoint2);
                lat = hdSections.get(riversID[i]).get(j).getLat();
                lon = hdSections.get(riversID[i]).get(j).getLon();
                nclLeft = hdSections.get(riversID[i]).get(j).getNclLeft();
                nclRiver = hdSections.get(riversID[i]).get(j).getNclRiver();
                nclRight = hdSections.get(riversID[i]).get(j).getNclRight();
                dx = hdSections.get(riversID[i]).get(j).getDx();
                ArrayList<Double> elevation = new ArrayList<>();
                for (int k = 0; k < hDpoints.size(); k++) {
                    elevation.add(hDpoints.get(k).getElevation());
                }
                min = getHDPointsMin(elevation);
                max = getHDPointsMax(elevation);
                HDSection hdSection = new HDSection(riversID[i], SectionID, SectionName, hDpoints, lat, lon, nclLeft,
                        nclRiver, nclRight, dx, min, max);
                hdSections2.add(hdSection);
            }
            hdSections1.put(riversID[i], hdSections2);
        }
        return hdSections1;
    }

    /**
     * 获取所有点中的最小点
     *
     * @param hdPoints
     * @return
     */
    private double getHDPointsMin(ArrayList<Double> hdPoints) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < hdPoints.size(); i++) {
            if (min >= hdPoints.get(i)) {
                min = hdPoints.get(i);
            }
        }
        return min;
    }

    /**
     * 获取所有点中的最大值ֵ
     *
     * @param hdPoints
     * @return
     */
    private double getHDPointsMax(ArrayList<Double> hdPoints) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < hdPoints.size(); i++) {
            if (max < hdPoints.get(i)) {
                max = hdPoints.get(i);
            }
        }
        return max;
    }

    /**
     * 获取模型的所有断面
     *
     * @param riverTopos
     * @return
     */
    private ArrayList<String> getRivers(ArrayList<RiverTopo> riverTopos) {
        ArrayList<String> riversID = new ArrayList<String>();
        for (int i = 0; i < riverTopos.size(); i++) {
            riversID.add(data.riverTopos.get(i).riverID);
        }
        return riversID;
    }

    /**
     * 获取河网的计算顺序
     *
     * @param hdTopo
     * @param riverID
     * @return
     */
    private String[] getComputeOrder(Hashtable<String, HDTopo> hdTopo, ArrayList<String> riverID) {
        String[] riverOrder = new String[hdTopo.size()];
        HDFunction hdFunction = new HDFunction();
        ArrayList<String> topRivers = new ArrayList<>();
        ArrayList<String> topRivers2 = new ArrayList<>();
        String lastRiver = null;
        for (int i = 0; i < hdTopo.size(); i++) {//找出最下游的河段
            if (hdTopo.get(riverID.get(i)).getDownriverID() == null) {
                lastRiver = riverID.get(i);
            }
        }
        if (riverID.size() > 1) {//即河段不是直线型河网的判断
            topRivers.add(lastRiver);//将最下游的河段加入到集合中
            topRivers.add(null);//添加一个null
            topRivers = hdFunction.findUpRivers(0, lastRiver, hdTopo, topRivers);
            for (int i = 0; i < topRivers.size(); i++) {
                topRivers2.add(topRivers.get(i));
            }
            topRivers.clear();
            topRivers.add(lastRiver);
            topRivers.add(null);
            topRivers2.addAll(hdFunction.findUpRivers(1, lastRiver, hdTopo, topRivers));
            for (int i = 1; i < topRivers2.size(); i++) {
                if (topRivers2.get(i) == topRivers2.get(0))
                    topRivers2.remove(i);
            }
        }
        for (int i = 0; i < riverID.size(); i++) {
            riverOrder[i] = riverID.get(i);
        }
        return riverOrder;
    }


    /**
     * 获取模型的河网拓扑结构
     *
     * @param riverTopos
     * @return
     */
    private Hashtable<String, HDTopo> getRiverTopo(ArrayList<RiverTopo> riverTopos) {
        // 将riverTopos当中的控制赋值为null
        for (int i = 0; i < riverTopos.size(); i++) {
            if (riverTopos.get(i).getRiverID() == null || riverTopos.get(i).getRiverID().equals("")) {
                riverTopos.get(i).setDownRiverID(null);
            }
            if (riverTopos.get(i).getDownRiverID() == null || riverTopos.get(i).getDownRiverID().equals("")) {
                riverTopos.get(i).setDownRiverID(null);
            }
        }
        Hashtable<String, HDTopo> hdTopos = new Hashtable<>();
        String riverID;
        String downRiverID;
        for (int i = 0; i < riverTopos.size(); i++) {
            String[] upRiverID = new String[2];
            riverID = riverTopos.get(i).getRiverID();
            downRiverID = riverTopos.get(i).getDownRiverID();
            int k = 0;
            for (int j = 0; j < riverTopos.size(); j++) {
                if (riverID.equals(riverTopos.get(j).getDownRiverID())) {
                    upRiverID[k] = riverTopos.get(j).getRiverID();
                    k++;
                    if (k > 1)
                        break;
                }
            }
            HDTopo hdTopo = new HDTopo(riverID, upRiverID, downRiverID);
            hdTopos.put(riverID, hdTopo);
        }
        return hdTopos;
    }

    /**
     * 初始化计算条件
     *
     * @param initialDatas
     * @param riverID
     * @return
     */
    private Hashtable<String, HDInitCondition> createInitConditionBeanHT(Hashtable<String, ArrayList<HDSection>> hdSections, Hashtable<String, ArrayList<InitialData>> initialDatas, String[] riverID) {
        Hashtable<String, HDInitCondition> hdInitCondition = new Hashtable<>();
        // River cycle
        for (int i = 0; i < riverID.length; i++) {
            ArrayList<Double> initQ = new ArrayList<>();
            ArrayList<Double> initZ = new ArrayList<>();
            // Section cycle
            for (int j = 0; j < initialDatas.get(riverID[i]).size(); j++) {
                initQ.add(initialDatas.get(riverID[i]).get(j).initialQ);
                initZ.add(initialDatas.get(riverID[i]).get(j).initialZ);
//                initZ.add(initialDatas.get(riverID[i]).get(j).initialZ + 0.15 + hdSections.get(riverID[i]).get(j).getMinz());
            }
            HDInitCondition hdInitCondition1 = new HDInitCondition(riverID[i], initQ, initZ);
            hdInitCondition.put(riverID[i], hdInitCondition1);
        }
        return hdInitCondition;
    }

    /**
     * 正式计算的边界条件给定
     *
     * @param byDatas
     * @return
     */
    private Hashtable<String, HDBoundary> createBoundaryBeanHT(ArrayList<BYData> byDatas) {
        Hashtable<String, HDBoundary> hdBoundary = new Hashtable<>();
        for (int i = 0; i < byDatas.size(); i++) {
            String riverID = byDatas.get(i).getRiverID();
            int BY1 = byDatas.get(i).getBY1();
            int BY2 = byDatas.get(i).getBY2();
            String upSectionID = byDatas.get(i).getUpSectionID();
            String downSectionID = byDatas.get(i).getDownSectionID();
            ArrayList<Double> upBoundary = new ArrayList<>();
            if (BY1 == 0 || BY1 == 1) {//当上边界为水位或者流量时才赋值
                upBoundary = byDatas.get(i).getUpBoundary();
            }
            ArrayList<Double> downBoundary = new ArrayList<>();
            if (BY2 == 0 || BY2 == 1) {//当下边界为水位或者流量时才赋值
                downBoundary = byDatas.get(i).getDownBoundary();
            }
            double[][] xy = null;
            if (BY2 == 2) {//当下边界为水位流量关系时才赋值
                xy = byDatas.get(i).getXy();
            }
            HDBoundary hdBoundaryBean = new HDBoundary(riverID, BY1, BY2, upSectionID, downSectionID, upBoundary,
                    downBoundary, xy);
            hdBoundary.put(riverID, hdBoundaryBean);
        }
        return hdBoundary;
    }

    /**
     * 构建模型计算的结构
     *
     * @param riverIDOrder
     * @param boundaryBeanHT
     * @param initConditionBeanHT
     * @param hdSections
     * @param theta
     * @param alpha
     * @param dt
     * @param hatopo
     * @return
     */
    private Hashtable<String, HDRiver> createRiverHT(String[] riverIDOrder, Hashtable<String, HDBoundary> boundaryBeanHT, Hashtable<String, HDInitCondition> initConditionBeanHT, Hashtable<String, ArrayList<HDSection>> hdSections, double theta, double alpha, double dt, Hashtable<String, HDTopo> hatopo) {
        Hashtable<String, HDRiver> hdRiver = new Hashtable<>();
        String[] riverAL = riverIDOrder;
        for (int i = 0; i < riverAL.length; i++) {
            String riverID = riverAL[i];
            HDBoundary boundaryBean = boundaryBeanHT.get(riverID);
            HDInitCondition initConditionBean = initConditionBeanHT.get(riverID);
            HDRiver river = new HDRiver(riverID, hdSections, theta, alpha, dt, boundaryBean.getBY1(),
                    boundaryBean.getBY2(), boundaryBean.getUpBoundary(), boundaryBean.getDownBoundary(),
                    initConditionBean.getQAL(), initConditionBean.getZAL(), hatopo, boundaryBean.getXy());
            hdRiver.put(riverID, river);
        }
        return hdRiver;
    }

    /**
     * 人工设定河道的初始水位
     *
     * @param hdSection
     * @param riverID
     * @param hInitial
     * @return
     */
    private ArrayList<Double> setHDInitial(Hashtable<String, ArrayList<HDSection>> hdSection, String riverID, double hInitial) {
        ArrayList<Double> ZAL = new ArrayList<>();
        for (int j = 1; j < hdSection.get(riverID).size(); j++) {
            //断面上点的最小高程
            double ZMin = hdSection.get(riverID).get(j).getMinz();
            //相邻上断面的最小高程
            double ZMinUp = hdSection.get(riverID).get(j - 1).getMinz();
            //如果下断面的最低高程小于下断面的最低高程
            if (ZMin <= ZMinUp) {
                ZAL.add(ZMin + hInitial);//断面水位等于断面最低高程加上下边界断面的水深
            } else {//如果下断面的最低高程大于上断面的最低高程
                ZAL.add(ZMinUp + hInitial);//断面水位等于下断面最低高程加上下边界断面的水深
            }
        }
        return ZAL;
    }

    /**
     * 两河交汇追赶系数P和V的处理
     *
     * @param hatopo
     * @param riverID
     * @param riverHT
     * @param boundaryBeanHT
     * @return
     */
    private double[] getPVOfJunction(Hashtable<String, HDTopo> hatopo, String riverID, Hashtable<String, HDRiver> riverHT, Hashtable<String, HDBoundary> boundaryBeanHT) {
        double[] pv = new double[2];
        double p = 0;
        double v = 0;
        //获取上游的河流和末断面,目前只处理两河交汇的情况
        String[] riverid1 = hatopo.get(riverID).getUpRiverID();//获取河流的上游两个断面
        String riverID1 = riverid1[0];
        int lastIndex1 = riverHT.get(riverID1).getSectionAL().get(riverID1).size() - 1;
        String riverID2 = riverid1[1];
        int lastIndex2 = riverHT.get(riverID2).getSectionAL().get(riverID2).size() - 1;

        //riverID1上边界为流量或者内节点，而且riverID2上边界为水位
        if ((boundaryBeanHT.get(riverID1).getBY1() == 1 || boundaryBeanHT.get(riverID1).getBY1() == -1) && boundaryBeanHT.get(riverID2).getBY1() == 0) {
            p = riverHT.get(riverID1).getPP().get(lastIndex1) + riverHT.get(riverID2).getPP().get(lastIndex2) / riverHT.get(riverID2).getVV().get(lastIndex2);
            v = riverHT.get(riverID1).getVV().get(lastIndex1) + 1.0 / riverHT.get(riverID2).getVV().get(lastIndex2);
            riverHT.get(riverID).initPPVV(p, v);
        }

        //riverID1上边界为水位，而且riverID2上边界为流量
        if ((boundaryBeanHT.get(riverID2).getBY1() == 1 || boundaryBeanHT.get(riverID2).getBY1() == -1) && boundaryBeanHT.get(riverID1).getBY1() == 0) {
            p = riverHT.get(riverID2).getPP().get(lastIndex2) + riverHT.get(riverID1).getPP().get(lastIndex1) / riverHT.get(riverID1).getVV().get(lastIndex1);
            v = riverHT.get(riverID2).getVV().get(lastIndex2) + 1.0 / riverHT.get(riverID1).getVV().get(lastIndex1);
            riverHT.get(riverID).initPPVV(p, v);
        }
        //riverID1和riverID2上边界都为水位
        if (boundaryBeanHT.get(riverID1).getBY1() == 0 && boundaryBeanHT.get(riverID2).getBY1() == 0) {
            p = riverHT.get(riverID2).getPP().get(lastIndex2) / riverHT.get(riverID2).getVV().get(lastIndex2) + riverHT.get(riverID1).getPP().get(lastIndex1) / riverHT.get(riverID1).getVV().get(lastIndex1);
            v = 1.0 / riverHT.get(riverID2).getVV().get(lastIndex2) + 1.0 / riverHT.get(riverID1).getVV().get(lastIndex1);
            riverHT.get(riverID).initPPVV(p, v);
        }
        //riverID1上边界为流量或者内节点，而且riverID2上边界为流量或者这内节点
        if ((boundaryBeanHT.get(riverID1).getBY1() == 1 || boundaryBeanHT.get(riverID1).getBY1() == -1) && (boundaryBeanHT.get(riverID1).getBY1() == 1 || boundaryBeanHT.get(riverID1).getBY1() == -1)) {
            p = riverHT.get(riverID2).getPP().get(lastIndex2) + riverHT.get(riverID1).getPP().get(lastIndex1);
            v = riverHT.get(riverID2).getVV().get(lastIndex2) + riverHT.get(riverID1).getVV().get(lastIndex1);
            riverHT.get(riverID).initPPVV(p, v);
        }
        pv[0] = p;
        pv[1] = v;
        return pv;
    }

    /**
     * 获取水动力模型的计算结果
     * @param riverHT
     * @param i
     * @param riverIDOrder
     * @return
     */
    private Hashtable<String, ArrayList<outputData>>  getHDOutputData(Hashtable<String, HDRiver> riverHT,int i,String[] riverIDOrder){
        Hashtable<String, ArrayList<outputData>> outputDatas=new Hashtable<>();
        for (int i1 = 0; i1 < riverHT.size(); i1++) {//河道的数目
            ArrayList<outputData> outputData1 = new ArrayList<>();
            for (int j = 0; j < riverHT.get(riverIDOrder[i1]).getSectionAL().get(riverIDOrder[i1]).size(); j++) {//断面的个数
                double z[] = new double[i];
                double q[] = new double[i];
                String sectionID = riverHT.get(riverIDOrder[i1]).getSectionAL().get(riverIDOrder[i1]).get(j).getSectionID();
                for (int k = 0; k < i; k++) {
                    z[k] = riverHT.get(riverIDOrder[i1]).getSectionAL().get(riverIDOrder[i1]).get(j).getZ().get(k);
                    q[k] = riverHT.get(riverIDOrder[i1]).getSectionAL().get(riverIDOrder[i1]).get(j).getQ().get(k);
                }
                outputData output = new outputData(sectionID, z, q);
                outputData1.add(output);
            }
            outputDatas.put(riverIDOrder[i1], outputData1);
        }
        return outputDatas;
    }
}
