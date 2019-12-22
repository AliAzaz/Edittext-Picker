# EditTextPicker Library

[![Build Status](https://travis-ci.org/AliAzaz/Edittext-Library.svg?branch=master)](https://travis-ci.org/AliAzaz/Edittext-Library) [![](https://jitpack.io/v/AliAzaz/Edittext-Library.svg)](https://jitpack.io/#AliAzaz/Edittext-Library) [![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditTextPicker-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7671)

Edittext library by which you can easily achieve lot of functionalities though directly implemented some lines of code in XML and on Java side.
Following are the functions that EditTextPicker provides:

  - Empty checking
  - Masking Edittext
  - Pattern checking
  - Range checking

## Description:
Please see the description of this library on my article ***[Edittext Picker Library](https://medium.com/@ali.azaz.alam/edittext-picker-library-4c71ae7d7863)***

## Some Output Screenshots

<img alt="Pic-1" src="https://github.com/AliAzaz/Edittext-Library/blob/master/demo/pic1.png"/> <img alt="Pic-2" src="https://github.com/AliAzaz/Edittext-Library/blob/master/demo/pic2.png"/>

## How to use it??

### Implementation 
In project.gradle add this code it in root build.gradle at the end of repositories:
```sh
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
```sh
Note: In future the new amendments will only reflect on AndroidX support
```

Now, add the dependency in app.gradle:
```sh
dependencies {
    implementation 'com.github.AliAzaz:Edittext-Library:X.X.X'
}
```

## Quick Usage

### XML
Note: By default required is true. But if you don't want to validate specific edittext then simply set it to false: 
***app:required="false"***

-- Required Edittext
```sh
    <com.edittextpicker.aliazaz.EditTextPicker
        android:id="@+id/txtBoxReq"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:required="true" />
```

-- Range (5-10) with default value of 999
```sh
    <com.edittextpicker.aliazaz.EditTextPicker
        android:id="@+id/txtBoxRange"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="number"
        app:defaultValue="999"
        app:maxValue="10"
        app:minValue="5"
        app:required="true"
        app:type="range" />
```

-- Masking
```sh
    <com.edittextpicker.aliazaz.EditTextPicker
        android:id="@+id/txtMask"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="number"
        app:mask="##-##-####"
        app:required="false" />
```

-- Pattern with default value checking [Following pattern is: (2-4)Characters with (3-5)Digits ]
```sh
    <com.edittextpicker.aliazaz.EditTextPicker
        android:id="@+id/txtBoxDefault"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:defaultValue="null"
        app:pattern="[^0-9]{2,4}[0-9]{3,5}"
        app:required="true"
        app:type="equal" />
```

### Java
Implement this code in submit button click

--- For Required component
```sh
    if (!txtBoxReq.isEmptyTextBox())
                return;
```

-- For validating range component
```sh
    if (!txtBoxRange.isRangeTextValidate())
                return;
```

-- For validating pattern component
```sh
    if (!txtBoxDefault.isTextEqualToPattern())
                return;
```


CONNECT👍

Medium: https://medium.com/@ali.azaz.alam

Twitter: https://twitter.com/AliAzazAlam1

Github: https://github.com/aliazaz

LinkedIn: https://www.linkedin.com/in/aliazazalam/


## LICENSE
Distributed under the MIT license. See [LICENSE](https://github.com/AliAzaz/Edittext-Library/blob/master/LICENSE) information.
