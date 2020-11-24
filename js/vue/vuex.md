1. create store

```
//挂载Vuex
Vue.use(Vuex)

//创建VueX对象
const store = new Vuex.Store({
    state:{
        //存放的键值对就是所要管理的状态
        name:'helloVueX',
         mutations:{
            //es6语法，等同edit:funcion(){...}
            edit1(state){
                state.name = 'jack'
            },
            edit(state,payload){
                state.name = 'jack'
                console.log(payload) // 15或{age:15,sex:'男'}
            }
        },
        getters:{
            nameInfo(state){
                return "姓名:"+state.name
            },
            fullInfo(state,getters){
                return getters.nameInfo+'年龄:'+state.age
            }
        },
        actions: {
            increment (context) {
                context.commit('increment')
            }
        }
    }
})
new Vue({
  el: '#app',

  store,  //store:store 和router一样，将我们创建的Vuex实例挂载到这个vue实例中
  render: h => h(App)
})

```

2. state 存放状态

- mapState 辅助函数

```
    computed: mapState({
        // 箭头函数可使代码更简练
        count: state => state.count,

        // 传字符串参数 'count' 等同于 `state => state.count`
        countAlias: 'count',

        // 为了能够使用 `this` 获取局部状态，必须使用常规函数
        countPlusLocalState (state) {
        return state.count + this.localCount
        }
    })

    //or

    computed: mapState([
        // 映射 this.count 为 store.state.count
        'count'
    ])

    //
    computed: {
        localComputed () { /* ... */ },
        // 使用对象展开运算符将此对象混入到外部对象中
        ...mapState({
            // ...
        })
    }

```

3. mutations state 成员操作

- mapMutations

```
this.$store.commit('edit1')
this.$store.commit({
    type:'edit',
    payload:{
        age:15,
        sex:'男'
    }
})

...mapMutations([
      'increment', // 将 `this.increment()` 映射为 `this.$store.commit('increment')`

      // `mapMutations` 也支持载荷：
      'incrementBy' // 将 `this.incrementBy(amount)` 映射为 `this.$store.commit('incrementBy', amount)`
    ]),
    ...mapMutations({
      add: 'increment' // 将 `this.add()` 映射为 `this.$store.commit('increment')`
    })
```

4. getters 加工 state 成员给外界

- mapGetters 辅助函数

```
this.$store.getters.fullInfo

//
  // ...
  computed: {
  // 使用对象展开运算符将 getter 混入 computed 对象中
    ...mapGetters([
      'doneTodosCount',
      'anotherGetter',
      // ...
    ])
  }

```

5. actions 异步操作

- mapActions

```
// 以载荷形式分发
store.dispatch('incrementAsync', {
  amount: 10
})

// 以对象形式分发
        store.dispatch({
        type: 'incrementAsync',
        amount: 10
        })

        methods: {
            ...mapActions([
            'increment', // 将 `this.increment()` 映射为 `this.$store.dispatch('increment')`

            // `mapActions` 也支持载荷：
            'incrementBy' // 将 `this.incrementBy(amount)` 映射为 `this.$store.dispatch('incrementBy', amount)`
            ]),
            ...mapActions({
            add: 'increment' // 将 `this.add()` 映射为 `this.$store.dispatch('increment')`
            })
        }
```

6. modules 模块化状态管理

```
onst moduleA = {
  state: () => ({ ... }),
  mutations: { ... },
  actions: { ... },
  getters: { ... }
}

const moduleB = {
  state: () => ({ ... }),
  mutations: { ... },
  actions: { ... }
}

const store = new Vuex.Store({
  modules: {
    a: moduleA,
    b: moduleB
  }
})
```
