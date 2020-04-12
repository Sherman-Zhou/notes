# attribute: 
 - th:text
 - th:utext: unescaped text
 - th:ref
 - th:object
 - th:src
 - th:insert
 - th:replace
 - th:class
 - th:with
 ```
   <div th:with="isEven=(${prodStat.count} % 2 == 0)"> Note
 ```
 - th:attr: scarcely used
```
<input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
<img src="../../images/gtvglogo.png" th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```
- th:alt-title
- th:lang-xmllang will set lang and xml:lang
- th:attrappend and th:attrprepend
- th:classappend and th:styleappend

# Iteration basics
- th:each
```
<tr th:each="prod : ${prods}">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>

<tr th:each="prod,iterStat : ${prods}" th:class="${iterStat.odd}? 'odd'">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>

<tr th:each="prod : ${prods}" th:class="${prodStat.odd}? 'odd'">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>

```

# Conditional Evaluation
## th:if/th:unless
     <a href="comments.html"
     th:href="@{/product/comments(prodId=${prod.id})}"
     th:if="${not #lists.isEmpty(prod.comments)}">view</a>

# Switch statements
## th:switch
    <div th:switch="${user.role}">
        <p th:case="'admin'">User is an administrator</p>
        <p th:case="#{roles.manager}">User is a manager</p>
    </div>

# Including template fragments

- th:fragment
- th:insert: ~{templatename::selector} ~{templatename}
- th:replace
- th:include:
```
<div th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</div>
```
```
<div th:insert="~{footer :: copy}"></div>
<div th:insert="footer :: (${user.isAdmin}? #{footer.admin} : #{footer.normaluser})">
</div>
```

# Referencing fragments without th:fragment
```
...
<div id="copy-section">
    &copy; 2011 The Good Thymes Virtual Grocery
</div>
...

 <div th:insert="~{footer :: #copy-section}"></div>
 
```

# Removing template fragments
## th:remove="option"
- all : Remove both the containing tag and all its children.
- body : Do not remove the containing tag, but remove all its children.
- tag : Remove the containing tag, but do not remove its children.
- all-but-first : Remove all children of the containing tag except the first one.
- none : Do nothing. This value is useful for dynamic evaluation.
 

# Simple expressions:
- Variable Expressions: ${...}
- Selection Variable Expressions: *{...}
    ```
     <div th:object="${session.user}">
        <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
        <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
        <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
    ```


- Message Expressions: #{...}

    th:utext="#{home.welcome(${session.user.name})}"

- Link URL Expressions: @{...}
- Fragment Expressions: ~{...}

# Literals:
- Text literals: 'one text' , 'Another one!' ,…
- Number literals: 0 , 34 , 3.0 , 12.3 ,…
- Boolean literals: true , false
- Null literal: null
- Literal tokens: one , sometext , main ,…

# Text operations:
- String concatenation: +
- Literal substitutions: |The name is ${name}|

```
     <span th:text="|Welcome to our application, ${user.name}!|">
     <span th:text="'Welcome to our application, ' + ${user.name} + '!'">
     <span th:text="${onevar} + ' ' + |${twovar}, ${threevar}|">
```


# Arithmetic operations:
- Binary operators: + , - , * , / , %
- Minus sign (unary operator): -
# Boolean operations:
- Binary operators: and , or
- Boolean negation (unary operator): ! , not
# Comparisons and equality:
- Comparators: > , < , >= , <= ( gt , lt , ge , le )
- Equality operators: == , != ( eq , ne )
# Conditional operators:
- If-then: (if) ? (then)
-  If-then-else: (if) ? (then) : (else)
- Default: (value) ?: (defaultvalue)
# Special tokens: 
- No-Operation: _
```
<span th:text="${user.name} ?: 'no user authenticated'">...</span>
<span th:text="${user.name} ?: _">no user authenticated</span>
```

# Expression Basic Objects

    #ctx : the context object.
    #vars: the context variables.
    #locale : the context locale.
    #request : (only in Web Contexts) the HttpServletRequest object.
    #response : (only in Web Contexts) the HttpServletResponse object.
    #session : (only in Web Contexts) the HttpSession object.
    #servletContext : (only in Web Contexts) the ServletContext object.

    Established locale country: <span th:text="${#locale.country}">US</span>.

# Expression Utility Objects
    #execInfo : information about the template being processed.
    #messages : methods for obtaining externalized messages inside variables expressions, in the same way as they   would be obtained using #…} syntax.
    #uris : methods for escaping parts of URLs/URIs 
    #conversions : methods for executing the configured conversion service (if any).
    #dates : methods for java.util.Date objects: formatting, component extraction, etc.
    #calendars : analogous to #dates , but for java.util.Calendar objects.
    #numbers : methods for formatting numeric objects.
    #strings : methods for String objects: contains, startsWith, prepending/appending, etc.
    #objects : methods for objects in general.
    #bools : methods for boolean evaluation.
    #arrays : methods for arrays.
    #lists : methods for lists.
    #sets : methods for sets.
    #maps : methods for maps.
    #aggregates : methods for creating aggregates on arrays or collections.
    #ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).

    Today is: <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 May 2011</span>

# comment:
        <!--/* This code will be removed at Thymeleaf parsing time! */-->
        <!--/*-->
        <div>
            you can see me only before Thymeleaf processes me!
        </div>
        <!--*/-->
# inline:
  <p>The message is "[[${msg}]]"</p>


