package com.guochuang.mimedia.mvp.model;

public class MegviiSerach {

    /**
     * result : 1001
     * completeness : 1
     * name : {"quality":0.765,"result":"辛世超","rect":{"rt":{"y":116,"x":343},"lt":{"y":119,"x":218},"lb":{"y":158,"x":217},"rb":{"y":155,"x":343}},"logic":0}
     * time_used : 467
     * gender : {"quality":0.785,"result":"男","rect":{"rt":{"y":194,"x":244},"lt":{"y":195,"x":211},"lb":{"y":228,"x":211},"rb":{"y":227,"x":243}},"logic":0}
     * address : {"quality":0.825,"result":"河北省廊坊市永清县别古庄镇别古庄村607号","rect":{"rt":{"y":326,"x":609},"lt":{"y":336,"x":208},"lb":{"y":421,"x":206},"rb":{"y":411,"x":612}},"logic":0}
     * card_rect : {"rt":{"y":25,"x":965},"lt":{"y":45,"x":20},"lb":{"y":654,"x":-10},"rb":{"y":626,"x":1020}}
     * request_id : 1534253575,8f549c0c-b0f9-415a-a50a-450e0f29be8d
     * idcard_number : {"quality":0.882,"result":"132825199302022014","rect":{"rt":{"y":517,"x":904},"lt":{"y":531,"x":353},"lb":{"y":568,"x":353},"rb":{"y":553,"x":907}},"logic":0}
     * birth_month : {"quality":0.875,"result":"2","rect":{"rt":{"y":263,"x":386},"lt":{"y":264,"x":362},"lb":{"y":292,"x":362},"rb":{"y":292,"x":386}},"logic":0}
     * birth_day : {"quality":0.875,"result":"2","rect":{"rt":{"y":261,"x":478},"lt":{"y":261,"x":446},"lb":{"y":290,"x":447},"rb":{"y":289,"x":479}},"logic":0}
     * nationality : {"quality":0.873,"result":"汉","rect":{"rt":{"y":192,"x":437},"lt":{"y":193,"x":403},"lb":{"y":225,"x":403},"rb":{"y":224,"x":438}},"logic":0}
     * birth_year : {"quality":0.795,"result":"1993","rect":{"rt":{"y":264,"x":281},"lt":{"y":266,"x":210},"lb":{"y":295,"x":209},"rb":{"y":293,"x":281}},"logic":0}
     * side : 0
     * legality : {"Edited":0,"ID_Photo_Threshold":0.8,"Photocopy":0,"Temporary_ID_Photo":0,"ID_Photo":0.988,"Screen":0.012}
     */

    private int result;
    private int completeness;
    private NameBean name;
    private int time_used;
    private GenderBean gender;
    private AddressBean address;
    private CardRectBean card_rect;
    private String request_id;
    private IdcardNumberBean idcard_number;
    private BirthMonthBean birth_month;
    private BirthDayBean birth_day;
    private NationalityBean nationality;
    private BirthYearBean birth_year;
    private int side;
    private LegalityBean legality;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCompleteness() {
        return completeness;
    }

    public void setCompleteness(int completeness) {
        this.completeness = completeness;
    }

    public NameBean getName() {
        return name;
    }

