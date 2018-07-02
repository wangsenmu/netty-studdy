package com.wsm.reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by wangsm on 2018/7/2.
 */
public class Acceptor implements Runnable {

    private Reactor reactor;

    public Acceptor(Reactor reactor) {
        this.reactor = reactor;
    }

    public void run() {
        try {
            /*
             * ServerSocketChannel可以设置成非阻塞模式。
             * 在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。
             * 因此，需要检查返回的SocketChannel是否是null.
             */
            SocketChannel socketChannel = reactor.serverSocketChannel.accept();

            /*
             * 调用Handler来处理channel
             * 在SocketReadHandler构造方法中将socketChannel register到Selector,返回selectionKey
             * 再将该socketChannel的selectionKey attach(this); this represent new出来的SocketReadHandler
             */
            if (socketChannel != null) new SocketReadHandler(reactor.selector, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
