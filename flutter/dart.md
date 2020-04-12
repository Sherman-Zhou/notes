# support both dynamic and strict type. (js->dart->java)

## every thing is object

## build-in type:

- int->double->num
- bool
- String : String interpolation

      int howManyCups = 12;
      print('Cups #: ${howManyCups}');
      print('Cups #: $howManyCups');

- List

      var list = [1, 2, 3];

- Map
- Set

      var halogens = {'fluorine', 'chlorine', 'bromine', 'iodine', 'astatine'};
      var names = <String>{};

## variable:(type inference)

     var i = 1;
     int j = 2;

## Uniq Operator:

### ~/: divide and returning an integer

### ?? null test

      String playerName(String name) => name ?? 'Guest';

### Type test operators

- as: `import 'travelpoints .dart' as travel;`
- is: `if (points is Places)`
- is!: `if (points is! Places)`

### Assignment operators

- ??=:

  Assigns value only if variable being assigned to has a value of null

  Null ??= 3 = 3
  7 ??= 3 = 7

```
Conditional member access
 // If p is non-null, set its y value to 4.
 p?.y = 4;

```

### type test: as, is, and is!

      int myNumber = 13;
      bool isTrue = true;
      print(myNumber is int);
      print(myNumber is! int);
      print(myNumber is! bool);
      print(myNumber is bool);

### spread and non-aware spread

- ...
- ...?
- collection if and for

      var promoActive = true;
      var nav = [
          'Home',
          'Furniture',
          'Plants',
          if (promoActive) 'Outlet'
      ];


      var listOfInts = [1, 2, 3];
      var listOfStrings = [
          '#0',
          for (var i in listOfInts) '#$i'
      ];
      assert(listOfStrings[1] == '#1');

### Cascade notation (..)

- **_.._**

  The cascade notation is represented by double dots (..) and allows you to make a sequence of operations on the same object.

  `Matrix4.identity() ..scale(1.0, 1.0) ..translate(30, 30);`

## function

- array function:

  `void main() => runApp(new MyApp());`

- optional parameter:

  - Positional

        String say(String from, String msg, [String device]) {
          var result = '$from says $msg';
          if (device != null) {
               result = '$result with a $device';
          }
          return result;
        }

  - named(default to optional)

          const Scrollbar({Key key, @required Widget child})

- default

       /// Sets the [bold] and [hidden] flags ...
       void enableFlags({bool bold = false, bool hidden = false}) {...}

       // bold will be true; hidden will be false.
       enableFlags(bold: true);

       String say(String from, String msg,   [String device = 'carrier pigeon', String mood]) {
          var result = '$from says $msg';
          if (device != null) {
               result = '$result with a $device';
          }
          if (mood != null) {
               result = '$result (in a $mood mood)';
          }
          return result;
       }

       assert(say('Bob', 'Howdy') == 'Bob says Howdy with a carrier pigeon');

## import package

```
// Import the material package
import 'package:flutter/material.dart';
// Import external class
import 'charts.dart';
// Import external class in a different folder
import 'services/charts_api.dart';
// Import external class with package: directive
import 'package:project_name/services/charts_api.dart';
```

## class (**_no need new_**)

     class Fruit {
          String type;
          // Constructor - Same name as class
          Fruit(this.type);
     }
     Fruit apple = Fruit('apple');

- named parameter:

  class Fruit {
  String type;
  // Constructor - Same name as class
  Fruit({this.type})
  }
  Fruit apple = Fruit(type: 'Apple');.

- required parameter:

  // Required parameter
  Fruit({@required this.type});
  // Constructor - With optional parameter name at init
  Fruit({this.type});

- **_assert_**: The assert statement throws an error during development (debug) mode and has no effect in production code (release).

  // Constructor - Required parameter plus assert
  class Fruit {
  String type;
  Fruit({@required this.type}) : assert(type != null);
  }
  // Call the Fruit class
  Fruit fruit = Fruit(type: 'Apple');
  print('fruit.type: \${fruit.type}');

- named constructor

  // Class with additional named constructor
  class BaristaNamedConstructor {
  String name;
  int experience;

          // Constructor - Named parameters { }
          BaristaNamedConstructor({this.name, this.experience});

          // Named constructor - baristaDetails - With named parameters
          BaristaNamedConstructor.baristaDetails({this.name, this.experience});

  }
  BaristaNamedConstructor barista = BaristaNamedConstructor.baristaDetails(name:'Sandy', experience: 10);
  print('barista.name: ${barista.name} - barista.experience: ${barista.experience}');
  // barista.name: Sandy - barista.experience: 10

- Class Inheritance: **_Constructors are not inherited in the subclass._**

          // Class inheritance
          class BaristaInheritance extends BaristaNamedConstructor {
          int yearsOnTheJob;
          BaristaInheritance({this.yearsOnTheJob}) : super();
          }
          // Init Inherited Class
          BaristaInheritance baristaInheritance = BaristaInheritance(yearsOnTheJob: 7);
          // Assign Parent Class variable
          baristaInheritance.name = 'Sandy';
          print('baristaInheritance.yearsOnTheJob: ${baristaInheritance.yearsOnTheJob}');
               print('baristaInheritance.name: ${baristaInheritance.name}');

- Class Mixins

      abstract class Musical {
          bool canPlayPiano = false;
          bool canCompose = false;
          bool canConduct = false;

          void entertainMe() {
               if (canPlayPiano) {
                    print('Playing piano');
               } else if (canConduct) {
                    print('Waving hands');
               } else {
                    print('Humming to self');
               }
          }

      }
      class Musician extends Performer with Musical {
      // ...
      }

      class Maestro extends Person with Musical,
          Aggressive, Demented {

          Maestro(String maestroName) {
               name = maestroName;
               canConduct = true;
          }

      }

* Exception

  - throw Exception:

        throw new FormatException('Expected at least 1 section');

  - throw anything

        throw 'Out of llamas!';

- catch

       try {
            breedMoreLlamas();
       } on OutOfLlamasException {
            // A specific exception
            buyMoreLlamas();
       } on Exception catch (e) {
            // Anything else that is an exception
            print('Unknown exception: $e');
       } catch (e, s) {
            print('Exception details:\n $e');
            print('Stack trace:\n $s');
       }

- rethrow

      void misbehave() {
          try {
               foo = "1";
          } catch (e) {
               print('2');
               rethrow;// 如果不重新抛出异常，main函数中的catch语句执行不到
          }

  }

  #:Flutter 常见错误：

  ## 错误：Waiting for another flutter command to release the startup lock...

  解决方式：

1. 先打开任务管理器，结束掉所有 dart.exe。
2. 然后打开 flutter 安装目录文件夹，找到\bin\cache 中的 lockfile 文件删除。
3. 配置环境变量：PUB_HOSTED_URL 和 FLUTTER_STORAGE_BASE_URL。


    PUB_HOSTED_URL 的值是 https://pub.flutter-io.cn
    FLUTTER_STORAGE_BASE_URL 的值是 https://storage.flutter-io.cn

4. 在项目根目录下，用 powershell 执行 flutter packages get，只能是 powershell，不支持其他终端。
5. 重启 idea。
