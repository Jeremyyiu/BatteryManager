<h1> Battery Manager </h1>

Hi there!

This is my capstone project - Battery Manager. This project aims to help users maximise their usage of their battery life. Users can use the app to adjust their settings such as WiFi, bluetooth as well as view battery statistics e.g. battery temperature. Users are also given the option to turn on monochrome mode allowing them to save battery life. However this only affects phones with LED screens, LCD screens on the other hand, still produce the same amount of light and uses a light filter to block out the light. Thus, LCD will still use the same amount of battery in monochrome mode as using colour display. 

Eventually, the app aims to be more automated by being able to observe the user's activities and react accordingly to maximise the battery life using machine learning.

Libraries implemented:
- Butterknife to reduce boilerplate view code.
- Partially implemented Dagger 2 - Will expand this to the entire application later.

<h2> Current Features </h2>

- Users can check their network status - wifi, mobile data, bluetooth, gps, hotspot, airplane mode and their data usage. Users can also toggle these settings accordingly.

- Users can also toggle their brightness manually, switch on/off autobrightness and also switch on/off monochrome mode (This requires adb).

- Users can view their battery statistics - battery life percentage, temperature, voltage, health and technology.
- Users can create geofences and be notified when they enter/leave a geofence.
- Users can choose whether or not to toggle WiFi/bluetooth when entering/leaving a geofence. (Currently affects all geofences)

<h2> Features - working in progress </h2>
- Implement battery saving modes by checking screen state and time.
    - Changing ringtone states - from normal modes to vibrate/silent modes during night time.

<h2> Features to do: </h2>

- Implement battery saving mode e.g. night mode. (Priority).
- Implement battery usage graphs.
- Implement machine learning in battery saving modes! - observe user usage patterns and react accordingly.

<h2> Design stuff to do: </h2>

- Revamp UI - make everything prettier!
- Create Logo
- Fix up UI issue where the header bar remains white

<h2> How to use adb to grant permission to access features such as monochrome mode </h2>
<b>1.</b> Go to Command prompt <br />
<b>2.</b> Go to the location of where your adb.exe is located (For me it was cd C:\Users\*Username*\Appdata\Local\Android\sdk\platform-tools) <br /> 
<b>3.</b> adb -d shell pm grant com.example.jeremy.controller android.permission.WRITE_SECURE_SETTINGS <br />
<b>4.</b>  Now the permission should be granted and you should now be able to switch on and off monochrome mode!  <br />


<h2> License </h2>
MIT License

Copyright (c) 2018 Jeremy Yiu

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
