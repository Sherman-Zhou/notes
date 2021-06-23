#  core
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


            <p *ngIf="serverCreated; else noserver">Server was created, server name is {{serverName}}</p>
            <ng-template #noserver><p>No Server is created</p></ng-template>

            ```

      2. *NgFor 
            ```
            *ngFor ="let post of posts; let i = index"
            ```
      3. ngSwitch
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
     
    - Attribute
        1. ngClass
            ```
            [ngClass] ="{active: currentPage === i}"
            [ngClass] ="[cls1, cls2]"
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

        //NOTE:  the above template will convert to below:
        <ul >
            <ng-template [appTimes]= "5">
             <li>hi There</li>
             <ng-template>
        </ul>


        ```
    - HostListener, Renderer2 & HostBinding
    ```
        @Directive({
            selector: '[appBetterHighlight]'
        })
        export class BetterHighlightDirective implements OnInit {


            @HostBinding('style.backgroundColor') backgroundColor = 'transparent'

            constructor(private elRef: ElementRef, private renderer: Renderer2) { }

            ngOnInit(): void {
            }
            @HostListener('mouseenter') mounseover(eventData: Event) {
                this.backgroundColor = 'red'
                // this.renderer.setStyle(this.elRef.nativeElement, 'background-color', 'red');
            }

            @HostListener('mouseleave') mouseout(eventData: Event) {
                // this.renderer.setStyle(this.elRef.nativeElement, 'background-color', 'blue');
                this.backgroundColor = 'transparent'
            }
        }

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
 - ng-content

    ```
        <ng-content select="header"> </ng-content>
        <ng-content select=".header"> </ng-content>

    ```

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
   - @NgModule:
        - declarations : List components, pipes, directives that are created in this module
        - imports: List of other modues that this module depends on
        - exports: List components, pipes, directives that this module makes availabe to other modules
        - provides: ***OLD*** way of connecting modules and services
        - bootstrap: used by the AppModule only to declare what compoent will be display first
8. Lifecycle Hook

  - ngOnChanges
  - ngOnInit
  - ngDoCheck
  - ngAfterContentInit
  - ngAfterContentChecked
  - ngAfterViewInit
  - ngAfterViewChecked  
  - ngOnDestroy
  
9. Event
    ```
    //child component
    @Output() close = new EventEmitter()
    onCloseClick(){
        this.close.emit()
    }

    //in parent template
    <app-modal *ngIf ="modalOpen" (close)="onClick()"/>

    ```

 10. Form:

- Template driven Form

- Reactive Form
    1.   formGroup 
    2.   formControlName
    3.   formArrayName
    ```
       export class AppComponent implements OnInit {
            genders = ['male', 'female'];
            signupForm: FormGroup;

            ngOnInit(): void {
                this.signupForm = new FormGroup({
                userData: new FormGroup({
                    username: new FormControl(null, Validators.required),
                    email: new FormControl(null, [Validators.required, Validators.email]),
                }),
                gender: new FormControl('male'),
                hobbies: new FormArray([]),
                });
            }

            onSubmit() {
                console.log(this.signupForm);
            }
            onAddHobby() {
                const control = new FormControl(null, Validators.required);
                (<FormArray>this.signupForm.get('hobbies')).push(control)
            }

            get controls() {
                return (<FormArray>this.signupForm.get('hobbies')).controls;
            }
        }


        <form [formGroup]="signupForm" (ngSubmit)="onSubmit()">
        <div formGroupName="userData">
          <div class="form-group">
            <label for="username">Username</label>
            <input
              type="text"
              id="username"
              formControlName="username"
              class="form-control"
            />
             <span
              *ngIf="!signupForm.get('userData.username').valid &&signupForm.get('userData.username').touched"
              class="help-block"
              >Please enter a valid username!</span>
            ... 

             <div formArrayName="hobbies">
          <h4>Your Hobbies</h4>
          <button class="btn btn-default" type="button" (click)="onAddHobby()">Add Hobby</button>

          <div class="form-group" *ngFor="let hobbyControl of controls; let i =index">
            <input type="text" [formControlName]="i"  class="form-control">
          </div>
        </div>

    ```

# Route
   1. create component with route
     
      `ng n c CompName --route`

   2. in RouterModule create RouterRule
        ```
            const routes: Routes = [
                {
                    path: 'elements',
                    component: ElementsHomeComponent
                },
                // catch all router... must put AppRoutingModule at last
                 {
                    path: '**',
                    component: NotFoundComponent,
                },
            ];

            //nested routeL
            const routes: Routes = [
            {
                path: '',
                component: CollectionsHomeComponent,
                children: [
                {
                    path: '',
                    component: BiographyComponent,
                },
                {
                    path: 'companies',
                    component: CompaniesComponent,
                },
                {
                    path: 'partners',
                    component: PartnersComponent,
                },
                ],
            },
            ];
        ```

  3. routerLink

    ```
      <a routerLink="/elements" 
       [routerLinkActiveOptions]="{exact:true}" 
       routerLinkActive="activeClass" >elements</a>
       
    ```
    

  4. lazy load
   ```
    //donot import in the Module, load the modue which wants to lazy load in router module
    {
        path: 'elements',
        loadChildren: () =>
        import('./elements/elements.module')
        .then((m) => m.ElementsModule),
    },
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
5. `ng generate module ModuleName --routing` or `ng g m`
6. `ng generate service serviceName` or `ng g s`