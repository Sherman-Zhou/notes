# module system
```
// in xxx.js
module.exports.xxx =xxx

//in app.js
const xxx= require('./xxx')

// node.js module wrapper
function(exports, require, module, __filename, __dirname) {
    //your code here
}

```
## build-in modules:

 1. path
 - parse
 - resolve

 2. os
 - freemen
 - totalmen

 3. fs (always use Async)
 - readdir/readdirSync
  ```
    fs.readdir('/', (err, files) ={
        if(err){
            //error handler
        }else {
            //process
        }
    })

  ```
 4. Event
  - EventEmitter 
   ```
    //use directly
    const EventEmitter = require('events')

    const emitter = new EventEmitter();
    emitter.on('eventName', (data) => {
        console.log('eventName:', data)
    })
    emitter.emit('eventName', {id: 1, url: 'url'})

    // extend

    const EventEmitter = require('events') 

    class Logger extends EventEmitter {
        log(message ) {
            console.log(message);
            this.emit('messageLogged', message)
        }        
    }
    const logger = new Logger();
    logger.on('messageLogged', (arg)=> {
        console.log('Listener called', arg);
    })

    logger.log('hello')   
   
   ```
   5. http

# npm
 1. npm init
 2. npm i (npm install) package[@version]
    - devDependencies: npm i -D 
 3. SemVer: Marjor.Minor.Patch
  -  ^: Caret Marjor.x
  -  ~: Tilde Marjor.Minor.x
 4. npm ls --depth=0
 5. npm view lodash [dependencies|versions]
 6. npm outdated
 7. npm update(only minor version)
 8. npm -i npm-check-updates ( ncu -u : update to latest version)
 9. npm un/uninstall
 
# eniroment
 - system env
 ```
 //in window
  set PORT=3001

 //in js
 process.env.PORT
 ```
 - NODE_ENV
 ```
 set NODE_ENV=development
 ```

 # useful packages
  1. nodemon
  2. joi: validation