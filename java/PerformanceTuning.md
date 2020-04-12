# JVM tuning flags
  print all flags:  -XX:+PrintFlagsFinal
## Boolean flags
  - enable: -XX:+FlagName
  - disable: -XX:-FlagName

## flags with parameter:
  -XX:FlagName=something

# categories:
- microbenchmark
- macrobenchmark
- mesobenchmarks

# Tools:
-  JMH: similar with JUnit


## CPU Usage (vmstat on linux, typeperf on Window)
- CPU time is the first thing to examine
- The goal in optimizing code is to drive the CPU usage up, not down
- Understand why CPU usage is low before diving in and attempting
to tune an application

## Disk Usage (iostat -xm 5)
  w_await—the time to service each I/O write
  r/s w/s %system
  - Monitoring disk usage is important for all applications.
  - Applications that write to disk can be bottlenecked both
because they are writing data inefficiently (too little
throughput) or because they are writing too much data (too much
throughput).

## Network Usage (netstat/nicstat, typeperf ) <40%

# Java Monitoring Tools
## jcmd
 Prints basic class, thread, and JVM information for a Java
process

## jconsole
Provides a graphical view of JVM activities, including
thread usage, class usage, and GC activities. should run locally
and attach to a remote system

## jmap
Provides heap dumps and other information about JVM memory
usage.

## jinfo
Provides visibility into the system properties of the JVM,
and allows some system properties to be set dynamically.

## jstack
Dumps the stacks of a Java process.

## jstat
Provides information about GC and class-loading
activities.

## jvisualvm
A GUI tool to monitor a JVM, profile a running
application, and analyze JVM heap dumps

## Basic VM Information
1. Uptime:

    jcmd process_id VM.uptime

2. System properties:

    jcmd process_id VM.system_properties

    jinfo -sysprops process_id

3. JVM version
    
    jcmd process_id VM.version

4. JVM command line

     jcmd process_id VM.command_line

5. JVM tuning flags

    jcmd process_id VM.flags [-all]

## Thread Information

jconsole and jvisualvm display information (in real time)
about the number of threads running in an application.

### look at the stack of running threads: 
1. jstack：  jstack process_id

2. jcmd： jcmd process_id Thread.print

## Class Information

Information about the number of classes in use by an
application can be obtained from jconsole or jstat. jstat can
also provide information about class compilation

## Live GC Analysis
- jconsole
- jcmd
- jmap
- jstat

## Heap Dump Postprocessing
- jvisualvm
- jcmd
- jmap

 jvisualvm and Eclipse Memory Analyzer Tool (mat) can analyze the heap dump

# Profiling Tools
## Sampling Profilers
## Instrumented Profilers
## Blocking Methods and Thread Timelines
## Native Profilers
## Java Flight Recorder (JFR)
-XX:+FlightRecorder
java 8: -XX:+UnlockCommercialFeatures  -XX:+FlightRecorder