1. install

```
    const routes = [
    { path: '/', component: ProductList },
    { path: '/cart', component: Cart },
    { path: '/products/:productId', name: 'viewProduct', component: ViewProduct },
    { path: '*', component: NotFound },
    ];

    Vue.use(VueRouter);
    const router = new VueRouter({
    mode: 'history',
    routes,
    });

    new Vue({
    render: (h) => h(App),
    router,
    }).$mount('#app');

```

2. router-view

3. router-link

```
    <router-link  tag="h4"
                :to="{ name: 'viewProduct', params: { productId: product.id } }"
                class="group inner list-group-item-heading">
        <a>{{ product.name }}</a>
    </router-link>

    <router-link to="/" exact active-class="active" tag="li">
        <a>Products</a>
    </router-link>

```

4. route parameter

```
    //old maner
    created() {
        let productId = this.$route.params.productId;

    },

    //new maner
    {
        path: '/products/:productId',
        name: 'viewProduct',
        props: true,
        component: ViewProduct,
    }

    props: {
        productId: {
        required: true,
        },
    },

    //React to parameter change
    watch: {
        productId(newValue, oldValue) {
        this.product = this.getProduct(newValue);
        },
    },
```

5. navigating programmatically

```
    this.$router.push('/')
    //or
    this.$router.replace('/')

    // name route
    this.$router.push({
        name: 'viewProduct',
        params: {
            productId: product.id
        },
        callback: ()=> {console.log('routed..')}
    })
    // go back
    this.$router.go(-2)

```

6. redirect

```
    { path: '/products/:productId/view', redirect: '/products/:productId' },
    // name
    { path: '/products/:productId/view', redirect: {name: 'viewProduct'} },
```

7. alias

```
      { path: '/cart', component: Cart, alias: '/shopping-cart' },
```

8. nested route (children)

```
  {
    path: '/products/:productId',
    name: 'product',
    props: true,
    component: Product,
    children: [
        {
            path: 'details',
            name: 'viewProduct',
            props: true,
            component: ViewProduct
        },
        {
            path: 'reviews',
            name: 'productReviews',
            props: true, component:
            ProductReviews
        }
    ] },
```

9. Query Parameter

```
 this.$router.push({
        name: 'viewProduct',
        params: {
            productId: product.id
        },
        query: {
            discount: 0.9
        }}
    })

    // get the query paramter from another component
    this.$route.query.discount

   //watch the query paramter
    this.$watch('$route.query.discount', (newVal, oldVal){

    })
```

10. Hash Fragment

```
const router = new VueRouter({
    routes: routes,
    mode: 'history',
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return {
                selector: to.hash
            };
        }
        if (savedPosition) {
            return savedPosition;
        }
        return { x: 0, y: 0 };
    }
});

```

11. route guards(守护)

```
    //Global, meta data based.
    router.beforeEach((to, from, next) => {
        if (to.matched.some(record => record.meta.isAuthRequired)) {
            if (!authService.isLoggedIn) {
                alert("You must be logged in!");
                return next(false);
            }
        }

        next();
    });

    router.afterEach((to, from) => {
        //alert("You just navigated somewhere!");
    });

    //Route level

    {
        path: '/user/profile',
        name: 'viewProfile',
        component: ViewProfile,
        beforeEnter(to, from, next) => {
            //call next(false) to cancel
            next();
        }
    },

    //Component level

   export default {
       beforeRouteEnter(to, from, next) => {
            //call next(false) to cancel
            next();
        },

        beforeRouteUpdate(to, from, next) {
            this.discount = this.getDiscount(this.product.price, to.query.discount);
            this.product = this.getProduct(to.params.productId);

            next();
        },

        beforeRouteLeave(to, from, next) => {
            console.log('before leave')
        }
   }
```

12. lazy load

```
    {
        path: 'page',
        component: () => import('@/views/permission/page'),
        name: 'PagePermission',
        meta: {
          title: 'Page Permission',
          roles: ['admin'] // or you can only set roles in sub nav
        }
      },
```
