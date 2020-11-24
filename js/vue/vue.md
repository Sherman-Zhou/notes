l1. Vue 属性：
  - el
  - data
  - methods
  - computed (sync)
  - watch (async, no need return value)
  - filters

2. 指令：
  -  v-bind -> :
  -  v-on  -> @

        modifers: 
        -  stop
        -  once
        -  prevent
        key modifers:
        - enter
        - keycode: i.e. 13
        - space

        modifier keys:
        - shift
        - crtl 
        - meta(window key/cmd)

            
            v-on:click=xxxx($event)
            @click=xxxx($event)
            @click.stop=xxxx($event)
            @keyup.shift.enter="xxx"

  
- v-model
    - modifier
        - trim
        - number
        - lazy
- v-html(danger)
- v-once
- v-if/v-else-if/v-else
- v-show
- v-cloak (need css)
```
css:
   [v-cloak]: {
       display: none
   }
```
- v-for 
    - Update Stragedy
        1. inplace patch without key prop
        2. reorder with key.
```
array:
<li v-for="(item, idx) in items>
  {{idx}} : {{item.title}}
</li>

object:
<li v-for="(value, properName, idx) in person>
 {{idx}} : {{properName}} : {{value}}
</li>

number range: 
<li v-for= "n in 10">{{n}</li>}
```
3. change detection
- array
    1. Vue.set(target, key, value)

4. Computed Properties
 call only when depenecies are changed.
 ```
computed: {
    fullname() {
        return x+y
    }
    xxx: {
        get: function() {

        }
        set： function(newValue) {
            //set the value to vue
        }
    }
     
}
 ```

5. watch
```
watch: {
    prop2watch: function(query) {

    }
}
```

6. Filter
```
filters: {
    uppercase: function(value) {
        if(!value) {
            return '';
        }
        return value.toUpperCase();
    }
}

<h1>{{name | uppercase}}</h1>

//Gobal Filter
Vue.filter('currency', (value)=> {
    return `$${value}`
})

``` 

7. vue instance
 - proxying
 - reactivity
 - Asynchronous Update Queue
```
 vue.nextTick(()=> {
     console.log('updated DOM..')
 })
``` 
- Virtual DOM
- add watch dynamically
```
vm.$watch('person.name', (newVal, oldValue){

}, {deep: true})
```
- refs
```
<button ref ='btn'></button>

this.refs.btn
```

- mount dynamically
 ```vm.$mount('#app')```    

 - inline template

 9. lifecycle
    - beforeCreate
    - created
    - beforeMount
    - mounted
    - beforeUpdate
    - updated
    - destroyed

 10. Component
 data prop must be function, otherwise it will be shared by all component instance
 ```
 //global
 Vue.componenet('cmp',{
     data(){
         return {
             xxx:'xx'
         }
     }
     template: `<h1>{{xxx}}</h1>`
 })

 new Vue({
     el:'#app'  
     components: {
         'cmp-b': {
              data(){
                return {
                    xxx:'xxx'
                }
             }
         }
     }    
 })
 ```
-  css
 ```
 //global:
 <style>
 </style>

//scoped:

 <style scoped>
 </style>

 ```
- props
```
<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>
```
- event
```
// event bus
const eventBus = new Vue();

//one component
eventBus.$emit('eventName', {data:xxx})

//in another component
eventBus.$on('eventName', data=> console.log(data))

```

11. slot

```
//Cmp1
<slot>Default</slot>

<Cmp1>
    <slot>new text</slot>
</Cmp1>

//named slot
<slot name="header"> header</slot>
<slot name="footer"> Footer</slot>

<Comp2>
    <p slot="header"><header>Header</header></p>
    <p slot="footer"><footer>Footer</footer></p>
<Comp2>
```

12. dynamic component

    new lifecycle:
    - activated
    - deactivated
```
export default {
  name: 'HelloWorld',
  data() {
      return {
          compName: 'comp-name'
      }
  }
}
<component  is="compName" ></component>
//keey dynamic componet alive
<keep-live>
    <component  is="compName" ></component>
<keep-alive>
```

13. mixin

merge Stragedy
- lifecycle: called all by the order
- methods: last win
```
export default {
    mixins: [comp1, comp2]
}

//Global Mixin
Vue.mixin({
    created(){
        console.log('created')
    }
})
```

14. Form
- v-model == @input="value=$event.target.value" :value="value"
    - modifers: lazy, trim, number
```
<input type='text' v-model="post.title'>
<textarea   v-model="post.title'></textarea>

<input type='checkbox' v-model="post.title' true-value='Y' false-value='N'>

<select v-model="post.series>
    <option v-for="item in items" :value=item.value>{{item.label}}</option>
</select>

```
