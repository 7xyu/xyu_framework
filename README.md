# xyu_framework
personal use



Step 1. Add the JitPack repository to your build file 
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Copy

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.7xyu:xyu_framework:v1.0.1'
	}

