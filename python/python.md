# primitive data type
## string

```
    str ="abcdefg"
    print(str[0])
    print(str[1,2,3])
    print("number:"+ str(9))
    # f-string
    printf(f"the str= {str}")

    # String format
    "{:.2f}".format(12.3)

 
```

## Interger

## Float

## Boolean: True/False


## Data type convert
- str(num)
- float(str)
- int(str)
 
# Operation
## mathmatic
```
    +, -
    *, /, //, **, %

    +=, -=, *= ....
```
## condition
```
>, <, >=, <=, ==, !=

num = 1
# true
num in [1,2,3]
```

# Control Flow
```
if height > 120: 
    print('free")
elif height < 150:
    print('half')
else:
    print('full')
```

# Loop
## for loop
```
    for item in list_of_items:
        print(item)
    
```
## while loop
```
    num = 0
    while num < 16 :
        n+=1
    else:
        print('>16')
```

# List/dictionary comprehension

```
 numbers = [1,2,3]

 print([n for n in numbers if n % 2 ==0 ])

 new_list = [ n+1 for n in numbers]

 

print([2*n for n in range(1,100)])

#dict
names =['Alex', 'Beth']

students_scores = {item:random.randint(1,100) for item in names}

passed_students = {new_key:new_value for (student, score)in stduents_scores.items() if score > 59}


```

# function:

## build-in
- print()
- input("input value")
- type(val)
- round(8/3, 2)
- len(array), len("abc")
- sum(array)
- max(array)/min(array)
- range(start, end, step)

## define function
```
def myFunc(param):
  print('my func')
  print(f'line 2{param}')

myFunc('1')
myFunc(param='1')

```


# data structures

## list
```
  weekdays =["Monday", "Tuesday", "Wednesday", "Thursday","Friday", "Saturday", "Sunday"]

```

## dictionary

```
 dic = {
     'key': 1,
     'key2': 'val2',
     2: 22,
 }

 dic[3] = 3
```

## tuple

can not be changed
```
 my_tuple = (1,2,3)

```

# build-in module:
1. ramdom


# docstring

```
 def format_name(f_name, l_name):
    """This is document
    line2"""
    return f"{f_name} {l_name}"

```
# scope
1. global
2. function scope

```

// Modifying Global Scope
variable = 10

def my_func():  
    global variable
    variable += 1

```

# class

## magic method
1. `__init__(self, other_prop)`

```


```



# 3rd pacakge: pypi.org

## pandas
## numpy