package com.fly.demo.base.test.algorithm;

import java.io.*;

public class IPIdentityTest {

    public static void main(String[] args){
        File file = new File("D:\\MyFiles\\面试题\\test.txt");
        int A = 0, B = 0, C = 0, D = 0, E = 0, err = 0, priv = 0;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String s ;
            while((s = bufferedReader.readLine()) != null){
                String[] arrs = s.split("~");
                if("end".equals(arrs[0])){
                    break;
                }
                //1. 先判断IP地址是否有效
                String[] ips = arrs[0].split("\\.");
                if("0".equals(ips[0]) || "127".equals(ips[0])){
                    continue;
                }

                //2. 统计错误掩码个数
//                if(!isValidMaskIp(arrs[1])){
                if(!isValidMask(arrs[1])){
                    err++;
                    System.out.println("掩码错误: " + s);
                } else if(!isValidIp(ips)) { //3. 判断IP是否错误
                    err++;
                    System.out.println("IP错误: " + s);
                } else{ // 4. 统计IP类型
                    Integer head = Integer.valueOf(ips[0]);
                    if(head >= 1 && head <= 126){
                        //属于A类,
                        A++;
                        System.out.println("属于A类: " + s);
                        if(head == 10){
                            priv++;
                            System.out.println("属于私有IP: " + s);
                        }
                    } else if(head >= 128 && head <= 191){
                        B++;
                        System.out.println("属于B类: " + s);
                        if(head == 172 && Integer.parseInt(ips[1]) >= 16 && Integer.parseInt(ips[1]) <= 31){
                            priv++;
                            System.out.println("属于私有IP: " + s);
                        }
                    } else if(head >= 192 && head <= 223){
                        C++;
                        System.out.println("属于C类: " + s);
                        if(head == 192 && Integer.parseInt(ips[1]) == 168){
                            priv++;
                            System.out.println("属于私有IP: " + s);
                        }
                    } else if(head >= 224 && head <= 239){
                        D++;
                        System.out.println("属于D类: " + s);
                    } else if(head >= 240 && head <= 255){
                        System.out.println("属于E类: " + s);
                        E++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(A + " " + B + " " + C + " " + D + " " + E + " " + err + " " + priv);
    }

    private static boolean isValidIp(String[] ips){
        if(ips.length != 4){
            return false;
        }
        for(int i = 1; i < ips.length; i++){
            if(!isDigit(ips[i])){
                return false;
            }
            Integer num = Integer.valueOf(ips[i]);
            if(num < 0 || num > 255){
                return false;
            }
        }
        return true;
    }

    private static boolean isValidMaskIp(String s){
        String[] mcs = s.split("\\.");
        if(mcs.length == 4){
            boolean flag = false;
            for(int i = 0; i < mcs.length; i++){
                if(!isDigit(mcs[i])){
                    return false;
                }
                Integer num = Integer.valueOf(mcs[i]);
                if(i == 0){
//                     if(num == 0 || ((num + 1) & num) != 0){
                    if(num != 255){
                        return false;
                    }
                    continue;
                }
                if(i == mcs.length - 1){
                    if(num != 0){
                        return false;
                    }
                    break;
                }

                if(num == 0){
                    flag = true;
                } else{
                    if((flag && num != 0) || (!flag && num != 255)){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isDigit(String s){
        if(s == null || s.length() == 0){
            return false;
        }
        s = s.trim();
        if(s.length() == 0){
            return false;
        }
        if(!s.matches("[0-9]*")){
            return false;
        }
        return true;
    }

    public static boolean isValidMask(String mask){
//        if(!isValidIp(mask)){
//            return false;
//        }
        String[] maskTable = mask.split("\\.");
        //mask转为32位2进制字符串
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<maskTable.length;i++){
            maskTable[i] = Integer.toBinaryString(Integer.parseInt(maskTable[i]));
            if(maskTable[i].length() < 8){//不足8位补齐0
                for(int j=0;j < 8- maskTable[i].length();j++){
                    sb.append("0");//补完零
                }
                sb.append(maskTable[i]);//再添加转换的2进制串
            }else{
                sb.append(maskTable[i]);//刚好8位直接添加，因为之前已经判断过ip的有效性，所以不可能超过8位
            }
        }
        //最后一个1在第一个0之前，有效，否则无效
        return sb.toString().lastIndexOf("1") < sb.toString().indexOf("0");
    }
}
