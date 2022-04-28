# JsonReport
Library to print Json report for instrumented test
This is a beta version that allows to print report of instrumented (Android Test) Test in a json File.

The file is saved inside the emulator in the App external storage and the name is JsonTestReport.json

It is sufficient to implement the interface GlobalTWClass()

Example: 

    class ExampleInstrumentedTest: GlobalTWClass()

To implement the library in the project it needs to:
1 - Add this line in settings.gradle :

    repositories {
    ...
    maven { url 'https://jitpack.io' }
    ...
    }
    
2 - Implement the dependency in module Build.gradle:

    implementation 'com.github.giuseppegargani:JsonReport:0.0.1'

