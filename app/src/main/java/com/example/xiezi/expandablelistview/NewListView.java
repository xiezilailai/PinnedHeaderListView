package com.example.xiezi.expandablelistview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by 蝎子莱莱123 on 2015/12/2.
 */
public class NewListView extends ExpandableListView implements AbsListView.OnScrollListener,ExpandableListView.OnGroupClickListener{


    private static final int GONE=0;
    private static final int VISIBLE=1;
    private static final int PUSH_UP=2;

    private boolean visiable=false;//记录headerView是否能够看见
    private int groupPosition=0;
    private int childPosition=0;
    private View headerView;
    private ExpandableListAdapter expandableListAdapter;
    private boolean[] isOpen=new boolean[100];

    private int headerWidth,headerHeight;


    public void setAdapter(ExpandableListAdapter adapter) {
        super.setAdapter(adapter);
        expandableListAdapter=adapter;


    }

    public NewListView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        setOnGroupClickListener(this);

        LayoutInflater layoutInflater=LayoutInflater.from(getContext());

        headerView=(ViewGroup)layoutInflater.inflate(R.layout.group_view,null);
        headerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));




    }

    public NewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

        if(!isOpen[groupPosition])
        isOpen[groupPosition]=true;
        else isOpen[groupPosition]=false;
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        final long flatpos=getExpandableListPosition(firstVisibleItem);//根据firstItem获得这个item的识别码，可以用来转化获取所在组别和在组别中的位置

//        Log.i("my", firstVisibleItem+"");
         groupPosition = ExpandableListView.getPackedPositionGroup(flatpos);

        childPosition=ExpandableListView.getPackedPositionChild(flatpos);
        if(headerView!=null&&expandableListAdapter!=null)
        consider(groupPosition, childPosition);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(headerView==null)return;
        measureChild(headerView,widthMeasureSpec,heightMeasureSpec);
        headerWidth=headerView.getMeasuredWidth();
//        headerWidth= 800;
        headerHeight=headerView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {//每次布局发生改变时调用
        super.onLayout(changed, l, t, r, b);
        if(headerView==null)return;
        headerView.layout(0, 0, headerWidth, headerHeight);

    }
    private void setHeaderString(int groupPosition){

            TextView tv=(TextView)headerView.findViewById(R.id.group_textView);
            String s=expandableListAdapter.getGroup(groupPosition).toString();
            tv.setText(s);


    }
/*
* 在滑动listView时，根据位置判断header的位置
*
*
* */
    private void consider(int groupPosition,int childPosition) {
        int state=getHeaderState(groupPosition, childPosition);
        setHeaderString(groupPosition);
        switch (state){
            case GONE :
                visiable=false;
                break;
            case VISIBLE:
                if(headerView.getTop()!=0){
                    headerView.layout(0,0,headerWidth,headerHeight);
                }
                visiable=true;
                break;
            case PUSH_UP:

                View firstView=getChildAt(0);
                int bottom=firstView.getBottom();
                int y;
                if(bottom<headerHeight)y=bottom-headerHeight;
                else y=0;

                if(headerView.getTop()!=y){
                    headerView.layout(0,y,headerWidth,headerHeight+y);
                }

                visiable=true;
                break;


        }


    }
    /*
    * 通过获得第一个条目的组别和所在组的位置，如果是这组的最后一个，那么就开始移动headerView
    *
    *
    * */
    private int getHeaderState(int groupPosition,int childPosition){

            final int childCount=expandableListAdapter.getChildrenCount(groupPosition);//求这一个组一共有多少个元素
            if(childPosition==childCount-1){
                return PUSH_UP;
            }else if(childPosition==-1&&!isOpen[groupPosition]){
                return GONE;
            }
            else return VISIBLE;


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_MOVE:
                consider(groupPosition, childPosition);
                break;

            case MotionEvent.ACTION_UP:
                float currentX=ev.getX();
                float currentY=ev.getY();
                if(currentX>0&&currentX<=headerWidth&&currentY>0&&currentY<headerHeight&&visiable){
                    closeGroup();
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    private void closeGroup() {

        this.collapseGroup(groupPosition);

        if(!isOpen[groupPosition])
            isOpen[groupPosition]=true;
        else isOpen[groupPosition]=false;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(headerView==null)return;

        if(visiable){

            drawChild(canvas, headerView, getDrawingTime());
            setHeaderString(groupPosition);
        }

    }
}
