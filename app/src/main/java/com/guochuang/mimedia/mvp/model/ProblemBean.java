package com.guochuang.mimedia.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 * 	"problem": "我们公司名字是什么,你看我猜不猜得猜不猜猜得到？",
 * 	"type": 1,
 * 	"item": [{
 * 		"itemname": "A",
 * 		"itemcontent": "三一重工",
 * 		"isanswer": true
 *        }]
 * }
 */
public class ProblemBean implements Parcelable,Cloneable {


    /**
     * problem : 我们公司名字是什么,你看我猜不猜得猜不猜猜得到？
     * type : 1
     * item : [{"itemname":"A","itemcontent":"三一重工","isanswer":true}]
     *
     * 0 单选   1 多选   2填空
     *
     */

    private String problem ="";
    private int type;
    private ArrayList<ItemBean> item;


    public ProblemBean(Parcel in) {
        problem = in.readString();
        type = in.readInt();
        item = in.createTypedArrayList(ItemBean.CREATOR);
    }

    public static final Creator<ProblemBean> CREATOR = new Creator<ProblemBean>() {
        @Override
        public ProblemBean createFromParcel(Parcel in) {
            return new ProblemBean(in);
        }

        @Override
        public ProblemBean[] newArray(int size) {
            return new ProblemBean[size];
        }
    };

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<ItemBean> getItem() {
        return item;
    }

    public void setItem(ArrayList<ItemBean> item) {
        this.item = item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(problem);
        dest.writeInt(type);
        dest.writeTypedList(item);
    }


    public static class ItemBean implements Parcelable{
        /**
         * itemname : A
         * itemcontent : 三一重工
         * isanswer : true
         */

        private int problemType;
        private String itemname ="";
        private String itemcontent = "";
        private boolean isanswer = false;

        public ItemBean(Parcel in) {
            problemType = in.readInt();
            itemname = in.readString();
            itemcontent = in.readString();
            isanswer = in.readByte() != 0;
        }

        public static final Creator<ItemBean> CREATOR = new Creator<ItemBean>() {
            @Override
            public ItemBean createFromParcel(Parcel in) {
                return new ItemBean(in);
            }

            @Override
            public ItemBean[] newArray(int size) {
                return new ItemBean[size];
            }
        };

        public int getProblemType() {
            return problemType;
        }

        public void setProblemType(int problemType) {
            this.problemType = problemType;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getItemcontent() {
            return itemcontent;
        }

        public void setItemcontent(String itemcontent) {
            this.itemcontent = itemcontent;
        }

        public boolean isIsanswer() {
            return isanswer;
        }

        public void setIsanswer(boolean isanswer) {
            this.isanswer = isanswer;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "problemType=" + problemType +
                    ", itemname='" + itemname + '\'' +
                    ", itemcontent='" + itemcontent + '\'' +
                    ", isanswer=" + isanswer +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(problemType);
            dest.writeString(itemname);
            dest.writeString(itemcontent);
            dest.writeByte((byte) (isanswer ? 1 : 0));
        }
    }


    public ProblemBean clone() {
        ProblemBean problemBean = new ProblemBean(Parcel.obtain());
        problemBean.setType(this.getType());
        problemBean.setProblem(this.getProblem());
        //Item 集合
        List<ItemBean> item = this.getItem();
        ArrayList<ItemBean> cloneList = new ArrayList<>();
        for (ItemBean itemBean : item) {
            ItemBean itemBean1 = new ItemBean(Parcel.obtain());
            itemBean1.setProblemType(itemBean.getProblemType());
            itemBean1.setItemname(itemBean.getItemname());
            itemBean1.setItemcontent(itemBean.getItemcontent());
            itemBean1.setIsanswer(itemBean.isIsanswer());
            cloneList.add(itemBean1);
        }

        problemBean.setItem(cloneList);

        return problemBean;
    }


    @Override
    public String toString() {
        return "ProblemBean{" +
                "problem='" + problem + '\'' +
                ", type=" + type +
                ", item=" + item +
                '}';
    }
}