    public void setName(NameBean name) {
        this.name = name;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public GenderBean getGender() {
        return gender;
    }

    public void setGender(GenderBean gender) {
        this.gender = gender;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public CardRectBean getCard_rect() {
        return card_rect;
    }

    public void setCard_rect(CardRectBean card_rect) {
        this.card_rect = card_rect;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public IdcardNumberBean getIdcard_number() {
        return idcard_number;
    }

    public void setIdcard_number(IdcardNumberBean idcard_number) {
        this.idcard_number = idcard_number;
    }

    public BirthMonthBean getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(BirthMonthBean birth_month) {
        this.birth_month = birth_month;
    }

    public BirthDayBean getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(BirthDayBean birth_day) {
        this.birth_day = birth_day;
    }

    public NationalityBean getNationality() {
        return nationality;
    }

    public void setNationality(NationalityBean nationality) {
        this.nationality = nationality;
    }

    public BirthYearBean getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(BirthYearBean birth_year) {
        this.birth_year = birth_year;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public LegalityBean getLegality() {
        return legality;
    }

    public void setLegality(LegalityBean legality) {
        this.legality = legality;
    }

    public static class NameBean {
        /**
         * quality : 0.765
         * result : 辛世超
         * rect : {"rt":{"y":116,"x":343},"lt":{"y":119,"x":218},"lb":{"y":158,"x":217},"rb":{"y":155,"x":343}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBean rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBean getRect() {
            return rect;
        }

        public void setRect(RectBean rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBean {
            /**
             * rt : {"y":116,"x":343}
             * lt : {"y":119,"x":218}
             * lb : {"y":158,"x":217}
             * rb : {"y":155,"x":343}
             */

            private RtBean rt;
            private LtBean lt;
            private LbBean lb;
            private RbBean rb;

            public RtBean getRt() {
                return rt;
            }

            public void setRt(RtBean rt) {
                this.rt = rt;
            }

            public LtBean getLt() {
                return lt;
            }

            public void setLt(LtBean lt) {
                this.lt = lt;
            }

            public LbBean getLb() {
                return lb;
            }

            public void setLb(LbBean lb) {
                this.lb = lb;
            }

            public RbBean getRb() {
                return rb;
            }

            public void setRb(RbBean rb) {
                this.rb = rb;
            }

            public static class RtBean {
                /**
                 * y : 116
                 * x : 343
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBean {
                /**
                 * y : 119
                 * x : 218
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBean {
                /**
                 * y : 158
                 * x : 217
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBean {
                /**
                 * y : 155
                 * x : 343
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class GenderBean {
        /**
         * quality : 0.785
         * result : 男
         * rect : {"rt":{"y":194,"x":244},"lt":{"y":195,"x":211},"lb":{"y":228,"x":211},"rb":{"y":227,"x":243}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanX getRect() {
            return rect;
        }

        public void setRect(RectBeanX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanX {
            /**
             * rt : {"y":194,"x":244}
             * lt : {"y":195,"x":211}
             * lb : {"y":228,"x":211}
             * rb : {"y":227,"x":243}
             */

            private RtBeanX rt;
            private LtBeanX lt;
            private LbBeanX lb;
            private RbBeanX rb;

            public RtBeanX getRt() {
                return rt;
            }

            public void setRt(RtBeanX rt) {
                this.rt = rt;
            }

            public LtBeanX getLt() {
                return lt;
            }

            public void setLt(LtBeanX lt) {
                this.lt = lt;
            }

            public LbBeanX getLb() {
                return lb;
            }

            public void setLb(LbBeanX lb) {
                this.lb = lb;
            }

            public RbBeanX getRb() {
                return rb;
            }

            public void setRb(RbBeanX rb) {
                this.rb = rb;
            }

            public static class RtBeanX {
                /**
                 * y : 194
                 * x : 244
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanX {
                /**
                 * y : 195
                 * x : 211
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanX {
                /**
                 * y : 228
                 * x : 211
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanX {
                /**
                 * y : 227
                 * x : 243
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class AddressBean {
        /**
         * quality : 0.825
         * result : 河北省廊坊市永清县别古庄镇别古庄村607号
         * rect : {"rt":{"y":326,"x":609},"lt":{"y":336,"x":208},"lb":{"y":421,"x":206},"rb":{"y":411,"x":612}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXX {
            /**
             * rt : {"y":326,"x":609}
             * lt : {"y":336,"x":208}
             * lb : {"y":421,"x":206}
             * rb : {"y":411,"x":612}
             */

            private RtBeanXX rt;
            private LtBeanXX lt;
            private LbBeanXX lb;
            private RbBeanXX rb;

            public RtBeanXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXX rt) {
                this.rt = rt;
            }

            public LtBeanXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXX lt) {
                this.lt = lt;
            }

            public LbBeanXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXX lb) {
                this.lb = lb;
            }

            public RbBeanXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXX {
                /**
                 * y : 326
                 * x : 609
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXX {
                /**
                 * y : 336
                 * x : 208
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXX {
                /**
                 * y : 421
                 * x : 206
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXX {
                /**
                 * y : 411
                 * x : 612
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class CardRectBean {
        /**
         * rt : {"y":25,"x":965}
         * lt : {"y":45,"x":20}
         * lb : {"y":654,"x":-10}
         * rb : {"y":626,"x":1020}
         */

        private RtBeanXXX rt;
        private LtBeanXXX lt;
        private LbBeanXXX lb;
        private RbBeanXXX rb;

        public RtBeanXXX getRt() {
            return rt;
        }

        public void setRt(RtBeanXXX rt) {
            this.rt = rt;
        }

        public LtBeanXXX getLt() {
            return lt;
        }

        public void setLt(LtBeanXXX lt) {
            this.lt = lt;
        }

        public LbBeanXXX getLb() {
            return lb;
        }

        public void setLb(LbBeanXXX lb) {
            this.lb = lb;
        }

        public RbBeanXXX getRb() {
            return rb;
        }

        public void setRb(RbBeanXXX rb) {
            this.rb = rb;
        }

        public static class RtBeanXXX {
            /**
             * y : 25
             * x : 965
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class LtBeanXXX {
            /**
             * y : 45
             * x : 20
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class LbBeanXXX {
            /**
             * y : 654
             * x : -10
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class RbBeanXXX {
            /**
             * y : 626
             * x : 1020
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }
    }

    public static class IdcardNumberBean {
        /**
         * quality : 0.882
         * result : 132825199302022014
         * rect : {"rt":{"y":517,"x":904},"lt":{"y":531,"x":353},"lb":{"y":568,"x":353},"rb":{"y":553,"x":907}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXXX {
            /**
             * rt : {"y":517,"x":904}
             * lt : {"y":531,"x":353}
             * lb : {"y":568,"x":353}
             * rb : {"y":553,"x":907}
             */

            private RtBeanXXXX rt;
            private LtBeanXXXX lt;
            private LbBeanXXXX lb;
            private RbBeanXXXX rb;

            public RtBeanXXXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXXXX rt) {
                this.rt = rt;
            }

            public LtBeanXXXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXXXX lt) {
                this.lt = lt;
            }

            public LbBeanXXXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXXXX lb) {
                this.lb = lb;
            }

            public RbBeanXXXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXXXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXXXX {
                /**
                 * y : 517
                 * x : 904
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXXXX {
                /**
                 * y : 531
                 * x : 353
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXXXX {
                /**
                 * y : 568
                 * x : 353
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXXXX {
                /**
                 * y : 553
                 * x : 907
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class BirthMonthBean {
        /**
         * quality : 0.875
         * result : 2
         * rect : {"rt":{"y":263,"x":386},"lt":{"y":264,"x":362},"lb":{"y":292,"x":362},"rb":{"y":292,"x":386}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXXXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXXXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXXXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXXXX {
            /**
             * rt : {"y":263,"x":386}
             * lt : {"y":264,"x":362}
             * lb : {"y":292,"x":362}
             * rb : {"y":292,"x":386}
             */

            private RtBeanXXXXX rt;
            private LtBeanXXXXX lt;
            private LbBeanXXXXX lb;
            private RbBeanXXXXX rb;

            public RtBeanXXXXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXXXXX rt) {
                this.rt = rt;
            }

            public LtBeanXXXXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXXXXX lt) {
                this.lt = lt;
            }

            public LbBeanXXXXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXXXXX lb) {
                this.lb = lb;
            }

            public RbBeanXXXXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXXXXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXXXXX {
                /**
                 * y : 263
                 * x : 386
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXXXXX {
                /**
                 * y : 264
                 * x : 362
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXXXXX {
                /**
                 * y : 292
                 * x : 362
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXXXXX {
                /**
                 * y : 292
                 * x : 386
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class BirthDayBean {
        /**
         * quality : 0.875
         * result : 2
         * rect : {"rt":{"y":261,"x":478},"lt":{"y":261,"x":446},"lb":{"y":290,"x":447},"rb":{"y":289,"x":479}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXXXXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXXXXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXXXXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXXXXX {
            /**
             * rt : {"y":261,"x":478}
             * lt : {"y":261,"x":446}
             * lb : {"y":290,"x":447}
             * rb : {"y":289,"x":479}
             */

            private RtBeanXXXXXX rt;
            private LtBeanXXXXXX lt;
            private LbBeanXXXXXX lb;
            private RbBeanXXXXXX rb;

            public RtBeanXXXXXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXXXXXX rt) {
                this.rt = rt;
            }

            public LtBeanXXXXXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXXXXXX lt) {
                this.lt = lt;
            }

