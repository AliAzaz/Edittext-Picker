# EditTextPicker/Masked-Edittext Library

[![Build Status](https://travis-ci.com/AliAzaz/Edittext-Picker.svg?branch=master)](https://travis-ci.org/AliAzaz/Edittext-Library) [![](https://jitpack.io/v/AliAzaz/Edittext-Library.svg)](https://jitpack.io/#AliAzaz/Edittext-Library) [![API](https://img.shields.io/badge/API-15%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=15)
 [![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EditTextPicker-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7671)

Edittext library by which you can easily achieve a lot of functionalities by directly implementing some lines of code in XML and on the Java side.
The following are the functions that EditTextPicker provides:

  - Empty checking
  - Masking Edittext
  - Pattern checking
  - Range checking

## Description:
Please see the description of this library in my article ***[Edittext Picker Library](https://medium.com/@ali.azaz.alam/edittext-picker-library-4c71ae7d7863)***

## Result

<img alt="pic-1" src="https://github.com/AliAzaz/Edittext-Library/blob/master/demo/pic1.png" width="220" height="460"/> &nbsp; <img alt="pic-2" src="https://github.com/AliAzaz/Edittext-Library/blob/master/demo/pic2.png" width="220" height="460"/> &nbsp; <img alt="pic-3" src="https://github.com/AliAzaz/Edittext-Library/blob/master/demo/pic3.png" width="220" height="460"/>


## Implementation 
In project.gradle add this code it in root build.gradle at the end of repositories:
```sh
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
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

-- Range (5-10) with a default value of 999
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

### Java/Kotlin
Implement this code in submit button click

--- For Required component
```sh
if (!txtBoxReq.isEmptyTextBox()) return;
```

-- For validating range component
```sh
if (!txtBoxRange.isRangeTextValidate()) return;
```

-- For validating pattern component
```sh
if (!txtBoxDefault.isTextEqualToPattern()) return;
```

## Support Builder Pattern
This library also support chain request

```sh
txtPicker = EditTextPicker(this,
	     EditTextPickerBuilder().apply {
                setRequired(true)
                setRangeValues(0.5f, 40.0f)
                setMask("##.##")
                setPattern("^(\\d{2,2}\\.\\d{2,2})$")
          }.build())
          .apply {
                hint = "##.##"
                inputType = InputType.TYPE_CLASS_NUMBER
          }
```

## Set values at Run time

Users can easily set the attribute values at runtime.
```sh
txtDate.setMask("##-##-####").setRequired(false)
```
<br>


## CONNECT👍

[![LinkedIn Badge](https://img.shields.io/badge/-aliazazalam-blue?style=flat&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/aliazazalam/)](https://www.linkedin.com/in/aliazazalam)
[![Twitter Badge](https://img.shields.io/badge/-aliazaz-gray?style=flat&logo=twitter&logoColor=blue&link=https://twitter.com/AliAzazAlam1)](https://twitter.com/AliAzazAlam1)
[![Medium Badge](https://img.shields.io/badge/-aliazazalam-black?style=flat&logo=Medium&logoColor=white&link=https://medium.com/@ali.azaz.alam)](https://medium.com/@ali.azaz.alam)
[![Stackoverflow Badge](https://img.shields.io/badge/-aliazaz-gray?style=flat&logo=stackoverflow&logoColor=orange&link=https://stackoverflow.com/story/ali-azaz-alam)](https://stackoverflow.com/story/ali-azaz-alam)



## LICENSE
Distributed under the MIT license. See [LICENSE](https://github.com/AliAzaz/Edittext-Library/blob/master/LICENSE) information.
