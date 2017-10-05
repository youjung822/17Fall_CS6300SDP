# Design Document

*This is the template for your design document. The parts in italics are concise explanations of what should go in the corresponding sections and should not appear in the final document.*

**Author**: John Youngblood (jyoungblood8)

## 1 Design Considerations

*The subsections below describe the issues that need to be addressed or resolved prior to or while completing the design, as well as issues that may influence the design process.*

* How will the scramble() operation, outlined in Requirements 8 and 9, be implemented in Java.
* How exactly the External Web Service's operation work, assuming the EWS is provided to us.

### 1.1 Assumptions

*Describe any assumption, background, or dependencies of the software, its use, the operational environment, or significant project issues.*

* The External Web Service outlined in Requirement 4 will be provided to us.

### 1.2 Constraints

*Describe any constraints on the system that have a significant impact on the design of the system.*

* The application must run with a minimum Android API Level of 19

### 1.3 System Environment

*Describe the hardware and software that the system must operate in and interact with.*

* Our application will be developed for Android with a minimum target of API Level 19.

## 2 Architectural Design

*The architecture provides the high-level design view of a system and provides a basis for more detailed design work. These subsections describe the top-level components of the system you are building and their relationships.*

### 2.1 Component Diagram

*This section should provide and describe a diagram that shows the various components and how they are connected. This diagram shows the logical/functional components of the system, where each component represents a cluster of related functionality. In the case of simple systems, where there is a single component, this diagram may be unnecessary; in these cases, simply state so and concisely state why.*

* Our system is comprised of just a single APK that will run on an Android device.

### 2.2 Deployment Diagram

*This section should describe how the different components will be deployed on actual hardware devices. Similar to the previous subsection, this diagram may be unnecessary for simple systems; in these cases, simply state so and concisely state why.*

* Our system is comprised of just a single APK that will run on an Android device.

## 3 Low-Level Design

*Describe the low-level design for each of the system components identified in the previous section. For each component, you should provide details in the following UML diagrams to show its internal structure.*

### 3.1 Class Diagram

*In the case of an OO design, the internal structure of a software component would typically be expressed as a UML class diagram that represents the static class structure for the component and their relationships.*

![team44](https://github.gatech.edu/gt-omscs-se-2017fall/6300Fall17Team44/blob/master/GroupProject/Design-Team/images/team44Design.png?raw=true "Team design")

### 3.2 Other Diagrams

*<u>Optionally</u>, you can decide to describe some dynamic aspects of your system using one or more behavioral diagrams, such as sequence and state diagrams.*

## 4 User Interface Design
*For GUI-based systems, this section should provide the specific format/layout of the user interface of the system (e.g., in the form of graphical mockups).*

[This was generated using Balsamiq.](../GUI_Mockup.pdf) Embedded images to come.