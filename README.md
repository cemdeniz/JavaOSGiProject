# JavaOsgiProject

Java SE version = 1.8,
Java Sdk version = 8,
Recommended IDE = Eclipse

Overall, the application is composed of two OSGi services: one for translating numbers to words and words to numbers, and another for the user interface and basic math operations. 
The two services will communicate via interfaces defined in their respective bundles, and the user interface service will listen for changes to the active language preference and update the user interface accordingly. 
The application is be able to support Turkish and English languages, with Turkish being the default. Java Swing is used for the user interface and integer arithmetic for the math operations.

![osgiApp](https://user-images.githubusercontent.com/29862339/222442867-f80053f8-7994-4970-8518-5f40f3fb019a.png)

How to import?
	In Eclise IDE;
		-File -> Import -> select Git->Projects from Git -> Clone URl -> copy and paste this repositoriy's name(https://github.com/cemdeniz/JavaOsgiProject) into "URl: " field and click next until finish.

How to run?
	Right click CalculatorService -> Run As -> OSGi Framework
 
 
Possible Error fixes!
	"Unbound classpath container:" error;
		-Right click CalculatorService -> Properties -> Java Build Path -> in Libraries tab, double click your "JRE System Library" and select "Workspace default JRE".
	
	If you're missing "Eclipse Plugin Development Tools";
		-follow  this thread : https://stackoverflow.com/questions/46197398/eclipse-platform-plug-in-developer-guide-help-section-missing
	
	

