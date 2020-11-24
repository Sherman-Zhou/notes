d#  core
1. event: 

```
    (click)="method()"
    (input)="onChange($event)"
```

2. property bind:

```
    [value]="prop"
    [value]="method()"

    //custom prop 
    //Component
    @Input propName

    //template html
    [propName] =""


```

3. interplation

    ```
        {{prop}} 
        {{method()}}
    ```

4. Directives
    - structural
    
     ***only one *ng can be in one element****
      1. *ngIf
            ```
            *ngIf="flag"
            ```
      2. *NgFor 
            ```
            *ngFor ="let post of posts; let i = index"
            ```
     
    - Attribute
        1. ngClass
            ```
            [ngClass] ="{active: currentPage === i}"
            [ngClass] ="[cls1, cls2]"
            ``` 
        2. ngSwitch
            ```
              <div  [ngSwitch]="currentPage">
                    <div *ngSwitchCase ="0>
                    </div>
                     <div *ngSwitchCase ="1>
                    </div>
                     <div *ngSwitchCase ="2>
                    </div>
                    <div *ngSwitchDefault>
                    </div>
              </div>

            ```
 
    - custom directive
        ```
        // Attribute
        import { Directive, ElementRef } from '@angular/core';

        @Directive({
        selector: '[appClass]'
        })
        export class ClassDirective {

            //@Input() backgroundColor: string

            constructor(private element: ElementRef) {

            }

            @Input() set backgroundColor(color: string) {
                this.element.nativeElement.style.backgroundColor =  color
            }
        }

        //in template
        <li  appClass [backgroundColor] = "'red'">>
            <h1> custom directive
        </li>

        // use alias...
         @Input('appClass') set backgroundColor(color: string) {
            this.element.nativeElement.style.backgroundColor =  color
        }

         //in template
        <li    [appClass] = "'red'">>
            <h1> custom directive
        </li>

        //Structural

        import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';

        @Directive({
        selector: '[appTimes]'
        })
        export class TimesDirective {

            constructor(
                private viewContainer: ViewContainerRef,
                private templateRef: TemplateRef<any>
            ) {

            }

            @Input('appTimes') set render(times: number) {
                this.viewContainer.clear();
                for(let i = 0; i<times; i++) {
                    this.viewContainer.createEmbeddedView(this.templateRef, {
                        index: i
                    });
                }
            }

        }
        // in template
        <ul *appTimes = "5">
            <li>hi There</li>
        </ul>

        ```
5. Pipes

    ```
    // build-in pipe: angular.io/api?type=pipe
    {{name | titlecase}}
    {{someDate | date :'yyyy-MM-dd'}}
    {{amount | currency: 'JPY'}} 
    {{num | number: '1.0-2'}} 
    {{express | json}}

    //custom pipe

    import { Pipe, PipeTransform } from '@angular/core';

    @Pipe({
    name: 'convert'
    })
    export class ConvertPipe implements PipeTransform {

    transform(value: number, ...args: string[]): number {
        return value +20;
    }

    }

    //use together with direct
    <tr *ngIf="(express| pipe:arg) > 10">
    </tr>

    ```
6. element
 - ng-container

7. Module
  - export from module
    ```
        @NgModule({
            declarations: [ElementsHomeComponent],
            imports: [
                CommonModule,
                ElementsRoutingModule
            ],
            exports:[ElementsHomeComponent]
        })
        export class ElementsModule { }
    ```
   - import the moduel
   ```
        @NgModule({
            declarations: [
                AppComponent
            ],
            imports: [
                BrowserModule,
                AppRoutingModule,
                ElementsModule
            ],
            providers: [],
            bootstrap: [AppComponent]
        })
        export class AppModule { }
   ```
# css
```
  //global css
  // add in style.css
   @import 'bootstrap/dist/css/bootstrap.css'

  //select host element:  the root element, i.e <app-root> for AppComponent
  :host {
    display: flex;
  }

```

# Angular-cli
1. `ng new app-name` or    `n n app-name`
2. `ng generate component [module/]component-name` or   `ng g c`
3. `ng generate pipe pipe-name` or `ng g p`
4. `ng generate directive directive-name` or `ng g d`
5. `ng generate module ModuleName --routing` or `n g m`