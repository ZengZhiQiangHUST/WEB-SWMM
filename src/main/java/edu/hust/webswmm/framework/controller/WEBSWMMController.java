package edu.hust.webswmm.framework.controller;

import edu.hust.webswmm.framework.mathmodel.tools.Tools;
import edu.hust.webswmm.framework.service.mdb.MdbDataService;
import edu.hust.webswmm.model.mdb.entity.*;
import edu.hust.webswmm.model.mdb.nonentity.RequestClass;
import edu.hust.webswmm.model.mdb.nonentity.resList.*;
import edu.hust.webswmm.model.mdb.nonentity.resStatistic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Double.MIN_VALUE;


@RestController
@RequestMapping("WebswmmController")
public class WEBSWMMController {
    @Autowired
    MdbDataService mdbDataService;
    Tools tools = new Tools();
//-----------------------------Rainwater pipe network---------------------------------
    /**
     * 单出水口的查询
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("outfall/info")
    public Outfall getResOfOutfall(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "outfall";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getOutfallResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResOutfalls> outfallResults = mdbDataService.getOutfallResultsService(id, startTime, endTime);
        if (startTime.getTime() < outfallResults.get(0).getOutfallTime().getTime())
            startTime = outfallResults.get(0).getOutfallTime();
        int timeStep = 0;
        if (outfallResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((outfallResults.size() - 1) * 1000));
        Outfall outfall = new Outfall(type, id, timeStep, startTime, endTime, outfallResults);
        return outfall;
    }

    /**
     * 多出水口的查询
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("outfalllist/info")
    public List<Outfall> getResOfbOutfall(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "outfall";
        List<Outfall> outfalls = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getOutfallResMT(ids[i]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResOutfalls> outfallResults = mdbDataService.getOutfallResultsService(ids[i], startTime, endTime);
            if (startTime.getTime() < outfallResults.get(0).getOutfallTime().getTime())
                startTime = outfallResults.get(0).getOutfallTime();
            int timeStep = 0;
            if (outfallResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((outfallResults.size() - 1) * 1000));
            Outfall outfall = new Outfall(type, ids[i], timeStep, startTime, endTime, outfallResults);
            outfalls.add(outfall);
        }
        return outfalls;
    }

    /**
     * 单出水口的信息统计
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("outfallStatistic/info")
    public OutfallStatistic getResOfOutStatistic(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "outfall";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getOutfallResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResOutfalls> outfallResults = mdbDataService.getOutfallResultsService(id, startTime, endTime);
        if (startTime.getTime() < outfallResults.get(0).getOutfallTime().getTime())
            startTime = outfallResults.get(0).getOutfallTime();
        int timeStep = 0;
        if (outfallResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((outfallResults.size() - 1) * 1000));
        Outfall outfall = new Outfall(type, id, timeStep, startTime, endTime, outfallResults);
        /**
         * 统计值指标值
         */
        double aveDepth = 0.f;//平均水深
        double maxDepth = -MIN_VALUE;//最大水深
        double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
        Date maxTimeDepth = null;//最大水深发生时间
        double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
        double maxInFlow = -MIN_VALUE; //最大入流量
        Date maxTimeInflow = null;//最大入流量发生时间
        double latFlowVolume = 0.f;//侧向补给总体积
        double totalFlowVolume = 0.f;//总入流体积
        double avgFlow = 0.f;

