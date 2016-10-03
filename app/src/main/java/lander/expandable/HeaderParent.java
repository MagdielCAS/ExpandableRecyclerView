package lander.expandable;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.security.Key;
import java.util.List;
import java.util.UUID;

/**
 * Created by magdi on 01/08/2016.
 */
public class HeaderParent implements ParentObject {

    private UUID mId;
    private String mTitle;

    private List<Object> mChildItemList;

    public HeaderParent() {
        mId = UUID.randomUUID();
    }
    public HeaderParent(String mTitle) {
        mId = UUID.randomUUID();
        this.mTitle = mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    @Override
    public List<Object> getChildObjectList() {
        return  mChildItemList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildItemList = list;
    }

}
