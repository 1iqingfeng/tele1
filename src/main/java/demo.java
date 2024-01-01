import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class demo {
    //    初始化日志
    public static final Logger LOGGER = LoggerFactory.getLogger(demo.class);
    static final String proxyHost = "127.0.0.1";
    static final String proxyPort = "59829";

    //初始化外网链接
    static {
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
    }

    public static void main(String[] args) {
        // 从@BotFather接收到的令牌创建您的机器人
        TelegramBot bot = new TelegramBot("6374366540:AAF8te7OmhMKcTqlC97fig9xp_1z3TldEq0");

        // 注册更新
        bot.setUpdatesListener(updates -> {

            // 处理更新
            // 提取消息

            Message messaged = updates.get(0).message();
//            messaged
            LOGGER.info("messaged:{}", messaged.forwardFromMessageId());

            LOGGER.info("messagedText:{}", messaged.text());

            // 从消息创建一个消息

            SendMessage msg = new SendMessage(messaged.chat().id(), "Hello!");

            bot.execute(msg, new Callback<SendMessage, SendResponse>() {
                @Override
                public void onResponse(SendMessage request, SendResponse response) {
                    if (!response.isOk()) {
                        LOGGER.error("response:{}", response);
                    } else {
                        LOGGER.info("message:{}", response.message().from().id());
                    }
                }

                @Override
                public void onFailure(SendMessage request, IOException e) {
                    LOGGER.error("e:{}", e.getMessage());
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
            // 创建异常处理程序
        }, e -> {
            if (e.response() != null) {
                // 从Telegram收到错误响应
                e.response().errorCode();
                e.response().description();
            } else {
                // 可能是网络错误
                LOGGER.error("e:{}", e.getMessage());
            }
        });
    }
}