        for (int i = 0; i < outfall.getOutfallDepth().size(); i++) {
            aveDepth = aveDepth + outfall.getOutfallDepth().get(i);
            avgFlow = avgFlow + outfall.getOutfallFlow().get(i);
            if (maxDepth < outfall.getOutfallDepth().get(i)) {
                maxDepth = outfall.getOutfallDepth().get(i);
                maxTimeDepth = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
            }
            if (maxHGL < outfall.getOutfallHgl().get(i))
                maxHGL = outfall.getOutfallHgl().get(i);
            if (maxLateralFlow < outfall.getOutfallLatflow().get(i))
                maxLateralFlow = outfall.getOutfallLatflow().get(i);
            if (maxInFlow < outfall.getOutfallFlow().get(i)) {
                maxInFlow = outfall.getOutfallFlow().get(i);
                maxTimeInflow = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
            }
            if (i < outfall.getOutfallDepth().size() - 1) {
                latFlowVolume = latFlowVolume + outfall.getOutfallLatflow().get(i) * outfall.getTimeStep();
                totalFlowVolume = totalFlowVolume + outfall.getOutfallFlow().get(i) * outfall.getTimeStep();
            }
        }
        aveDepth = aveDepth / (1.0F * outfall.getOutfallDepth().size());
        avgFlow = avgFlow / (1.0F * outfall.getOutfallFlow().size());
        OutfallStatistic outfallStatistic = new OutfallStatistic(id, type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, avgFlow);
        return outfallStatistic;
    }

    /**
     * 多出水口的信息统计
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("outfallStatistics/info")
    public List<OutfallStatistic> getResOfOutStatistic(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "outfall";
        List<OutfallStatistic> outfallStatistics = new ArrayList<>();
        for (int k = 0; k < ids.length; k++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getOutfallResMT(ids[k]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResOutfalls> outfallResults = mdbDataService.getOutfallResultsService(ids[k], startTime, endTime);
            if (startTime.getTime() < outfallResults.get(0).getOutfallTime().getTime())
                startTime = outfallResults.get(0).getOutfallTime();
            int timeStep = 0;
            if (outfallResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((outfallResults.size() - 1) * 1000));
            Outfall outfall = new Outfall(type, ids[k], timeStep, startTime, endTime, outfallResults);
            /**
             * 统计值指标值
             */
            double aveDepth = 0.f;//平均水深
            double maxDepth = -MIN_VALUE;//最大水深
            double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
            Date maxTimeDepth = null;//最大水深发生时间
            double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
            double maxInFlow = -MIN_VALUE; //最大入流量
            Date maxTimeInflow = null;//最大入流量发生时间
            double latFlowVolume = 0.f;//侧向补给总体积
            double totalFlowVolume = 0.f;//总入流体积
            double avgFlow = 0.f;
            for (int i = 0; i < outfall.getOutfallDepth().size(); i++) {
                aveDepth = aveDepth + outfall.getOutfallDepth().get(i);
                avgFlow = avgFlow + outfall.getOutfallFlow().get(i);
                if (maxDepth < outfall.getOutfallDepth().get(i)) {
                    maxDepth = outfall.getOutfallDepth().get(i);
                    maxTimeDepth = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
                }
                if (maxHGL < outfall.getOutfallHgl().get(i))
                    maxHGL = outfall.getOutfallHgl().get(i);
                if (maxLateralFlow < outfall.getOutfallLatflow().get(i))
                    maxLateralFlow = outfall.getOutfallLatflow().get(i);
                if (maxInFlow < outfall.getOutfallFlow().get(i)) {
                    maxInFlow = outfall.getOutfallFlow().get(i);
                    maxTimeInflow = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
                }
                if (i < outfall.getOutfallDepth().size() - 1) {
                    latFlowVolume = latFlowVolume + outfall.getOutfallLatflow().get(i) * outfall.getTimeStep();
                    totalFlowVolume = totalFlowVolume + outfall.getOutfallFlow().get(i) * outfall.getTimeStep();
                }
            }
            aveDepth = aveDepth / (1.0F * outfall.getOutfallDepth().size());
            avgFlow = avgFlow / (1.0F * outfall.getOutfallFlow().size());
            OutfallStatistic outfallStatistic = new OutfallStatistic(ids[k], type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, avgFlow);
            outfallStatistics.add(outfallStatistic);
        }
        return outfallStatistics;
    }

    /**
     * 所有出水口的信息统计
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("alloutfallStat/info")
    public List<OutfallStatistic> getResOfAllOutStatistic(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<OutfallStatistic> outfallStatisticList = new ArrayList<>();
        List<String> idOfOutfall = mdbDataService.getFeatureAllIDService("outfall");
        for (int k = 0; k < idOfOutfall.size(); k++) {
            String type = "outfall";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getOutfallResMT(idOfOutfall.get(k));
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResOutfalls> outfallResults = mdbDataService.getOutfallResultsService(idOfOutfall.get(k), startTime, endTime);
            if (startTime.getTime() < outfallResults.get(0).getOutfallTime().getTime())
                startTime = outfallResults.get(0).getOutfallTime();
            int timeStep = 0;
            if (outfallResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((outfallResults.size() - 1) * 1000));
            Outfall outfall = new Outfall(type, idOfOutfall.get(k), timeStep, startTime, endTime, outfallResults);
            /**
             * 统计值指标值
             */
            double aveDepth = 0.f;//平均水深
            double maxDepth = -MIN_VALUE;//最大水深
            double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
            Date maxTimeDepth = null;//最大水深发生时间
            double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
            double maxInFlow = -MIN_VALUE; //最大入流量
            Date maxTimeInflow = null;//最大入流量发生时间
            double latFlowVolume = 0.f;//侧向补给总体积
            double totalFlowVolume = 0.f;//总入流体积
            double avgFlow = 0.f;

            for (int i = 0; i < outfall.getOutfallDepth().size(); i++) {
                aveDepth = aveDepth + outfall.getOutfallDepth().get(i);
                avgFlow = avgFlow + outfall.getOutfallFlow().get(i);
                if (maxDepth < outfall.getOutfallDepth().get(i)) {
                    maxDepth = outfall.getOutfallDepth().get(i);
                    maxTimeDepth = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
                }
                if (maxHGL < outfall.getOutfallHgl().get(i))
                    maxHGL = outfall.getOutfallHgl().get(i);
                if (maxLateralFlow < outfall.getOutfallLatflow().get(i))
                    maxLateralFlow = outfall.getOutfallLatflow().get(i);
                if (maxInFlow < outfall.getOutfallFlow().get(i)) {
                    maxInFlow = outfall.getOutfallFlow().get(i);
                    maxTimeInflow = new Date(outfall.getStartTime().getTime() + i * outfall.getTimeStep() * 1000);
                }
                if (i < outfall.getOutfallDepth().size() - 1) {
                    latFlowVolume = latFlowVolume + outfall.getOutfallLatflow().get(i) * outfall.getTimeStep();
                    totalFlowVolume = totalFlowVolume + outfall.getOutfallFlow().get(i) * outfall.getTimeStep();
                }
            }
            aveDepth = aveDepth / (1.0F * outfall.getOutfallDepth().size());
            avgFlow = avgFlow / (1.0F * outfall.getOutfallFlow().size());
            OutfallStatistic outfallStatistic = new OutfallStatistic(idOfOutfall.get(k), type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, avgFlow);
            outfallStatisticList.add(outfallStatistic);
        }
        return outfallStatisticList;
    }

    /**
     * 单管段的信息查询
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("link/info")
    public Link getResOfLink(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        /**
         * 管段的ID需要映射
         */
        String gId = mdbDataService.getinOfLinks(id);
        if (gId == null)
            gId = id;
        String type = "link";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getLinkResMT(gId);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResConduits> linkResults = mdbDataService.getLinkResultsSevice(gId, startTime, endTime);
        //数据不足指定小时数的处理
        if (startTime.getTime() < linkResults.get(0).getLinkTime().getTime())
            startTime = linkResults.get(0).getLinkTime();
        int timeStep = 0;
        if (linkResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((linkResults.size() - 1) * 1000));
        Link link = new Link(type, id, timeStep, startTime, endTime, linkResults);
        return link;
    }

    /**
     * 多管段信息的查询
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("linklist/info")
    public List<Link> getResOfLink(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "link";
        List<Link> links = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            String gId = mdbDataService.getinOfLinks(ids[i]);
            if (gId == null)
                gId = ids[i];
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getLinkResMT(gId);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResConduits> linkResults = mdbDataService.getLinkResultsSevice(gId, startTime, endTime);
            //数据不足指定小时数的处理
            if (startTime.getTime() < linkResults.get(0).getLinkTime().getTime())
                startTime = linkResults.get(0).getLinkTime();
            int timeStep = 0;
            if (linkResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((linkResults.size() - 1) * 1000));
            Link link = new Link(type, ids[i], timeStep, startTime, endTime, linkResults);
            links.add(link);
        }
        return links;
    }

    /**
     * 单管段信息的统计
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("linkStatistic/info")
    public LinkStatistic getResOfLinkStatistic(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        /**
         * 管段的ID需要映射
         */
        String gId = mdbDataService.getinOfLinks(id);
        if (gId == null)
            gId = id;
        String type = "link";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getLinkResMT(gId);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResConduits> linkResults = mdbDataService.getLinkResultsSevice(gId, startTime, endTime);
        if (startTime.getTime() < linkResults.get(0).getLinkTime().getTime())
            startTime = linkResults.get(0).getLinkTime();
        int timeStep = 0;
        if (linkResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((linkResults.size() - 1) * 1000));
        Link link = new Link(type, gId, timeStep, startTime, endTime, linkResults);
        /**
         * 统计值的计算
         */
        double maxFlow = 0f;//最大流量
        Date maxTimeFlow = null;//最大流量发生时间
        double maxVelocity = 0f;//最大流速
        for (int i = 0; i < link.getLinkDepth().size(); i++) {
            if (maxFlow < Math.abs(link.getLinkFlow().get(i))) {
                maxFlow = link.getLinkFlow().get(i);
                maxTimeFlow = new Date(link.getStartTime().getTime() + i * link.getTimeStep() * 1000);
            }
            if (maxVelocity < Math.abs(link.getLinkVelocity().get(i))) {
                maxVelocity = link.getLinkVelocity().get(i);
            }
        }
        LinkStatistic linkStatistic = new LinkStatistic(id, type, startTime, endTime, maxFlow, maxTimeFlow, maxVelocity);
        return linkStatistic;
    }

    /**
     * 多管段信息的统计
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("linkStatistics/info")
    public List<LinkStatistic> getResOfLinkStatistics(@RequestBody RequestClass requestClass) throws ParseException {
        List<LinkStatistic> linkStatisticList = new ArrayList<>();
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        for (int k = 0; k < ids.length; k++) {
            /**
             * 管段的ID需要映射
             */
            String gId = mdbDataService.getinOfLinks(ids[k]);
            if (gId == null)
                gId = ids[k];
            String type = "link";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getLinkResMT(gId);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResConduits> linkResults = mdbDataService.getLinkResultsSevice(gId, startTime, endTime);
            if (startTime.getTime() < linkResults.get(0).getLinkTime().getTime())
                startTime = linkResults.get(0).getLinkTime();
            int timeStep = 0;
            if (linkResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((linkResults.size() - 1) * 1000));
            Link link = new Link(type, gId, timeStep, startTime, endTime, linkResults);
            /**
             * 统计值的计算
             */
            double maxFlow = 0f;//最大流量
            Date maxTimeFlow = null;//最大流量发生时间
            double maxVelocity = 0f;//最大流速
            for (int i = 0; i < link.getLinkDepth().size(); i++) {
                if (maxFlow < Math.abs(link.getLinkFlow().get(i))) {
                    maxFlow = link.getLinkFlow().get(i);
                    maxTimeFlow = new Date(link.getStartTime().getTime() + i * link.getTimeStep() * 1000);
                }
                if (maxVelocity < Math.abs(link.getLinkVelocity().get(i))) {
                    maxVelocity = link.getLinkVelocity().get(i);
                }
            }
            LinkStatistic linkStatistic = new LinkStatistic(ids[k], type, startTime, endTime, maxFlow, maxTimeFlow, maxVelocity);
            linkStatisticList.add(linkStatistic);
        }
        return linkStatisticList;
    }

    /**
     * 所有管段信息的统计
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("alllinkStatistic/info")
    public List<LinkStatistic> getResOfAllLinkStatistic(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<LinkStatistic> linkStatisticList = new ArrayList<>();
        List<String> idOfLink = mdbDataService.getFeatureAllIDService("link");
        for (int k = 0; k < idOfLink.size(); k++) {
            /**
             * 管段的ID需要映射
             */
            String gId = mdbDataService.getinOfLinks(idOfLink.get(k));
            if (gId == null)
                gId = idOfLink.get(k);
            String type = "link";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getLinkResMT(gId);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResConduits> linkResults = mdbDataService.getLinkResultsSevice(gId, startTime, endTime);
            if (startTime.getTime() < linkResults.get(0).getLinkTime().getTime())
                startTime = linkResults.get(0).getLinkTime();
            int timeStep = 0;
            if (linkResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((linkResults.size() - 1) * 1000));
            Link link = new Link(type, gId, timeStep, startTime, endTime, linkResults);
            /**
             * 统计值的计算
             */
            double maxFlow = 0f;//最大流量
            Date maxTimeFlow = null;//最大流量发生时间
            double maxVelocity = 0f;//最大流速
            for (int i = 0; i < link.getLinkDepth().size(); i++) {
                if (maxFlow < Math.abs(link.getLinkFlow().get(i))) {
                    maxFlow = link.getLinkFlow().get(i);
                    maxTimeFlow = new Date(link.getStartTime().getTime() + i * link.getTimeStep() * 1000);
                }
                if (maxVelocity < Math.abs(link.getLinkVelocity().get(i))) {
                    maxVelocity = link.getLinkVelocity().get(i);
                }
            }
            LinkStatistic linkStatistic = new LinkStatistic(idOfLink.get(k), type, startTime, endTime, maxFlow, maxTimeFlow, maxVelocity);
            linkStatisticList.add(linkStatistic);
        }
        return linkStatisticList;
    }

    /**
     * 单雨水井信息的查询
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("junction/info")
    public Junction getResOfJun(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "junction";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getJunResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResJunctions> ResJunctions = mdbDataService.getJunResultsService(id, startTime, endTime);
        if (startTime.getTime() < ResJunctions.get(0).getJunTime().getTime())
            startTime = ResJunctions.get(0).getJunTime();
        int timeStep = 0;
        if (ResJunctions.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((ResJunctions.size() - 1) * 1000));
        Junction junction = new Junction(type, id, timeStep, startTime, endTime, ResJunctions);
        return junction;
    }

    /**
     * 多雨水井信息的查询
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("junlist/info")
    public List<Junction> getResJun(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "junction";
        List<Junction> junctions = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getJunResMT(ids[i]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResJunctions> ResJunctions = mdbDataService.getJunResultsService(ids[i], startTime, endTime);
            if (startTime.getTime() < ResJunctions.get(0).getJunTime().getTime())
                startTime = ResJunctions.get(0).getJunTime();
            int timeStep = 0;
            if (ResJunctions.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((ResJunctions.size() - 1) * 1000));
            Junction junction = new Junction(type, ids[i], timeStep, startTime, endTime, ResJunctions);
            junctions.add(junction);
        }
        return junctions;
    }

    /**
     * 单雨水井信息的统计
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("junStatistic/info")
    public JunStatistic getResOfJunStatistic(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        /**
         * 获取对应ID的计算结果
         */
        String type = "junction";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getJunResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResJunctions> ResJunctions = mdbDataService.getJunResultsService(id, startTime, endTime);
        if (startTime.getTime() < ResJunctions.get(0).getJunTime().getTime())
            startTime = ResJunctions.get(0).getJunTime();
        int timeStep = 0;
        if (ResJunctions.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((ResJunctions.size() - 1) * 1000));
        Junction junction = new Junction(type, id, timeStep, startTime, endTime, ResJunctions);
        /**
         * 统计值指标值
         */
        double aveDepth = 0.f;//平均水深
        double maxDepth = -MIN_VALUE;//最大水深
        double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
        Date maxTimeDepth = null;//最大水深发生时间
        double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
        double maxInFlow = -MIN_VALUE; //最大入流量
        Date maxTimeInflow = null;//最大入流量发生时间
        double latFlowVolume = 0.f;//侧向补给总体积
        double totalFlowVolume = 0.f;//总入流体积
        double maxFlood = -MIN_VALUE;//最大溢出量
        double floodedTime;//溢出时长
        Date maxTimeFlood = null;//最大溢出量发生时间
        double totalFloodVolume = 0.f; //总计溢出体积

        double times = 0.f;
        for (int i = 0; i < junction.getJunDepth().size(); i++) {
            aveDepth = aveDepth + junction.getJunDepth().get(i);
            if (maxDepth < junction.getJunDepth().get(i)) {
                maxDepth = junction.getJunDepth().get(i);
                maxTimeDepth = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
            }
            if (maxHGL < junction.getJunHgl().get(i))
                maxHGL = junction.getJunHgl().get(i);
            if (maxLateralFlow < junction.getJunLatFlow().get(i))
                maxLateralFlow = junction.getJunLatFlow().get(i);
            if (maxInFlow < junction.getJunFlow().get(i)) {
                maxInFlow = junction.getJunFlow().get(i);
                maxTimeInflow = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
            }
            if (maxFlood < junction.getJunFlood().get(i)) {
                maxFlood = junction.getJunFlood().get(i);
                maxTimeFlood = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
            }
            if (junction.getJunFlood().get(i) > 0)//水溢出的判定
            {
                times++;
            }
            if (i < junction.getJunDepth().size() - 1) {
                latFlowVolume = latFlowVolume + junction.getJunLatFlow().get(i) * junction.getTimeStep() / 1000.0F;
                totalFlowVolume = totalFlowVolume + junction.getJunFlow().get(i) * junction.getTimeStep() / 1000.0F;
                totalFloodVolume = totalFloodVolume + junction.getJunFlood().get(i) * junction.getTimeStep() / 1000.0F;
            }
        }
        aveDepth = aveDepth / (1.0F * junction.getJunDepth().size());
        floodedTime = times * junction.getTimeStep();
        JunStatistic junStatistic = new JunStatistic(id, type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, floodedTime, maxFlood, maxTimeFlood, totalFloodVolume);
        return junStatistic;
    }

    /**
     * 多雨水井信息的统计
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("junStatistics/info")
    public List<JunStatistic> getResOfJunStatistics(@RequestBody RequestClass requestClass) throws ParseException {
        List<JunStatistic> junStatisticList = new ArrayList<>();
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        for (int k = 0; k < ids.length; k++) {
            /**
             * 获取对应ID的计算结果
             */
            String type = "junction";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getJunResMT(ids[k]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResJunctions> ResJunctions = mdbDataService.getJunResultsService(ids[k], startTime, endTime);
            if (startTime.getTime() < ResJunctions.get(0).getJunTime().getTime())
                startTime = ResJunctions.get(0).getJunTime();
            int timeStep = 0;
            if (ResJunctions.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((ResJunctions.size() - 1) * 1000));
            Junction junction = new Junction(type, ids[k], timeStep, startTime, endTime, ResJunctions);
            /**
             * 统计值指标值
             */
            double aveDepth = 0.f;//平均水深
            double maxDepth = -MIN_VALUE;//最大水深
            double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
            Date maxTimeDepth = null;//最大水深发生时间
            double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
            double maxInFlow = -MIN_VALUE; //最大入流量
            Date maxTimeInflow = null;//最大入流量发生时间
            double latFlowVolume = 0.f;//侧向补给总体积
            double totalFlowVolume = 0.f;//总入流体积
            double maxFlood = -MIN_VALUE;//最大溢出量
            double floodedTime;//溢出时长
            Date maxTimeFlood = null;//最大溢出量发生时间
            double totalFloodVolume = 0.f; //总计溢出体积

            double times = 0.f;
            for (int i = 0; i < junction.getJunDepth().size(); i++) {
                aveDepth = aveDepth + junction.getJunDepth().get(i);
                if (maxDepth < junction.getJunDepth().get(i)) {
                    maxDepth = junction.getJunDepth().get(i);
                    maxTimeDepth = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (maxHGL < junction.getJunHgl().get(i))
                    maxHGL = junction.getJunHgl().get(i);
                if (maxLateralFlow < junction.getJunLatFlow().get(i))
                    maxLateralFlow = junction.getJunLatFlow().get(i);
                if (maxInFlow < junction.getJunFlow().get(i)) {
                    maxInFlow = junction.getJunFlow().get(i);
                    maxTimeInflow = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (maxFlood < junction.getJunFlood().get(i)) {
                    maxFlood = junction.getJunFlood().get(i);
                    maxTimeFlood = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (junction.getJunFlood().get(i) > 0)//水溢出的判定
                {
                    times++;
                }
                if (i < junction.getJunDepth().size() - 1) {
                    latFlowVolume = latFlowVolume + junction.getJunLatFlow().get(i) * junction.getTimeStep() / 1000.0F;
                    totalFlowVolume = totalFlowVolume + junction.getJunFlow().get(i) * junction.getTimeStep() / 1000.0F;
                    totalFloodVolume = totalFloodVolume + junction.getJunFlood().get(i) * junction.getTimeStep() / 1000.0F;
                }
            }
            aveDepth = aveDepth / (1.0F * junction.getJunDepth().size());
            floodedTime = times * junction.getTimeStep();
            JunStatistic junStatistic = new JunStatistic(ids[k], type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, floodedTime, maxFlood, maxTimeFlood, totalFloodVolume);
            junStatisticList.add(junStatistic);
        }
        return junStatisticList;
    }

    /**
     * 所有雨水井信息的统计
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("alljunStatistic/info")
    public List<JunStatistic> getResOfAllJunStatistic(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<JunStatistic> junStatisticList = new ArrayList<>();
        List<String> idOfJun = mdbDataService.getFeatureAllIDService("jun");
        for (int k = 0; k < idOfJun.size(); k++) {
            /**
             * 获取对应ID的计算结果
             */
            String type = "junction";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getJunResMT(idOfJun.get(k));
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResJunctions> ResJunctions = mdbDataService.getJunResultsService(idOfJun.get(k), startTime, endTime);
            if (startTime.getTime() < ResJunctions.get(0).getJunTime().getTime())
                startTime = ResJunctions.get(0).getJunTime();
            int timeStep = 0;
            if (ResJunctions.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((ResJunctions.size() - 1) * 1000));
            Junction junction = new Junction(type, idOfJun.get(k), timeStep, startTime, endTime, ResJunctions);
            /**
             * 统计值指标值
             */
            double aveDepth = 0.f;//平均水深
            double maxDepth = -MIN_VALUE;//最大水深
            double maxHGL = -MIN_VALUE;//最大水头高=(最大水深+井底高程)
            Date maxTimeDepth = null;//最大水深发生时间
            double maxLateralFlow = -MIN_VALUE; //最大侧向补给量
            double maxInFlow = -MIN_VALUE; //最大入流量
            Date maxTimeInflow = null;//最大入流量发生时间
            double latFlowVolume = 0.f;//侧向补给总体积
            double totalFlowVolume = 0.f;//总入流体积
            double maxFlood = -MIN_VALUE;//最大溢出量
            double floodedTime;//溢出时长
            Date maxTimeFlood = null;//最大溢出量发生时间
            double totalFloodVolume = 0.f; //总计溢出体积

            double times = 0.f;
            for (int i = 0; i < junction.getJunDepth().size(); i++) {
                aveDepth = aveDepth + junction.getJunDepth().get(i);
                if (maxDepth < junction.getJunDepth().get(i)) {
                    maxDepth = junction.getJunDepth().get(i);
                    maxTimeDepth = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (maxHGL < junction.getJunHgl().get(i))
                    maxHGL = junction.getJunHgl().get(i);
                if (maxLateralFlow < junction.getJunLatFlow().get(i))
                    maxLateralFlow = junction.getJunLatFlow().get(i);
                if (maxInFlow < junction.getJunFlow().get(i)) {
                    maxInFlow = junction.getJunFlow().get(i);
                    maxTimeInflow = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (maxFlood < junction.getJunFlood().get(i)) {
                    maxFlood = junction.getJunFlood().get(i);
                    maxTimeFlood = new Date(junction.getStartTime().getTime() + i * junction.getTimeStep() * 1000);
                }
                if (junction.getJunFlood().get(i) > 0)//水溢出的判定
                {
                    times++;
                }
                if (i < junction.getJunDepth().size() - 1) {
                    latFlowVolume = latFlowVolume + junction.getJunLatFlow().get(i) * junction.getTimeStep() / 1000.0F;
                    totalFlowVolume = totalFlowVolume + junction.getJunFlow().get(i) * junction.getTimeStep() / 1000.0F;
                    totalFloodVolume = totalFloodVolume + junction.getJunFlood().get(i) * junction.getTimeStep() / 1000.0F;
                }
            }
            aveDepth = aveDepth / (1.0F * junction.getJunDepth().size());
            floodedTime = times * junction.getTimeStep();
            JunStatistic junStatistic = new JunStatistic(idOfJun.get(k), type, startTime, endTime, aveDepth, maxDepth, maxHGL, maxTimeDepth, maxLateralFlow, maxInFlow, maxTimeInflow, latFlowVolume, totalFlowVolume, floodedTime, maxFlood, maxTimeFlood, totalFloodVolume);
            junStatisticList.add(junStatistic);
        }
        return junStatisticList;
    }

    /**
     * 管网系统信息分析
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("citysystem/info")
    public CitySystems getCitysystem(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getCitySystemMT();
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResSystem> resSystems = mdbDataService.getSystemResults(startTime, endTime);
        if (startTime.getTime() < resSystems.get(0).getSysTime().getTime())
            startTime = resSystems.get(0).getSysTime();
        int timeStep = 0;
        if (resSystems.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSystems.size() - 1) * 1000));
        CitySystems citysystems = new CitySystems(timeStep, startTime, endTime, resSystems);
        return citysystems;
    }

//-----------------------------Natural channel---------------------------------
    /**
     * 单断面信息的查询
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("section/info")
    public Section getResOfSec(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "section";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getSectionResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResSections> resSections = mdbDataService.getSectionResultsService(id, startTime, endTime);
        if (startTime.getTime() < resSections.get(0).getSecTime().getTime())
            startTime = resSections.get(0).getSecTime();
        int timeStep = 0;
        if (resSections.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSections.size() - 1) * 1000));
        Section section = new Section(type, id, timeStep, startTime, endTime, resSections);
        return section;
    }

    /**
     * 多断面信息的查询
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("seclist/info")
    public List<Section> getResOfSec(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "section";
        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSectionResMT(ids[i]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResSections> resSections = mdbDataService.getSectionResultsService(ids[i], startTime, endTime);
            if (startTime.getTime() < resSections.get(0).getSecTime().getTime())
                startTime = resSections.get(0).getSecTime();
            int timeStep = 0;
            if (resSections.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSections.size() - 1) * 1000));
            Section section = new Section(type, ids[i], timeStep, startTime, endTime, resSections);
            sections.add(section);
        }
        return sections;
    }

    /**
     * 单断面信息的统计
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("secStatistic/info")
    public SecStatistic getResOfSecStatistic(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "section";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getSectionResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }

        List<ResSections> resSections = mdbDataService.getSectionResultsService(id, startTime, endTime);
        if (startTime.getTime() < resSections.get(0).getSecTime().getTime())
            startTime = resSections.get(0).getSecTime();
        int timeStep = 0;
        if (resSections.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSections.size() - 1) * 1000));
        Section section = new Section(type, id, timeStep, startTime, endTime, resSections);
        /**
         * 统计值指标值
         */
        double maxWatlevel = 0;//平均流量
        double maxFlow = 0;//最大流量
        double maxVelocity = 0;//最大流速
        Date maxWatlevelTime = null;
        Date maxFlowTime = null;
        Date maxVelTime = null;
        for (int i = 0; i < section.getSecFlow().size(); i++) {
                if (maxFlow < Math.abs(section.getSecFlow().get(i))) {
                    maxFlow = section.getSecFlow().get(i);
                    maxFlowTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxVelocity < Math.abs(section.getSecVelocity().get(i))) {
                    maxVelocity = section.getSecVelocity().get(i);
                    maxVelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxWatlevel < Math.abs(section.getSecWatlevel().get(i))) {
                    maxWatlevel = section.getSecWatlevel().get(i);
                    maxWatlevelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
        }
        SecStatistic secStatistic  = new SecStatistic(id, type, startTime, endTime, maxWatlevel, maxFlow, maxVelocity, maxWatlevelTime, maxFlowTime, maxVelTime);
        return secStatistic;
    }

    /**
     * 多断面信息的统计
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("secStatistics/info")
    public List<SecStatistic> getResOfSecStatistics(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "section";
        List<SecStatistic> secStatistics = new ArrayList<>();
        for (int k = 0; k <ids.length; k++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSectionResMT(ids[k]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }

            List<ResSections> resSections = mdbDataService.getSectionResultsService(ids[k], startTime, endTime);
            if (startTime.getTime() < resSections.get(0).getSecTime().getTime())
                startTime = resSections.get(0).getSecTime();
            int timeStep = 0;
            if (resSections.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSections.size() - 1) * 1000));
            Section section = new Section(type, ids[k], timeStep, startTime, endTime, resSections);
            /**
             * 统计值指标值
             */
            double maxWatlevel = 0;//平均流量
            double maxFlow = 0;//最大流量
            double maxVelocity = 0;//最大流速
            Date maxWatlevelTime = null;
            Date maxFlowTime = null;
            Date maxVelTime = null;
            for (int i = 0; i < section.getSecFlow().size(); i++) {
                if (maxFlow < Math.abs(section.getSecFlow().get(i))) {
                    maxFlow = section.getSecFlow().get(i);
                    maxFlowTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxVelocity < Math.abs(section.getSecVelocity().get(i))) {
                    maxVelocity = section.getSecVelocity().get(i);
                    maxVelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxWatlevel < Math.abs(section.getSecWatlevel().get(i))) {
                    maxWatlevel = section.getSecWatlevel().get(i);
                    maxWatlevelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
            }
            SecStatistic secStatistic = new SecStatistic(ids[k], type, startTime, endTime, maxWatlevel, maxFlow, maxVelocity, maxWatlevelTime, maxFlowTime, maxVelTime);
            secStatistics.add(secStatistic);
        }
        return secStatistics;
    }

    /**
     * 查询某河流上断面的统计结果
     *
     * @param river
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("allsecStatistic/info")
    public List<SecStatistic> getResOfAllSecStatistic(@RequestParam(value = "river", required = true) String river, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<RiverDistance> riverDistances = mdbDataService.getRiverDistances(river);
        List<SecStatistic> secStatisticList=new ArrayList<>();
        String type="section";
        for (int k = 0; k <riverDistances.size(); k++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSectionResMT(riverDistances.get(k).getSecId());
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }

            List<ResSections> resSections = mdbDataService.getSectionResultsService(riverDistances.get(k).getSecId(), startTime, endTime);
            if (startTime.getTime() < resSections.get(0).getSecTime().getTime())
                startTime = resSections.get(0).getSecTime();
            int timeStep = 0;
            if (resSections.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((resSections.size() - 1) * 1000));
            Section section = new Section(type, riverDistances.get(k).getSecId(), timeStep, startTime, endTime, resSections);
            /**
             * 统计值指标值
             */
            double maxWatlevel = 0;//平均流量
            double maxFlow = 0;//最大流量
            double maxVelocity = 0;//最大流速
            Date maxWatlevelTime = null;
            Date maxFlowTime = null;
            Date maxVelTime = null;
            for (int i = 0; i < section.getSecFlow().size(); i++) {
                if (maxFlow < Math.abs(section.getSecFlow().get(i))) {
                    maxFlow = section.getSecFlow().get(i);
                    maxFlowTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxVelocity < Math.abs(section.getSecVelocity().get(i))) {
                    maxVelocity = section.getSecVelocity().get(i);
                    maxVelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
                if (maxWatlevel < Math.abs(section.getSecWatlevel().get(i))) {
                    maxWatlevel = section.getSecWatlevel().get(i);
                    maxWatlevelTime = new Date(section.getStartTime().getTime() + i * section.getTimeStep() * 1000);
                }
            }
            SecStatistic secStatistic = new SecStatistic(riverDistances.get(k).getSecId(), type, startTime, endTime, maxWatlevel, maxFlow, maxVelocity, maxWatlevelTime, maxFlowTime, maxVelTime);
            secStatisticList.add(secStatistic);
        }
        return secStatisticList;
    }

