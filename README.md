# DemoOfEverything 

This repository contains some example code for some specific features with Java.

And these code are based on JDK1.8.

### [DemoExportSomethingToExcel]

This demo implements how to write something to an Excel file, and 
the following dependency needed to be added in pom.xml file firstly 
if you build project with [Maven].
```xml
<dependency>
    <groupId>net.sourceforge.jexcelapi</groupId>
    <artifactId>jxl</artifactId>
    <version>2.6.12</version>
</dependency>
```

### [DemoOfSerializable]

This demo shows the serialization and deserialization.


### [DemoOfException]

This demo shows the difference between RuntimeException and Non-RuntimeException.


### [DemoOfES]

This demo shows the basic operation of ElasticSearch.


### [DemoOfFutureTask]

FutureTask is base concrete implementation of Future interface and provides asynchronous processing. 
It contains the methods to start and cancel a task and also methods that can return the state of the 
FutureTask as whether it’s completed or cancelled. We need a callable object to create a future task 
and then we can use [Java Thread Pool Executor] to process these asynchronously.This demo shows the 
example of FutureTask with a simple program.


[Maven]: https://maven.apache.org/
[DemoExportSomethingToExcel]: https://github.com/WangChengCheng/DemoOfEverything/blob/master/src/main/java/DemoExportSomethingToExcel.java
[DemoOfSerializable]: https://github.com/WangChengCheng/DemoOfEverything/blob/master/src/main/java/DemoOfSerializable.java
[DemoOfException]: https://github.com/WangChengCheng/DemoOfEverything/blob/master/src/main/java/DemoOfException.java
[DemoOfES]: https://github.com/WangChengCheng/DemoOfEverything/blob/master/src/main/java/com/southeastideas/elasticsearch/DemoOfES.java
[DemoOfFutureTask]: https://github.com/WangChengCheng/DemoOfEverything/blob/master/src/main/java/DemoOfFutureTask.java
[Java Thread Pool Executor]: https://www.journaldev.com/1069/threadpoolexecutor-java-thread-pool-example-executorservice