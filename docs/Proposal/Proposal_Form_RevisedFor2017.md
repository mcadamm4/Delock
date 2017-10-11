**School of Computing**

**Year 4 Project Proposal Form**

**SECTION A**

Project Title: Rental System based on Blockchain

Student Name: Mark McAdam

Student ID: 14566803 Stream: CASE

Project Supervisor Name
\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**General area covered by project**

My proposal is for a smart lock system based on the Ethereum platform. The
system would allow the renting and sharing of goods and services which were
traditionally very hard to monetise effectively. The main implementation would
be smart locks for equipment allowing people to rent personal effects to others
who pay a deposit and unlock the items via NFC or Bluetooth using their
smartphone.

Owners create a “profile” for their items. This profile includes pictures and a
description of the condition of the item as well as defining a deposit amount,
rental price, availability and pickup/drop-off areas for those renting the item.

The renter of the item will be able to view nearby items for rent and book them
for a period of time, if they use the item outside this specified time then a
surplus is added to the rental price depending on what terms the owner has set
out.

**Background**

The idea for the system stems an article I read some time ago. A company was
working on implementing a blockchain based system to allow owners of autonomous
cars to send their cars out at night while they were sleeping to act as taxis
for others, basically earning a person money as they sleep. This is a phenomenal
idea and has countless applications in other areas.

**Achievements**

The system would be open to anyone who wants to avail of a good or service for
short to medium terms, a person would have to verify their identity when signing
up to the system by providing personal details or depending on resources and
time available for building the system they could send a photo of an ID as well
as a photo of themselves to have their account verified similar to systems used
by some cryptocurrency exchanges.

The system would allow owners to set a deposit for use of the product as well as
a cost price for hourly, daily or weekly use, this is at the owner’s discretion.
They create a profile for the good or service they want to rent and in doing
this they define a number of conditions such as the availability period, the
radius within which it may be used and returned to when a renter has finished.

The owner locks the item with a specialised lock. Assuming they meet all the
conditions specified by the owner and pay the deposit, a renter can book the
item. This booking and payment is recorded as a transaction on the blockchain,
when the renter arrives they can simply tap their phone to the lock and it will
verify they are who they say they are by cross-referencing information provided
via NFC with that on the blockchain.

The lock will disengage and the renter is then free to open/close that lock as
many times as they want until they return the item. The user returning the item
will also be processed and recorded as a block on the blockchain but
interactions with the lock in between will just be simple and quick verification
processes.

When they return the item, the renters deposit is returned to them and they are
debited the total rental cost.

The owner then receives their payment from the user.

**Justification**

A Use Case very much applicable to Ireland due to the size of the farming
industry here would be the rental of farming equipment:

Farmers could avail of equipment owned by other farmers, this means small scale
farmers get access to high quality equipment and the owners find a way to
monetise equipment that only sees about one days use every year.

As well as this, co-ops and plant hire business that already own fleets of
equipment can switch to this system and allow farmers to use their equipment and
the system will manage itself.

This allows farmers to avoid buying very expensive equipment and for those who
do, they can earn money from said equipment and curb the cost of their
investment.

With minimal adaptation the system would cope with all kinds of other use cases,
for proof of concept this project will deal primarily with bicycle locks.

**Programming Languages**

C++ client running on a device such as an Arduino, Samsung Artik or Intel
Edison.

Ethereum blockchain platform.

**Learning Challenges**

Creating a blockchain.

Running clients on the locks that can communicate with each other.

**Hardware**

NFC or Bluetooth

GPS

Devices for locks i.e. one of the following Arduino, Samsung Artik or Intel
Edison.

Ethereum blockchain platform

**Proposal Guidelines**

**[Note: It is the student’s responsibility to ensure that the Supervisor
accepts your project and this is only recognised once the Supervisor assigns
herself/himself via the project dashboard. Project proposals without an assigned
Supervisor will not be accepted for presentation to the Approval Panel.]**

**SECTION B**

Proposal Description – *using the following headings*:

-   General area covered by the project

-   Outline of the proposed project

    -   Background - where the ideas came from

    -   Achievements - what functions it provides, who the users will be

    -   Justification - why/when/where/how it will be useful

-   Programming language(s) - List the proposed language(s) to be used

-   Programming tools / Tech stack – e.g. compiler, database, web server, etc.

-   Learning Challenges - List the main new things (technologies, languages,
    tools, etc) that you will have to learn

-   Hardware / software platform - State the hardware and software platform for
    development

-   Special hardware / software requirements - Describe any special
    requirements.

Make use of figures / diagrams where appropriate.

**Note:** The final revision of your proposal form should be converted to a
**PDF** in your GitLab repo from where it will be automatically collected.
