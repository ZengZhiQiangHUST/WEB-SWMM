package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 * 一维水动力模型的功能函数类
 * @author zengz
 *
 */
public class HDFunction {
	/**
	 * 寻找河段的上游河段
	 * @param a
	 * @param lastriverID
	 * @param river
	 * @param toprivers
	 * @return
	 */
	public  static ArrayList<String> findUpRivers(int a, String lastriverID, Hashtable<String, HDTopo> river, ArrayList<String> toprivers)
	{
		String lastRivers=null;
		for (int i = 0; i <2; i++) {//一个河段只有两个上游
			if(toprivers.get(1)==null) {//添加了一个null的
				lastRivers = river.get(lastriverID).getUpRiverID()[a];//获取其中的一个上游河段
				toprivers.remove(1);
			}
			else
				lastRivers =river.get(lastriverID).getUpRiverID()[i];
			if(lastRivers!=null){
				toprivers.add(lastRivers);
				lastriverID=lastRivers;
				findUpRivers(a,lastriverID, river, toprivers);
			}
		}
		return toprivers;
	}
	/**
	 * 求p1和p2之间的距离
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double getDistance(HDpoint p1, HDpoint p2) {
		double wet = Math.sqrt((p1.getstartDisatance() - p2.getstartDisatance()) * (p1.getstartDisatance() - p2.getstartDisatance()) + (p1.getElevation() - p2.getElevation()) * (p1.getElevation() - p2.getElevation()));
		return wet;
	}
	/**
	 * 知道任意梯形四个点的坐标，求梯形的面积
	 *  p1 p2上边， p3 p4下边
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return S
	 */
	public static double getTrapezoidArea(HDpoint p1, HDpoint p2, HDpoint p3, HDpoint p4) {
		double up = Math.abs(p1.getElevation() - p2.getElevation());
		double down = Math.abs(p3.getElevation() - p4.getElevation());
		double h = Math.abs(p2.getstartDisatance() - p3.getstartDisatance());
		double S = ((up + down) * h) / 2;
		return S;
	}
	/**
	 * 获取断面上的水力要素
	 * @return
	 */
	public static HDARWB getHDArwBeanLikeU(ArrayList<HDpoint> U) {
		double area = 0;
		double wet = 0;
		double r = 0;
		double b = 0;
		if (U == null || U.size() == 1 || U.size() == 0) {
			return null;
		}
		for (int i = 0; i < U.size() - 1; i++) {
			HDpoint pStart = U.get(i);
			HDpoint pEnd = U.get(i + 1);
			HDpoint p1 = new HDpoint(pStart.getstartDisatance(), 0,pStart.getMark());
			HDpoint p2 = pStart;
			HDpoint p3 = pEnd;
			HDpoint p4 = new HDpoint(pEnd.getstartDisatance(), 0,pEnd.getMark());
			area += HDFunction.getTrapezoidArea(p1, p2, p3, p4);
			if (pStart.getElevation() != 0 || pEnd.getElevation() != 0) {
				wet +=  HDFunction.getDistance(p2, p3);
				b += (pEnd.getstartDisatance() - pStart.getstartDisatance());
			}
		}
		if(wet==0)
        {
           r=0;
        }
        else
		r = area / wet;
        HDARWB hdarw = new HDARWB(area, r, wet, b);
		return hdarw;
	}
/**
 * 计算过水断面的面积
 * @param U
 * @return
 */
    public static double getHDArwBeanArea(ArrayList<HDpoint> U) {
        double area = 0;
        double wet = 0;
        double r = 0;
        double b = 0;
        if (U == null || U.size() == 1 || U.size() == 0) {
            return 0;
        }
        for (int i = 0; i < U.size() - 1; i++) {
            HDpoint pStart = U.get(i);
            HDpoint pEnd = U.get(i + 1);
            HDpoint p1 = new HDpoint(pStart.getstartDisatance(), 0,pStart.getMark());
            HDpoint p2 = pStart;
            HDpoint p3 = pEnd;
            HDpoint p4 = new HDpoint(pEnd.getstartDisatance(), 0,pEnd.getMark());
            area += HDFunction.getTrapezoidArea(p1, p2, p3, p4);
        }
        return area;
    }
   /**
     * 最小二乘法
     */
    public static class LeastSquareMethod {
        private double[] x;
        private double[] y;
        private double[] weight;
        private int n;//多项式次数
        private double[] coefficient;//系数矩阵
        /**
         *构造方法
         * @param x
         * @param y
         * @param n
         */
        public LeastSquareMethod(double[] x, double[] y, int n) {
            if (x == null || y == null || x.length < 2 || x.length != y.length
                    || n < 2) {
                throw new IllegalArgumentException(
                        "IllegalArgumentException occurred.");
            }
            this.x = x;
            this.y = y;
            this.n = n;
            weight = new double[x.length];
            for (int i = 0; i < x.length; i++) {
                weight[i] = 1;
            }
            compute();
        }
        /**
         * 构造方法
         * @param x
         * @param y
         * @param weight
         * @param n
         */
        public LeastSquareMethod(double[] x, double[] y, double[] weight, int n) {
            if (x == null || y == null || weight == null || x.length < 2
                    || x.length != y.length || x.length != weight.length || n < 2) {
                throw new IllegalArgumentException(
                        "IllegalArgumentException occurred.");
            }
            this.x = x;
            this.y = y;
            this.n = n;
            this.weight = weight;
            compute();
        }

