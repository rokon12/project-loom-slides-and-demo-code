## Your Java Code in the Fastlane: Creating a Million Virtual Threads Using Project Loom to Improve Throughput


## Abstract

Project Loom introduces virtual threads, lightweight threads that aim to dramatically reduce the effort of writing, maintaining, and monitoring high-throughput concurrent applications on the Java platform.

We need threads to achieve high throughput. However, the classic threads are not cheap and are limited in number. Various alternatives, such as the reactive programming style, have emerged to get around this problem. Unfortunately, these techniques bypass creating a lot of threads at the expense of more difficult debugging and profiling. This makes developers grumpy. However, we get the best of both worlds with virtual threads: cheap, lightweight threads and easy debugging, which would make developers happy again.

This talk will explore what virtual threads are, how they are implemented, how they solve our modern problems, and what shortcomings there are.

## About Me: https://bazlur.ca/about/



#### _Demo required Java 19 installed on the machine!_
