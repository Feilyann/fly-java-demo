package com.fly.demo.base.test.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @project: fly-demo
 * @package: com.fly.demo.test.algorithm
 * @author: feilongyan
 * @time: 2024/11/22 0022
 * @description: TODO
 * @version: 1.0
 */
public class TriTest {

        public double duobianxing[][]=new double[8][8];//存贮多边形,每天边和弦的长度

        public double t[][]=new double[8][8];

        public int s[][]=new int[8][8];

        public List dian = new ArrayList();

        private static class b {
            double x, y;

            private b(double x, double y) {

            }
        }

    public static void main(String args[])

    {

        TriTest a1=new TriTest();

        a1.init();

        if(a1.isduobianxing()) {

            a1.setdoubianxing();

            a1.printduobianxing();

            a1.minWeightTrangulatian(8,a1.t,a1.s);

            a1.prints();

        }

    }

        //将坐标点添加进来

        public  void init(){

            dian.add(new b(8.0,26.0));

            dian.add(new b(0,20.0));

            dian.add(new b(0,10.0));

            dian.add(new b(10.0,0));

            dian.add(new b(22.0,12.0));

            dian.add(new b(27.0,21.0));

            dian.add(new b(15.0,26.0));

        }



        public void setdoubianxing()//设置多边形值，测试形状为梯形，左边为直角，右边为45度角，上边1，高1，底边为2

        {

            for (int i=0;i<8;i++)

            {

                //  doubianxiang[i][]=new double [5];

                for (int j=0;j<8;j++)

                {

                    duobianxing[i][j]=0;

                }

            }



            for(int i=1;i<7;i++)

                for(int j=i+1;j<8;j++)

                {

                    b diani = (b) dian.get(i-1);

                    b dianj = (b) dian.get(j-1);

                    duobianxing[i][j] = len(diani.x,diani.y,dianj.x,dianj.y);

                }

        }



        public static double len(double x1,double y1,double x2,double y2){

            return java.lang.Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));

        }



        public double w(int i,int j,int k)//权重为三边之和

        {

            // System.out.println(duobianxing[i][j]+"        "+duobianxing[i][k]+"          "+duobianxing[j][k]);

            return duobianxing[i][j]+duobianxing[i][k]+duobianxing[j][k];

        }

        public  void minWeightTrangulatian(int n,double [][]t,int [][]s)//求最优三角剖分

        {

            for (int i=1;i<8;i++) t[i][i]=0;

            for (int r=2;r<=n;r++)

                for (int i=1;i<n-r+1;i++)

                {

                    int j=i+r-1;

                    t[i][j]=t[i+1][j]+w(i-1,i,j);

                    System.out.println("第一种可能：t("+(i+1)+"  ,"+j+")    + w("+(i-1)+","+i+","+j+")");

                    //   System.out.println(t[i+1][j]+"   "+w(i-1,i,j));

                    s[i][j]=i;

                    for (int k=i+1;k<i+r-1;k++)

                    {

                        double u=t[i][k]+t[k+1][j]+w(i-1,k,j);

                        System.out.println("寻找其他可能"+i+","+k+"="+t[i][k]+"    "+(k+1)+","+k+"="+t[k+1][j]+"     "+(i-1)+"   "+k+"   "+j);

                        if (u<t[i][j])

                        {

                            System.out.println("                             "+t[i][j]+">    "+u);

                            t[i][j]=u;s[i][j]=k;

                        }

                    }

                    System.out.println(i+"  "+j);

                    printresult();

                    System.out.println("****************************************************************************");

                }

        }

        public void printresult()

        {

            System.out.println("--------------------");

            System.out.println("    1      2    3    4     5     6     7     ");

            System.out.println("");

            for (int i=1;i<8;i++)

            {

                //  doubianxiang[i][]=new double [5];

                System.out.print(i+"   ");

                for (int j=1;j<8;j++)

                {

                    System.out.print(Math. floor(t[i][j]*10)/10+"   ");

                }

                System.out.println();

            }

            System.out.println("--------------------");

        }

        public void printduobianxing()

        {

            System.out.println("--------------------");

            System.out.println("    1      2    3    4     5     6     7     ");

            System.out.println("");

            for (int i=1;i<8;i++)

            {

                //  doubianxiang[i][]=new double [5];

                System.out.print(i+"   ");

                for (int j=1;j<8;j++)

                {

                    System.out.print(Math.floor(duobianxing[i][j]*10)/10+"   ");

                }

                System.out.println();

            }

            System.out.println("--------------------");

        }



        public void prints()

        {

            System.out.println("--------------------");

            System.out.println("    1      2    3    4     5     6     7     ");

            System.out.println("");

            for (int i=1;i<8;i++)

            {

                //  doubianxiang[i][]=new double [5];

                System.out.print(i+"   ");

                for (int j=1;j<8;j++)

                {

                    System.out.print(Math. floor(s[i][j]*10)/10+"   ");

                }

                System.out.println();

            }

            System.out.println("--------------------");

        }



        public boolean isduobianxing(){

            boolean ff = true;

//System.out.println(isTuuobianxing(new b(1,1),new b(2,2),new b(4,5)));

            for(int i=0;i<dian.size()-1;i++) {

                int j = i+1;//标示第i个点的下一个点

                b diani = (b) dian.get(i);

                b dianj = (b) dian.get(j);

                double flag = 0;//标示是否所有的点都在直线的一边

                int count  = 0;

                System.out.println("第"+(i+1)+"个点和第"+(j+1)+"个点构成的直线为：y="

                        + (diani.y-dianj.y)/(diani.x-dianj.x) + "x+" +

                        (diani.x*dianj.y-dianj.x*dianj.y)/(diani.x-dianj.x));

                for(int k=0; k<dian.size();k++) {

                    if(k==i||k==j)

                        continue;

                    b diank = (b) dian.get(k);

                    if(count == 0)

                        flag = isTuuobianxing(diani,dianj,diank);

                    else {

                        if(flag*isTuuobianxing(diani,dianj,diank)<0) {

                            System.out.println("第"+(k-1)+"个点和第"+k+"个点不在同一边！此多边形不是凸多边形");

                            ff = false;

                            break;

                        }

                        flag = isTuuobianxing(diani,dianj,diank);

                        if(flag > 0)

                            System.out.println("第"+k+"个点在此直线的正侧");

                        else

                            System.out.println("第"+k+"个点在此直线的负侧");

                    }

                    count++;

                }

            }

            if(ff == true)

                System.out.println("此多边形为凸多边形！");

            return ff ;

        }

        public static double isTuuobianxing(b point1,b point2, b point3) {

            return point3.y - (point1.y-point2.y)/(point1.x-point2.x)*point3.x - (point1.x*point2.y

                    -point2.x*point1.y)/(point1.x-point2.x);

        }
}
