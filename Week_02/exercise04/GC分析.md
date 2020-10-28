1. 系统环境
-----------------

2. 分析维度
-----------------

GC 内存分配 维度为  

216m   

512m   

1024m

分析的垃圾搜集器有

UseSerialGC 

UseParallelGC

UseConcMarkSweepGC

3. 具体分析
-----------------

UseSerialGC 串行GC

单线程

Minor GC: 使用  Serial收集器串行回收 （新生代） 复制算法

Full GC  : Serial Old收集器串行回收（老年代）标记-整理算法

特点

**当进行垃圾收集时必须暂停其他线程的所有工作，直到它收集结束为止**

java -Xms216m -Xmx216m  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseSerialGC GCLogAnalysis

|          | 216m                              | 512m                         | 1024m                     |
| -------- | --------------------------------- | ---------------------------- | ------------------------- |
| GC次数   | 9次 （minor gc)  -> / 20(full gc) | 8次（minor gc)               | 5 （minor gc)             |
| Eden区   | 59008K    /    100% used          | 139776K    /    4% used      | 279616K    /   4% used    |
| S1(from) | 7360K      /    97% used          | 17472K      /    99% used    | 34944K      /     100%    |
| S2(to)   | 7360K      /    0% used           | 17472K      /    0% used     | 34944K      /     0%      |
| Old      | 147456K  /    99% used            | 349568K    /        96% used | 699072K    /     52% used |
| 运行时间 | 1000 ms                           | 1000 ms                      | 1000 ms                   |
| 生成对象 | OOM                               | 5313                         | 6907                      |



UseParallelGC  并行GC

Minor GC: 使用 Parallel收集器并行回收 （新生代） 复制算法

Full GC  : Serial Old收集器串行回收 （**Serial收集器的老年代版本**） 单线程收集器，标记-整理

java -Xms216m -Xmx216m  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:UseParallelGC GCLogAnalysis

|          | 216m                     | 512m                         | 1024m                     |
| -------- | ------------------------ | ---------------------------- | ------------------------- |
| GC次数   | 10m->26f                 | 10m ->1f->4m->1f->3m->1f->1m | 4m                        |
| Eden区   | 24576K    /    100% used | 58880K    /    5% used       | 262144K    /   4% used    |
| S1(from) | 24576K      /    0% used | 57856K      /    36% used    | 43520K      /     99%     |
| S2(to)   | 24576K      /    0% used | 57856K      /    0% used     | 43520K      /     0%      |
| Old      | 147456K  /    99% used   | 349696K    /        84% used | 699392K    /     36% used |
| 运行时间 | 1000 ms                  | 1000 ms                      | 1000 ms                   |
| 生成对象 | OOM                      | 5647                         | 6907                      |

CMS

Minor GC: 使用  ParNew收集器（**Serial收集器的多线程版本**）并行回收  （新生代） 复制算法

Full GC  : 使用CMS收集器并发回收（老年代）初始标记—并发标识—重新标记—并发清除



java -Xms216m -Xmx216m  -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseConcMarkSweepGC GCLogAnalysis



|          | 216m                     | 512m                        | 1024m                     |
| -------- | ------------------------ | --------------------------- | ------------------------- |
| GC次数   |                          |                             |                           |
| Eden区   | 59008K    /    100% used | 139776K    /    6% used     | 272640K    /   4% used    |
| S1(from) | 7360K      /    21% used | 17472K      /    99% used   | 34048K      /     99%     |
| S2(to)   | 7360K      /    0% used  | 17472K      /    0% used    | 34048K      /     0%      |
| Old      | 147456K  /    99% used   | 349568K    /       95% used | 707840K    /     50% used |
| 运行时间 | 1000 ms                  | 1000 ms                     | 1000 ms                   |
| 生成对象 | 2964                     | 4241                        | 5184                      |



1. 分代搜集
2. 新生代 主要使用 复制算法
3. 老年代主要是用标记整理算法
4. 单线程搜集器 **进行垃圾收集时必须暂停其他线程的所有工作，直到它收集结束为止**

