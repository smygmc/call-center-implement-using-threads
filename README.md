# call-center-implementation-using-threads
An assignment I made for imitating a call center operation using threads and semaphores in Java.
---INSTRUCTIONS---
There is a phone operation station with two ladies working in. The duty of these ladies is to establish connections between people trying to call each other on two sides of a city. Everything in this station is analogue; there are only two physical cables to make people talk. This means that only two phone calls may occur at the same time.

To make a phone call, you first have to catch a lady idle. If she is idle and one of the lines is also free, she can make you talk to your friend. If the ladies or the lines are busy, you have to wait until you catch first the lady, then the line respectively. There are 6 friends on this side of the city (say side A), trying to talk to their 6 common friends on the other side of the city (say side B).

Write a program to simulate the phone calls. Since at most two people can make a phone call with their friends at the same time, the program ends when each of 6 people on side A have talked to 6 people on side B (which makes 36 phone calls). Phone calls should always be established from side A to side B (do not design your solution in a two-direction manner).
