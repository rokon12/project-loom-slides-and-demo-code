## Your Java Code in the Fastlane: Creating a Million Virtual Threads Using Project Loom to Improve Throughput


## Abstract

Project Loom introduces virtual threads, lightweight threads that aim to dramatically reduce the effort of writing, maintaining, and monitoring high-throughput concurrent applications on the Java platform.

We need threads to achieve high throughput. However, the classic threads are not cheap and are limited in number. Various alternatives, such as the reactive programming style, have emerged to get around this problem. Unfortunately, these techniques bypass creating a lot of threads at the expense of more difficult debugging and profiling. This makes developers grumpy. However, we get the best of both worlds with virtual threads: cheap, lightweight threads and easy debugging, which would make developers happy again.

This talk will explore what virtual threads are, how they are implemented, how they solve our modern problems, and what shortcomings there are.

## Bio:

**A N M Bazlur Rahman** works at [Contrast Security](https://www.contrastsecurity.com/) as a Sr. Software Engineer. He has more than ten years of professional experience in the software industry, predominantly in Java and Java-related technologies. Recently, he was honoured by being given the title of [ Java Champion](https://twitter.com/Java_Champions/status/1523728715368509440).

He loves mentoring, writing, speaking at conferences, and contributing to open-source projects outside of his regular work hours. He is the founder and current moderator of the[ Java User Group in Bangladesh](https://jugbd.org/). He has organized meetups and conferences to share Java-related knowledge with the community since 2013. He was named Most Valuable Blogger (MVP) at [DZone](https://dzone.com/users/1298119/bazlur_rahman.html), one of the most recognized technology publishers in the world. Besides DZone, he is an editor for the Java Queue at [InfoQ](https://www.infoq.com/profile/A-N-M-Bazlur-Rahman/), another leading technology content publisher and conference organizer, and an editor at [Foojay.io](https://foojay.io/today/author/bazlur-rahman/), a place for friends of OpenJDK. In addition, he has published [four books](https://www.rokomari.com/book/129165/java-programming) about the Java programming language in Bengali; they were bestsellers in Bangladesh.

He earned his bachelor’s degree from the Institute of Information Technology, University of Dhaka, Bangladesh, in Information Technology, majoring in Software Engineering. He currently lives in Toronto, Canada.


#### _Demo required Java 19 installed on the machine!_