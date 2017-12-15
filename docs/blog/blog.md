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

~~Setup Infura test network~~

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

~~Complete first draft of Specification~~
### `To-Do`

- Refine Functional Specifiacction (due next Friday)
- Investigate Uport and Web3j

---

## `Wednesday November 22, 2017`

I met with my supervisor yesterday to review the Functional spec, he was happy except I was missing hardware requirements. I finished work on the spec today by completing the schedule, high level design and fixing all the formatting.

### `Done`

~~Functional Spec~~

### `To-Do`

- Have hardware in hand for beginning of next semester
- Complete tutorials over the break to learn solidity and hooking an app to a blockchain network.

---

## `Friday December 15th 2017`

This last few weeks I have been very busy with assignments and have been researching what type of hardware I will need. Hoping to order soon so I will have it for the end of January.

I have also found an online book being developed by the Ethereum community on github as a comprehensive knowledge base for developing with Ethereum and have begun reading it. I will read this over the Christmas period.

[Ethereum Builder's Guide](https://ethereumbuilders.gitbooks.io/guide/content/en/index.html)

---


## ....
##### Done
##### To-Do
## ....
##### Done
##### To-Do


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
