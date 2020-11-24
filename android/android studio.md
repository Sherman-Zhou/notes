编译错误乱码；
Help—>Edit Custom VM Options: 
-Dfile.encoding=UTF-8

- build types: 
1. debug
1. release

```
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
```

- Flavoirs

1. flavors
2. variants
```
productFlavors {
    free {
        applicationId "com.example.flavors.free"
    }
    paid {
        applicationId "com.example.flavors.free"
    }
}
```