            public LbBeanXXXXXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXXXXXX lb) {
                this.lb = lb;
            }

            public RbBeanXXXXXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXXXXXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXXXXXX {
                /**
                 * y : 261
                 * x : 478
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXXXXXX {
                /**
                 * y : 261
                 * x : 446
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXXXXXX {
                /**
                 * y : 290
                 * x : 447
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXXXXXX {
                /**
                 * y : 289
                 * x : 479
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class NationalityBean {
        /**
         * quality : 0.873
         * result : 汉
         * rect : {"rt":{"y":192,"x":437},"lt":{"y":193,"x":403},"lb":{"y":225,"x":403},"rb":{"y":224,"x":438}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXXXXXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXXXXXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXXXXXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXXXXXX {
            /**
             * rt : {"y":192,"x":437}
             * lt : {"y":193,"x":403}
             * lb : {"y":225,"x":403}
             * rb : {"y":224,"x":438}
             */

            private RtBeanXXXXXXX rt;
            private LtBeanXXXXXXX lt;
            private LbBeanXXXXXXX lb;
            private RbBeanXXXXXXX rb;

            public RtBeanXXXXXXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXXXXXXX rt) {
                this.rt = rt;
            }

            public LtBeanXXXXXXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXXXXXXX lt) {
                this.lt = lt;
            }

            public LbBeanXXXXXXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXXXXXXX lb) {
                this.lb = lb;
            }

            public RbBeanXXXXXXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXXXXXXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXXXXXXX {
                /**
                 * y : 192
                 * x : 437
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXXXXXXX {
                /**
                 * y : 193
                 * x : 403
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXXXXXXX {
                /**
                 * y : 225
                 * x : 403
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXXXXXXX {
                /**
                 * y : 224
                 * x : 438
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class BirthYearBean {
        /**
         * quality : 0.795
         * result : 1993
         * rect : {"rt":{"y":264,"x":281},"lt":{"y":266,"x":210},"lb":{"y":295,"x":209},"rb":{"y":293,"x":281}}
         * logic : 0
         */

        private double quality;
        private String result;
        private RectBeanXXXXXXX rect;
        private int logic;

        public double getQuality() {
            return quality;
        }

        public void setQuality(double quality) {
            this.quality = quality;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public RectBeanXXXXXXX getRect() {
            return rect;
        }

        public void setRect(RectBeanXXXXXXX rect) {
            this.rect = rect;
        }

        public int getLogic() {
            return logic;
        }

        public void setLogic(int logic) {
            this.logic = logic;
        }

        public static class RectBeanXXXXXXX {
            /**
             * rt : {"y":264,"x":281}
             * lt : {"y":266,"x":210}
             * lb : {"y":295,"x":209}
             * rb : {"y":293,"x":281}
             */

            private RtBeanXXXXXXXX rt;
            private LtBeanXXXXXXXX lt;
            private LbBeanXXXXXXXX lb;
            private RbBeanXXXXXXXX rb;

            public RtBeanXXXXXXXX getRt() {
                return rt;
            }

            public void setRt(RtBeanXXXXXXXX rt) {
                this.rt = rt;
            }

            public LtBeanXXXXXXXX getLt() {
                return lt;
            }

            public void setLt(LtBeanXXXXXXXX lt) {
                this.lt = lt;
            }

            public LbBeanXXXXXXXX getLb() {
                return lb;
            }

            public void setLb(LbBeanXXXXXXXX lb) {
                this.lb = lb;
            }

            public RbBeanXXXXXXXX getRb() {
                return rb;
            }

            public void setRb(RbBeanXXXXXXXX rb) {
                this.rb = rb;
            }

            public static class RtBeanXXXXXXXX {
                /**
                 * y : 264
                 * x : 281
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LtBeanXXXXXXXX {
                /**
                 * y : 266
                 * x : 210
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class LbBeanXXXXXXXX {
                /**
                 * y : 295
                 * x : 209
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }

            public static class RbBeanXXXXXXXX {
                /**
                 * y : 293
                 * x : 281
                 */

                private int y;
                private int x;

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }
            }
        }
    }

    public static class LegalityBean {
        /**
         * Edited : 0
         * ID_Photo_Threshold : 0.8
         * Photocopy : 0
         * Temporary_ID_Photo : 0
         * ID_Photo : 0.988
         * Screen : 0.012
         */

        private double Edited;
        private double ID_Photo_Threshold;
        private double Photocopy;
        private double Temporary_ID_Photo;
        private double ID_Photo;
        private double Screen;

        public double getEdited() {
            return Edited;
        }

        public void setEdited(double Edited) {
            this.Edited = Edited;
        }

        public double getID_Photo_Threshold() {
            return ID_Photo_Threshold;
        }

        public void setID_Photo_Threshold(double ID_Photo_Threshold) {
            this.ID_Photo_Threshold = ID_Photo_Threshold;
        }

        public double getPhotocopy() {
            return Photocopy;
        }

        public void setPhotocopy(double Photocopy) {
            this.Photocopy = Photocopy;
        }

        public double getTemporary_ID_Photo() {
            return Temporary_ID_Photo;
        }

        public void setTemporary_ID_Photo(double Temporary_ID_Photo) {
            this.Temporary_ID_Photo = Temporary_ID_Photo;
        }

        public double getID_Photo() {
            return ID_Photo;
        }

        public void setID_Photo(double ID_Photo) {
            this.ID_Photo = ID_Photo;
        }

        public double getScreen() {
            return Screen;
        }

        public void setScreen(double Screen) {
            this.Screen = Screen;
        }
    }
}
