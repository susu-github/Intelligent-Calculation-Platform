package sample;

import com.alibaba.fastjson.JSONObject;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    final Label lab1 = new Label("账号:");
    final Label lab2 = new Label("密码:");
//    final Button button1 = new Button("找回密码");
    final Button button2 = new Button("帮助文档");
    final PasswordField pf = new PasswordField();
    final TextField tf = new TextField();
    final Text actiontarget = new Text();
    Browser browser = new Browser();
    @Override
    public void start(Stage primaryStage) {
        //关闭窗口事件
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //先关闭界面再关闭浏览器内核
                primaryStage.close();
                browser.dispose();
//                System.exit(0);
            }
        });
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(0, 0, 0, 0));
        rootPane.setVgap(5);
//        rootPane.setGridLinesVisible(true);
//      设置背景图
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image("/resource/image/logo4.png"));
        Image ima_login = new Image("/resource/image/4545d8e1db93fbea7811011f2f9753b0.jpg");
        ImageView imgView_login = new ImageView(ima_login);
        imgView_login.setFitWidth(330);
        imgView_login.setFitHeight(130);
        imgView_login.autosize();
        imgView_login.setSmooth(true);
        imgView_login.setCache(true);
        tf.setBorder(null);
        tf.setOpacity(0.5);
        tf.setPromptText("请输入QQ账号");
        tf.setFont(new Font("微软雅黑",16));
        pf.setPromptText("输入密码");
        HBox HB_checkbox = new HBox(10);
//        CheckBox cb1 = new CheckBox("自动登录");
//        CheckBox cb2 = new CheckBox("记住密码");
//        HB_checkbox.getChildren().add(cb1);
//        HB_checkbox.getChildren().add(cb2);
//        HB_checkbox.getChildren().add(button1);
        HB_checkbox.setMaxWidth(50);
        Label line = new Label(null,new ImageView(new Image("/resource/image/line1.png")));
        Button bt1 = new Button("登录");
        bt1.setId("submit-button");
        bt1.setFont(new Font("微软雅黑", 14));
//        设置按钮事件
        bt1.setPrefSize(240, 50);
//        button1.setId("button_web");
        button2.setId("button_web");
        bt1.setOnAction(event -> {
            String userNameValue = tf.getText();
            String passwordValue = pf.getText();
            if(userNameValue != null && !"".equals(userNameValue) && passwordValue != null && !"".equals(passwordValue)){
                String macAddr = getMacAddress();
                if(macAddr != null && macAddr != ""){
//                    System.out.println(macAddr);
                    String logonUrl = "http://127.0.0.1:8080/logon";
                    JSONObject paraJson = new JSONObject();
                    paraJson.put("username",userNameValue);
                    paraJson.put("password",passwordValue);
                    paraJson.put("macAddr",macAddr);
//                    String logonResult = sendPost(logonUrl, paraJson);
//                    if(!"false".equals(logonResult)) {
                    if(true) {
                        primaryStage.close();
//                        String url = "http://192.168.101.35:8081/command/base/zh-cn/code/index.html";
                        String url = "https://www.baidu.com";
                        // 谷歌内核浏览器
                        BrowserView view = new BrowserView(browser);
                        browser.setPopupHandler(new PopupHandler() {
                            @Override
                            public PopupContainer handlePopup(PopupParams popupParams) {
                                browser.loadURL(popupParams.getURL());
                                return null;
                            }
                        });
                        StackPane pane = new StackPane();
                        pane.getChildren().add(view);
                        Scene scene = new Scene(pane, 1680, 900);
//                        // 设置标题
                        primaryStage.getIcons().add(new Image("/resource/image/logo4.png"));
                        primaryStage.setTitle("智算系统 (Intelligent Calculation Platform)");
//                        primaryStage.setMaximized(true);
                        // 设置默认宽高 并加入根节点
//                        primaryStage.setScene(new Scene(view, 1480, 900));// 显示窗口
                        primaryStage.setScene(scene);// 显示窗口
//                        primaryStage.setFullScreen(true);
                        primaryStage.show();
                        browser.loadURL(url);
//                        // 获取IndexController
//                        IndexController controller = (IndexController)fxmlLoader.getController();
//                        // 执行IndexController中的初始化方法
//                        controller.init();
                    }else {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("账户名或密码错误");
                    }
                }else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("本电脑没有权限");
                }
            }else {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("账户名或密码不能为空");
            }
        });

//        this.openWebsite(button1, "https://aq.qq.com/v2/uv_aq/html/reset_pwd/pc_reset_pwd_input_account.html?v=3.0&old_ver_account=1986985788");
        this.openWebsite(button2, "https://ssl.zc.qq.com/v3/index-chs.html?type=0");
        rootPane.add(bt1, 1, 4, 4, 1);
        rootPane.add(imgView_login, 0, 0, 4, 1);
        rootPane.add(button2, 0, 5);
        rootPane.add(lab1, 1, 1);
        rootPane.add(tf, 1, 1, 2, 1);
        rootPane.add(lab2, 1, 2);
        rootPane.add(pf, 1, 2, 2, 1);
        rootPane.add(line, 1, 1);
//        rootPane.add(cb2, 2, 3);
        rootPane.add(actiontarget,2,3);
        GridPane.setMargin(bt1, new Insets(0, 0, 0, 10));
        GridPane.setMargin(lab1, new Insets(0, 0, 0, 0));
        GridPane.setMargin(tf, new Insets(0, 0, 0, 60));
        GridPane.setMargin(lab2, new Insets(0, 0, 0, 0));
        GridPane.setMargin(pf, new Insets(0, 0, 0, 60));
//        GridPane.setMargin(line, new Insets(0, 0, 0, 0));
//        GridPane.setMargin(cb2, new Insets(0, 0, 0, 10));
        GridPane.setMargin(actiontarget, new Insets(0, 0, 0, 30));
        GridPane.setMargin(button2, new Insets(0, 0, 5, 10));
        Scene scene = new Scene(rootPane, 425, 300);
        rootPane.setStyle("-fx-background-color:#ffffff;");
        primaryStage.setTitle("智算系统 (Intelligent Calculation Platform)");
//        primaryStage.getIcons().add(new Image("file:src/resource/qq.png"));
        scene.getStylesheets().add(
                getClass().getResource("/resource/css/Main.css")
                        .toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openWebsite(Button button, String website) {
        button.setOnAction(event -> {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(website));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 发送HttpPost请求，参数为map
     * @param url
     * @param paraJson
     * @return
     */
    public static String sendPost(String url, JSONObject paraJson) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity entity = new StringEntity(paraJson.toString(), Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-type","application/json;charset=utf-8");
        httppost.setHeader("Accept","application/json");
        httppost.setEntity(entity);     //设置请求体
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity1 = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("MAC地址获取失败");
        }
        return "";
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
