# three primary categories from  react-router-dom
1. routers, like <BrowserRouter> and <HashRouter>
2. route matchers, like <Route> and <Switch>
3.  navigation, like <Link>, <NavLink>, and <Redirect>

## Router
- <BrowserRouter> :  uses regular URL paths.
   - basename: string
   - getUserConfirmation: func
   - forceRefresh: bool
   - keyLength: number
- <HashRouter>    :  stores the current location in the hash portion of the URL
 
 ### child component: 
   - Switch    
   - Route

 

## Switch
  it searches through its children <Route> elements to find one whose path matches the current URL. When it finds one, it renders that <Route> and ignores all others.

## Route
- exact: bool

### 三种渲染方式:
1. component:
所有路由中指定的组件将被传入以下三个 props:
- match(.params)
match 对象包含以下属性：

    - params -（ object 类型）即路径参数，通过解析URL中动态的部分获得的键值对。
    - isExact - 当为 true 时，整个URL都需要匹配。
    - path -（ string 类型）用来做匹配的路径格式。在需要嵌套 <Route> 的时候用到。
    - url -（ string 类型）URL匹配的部分，在需要嵌套 <Link> 的时候会用到。
 
- location
```
{
  key: 'ac3df4', // 在使用 hashHistory 时，没有 key
  pathname: '/somewhere'
  search: '?some=search-string',
  hash: '#howdy',
  state: {
    [userDefined]: true
  }
}
```
- history
history 对象通常会具有以下属性和方法：

    - length -（ number 类型）指的是 history 堆栈的数量。
    - action -（ string 类型）指的是当前的动作（action），例如 PUSH，REPLACE 以及 POP 。
    - location -（ object类型）是指当前的位置（location），location 会具有如下属性：
    - pathname -（ string 类型）URL路径。
    - search -（ string 类型）URL中的查询字符串（query string）。
    - hash -（ string 类型）URL的 hash 分段。
    - state -（ string 类型）是指 location 中的状态，例如在 push(path, state) 时，state会描述什么时候 location 被放置到堆栈中等信息。这个 state 只会出现在 browser history 和 memory history 的环境里。
    - push(path, [state]) -（ function 类型）在 hisotry 堆栈顶加入一个新的条目。
    - replace(path, [state]) -（ function 类型）替换在 history 堆栈中的当前条目。
    - go(n) -（ function 类型）将 history 对战中的指针向前移动 n 。
    - goBack() -（ function 类型）等同于 go(-1) 。
    - goForward() -（ function 类型）等同于 go(1) 。
    - block(prompt) -（ function 类型）阻止跳转

 
```
<Route path='/p/:id' render={(match)=<h3>当前文章ID:{match.params.id}</h3>)} />
```

2. render
     
        <Route path="/home" render={() => {
            console.log('额外的逻辑');
            return (<div>Home</div>);
        }/> 

3. children

        // 在匹配时，容器的calss是light，<Home />会被渲染
        // 在不匹配时，容器的calss是dark，<About />会被渲染
        <Route path='/home' children={({ match }) => (
            <div className={match ? 'light' : 'dark'}>
            {match ? <Home/>:<About>}
            </div>
        )}/>



## Link 
anchor ( a ) will be rendered in your HTML document
- to
- replace:bool

## <NavLink>
is a special type of <Link> that can style itself as “active” when its to prop matches the current location

- to 可以是字符串或者对象，同Link组件
- exact 布尔类型，完全匹配时才会被附件class和style
- activeStyle Object类型
- activeClassName 字符串类型
- strict: bool类型，当值为 true 时，在确定位置是否与当前 URL 匹配时，将考虑位置 pathname 后的斜线。
 

## <Redirect>
Any time that you want to force navigation, you can render a  Redirect 



# RouteConfigExample: 


```
import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";

// Some folks find value in a centralized route config.
// A route config is just data. React is great at mapping
// data into components, and <Route> is a component.

////////////////////////////////////////////////////////////
// first our route components
const Main = () => <h2>Main</h2>;

const Sandwiches = () => <h2>Sandwiches</h2>;

const Tacos = ({ routes }) => (
  <div>
    <h2>Tacos</h2>
    <ul>
      <li>
        <Link to="/tacos/bus">Bus</Link>
      </li>
      <li>
        <Link to="/tacos/cart">Cart</Link>
      </li>
    </ul>

    {routes.map((route, i) => <RouteWithSubRoutes key={i} {...route} />)}
  </div>
);

const Bus = () => <h3>Bus</h3>;
const Cart = () => <h3>Cart</h3>;

////////////////////////////////////////////////////////////
// then our route config
const routes = [
  {
    path: "/sandwiches",
    component: Sandwiches
  },
  {
    path: "/tacos",
    component: Tacos,
    routes: [
      {
        path: "/tacos/bus",
        component: Bus
      },
      {
        path: "/tacos/cart",
        component: Cart
      }
    ]
  }
];

// wrap <Route> and use this everywhere instead, then when
// sub routes are added to any route it'll work
const RouteWithSubRoutes = route => (
  <Route
    path={route.path}
    render={props => (
      // pass the sub-routes down to keep nesting
      <route.component {...props} routes={route.routes} />
    )}
  />
);

const RouteConfigExample = () => (
  <Router>
    <div>
      <ul>
        <li>
          <Link to="/tacos">Tacos</Link>
        </li>
        <li>
          <Link to="/sandwiches">Sandwiches</Link>
        </li>
      </ul>

      {routes.map((route, i) => <RouteWithSubRoutes key={i} {...route} />)}
    </div>
  </Router>
);

export default RouteConfigExample;