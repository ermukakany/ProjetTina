package cloudm120152016.puy2docs.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    String id;
    String owner;
    String type;
    String name;
    String last_modify;

    public Item(String id,  String owner, String type, String name, String last_modify) {
        this.id = id;
        this.owner = owner;
        this.type = type;
        this.name = name;
        this.last_modify = last_modify;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    @Override
    public String toString() {
        return name;
    }

    protected Item(Parcel in) {
        id = in.readString();
        owner = in.readString();
        type = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(type);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
