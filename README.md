[![Travis](https://img.shields.io/travis/dyng/polling.svg)](https://travis-ci.org/dyng/polling)
[![license](https://img.shields.io/github/license/dyng/polling.svg)](https://raw.githubusercontent.com/dyng/polling/master/LICENSE)

# What Is This

Polling is a library to help you implement your own code periodically, having full control on *when to abort*, *how long to wait between each attempt*, *how to handle exception*, and more.

Let's take as simple an example as **polling a remote API for a short message**.

In vanilla java, the code looks like

```java
public String fetchMessage() throws InterruptedException {
    int failCnt = 0;
    for (;;) {
        // quit if failed for more than 3 times
        if (failCnt >= 3) {
            throw new IllegalStateException("Failed to fetch message");
        }

        try {
            Response resp = fetchMessageFromRemote();
            if (resp.isSuccess()) {
                // success
                return resp.getMessage();
            }
        } catch (Exception e) {
            // log a failure
            System.out.println("Request failure");
        }

        failCnt++;

        // sleep 1 second before next try
        try {
            Thread.sleep(1000); // sleep 1 second
        } catch (InterruptedException e) {
            // quit if interrupted
            System.out.println("Interrupted");
            throw e;
        }
    }
}
```

With Polling, the code can be simplified into

```java
public String fetchMessage() {
    return Polling
            .waitPeriodly(1, TimeUnit.SECONDS)
            .stopAfterAttempt(3)
            .run(new AttemptMaker<String>() {
                @Override
                public AttemptResult<String> process() throws Exception {
                    Response resp = fetchMessageFromRemote();
                    if (resp.isSuccess()) {
                        return AttemptResults.finishWith(resp.getMessage());
                    } else {
                        return AttemptResults.justContinue();
                    }
                }
            });
}
``` 

You can see **the code is reduced to 70%**, and more clear, easier to read.

The power of Polling is even more than above example. Many builtin `StopStrategy` and `WaitStrategy` are available out of box, with them you can do *random waiting*, *fibonacci waiting*, *stop after a given period*, etc.

## Feature

- No Dependency!
- Flexible polling either in current Thread or a dedicated Thread/ExecutorService.
- Java 1.7+ supported.

# How To Install

## Maven

```xml
<dependency>
    <groupId>com.dyngr</groupId>
    <artifactId>polling</artifactId>
    <version>1.1.3</version>
</dependency>
```

# Thanks

Polling is heavily inspired by [guava-retrying](https://github.com/rholder/guava-retrying), which is original written by Ryan Holder (rholder). The implementation of many `StopStrategy` and `WaitStrategy` are also migrated from [guava-retrying](https://github.com/rholder/guava-retrying). Thanks for his and other contributors' pioneer work.
