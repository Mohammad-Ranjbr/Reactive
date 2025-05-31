package com.example.reactorprogrammingplayground.common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public class AbstractHttpClient {

    //If it were private, it could only be used within the AbstractHttpClient class itself.
    //With protected, it could be used both within the base class itself and in subclasses.
    protected final HttpClient httpClient;
    private static final String BASE_URL = "http://localhost:8080";

    public AbstractHttpClient() {
        var loopResources = LoopResources.create("vins", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }

    //Creates a dedicated Event-Loop named vins-… threads.
    //Thread Naming ("vins") is for identification in logs only. You can give any other prefix.
    //The second parameter (= 1) means create only one Worker Thread (to demonstrate the power of Non-Blocking I/O).
    //The third parameter (true) means the threads are Daemons; we don't need to stop them manually when the JVM terminates.
    //Daemon Threads do not require manual dispose() or shutdown()
    //HttpClient.create() creates an empty Netty HttpClient Reactor.
    //.runOn(loopResources) tells the Client to run all connections and I/O operations on this Event-Loop.

    //When the thread is Daemon (true):
    //When it has something to do, it does its work.
    //But if the only threads left in the JVM are Daemon Threads (and no main or user threads), the JVM says: "Well, the program is finished", and it automatically closes all Daemon Threads.
    //So: No, the Daemon Thread itself does not close automatically after it is done.
    //Rather, it is only destroyed when the JVM decides to go down (for example, the main thread ends).
    //When the thread is not Daemon (false):
    //These threads (User Threads) keep the JVM alive.
    //Even if there is nothing else to do in the program, the JVM will not go down because of these threads.
    //So you must close the thread yourself, for example with loopResources.dispose() or disposeLater().block().

    //Main goal: Better control over Thread Pool and resource management
    //Normally, when you use HttpClient.create() and send requests directly, Netty uses its own default thread pool (called eventLoopGroup).
    //But with LoopResources.create(...):
    //You create a dedicated thread pool:
    //This gives you full control over the backend threads (like IO, connection handling, etc.).
    //You can specify exactly:
    //How many threads it will have (e.g. 1 thread in the example above)
    //What the thread names will be ("vins", e.g. vins-1-1, vins-1-2, ...)
    //Whether the threads are Daemons or not
    //What happens if we don't create one?
    //HttpClient still works, but:
    //It uses a shared event loop group.
    //This is fine for small applications, but in large applications (such as microservices or high-load systems), it puts pressure on shared resources.

}
