package demo.tab.act;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends Activity {
    String TAG = Main.class.getName();
    int tabSize = -1;
//    TableRow tab_leader_row;
//    TableRow tab_item_row;
    LinearLayout tab_leader;
    FrameLayout tab_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        tab_leader = (LinearLayout)findViewById(R.id.tab_leader);
        tab_item = (FrameLayout)findViewById(R.id.tab_item);
        // Must calling this function, after update tab_leader_row, tab_item_row
        updateList();
        /** tab click setup **/

        for( int i = 0 ; i < tabSize ; i++ ){
            (tabList.get(i)).setOnClickListener(new TabOnClickListener(i));
        }

        /** setup first page **/
        itemShow(0);

    }

    class TabOnClickListener implements View.OnClickListener {
        int index = 0;

        public TabOnClickListener(int index){
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            itemShow(index, true);
        }
    }

    protected List<View> itemList = new ArrayList();
    public List<View> getItemList(){return itemList;}

    private void updateList(){
        if( null != tab_leader && null != tab_item ){
            if ( tab_leader.getChildCount() == tab_item.getChildCount() ){
                tabSize = tab_leader.getChildCount();
                for( int i = 0 ; i < tabSize ; i++ ){
                    tabList.add(tab_leader.getChildAt(i));
                    itemList.add(tab_item.getChildAt(i));
                }
            }else{
                Log.d(TAG, "tab not fit with item.");
            }
        }else{
            Log.d(TAG, "tab_leader/tab_item is null.");
        }

    }

    protected List<View> tabList = new ArrayList();
    public List<View> getTabList(){return tabList;}

    private static int layoutHidingDelay = 100;
    private static int layoutDuration = 100;
    private static int layoutShowingDelay = layoutHidingDelay + layoutDuration;

    public static void setLayoutHidingDelay(int input){layoutHidingDelay = input;}
    public static void setLayoutDuration(int input){layoutDuration = input;}
    public static int getLayoutHidingDelay(){return layoutHidingDelay;}
    public static int getLayoutDuration(){return layoutDuration;}
    public static int getLayoutShowingDelay(){return layoutShowingDelay;}

    public void itemShow(int index, boolean animationFlag){
        hideAllItem();
        if ( index < tabSize && tabSize != -1 ){
            itemList.get(index).setVisibility(View.VISIBLE);
        }else{
            Log.d(TAG, "index error.");
        }
        if ( animationFlag ){
            animation(itemList.get(index), R.anim.enter_alpha);
        }

    }
    public void itemShow(int index) {
        itemShow(index, false);
    }
    public void itemHide(int index, boolean animationFlag) {
        if ( index < tabSize && tabSize != -1 ){
            itemList.get(index).setVisibility(View.GONE);
        }else{
            Log.d(TAG, "index error.");
        }
        if ( animationFlag ){
            animation(itemList.get(index), R.anim.exit_alpha);
        }
    }
    public void itemHide(int index) {
        itemHide(index, false);
    }
    public void hideAllItem(){
        for( int i = 0 ; i < tabSize ; i++ ){
            if ( null != itemList.get(i) ) {
                itemHide(i);
            }
        }
    }

    public void animation(View view, int animationId){
        Animation anim = AnimationUtils.loadAnimation(this, animationId);
        anim.setStartOffset(getLayoutShowingDelay());
        anim.setDuration(getLayoutDuration());
        view.startAnimation(anim);
    }
}
