# Delock Blog

## `Monday November 13, 2017`

I had my first meeting with my supervisor David Sinclair last week on Tuesday the 7th. Since then I have written a first draft of my Functional Specification, I have another meeting with David tomorrow in which we will review the draft.

---

## `Tuesday November 14, 2017`

I had my second meeting with David today, in the run up to this meeting I was working on the Functional Spec and created a number of detailed Use cases. This helped me emmensly in that I now have a much clearer idea of how the system will function and what I will need to do to implement it.

At the moment, I feel the best stack for developing the DApp will be:

1. Web3j (Android client for communicating with the blockchain)
2. Truffle (testing smart contracts)
3. Infura (Will host a network of nodes that will act as my blockchain network)

### `Done`

- ~~Setup Infura test network~~

### `To-Do`

- Complete full draft of Functional Spec
- Download and learn how to use Web3j

---

## `Friday November 17, 2017`

I spent this week working on a draft of the Functional Specification, I have the majority of the work done on the spec and it will just be a matter of cleaning it up and refining my diagrams next week. I also found he Uport library, it seems like it could be a good option for handling user authentication within the application. I will look into this further next week.

Drafted sketches of some spec diagrams:
![specDiagram](https://gitlab.computing.dcu.ie/mcadamm4/2018-ca400-mcadamm4/tree/master/docs/blog/images/specDiagram1.jpg)
![specDiagram2](https://gitlab.computing.dcu.ie/mcadamm4/2018-ca400-mcadamm4/tree/master/docs/blog/images/specDiagram2.jpg)
![specDiagram3](https://gitlab.computing.dcu.ie/mcadamm4/2018-ca400-mcadamm4/tree/master/docs/blog/images/specDiagram3.jpg)

### `Done`

- ~~Complete first draft of Specification~~

### `To-Do`

- Refine Functional Specifiacction (due next Friday)
- Investigate Uport and Web3j

---

## `Wednesday November 22, 2017`

I met with my supervisor yesterday to review the Functional spec, he was happy except I was missing hardware requirements. I finished work on the spec today by completing the schedule, high level design and fixing all the formatting.

### `Done`

- ~~Functional Spec~~

### `To-Do`

- Have hardware in hand for beginning of next semester
- Complete tutorials over the break to learn solidity and hooking an app to a blockchain network.

---

## `Friday December 15th 2017`

This last few weeks I have been very busy with assignments and have been researching what type of hardware I will need. Hoping to order soon so I will have it for the end of January.

I have also found an online book being developed by the Ethereum community on github as a comprehensive knowledge base for developing with Ethereum and have begun reading it. I will read this over the Christmas period.

[Ethereum Builder's Guide](https://ethereumbuilders.gitbooks.io/guide/content/en/index.html)

---

## `Tuesday February 13th 2018`

Over the break I ordered and received all of the parts I need to make the locking mechanism including a "Keyduino" which is a specialized Arduino Leonardo with NFC built in. I also pracitced some React Native by building an app.

### `Done`

- ~~Get parts~~

### `To-Do`

- Build react native application connected to Ethereum network
- Build Android application to communicate with Arduino
- Integrate these two applications into one

---

## `Wednesday February 22nd 2018`

So far I have built a simple application that uses the "Ethereum Android" wallet to connect to the ethereum blockchain behind the scenes. Seperate to that I have a simple React native application that shows listings for houses. My next step will be to try and integrate the functionality of both of these applications. 

I have also been thinking about using Firebase for storing details about listings and other things rather than IPFS.

### `Done`

- ~~Build react native application~~ 
- ~~Build app connected to Ethereum network~~

### `To-Do`
- Integrate react native app with ethereum app
- Research UPort for authentication if needed
- Research Firebase for use as storage rather than IPFS
- Integrate these two applications into one


## `Sunday March 4th`
Over the last week and a half, I was considerng making a lot of design changes which I unfortunelty forgot to document here. The basic problem was doubt about handling of user accounts thorugh the Ethereum Android wallet, I was planning on switching to the UPort framework and instead of having NFC enabled arduinos tht would unlock the items there would be a QR code stciker and when scanned with your phoned camera would provide you with a pin for unlocking the item.
The QR code part would work but UPort would not ork with mobile.

I ruled out these changes and carried on with my original design, hwoever I have decided not to use React Native as the workload and learning needed integrate Native Java code required for Ethereum Android with React Natives Javascript outweighs the benefit of using the frameowkr, seeing as I wont be targeting IOS I can focus on developing primarily for android.

From now on I will have more frequent and less verbose blogs documenting more granular decisions, at the moment I have a native android application that uses the Ethereum Android library to communicate with Ethereum through the Ethereum Android application.

I am now researching integration of IPFS, I cannot use Firebase as previously mentioned as this is at odds with the goal of totl decentralization.

I have also started developing a testing plan.






---


---


---


---



This week, I learned how to include
[images](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#images)
in my blog.

![cat](https://gitlab.computing.dcu.ie/sblott/2018-ca400-XXXX/raw/master/docs/blog/images/cat.jpg)

Here are the instructions:

- Add the image to your repo (probably using the `images` sub-directory here).
  The cat example above is in `./images/cat.jpg`.

- Commit that and push it to your repo.

- On Gitlab, navigate to your new image and click *Raw*.  You get the raw URL of your image.  Copy that URL.

- Add your image to this document using the following format:

    <pre>![alternative text](URL)</pre>

See the example [here](https://gitlab.computing.dcu.ie/sblott/2018-ca400-XXXX/raw/master/docs/blog/blog.md).

You can also mention other users (like me: @sblott).

## Including Code

Raw text:
```
Mary had a little lamb,
it's fleece was white as snow.
```

Syntax highlighting is also possible; for example...

Python:
```python
i = 0
while i < len(s):
   # So something.
   i = i + 1
```

Java:
```java
for (i=0; i<s.length(); i+=1) {
   // Do something.
}
```

Coffeescript:
```coffeescript
i = 0
while i < s.length
   # So something.
   i = i + 1
```

## Instructions

Once you've understood this sample, replace it with your own blog.
