# california-transfer-courses
A script to look up course requirements for California community college students yearning to transfer UC (University of California)

## Who should use this?
You are a community college student in California, and you are working you way to apply to UCLA, UCSB, UCSD and UCB. You want to know what courses are required to make sure you cover all course requirements of these four UC schools you desire. This script can offer some help.

## How does it work?
If you are a California community college student trying to transfer to UC, then you should already be very familiar with the [ASSIST](http://www.assist.org/web-assist/welcome.html) system. It can help you quicktly find out what you need to get to, say **UCLA**, from, say **Ventura College**. If you happend to major in **Mechanical Engineering**, then you should something like this:

![](https://www.dropbox.com/s/8d03ypdpjd0g73d/Screenshot%202018-06-13%2018.04.09.png?raw=1)

as shown in [this link](http://web2.assist.org/web-assist/report.do?agreement=aa&reportPath=REPORT_2&reportScript=Rep2.pl&event=19&dir=1&sia=VENTURA&ria=UCLA&ia=VENTURA&oia=UCLA&aay=16-17&ay=16-17&dora=MECH+ENG).

This script provides more help when you have more than one target UC schools. If you are trying to get from Ventura College to UCLA, UCSB, UCSD and UCB, then you can copy all the text results in webpages regarding the four schools together into one text file called `original.txt`, as shown in the current folder. View it to see understand how it's produced.

Then you run:
```
$ javac Foo.java
$ java Foo
```
This will produce an `output.csv` sheet that will list each of your Ventura College courses, together with the target schools that require equivalents of this school, and how that school calls this course.  
All course entries are listed from most demanded (more of your target universities need this course) to lower demanded.

## Disclaimer
This scirpt is a dirty and quick job. It is not perfect. For example, UCB provides a long list of courses where you only need to select one out of. I omitted, to avoid clutter, all these courses in the CSV sheet and it would be easier for you just to refer to the ASSIT result for the list, of which you only need to select one.  
Similarly, UCLA allows *OR* options, as in Course A in UCB can be fulfilled by one of multiple courses in Ventura College. It is possible to address this problem with some tweaks, but I don't think that is very necessary. All of the fulfilling alternatives are listed separetely. After all, there is no harm in taking more courses `:)`.

Please use this script to your own caution and discretion:
* This script's result is only meant to assist the process of course requirements tracking. You should always refer to the UC's official admission information for definite and updated guidance.
* If you come across more problems, you can submit an issue. I might or might not fix that. All the UCs use different formats makes me bored and I do not feel like catering to all of them.