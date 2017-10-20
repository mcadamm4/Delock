**School of Computing**

**Year 4 Project Proposal Form**

**Project Title:** Delock (Rental System based on Blockchain)

**Student Name:** Mark McAdam

**Student ID:** 14566803 **Stream:** CASE

**Project Supervisor Name:** David Sinclair

**General area covered by project**

My proposal is for a smart lock system based on the Ethereum Blockchain
platform. An Ethereum network consists of series of smart contracts or
autonomous digital entities that can talk to and transact with each other. These
entities are based on blockchain technology so they benefit from its properties
such as immutability i.e. a third party cannot arbitrarily change a smart
contract on the network. All nodes on the network must agree before changes can
take effect. Another advantage is the possibility for zero downtime due to the
decentralized architecture of the system.

In essence, this system would allow everyday object to run code. The system
would allow the renting and sharing of objects which were traditionally very
hard to monetise effectively. The main implementation would be smart locks for
equipment allowing people to rent objects to others who pay a deposit and unlock
the object via Bluetooth using their smartphone.

**Background**

The idea for the system stems an article I read some time ago. A company was
working on implementing a blockchain based system to allow owners of autonomous
cars to send their cars out at night while they were sleeping to act as taxis
for others, basically earning a person money as they sleep. This is a phenomenal
idea and has countless applications in other areas.

**Achievements**

The system would be open to anyone who wants to avail of a good or service for
short to medium terms, a person would have to verify their identity when signing
up to the system.

Owners create a “profile” for their objects listing relevant details about the
object which includes images and a description of the condition of the object as
well as defining a deposit amount, rental price, availability and an acceptable
drop-off radius upon return of the object. The owner locks the item with a
specialised lock and they can then trust the object to manage itself from there
on.

Potential renters open the Delock app, select an object they want to rent from a
list. The app will then read the blockchain to find the smart contract related
to the object selected by the user, when the user makes a payment they
essentially pay the lock itself.

When the user arrives and tries to unlock the object, they pair with the lock
via Bluetooth. The lock will then search the blockchain to find its own smart
contract reference on the chain, it will then cross-reference data from the user
with data stored on the blockchain. If the details match then the lock will
disengage and the user can remove it.

The lock will disengage and the renter is then free to open/close that lock as
many times as they want until they return the item. The user returning the item
will also be processed and recorded as a block on the blockchain but
interactions with the lock in between will just be simple and quick verification
processes.

When they return the item, the renters deposit is returned to them and they are
debited the total rental cost.

The owner then receives their payment from the user.

If renters keep the item outside the pre-agreed time frame then a surplus is
added to the rental price depending on what terms the owner has set out.

**Justification**

A Use Case very much applicable to Ireland due to the size of the farming
industry here would be the rental of farming equipment:

The Delock system would facilitate sharing of equipment between farm owners, as
a result, small scale farmers get access to high quality equipment which in turn
will increase yield from their crops and livestock, simultaneously the equipment
owners have a new revenue stream and an effective means by which to monetise
equipment that traditionally lay idle for much of the year.

Co-operatives and Plant hire business that already own fleets of equipment would
benefit from Delock by handing the bulk of the management over to the equipment
itself.

Delock would give small scale farm owners access to the best equipment and
encourage others to invest in new equipment knowing they have a new stream of
revenue to curb the cost.

With minimal adaptation, the system would cope with all kinds of other use
cases, for proof of concept this project will deal primarily with bicycle locks.

**Programming Languages**

*Android App*

-   Java

-   Javascript (React Native)

-   Ethereum Android

*Arduino*

-   C++ client

**Learning Challenges**

-   Running a blockchain client on a phone

-   Programming an Arduino or similar device

-   Using Arduino to control locks

-   Exchanging data via Bluetooth

**Hardware**

-   Bluetooth

-   Devices for locks i.e. one of the following Arduino, Samsung Artik or Intel
    Edison.
