package com.zeng.house.bean;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class LianjiaHouse {
    /**
     * 权重
     */
    public static int priceW = 8;      //单价
    public static int totalW = 9;      //总价
    public static int sizeW = 8;       //面积
    public static int floorW = 7;      //楼层
    public static int ageW = 8;        //房屋年代
    public static int shapeW = 8;      //户型
    public static int priceDiffW = 10; //与参加单价差值
    public static int lastSaleW = 9;   //上次交易时间
    public static int positionW = 9;   //位置
    public static int putOutW = 8;     //挂牌时间
    public static int directionW = 5;  //朝向
    public static int decorateW = 11;  //装修
    public static int elevatorW = 10;  //电梯
    public static int propertyW = 10;  //产权属性

    /**
     * 分值规则
     */
    public static Map<String, Integer> floorV;
    public static Map<String, Integer> positionV;
    public static Map<String, Integer> directionV;
    public static Map<String, Integer> decorateV;

    /**
     * 楼层枚举：低楼层，中楼层，高楼层
     */
    private static final String FLOOR_LOW = "低", FLOOR_MIDDLE = "中", FLOOR_HIGH = "高";
    /**
     * 位置枚举：白沙洲，老南湖，南湖沃尔玛，新南湖，珞狮南路，三环南，汤逊湖藏龙岛，
     * 金融港，光谷南，民族大道，关山大道，华科大，关西长职
     */
    private static final String POSITION_BSZ = "白沙洲", POSITION_LNH = "老南湖", POSITION_NHWEM = "南湖沃尔玛",
            POSITION_XNH = "新南湖", POSITION_LSNL = "珞狮南路", POSITION_SHN = "三环南", POSITION_TXHCLD = "汤逊湖藏龙岛",
            POSITION_JRG = "金融港", POSITION_GGN = "光谷南", POSITION_MZDD = "民族大道", POSITION_GSDD = "关山大道",
            POSITION_HKD = "华科大", POSITION_GXCZ = "关西长职";
    /**
     * 朝向枚举：南北，东西
     */
    private static final String DIRECTION_NB = "南", DIRECTION_EMPTY = "空";
    /**
     * 装修枚举：毛坯，简装，精装
     */
    private static final String DECORATE_NO = "毛", DECORATE_SAMPLE = "简", DECORATE_GOOD = "精";
    /**
     * 枚举缺省值
     */
    private static final String OTHER = "其他";

    static {
        floorV = new TreeMap<>();
        floorV.put(FLOOR_LOW, 70);
        floorV.put(FLOOR_MIDDLE, 100);
        floorV.put(FLOOR_HIGH, 50);
        floorV.put(OTHER, 60);
        positionV = new TreeMap<>();
        positionV.put(POSITION_BSZ, 50);
        positionV.put(POSITION_LNH, 80);
        positionV.put(POSITION_NHWEM, 90);
        positionV.put(POSITION_XNH, 100);
        positionV.put(POSITION_LSNL, 70);
        positionV.put(POSITION_SHN, 70);
        positionV.put(POSITION_TXHCLD, 60);
        positionV.put(POSITION_JRG, 75);
        positionV.put(POSITION_GGN, 75);
        positionV.put(POSITION_MZDD, 80);
        positionV.put(POSITION_GSDD, 85);
        positionV.put(POSITION_HKD, 80);
        positionV.put(POSITION_GXCZ, 85);
        positionV.put(OTHER, 0);
        directionV = new TreeMap<>();
        directionV.put(DIRECTION_NB, 100);
        directionV.put(OTHER, 0);
        directionV.put(DIRECTION_EMPTY, 50);
        decorateV = new TreeMap<>();
        decorateV.put(DECORATE_NO, 0);
        decorateV.put(DECORATE_SAMPLE, 70);
        decorateV.put(DECORATE_GOOD, 100);
        decorateV.put(OTHER, 50);
    }

    private String title;
    private String price;
    private Float priceF;   //向下过滤
    private String total;
    private Float totalF;   //向下过滤
    private String size;
    private Float sizeF;    //向上过滤
    private String floor;   //不过滤
    private String buildTime;
    private Float buildTimeF;     //向上过滤
    private String shape;   //转化为x室x厅x厨x卫，向上过滤
    private String priceAvg;
    private Float priceAvgF;
    private String lastSale;
    private Float lastSaleF;    //向下过滤
    private String position;    //转化为上面的枚举，相同过滤
    private String putOut;
    private Float putOutF;      //向上过滤
    private String direction;   //转化为南，其他，相同过滤
    private String decorate;    //转化为毛，简，精，相同过滤
    private String elevator;
    private String property;

    private String url;         //链接地址
    private int evaluatePoint;  //向上过滤
    private Date updateTime;

    public int calculate() {
        int priceP = getPricePoint(price);
        int totalP = getTotalPoint(total);
        int sizeP = getSizePoint(size);
        int ageP = getAgePoint(buildTime);
        int shapeP = getShapePoint(shape);
        int floorP = getFloorPoint(floor);
        int priceDiffP = null == priceAvgF ? 0 : (int) ((priceAvgF - priceF) / 13.0);
        int lastSaleP = getLastSalePoint(lastSale);
        int positionP = getPositionPoint(position);
        int putOutP = getPutOutPoint(putOut);
        int directionP = getDirectionPoint(direction);
        int decorateP = getDecoratePoint(decorate);
        int elevatorP = getElevatorPoint(elevator);
        int propertyP = getPropertyPoint(property);

        evaluatePoint = priceP * priceW
                + totalP * totalW
                + sizeP * sizeW
                + floorP * floorW
                + ageP * ageW
                + shapeP * shapeW
                + priceDiffP * priceDiffW
                + lastSaleP * lastSaleW
                + positionP * positionW
                + putOutP * putOutW
                + directionP * directionW
                + decorateP * decorateW
                + elevatorP * elevatorW
                + propertyP * propertyW;

        return evaluatePoint;
    }

    private int getElevatorPoint(String elevator) {
        if (null != elevator && !elevator.isEmpty()) {
            if (elevator.contains("有电梯")) {
                this.elevator = "有电梯";
                return 100;
            } else {
                return 0;
            }
        } else {
            return 50;
        }
    }

    private int getPropertyPoint(String property) {
        if (null == property || property.isEmpty()) {
            return 50;
        }
        if (property.contains("商品房")) {
            this.property = "商品房";
            return 100;
        }
        return 0;
    }

    private int getDecoratePoint(String decorate) {
        if (null != decorate && !decorate.isEmpty()) {
            if (decorate.contains(DECORATE_NO)) {
                this.decorate = DECORATE_NO;
                return decorateV.get(DECORATE_NO);
            } else if (decorate.contains(DECORATE_SAMPLE)) {
                this.decorate = DECORATE_SAMPLE;
                return decorateV.get(DECORATE_SAMPLE);
            } else if (decorate.contains(DECORATE_GOOD)) {
                this.decorate = DECORATE_GOOD;
                return decorateV.get(DECORATE_GOOD);
            }
        }
        return decorateV.get(OTHER);
    }

    private int getPricePoint(String price) {
        if (null == price || price.isEmpty() || null == priceF) {
            return 0;
        }
        return (int) ((13000 - priceF) / 30.0);
    }

    private int getSizePoint(String size) {
        if (null == size || size.isEmpty() || null == sizeF) {
            return 0;
        }
        return (int) ((sizeF - 50) * 2);
    }

    private int getTotalPoint(String total) {
        if (null == total || total.isEmpty() || null == totalF) {
            return 0;
        }
        return (int) ((150 - totalF) / 0.4);
    }

    private int getDirectionPoint(String direction) {
        if (null != direction && !direction.isEmpty()) {
            if (direction.contains(DIRECTION_NB)) {
                this.direction = DIRECTION_NB;
                return directionV.get(DIRECTION_NB);
            } else if (direction.contains("东") || direction.contains("西")) {
                this.direction = OTHER;
                return directionV.get(OTHER);
            } else if (direction.contains("北")) {
                this.direction = OTHER;
                return directionV.get(OTHER);
            }
        }
        return directionV.get(DIRECTION_EMPTY);
    }

    private int getPutOutPoint(String putOut) {
        if (null == putOut || putOut.isEmpty()) {
            return 0;
        }
        try {
            int year = Integer.parseInt(putOut.substring(0, 4)), month = Integer.parseInt(putOut.substring(5, 7)), day = Integer.parseInt(putOut.substring(8, 10));
            putOutF = (float) (year + month / 12.0 + day / 365.0);
            Date nowD = new Date();
            int y = nowD.getYear() + 1900, m = nowD.getMonth() + 1, d = nowD.getDate();
            float nowF = (float) (y + m / 12.0 + d / 365.0);
            return (int) (((putOutF - nowF) * 365 + 60) * 2);
        } catch (Exception e) {
            return 0;
        }
    }

    private int getPositionPoint(String position) {
        if (null == position || position.isEmpty()) {
            return positionV.get(OTHER);
        }
        for (String key : positionV.keySet()) {
            if (position.contains(key) || key.contains(position)) {
                this.position = key;
                return positionV.get(key);
            }
        }
        return positionV.get(OTHER);
    }

    private int getAgePoint(String age) {
        if (null == age || age.isEmpty()) {
            return 0;
        }
        try {
            this.buildTimeF = Float.parseFloat(age.substring(0, 4));
            if (this.buildTimeF > 2015) {
                return 50;
            }
            if (this.buildTimeF > 2012) {
                return 80;
            }
            return (int) ((this.buildTimeF - 1998) * 6);
        } catch (Exception e) {
            return 0;
        }
    }

    private int getFloorPoint(String floor) {
        if (null == floor || floor.isEmpty()) {
            return floorV.get(OTHER);
        }
        if (floor.contains(FLOOR_LOW)) {
            return floorV.get(FLOOR_LOW);
        } else if (floor.contains(FLOOR_MIDDLE)) {
            return floorV.get(FLOOR_MIDDLE);
        } else if (floor.contains(FLOOR_HIGH)) {
            return floorV.get(FLOOR_HIGH);
        }
        return floorV.get(OTHER);
    }

    private int getShapePoint(String shape) {
        if (null == shape || shape.isEmpty()) {
            return 50;
        }
        String n, t;
        int roomN = 0, livingN = 0, kitchenN = 0, toiletN = 0;
        try {
            for (int i = 1; i < shape.length(); i++) {
                n = shape.substring(i - 1, i);
                t = shape.substring(i, i + 1);
                if ("室".equals(t)) {
                    roomN = Integer.parseInt(n);
                } else if ("厅".equals(t)) {
                    livingN = Integer.parseInt(n);
                } else if ("厨".equals(t)) {
                    kitchenN = Integer.parseInt(n);
                } else if ("卫".equals(t)) {
                    toiletN = Integer.parseInt(n);
                }
            }
        } catch (Exception e) {}
        this.shape = roomN + "室" + livingN + "厅" + kitchenN + "厨" + toiletN + "卫";
        return roomN * 18 + livingN * 9 + kitchenN * 15 + toiletN * 15;
    }

    private int getLastSalePoint(String lastSale) {
        if (null == lastSale || lastSale.isEmpty()) {
            return 0;
        }
        try {
            String n;
            int start = 0, year = 0, month = 0, day = 0;
            for (int i = 1; i < lastSale.length(); i++) {
                if ("年".equals(lastSale.substring(i, i + 1))) {
                    n = lastSale.substring(start, i);
                    year = Integer.parseInt(n);
                    start = i + 1;
                } else if ("月".equals(lastSale.substring(i, i + 1))) {
                    n = lastSale.substring(start, i);
                    month = Integer.parseInt(n);
                    start = i + 1;
                } else if ("日".equals(lastSale.substring(i, i + 1))) {
                    n = lastSale.substring(start, i);
                    day = Integer.parseInt(n);
                    start = i + 1;
                }
            }
            lastSaleF = (float) (year + month / 12.0 + day / 365.0);
            Date nowD = new Date();
            int y = nowD.getYear() + 1900, m = nowD.getMonth() + 1, d = nowD.getDate();
            float nowF = (float) (y + m / 12.0 + d / 365.0);
            if (nowF - lastSaleF < 2 || lastSaleF < 2000) {
                return 0;
            }
            return (int) ((nowF - lastSaleF) * 12);
        } catch (Exception e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        LianjiaHouse lianjiaHouse = new LianjiaHouse("title", "17,539元/平", "160万",
                "91.23m²", "高楼层/7", "1999年",
                "2室2厅1厨1卫", "19,410元/平", "1999年06月25日",
                "洪山区，武昌火车站", "2017.07.11", "南 北",
                "精装", "有电梯", "商品房", "url");
        System.err.println(lianjiaHouse.toString());
    }

    public LianjiaHouse() {
    }

    public LianjiaHouse(String title, String price, String total, String size,
                        String floor, String buildTime, String shape, String priceAvg,
                        String lastSale, String position, String putOut, String direction,
                        String decorate, String elevator, String property, String url) {
        this.title = title;
        this.price = price;
        try {
            if (null != price) {
                int idx2 = price.indexOf("元/平"), idx1 = price.indexOf(",");
                String num = price.substring(0, idx1) + price.substring(idx1 + 1, idx2);
                priceF = Float.parseFloat(num);
            }
        } catch (Exception e) {}
        this.size = size;
        try {
            if (null != size) {
                int idx = size.indexOf("㎡");
                sizeF = Float.parseFloat(size.substring(0, idx));
            }
        } catch (Exception e) {}
        this.total = total;
        try {
            if (null != total && !total.isEmpty()) {
                int idx = total.indexOf("万");
                totalF = Float.parseFloat(total.substring(0, idx));
            } else if (null != price && null != size) {
                totalF = (float) (sizeF * priceF / 10000.0);
            }
        } catch (Exception e) {}
        this.floor = floor;
        this.buildTime = buildTime;
        this.shape = shape;
        this.priceAvg = priceAvg;
        try {
            if (null != priceAvg) {
                int idx2 = priceAvg.indexOf("元/平"), idx1 = priceAvg.indexOf(",");
                String num = priceAvg.substring(0, idx1) + priceAvg.substring(idx1 + 1, idx2);
                priceAvgF = Float.parseFloat(num);
            }
        } catch (Exception e) {}
        this.lastSale = lastSale;
        this.position = position;
        this.putOut = putOut;
        this.direction = direction;
        this.decorate = decorate;
        this.elevator = elevator;
        this.property = property;
        this.url = url;
        this.evaluatePoint = calculate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Float getPriceF() {
        return priceF;
    }

    public void setPriceF(Float priceF) {
        this.priceF = priceF;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Float getTotalF() {
        return totalF;
    }

    public void setTotalF(Float totalF) {
        this.totalF = totalF;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getSizeF() {
        return sizeF;
    }

    public void setSizeF(Float sizeF) {
        this.sizeF = sizeF;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public Float getBuildTimeF() {
        return buildTimeF;
    }

    public void setBuildTimeF(Float buildTimeF) {
        this.buildTimeF = buildTimeF;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getPriceAvg() {
        return priceAvg;
    }

    public void setPriceAvg(String priceAvg) {
        this.priceAvg = priceAvg;
    }

    public Float getPriceAvgF() {
        return priceAvgF;
    }

    public void setPriceAvgF(Float priceAvgF) {
        this.priceAvgF = priceAvgF;
    }

    public String getLastSale() {
        return lastSale;
    }

    public void setLastSale(String lastSale) {
        this.lastSale = lastSale;
    }

    public Float getLastSaleF() {
        return lastSaleF;
    }

    public void setLastSaleF(Float lastSaleF) {
        this.lastSaleF = lastSaleF;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPutOut() {
        return putOut;
    }

    public void setPutOut(String putOut) {
        this.putOut = putOut;
    }

    public Float getPutOutF() {
        return putOutF;
    }

    public void setPutOutF(Float putOutF) {
        this.putOutF = putOutF;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDecorate() {
        return decorate;
    }

    public void setDecorate(String decorate) {
        this.decorate = decorate;
    }

    public int getEvaluatePoint() {
        return evaluatePoint;
    }

    public void setEvaluatePoint(int evaluatePoint) {
        this.evaluatePoint = evaluatePoint;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "LianjiaHouse{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", priceF=" + priceF +
                ", total='" + total + '\'' +
                ", totalF=" + totalF +
                ", size='" + size + '\'' +
                ", sizeF=" + sizeF +
                ", floor='" + floor + '\'' +
                ", buildTime='" + buildTime + '\'' +
                ", buildTimeF=" + buildTimeF +
                ", shape='" + shape + '\'' +
                ", priceAvg='" + priceAvg + '\'' +
                ", priceAvgF=" + priceAvgF +
                ", lastSale='" + lastSale + '\'' +
                ", lastSaleF=" + lastSaleF +
                ", position='" + position + '\'' +
                ", putOut='" + putOut + '\'' +
                ", putOutF=" + putOutF +
                ", direction='" + direction + '\'' +
                ", decorate='" + decorate + '\'' +
                ", elevator='" + elevator + '\'' +
                ", property='" + property + '\'' +
                ", url='" + url + '\'' +
                ", evaluatePoint=" + evaluatePoint +
                ", updateTime=" + updateTime +
                '}';
    }
}
