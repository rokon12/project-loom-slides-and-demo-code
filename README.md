## Your Java Code in the Fastlane: Creating a Million Virtual Threads Using Project Loom to Improve Throughput


## Abstract

Project Loom introduces virtual threads, lightweight threads that aim to dramatically reduce the effort of writing, maintaining, and monitoring high-throughput concurrent applications on the Java platform.

We need threads to achieve high throughput. However, the classic threads are not cheap and are limited in number. Various alternatives, such as the reactive programming style, have emerged to get around this problem. Unfortunately, these techniques bypass creating a lot of threads at the expense of more difficult debugging and profiling. This makes developers grumpy. However, we get the best of both worlds with virtual threads: cheap, lightweight threads and easy debugging, which would make developers happy again.

This talk will explore what virtual threads are, how they are implemented, how they solve our modern problems, and what shortcomings there are.

## About Me: https://bazlur.ca/about/



#### _Demo required Java 19 installed on the machine!_


Since this is a preview feature, a developer will need to provide the --enable-preview flag to compile this code, as shown in the following command: 

```javac --release 19 --enable-preview Main.java```

The same flag is also required to run the program: 

```java --enable-preview Main```

However, one can directly run this using the source code launcher. In that case, the command line would be:

```java --source 19 --enable-preview Main.java```

The jshell option is also available but requires enabling the preview feature as well: 

```jshell --enable-preview```

