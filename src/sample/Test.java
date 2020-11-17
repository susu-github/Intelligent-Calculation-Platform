package sample;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/****
 *
 * @author tjw
 * @versuion 1.0
 * @date 2018/12/13 17:16
 */
public class Test {
    public static void init() {

        JFrame frame = new JFrame();
        String url = "http://www.baidu.com";
        // 谷歌内核浏览器
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        //禁用close功能
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("智算系统");
        //隐藏任务栏图标
        frame.setType(JFrame.Type.UTILITY);
//        //不显示标题栏,最大化,最小化,退出按钮
        frame.setUndecorated(false);
        //尺寸
        frame.setSize(1000, 500);
        //坐标
        frame.setLocation(0, 0);
        frame.add(view);
        //全屏显示
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // 是否显示
        frame.setVisible(true);
        //是否在屏幕最上层显示
        frame.setAlwaysOnTop(true);
        //加载地址
        browser.loadURL(url);
        browser.setPopupHandler(new PopupHandler() {
            @Override
            public PopupContainer handlePopup(PopupParams popupParams) {
                browser.loadURL(popupParams.getURL());
                return null;
            }
        });
//        System.out.println(frame.getX());
//        System.out.println(frame.getY());
//        list.add(frame);
        frame.addWindowListener(new WindowAdapter() {
            // 窗口关闭时间监听
            @Override
            public void windowClosing(WindowEvent e){
                System.out.println("窗口关闭...");
            }
        });
    }
}