//-----------------------------Sub-catchments---------------------------------
    /**
     * 子汇水区的淹没分析
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("subflood/info")
    public List<Flood> getResOfSubFlood(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<String> idOfSub = mdbDataService.getFeatureAllIDService("sub");
        String type = "subcatchment";
        List<Flood> floodList = new ArrayList<>();
        for (int k = 0; k < idOfSub.size(); k++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSubResMT(idOfSub.get(k));
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(idOfSub.get(k), startTime, endTime);
            if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
                startTime = subResults.get(0).getSubTime();
            int timeStep = 0;
            if (subResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
            List<Float> subflow = new ArrayList<>();
            for (int i = 0; i < subResults.size(); i++) {
                subflow.add(subResults.get(i).getSubFlow());
            }
            Flood flood = new Flood(type, idOfSub.get(k), timeStep, startTime, endTime, subflow);
            floodList.add(flood);
        }
        return floodList;
    }

    /**
     * 单子汇水区的查询
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("subcatchment/info")
    public Subcatchment getResOfSub(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "subcatchment";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getSubResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(id, startTime, endTime);
        if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
            startTime = subResults.get(0).getSubTime();
        int timeStep = 0;
        if (subResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
        Subcatchment subcatchment = new Subcatchment(type, id, timeStep, startTime, endTime, subResults);
        return subcatchment;
    }

    /**
     * 多子汇水区的查询
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("sublist/info")
    public List<Subcatchment> getResOfSubs(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "subcatchment";
        List<Subcatchment> subcatchments = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSubResMT(ids[i]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(ids[i], startTime, endTime);
            if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
                startTime = subResults.get(0).getSubTime();
            int timeStep = 0;
            if (subResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
            Subcatchment subcatchment = new Subcatchment(type, ids[i], timeStep, startTime, endTime, subResults);
            subcatchments.add(subcatchment);
        }
        return subcatchments;
    }

    /**
     * 单子汇水区的统计
     *
     * @param id
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("subStatistic/info")
    public SubStatistic getResOfSubStatistic(@RequestParam("id") String id, @RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        String type = "subcatchment";
        if (startTime == null && endTime == null) {
            endTime = mdbDataService.getSubResMT(id);
            if (endTime != null)
                startTime = tools.getStartDateByEndDate(endTime, hour);
            else
                startTime = null;
        }
        List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(id, startTime, endTime);
        if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
            startTime = subResults.get(0).getSubTime();
        int timeStep = 0;
        if (subResults.size() > 1)
            timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
        Subcatchment subcatchment = new Subcatchment(type, id, timeStep, startTime, endTime, subResults);
        /**
         * 统计值计算
         */
        double totalRain = 0;//总雨量(mm)
        double totalInfil = 0;//总下渗(mm)
        double totalRunoff = 0;//总径流(10^6L)
        double peakRunoff = -MIN_VALUE;//径流峰值(m³/s)
        for (int i = 0; i < subcatchment.getSubFlow().size(); i++) {
            if (peakRunoff < subcatchment.getSubFlow().get(i))
                peakRunoff = subcatchment.getSubFlow().get(i);
            totalRain = totalRain + subcatchment.getSubRain().get(i) * subcatchment.getTimeStep() / 3600;
            totalInfil = totalInfil + subcatchment.getSubInfil().get(i) * subcatchment.getTimeStep() / 3600;
            totalRunoff = totalRunoff + subcatchment.getSubFlow().get(i) * subcatchment.getTimeStep() / 1000.0F;
        }
        SubStatistic subStatistic = new SubStatistic(id, type, startTime, endTime, timeStep, totalRain, totalInfil, totalRunoff, peakRunoff);
        return subStatistic;
    }

    /**
     * 多子汇水区的统计
     *
     * @param requestClass
     * @return
     * @throws ParseException
     */
    @RequestMapping("subStatistic/info")
    public List<SubStatistic> getResOfSubStatistic(@RequestBody RequestClass requestClass) throws ParseException {
        String[] ids = requestClass.getIds();
        Date startTime = requestClass.getStartTime();
        Date endTime = requestClass.getEndTime();
        Integer hour = requestClass.getHour();
        String type = "subcatchment";
        List<SubStatistic> subStatisticList = new ArrayList<>();
        for (int k = 0; k < ids.length; k++) {
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSubResMT(ids[k]);
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(ids[k], startTime, endTime);
            if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
                startTime = subResults.get(0).getSubTime();
            int timeStep = 0;
            if (subResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
            Subcatchment subcatchment = new Subcatchment(type, ids[k], timeStep, startTime, endTime, subResults);
            /**
             * 统计值计算
             */
            double totalRain = 0;//总雨量(mm)
            double totalInfil = 0;//总下渗(mm)
            double totalRunoff = 0;//总径流(10^6L)
            double peakRunoff = -MIN_VALUE;//径流峰值(m³/s)
            for (int i = 0; i < subcatchment.getSubFlow().size(); i++) {
                if (peakRunoff < subcatchment.getSubFlow().get(i))
                    peakRunoff = subcatchment.getSubFlow().get(i);
                totalRain = totalRain + subcatchment.getSubRain().get(i) * subcatchment.getTimeStep() / 3600;
                totalInfil = totalInfil + subcatchment.getSubInfil().get(i) * subcatchment.getTimeStep() / 3600;
                totalRunoff = totalRunoff + subcatchment.getSubFlow().get(i) * subcatchment.getTimeStep() / 1000.0F;
            }
            SubStatistic subStatistic = new SubStatistic(ids[k], type, startTime, endTime, timeStep, totalRain, totalInfil, totalRunoff, peakRunoff);
            subStatisticList.add(subStatistic);
        }
        return subStatisticList;
    }

    /**
     * 所有汇水区的统计
     *
     * @param startTime
     * @param endTime
     * @param hour
     * @return
     * @throws ParseException
     */
    @GetMapping("allsubStatistic/info")
    public List<SubStatistic> getResOfAllSubStatistic(@RequestParam(value = "startTime", required = false) Date startTime, @RequestParam(value = "endTime", required = false) Date endTime, @RequestParam(value = "hour", required = false) Integer hour) throws ParseException {
        List<SubStatistic> subStatisticList = new ArrayList<>();
        List<String> idOfSub = mdbDataService.getFeatureAllIDService("sub");
        for (int k = 0; k < idOfSub.size(); k++) {
            String type = "subcatchment";
            if (startTime == null && endTime == null) {
                endTime = mdbDataService.getSubResMT(idOfSub.get(k));
                if (endTime != null)
                    startTime = tools.getStartDateByEndDate(endTime, hour);
                else
                    startTime = null;
            }
            List<ResSubcatchments> subResults = mdbDataService.getSubResultsService(idOfSub.get(k), startTime, endTime);
            if (startTime.getTime() < subResults.get(0).getSubTime().getTime())
                startTime = subResults.get(0).getSubTime();
            int timeStep = 0;
            if (subResults.size() > 1)
                timeStep = (int) ((endTime.getTime() - startTime.getTime()) / ((subResults.size() - 1) * 1000));
            Subcatchment subcatchment = new Subcatchment(type, idOfSub.get(k), timeStep, startTime, endTime, subResults);
            /**
             * 统计值计算
             */
            double totalRain = 0;//总雨量(mm)
            double totalInfil = 0;//总下渗(mm)
            double totalRunoff = 0;//总径流(10^6L)
            double peakRunoff = -MIN_VALUE;//径流峰值(m³/s)
            for (int i = 0; i < subcatchment.getSubFlow().size(); i++) {
                if (peakRunoff < subcatchment.getSubFlow().get(i))
                    peakRunoff = subcatchment.getSubFlow().get(i);
                totalRain = totalRain + subcatchment.getSubRain().get(i) * subcatchment.getTimeStep() / 3600;
                totalInfil = totalInfil + subcatchment.getSubInfil().get(i) * subcatchment.getTimeStep() / 3600;
                totalRunoff = totalRunoff + subcatchment.getSubFlow().get(i) * subcatchment.getTimeStep() / 1000.0F;
            }
            SubStatistic subStatistic = new SubStatistic(idOfSub.get(k), type, startTime, endTime, timeStep, totalRain, totalInfil, totalRunoff, peakRunoff);
            subStatisticList.add(subStatistic);
        }
        return subStatisticList;
    }

    /**
     * 解决类型转换问题
     *
     * @param binder
     * @param request
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}