        /**
         * 用于计算给定X的值
         * @param x
         * @return
         */
        public double fit(double x) {
            if (coefficient == null) {
                return 0;
            }
            double sum = 0;
            for (int i = 0; i < coefficient.length; i++) {
                sum += Math.pow(x, i) * coefficient[i];
            }
            return sum;
        }
        /**
         * 利用牛顿法求解方程
         * @param y
         * @param startX
         * @return
         */
        public double solve(double y, double startX) {
            final double EPS = 0.0000001d;
            if (coefficient == null) {
                return 0;
            }
            double x1 = 0.0d;
            double x2 = startX;
            do {
                x1 = x2;
                x2 = x1 - (fit(x1) - y) / calcReciprocal(x1);
            } while (Math.abs((x1 - x2)) > EPS);
            return x2;
        }
        /**
         * 计算X的倒数
         * @param x
         * @return
         */
        private double calcReciprocal(double x) {
            if (coefficient == null) {
                return 0;
            }
            double sum = 0;
            for (int i = 1; i < coefficient.length; i++) {
                sum += i * Math.pow(x, i - 1) * coefficient[i];
            }
            return sum;
        }
    /**
     * 该方法用于计算增广矩阵的各元素
     */
        private void compute() {
            if (x == null || y == null || x.length <= 1 || x.length != y.length
                    || x.length < n || n < 2) {
                return;
            }
            double[] s = new double[(n - 1) * 2 + 1];
            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < x.length; j++) {
                    s[i] += Math.pow(x[j], i) * weight[j];
                }
            }
            double[] b = new double[n];
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < x.length; j++) {
                    b[i] += Math.pow(x[j], i) * y[j] * weight[j];
                }
            }
            double[][] a = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    a[i][j] = s[i + j];
                }
            }
            // Now we need to calculate each coefficients of augmented matrix
            coefficient = calcLinearEquation(a, b);
        }
        /**
         *求解线性方程组
         * @param a
         * @param b
         * @return
         */
        private double[] calcLinearEquation(double[][] a, double[] b) {
            if (a == null || b == null || a.length == 0 || a.length != b.length) {
                return null;
            }
            for (double[] x : a) {
                if (x == null || x.length != a.length)
                    return null;
            }
            int len = a.length - 1;
            double[] result = new double[a.length];

            if (len == 0) {
                result[0] = b[0] / a[0][0];
                return result;
            }
            double[][] aa = new double[len][len];
            double[] bb = new double[len];
            int posx = -1, posy = -1;
            for (int i = 0; i <= len; i++) {
                for (int j = 0; j <= len; j++)
                    if (a[i][j] != 0.0d) {
                        posy = j;
                        break;
                    }
                if (posy != -1) {
                    posx = i;
                    break;
                }
            }
            if (posx == -1) {
                return null;
            }
            int count = 0;
            for (int i = 0; i <= len; i++) {
                if (i == posx) {
                    continue;
                }
                bb[count] = b[i] * a[posx][posy] - b[posx] * a[i][posy];
                int count2 = 0;
                for (int j = 0; j <= len; j++) {
                    if (j == posy) {
                        continue;
                    }
                    aa[count][count2] = a[i][j] * a[posx][posy] - a[posx][j]
                            * a[i][posy];
                    count2++;
                }
                count++;
            }
            // Calculate sub linear equation
            double[] result2 = calcLinearEquation(aa, bb);
            // After sub linear calculation, calculate the current coefficient
            double sum = b[posx];
            count = 0;
            for (int i = 0; i <= len; i++) {
                if (i == posy) {
                    continue;
                }
                sum -= a[posx][i] * result2[count];
                result[i] = result2[count];
                count++;
            }
            result[posy] = sum / a[posx][posy];
            return result;
        }
        public double[] getCoefficient() {
            return coefficient;
        }
    }
}